package edu.kh.fit.board.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.board.service.BoardService;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

    private final BoardService service;

    @GetMapping("{classNo:[0-9]+}/{boardNo:[0-9]+}")
    public String boardDetail(
            @PathVariable("classNo") int classNo,
            @PathVariable("boardNo") int boardNo,
            Model model,
            RedirectAttributes ra,
            @SessionAttribute(value = "memberLogin", required = false) Member memberLogin,
            @SessionAttribute(value = "trainerLogin", required = false) Trainer trainerLogin,
            @SessionAttribute(value = "adminLogin", required = false) Admin adminLogin
    ) throws ParseException {

        // SQL 수행에 필요한 파라미터들 Map으로 묶기
        Map<String, Object> map = new HashMap<>();
        map.put("classNo", classNo);
        map.put("boardNo", boardNo);

        /* 관리자가 로그인이 되어있는 경우 */
        if (adminLogin != null) {
            model.addAttribute("userType", "admin");
        }
        /* 강사가 로그인이 되어있는 경우 */
        else if (trainerLogin != null) {
            map.put("trainerNo", trainerLogin.getTrainerNo());
            model.addAttribute("userType", "trainer");
            model.addAttribute("user", trainerLogin);
        }
        /* 회원이 로그인이 되어있는 경우 */
        else if (memberLogin != null) {
            map.put("memberNo", memberLogin.getMemberNo());
            model.addAttribute("userType", "member");
            model.addAttribute("user", memberLogin);

            // 사용자가 결제했는지 여부 확인
            boolean hasPaid = service.checkPaymentStatus(memberLogin.getMemberNo(), boardNo);
            model.addAttribute("hasPaid", hasPaid);

            // 결제 상태에 따라 userType 설정
            if (hasPaid) {
                model.addAttribute("userType", "paidMember");
            } else {
                model.addAttribute("userType", "unpaidMember");
            }
        }
        /* 비회원일 경우 */
        else {
            model.addAttribute("userType", "guest");
        }

        // 게시글 상세 조회
        Board board = service.selectDetail(map);

        /* 게시글 상세조회 결과가 없을 경우 */
        if (board == null) {
            ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
            return "redirect:/main";
        }

        model.addAttribute("board", board);

        // 리뷰 목록 가져오기
        List<Comment> reviews = service.selectCommentList(boardNo);
        model.addAttribute("reviews", reviews);

        // 사용자가 해당 게시글에 이미 리뷰를 작성했는지 확인
        boolean hasReviewed = false;
        if (memberLogin != null) {
            Map<String, Object> params = new HashMap<>();
            params.put("memberNo", memberLogin.getMemberNo());
            params.put("boardNo", boardNo);
            hasReviewed = service.hasReviewed(params);
        }
        model.addAttribute("hasReviewed", hasReviewed);

        return "board/boardDetail";
    }







    // ------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------

    /**
     * 게시글 목록 조회
     * @param classNo 
     * <ul>
     * <li>2 : Home Gym</li>
     * <li>3 : Home Training</li>   
     * </ul>
     * @param model
     * @return
     */
    @GetMapping("{classNo:[1-3]+}")
    public String boardList(@PathVariable("classNo") int classNo, Model model) {

        Map<String, Object> map = service.selectBoardMain(classNo);

        List<Board> popularClass = (List<Board>) map.get("popularClass");
        List<Board> recentClass = (List<Board>) map.get("recentClass");
        List<Board> classList = (List<Board>) map.get("classList");
        Pagination pagination = (Pagination) map.get("pagination");
        
        // log.debug("popularClass : {} ", popularClass);
        // log.debug("recentClass : {} ", recentClass);
        // log.debug("classList : {} ", classList);

        model.addAttribute("popularClass", popularClass);
        model.addAttribute("recentClass", recentClass);
        model.addAttribute("classList", classList);
        model.addAttribute("pagination", pagination);   

        String path = null; 
        
        switch (classNo) {
            case 1: path = "notice"; break;
            case 2: path = "homeTraining"; break;
            case 3: path = "homeGym"; break;
        }
        
        return "board/" + path;
    }


    /**
     * 실시간 클래스 비동기 조회(페이지, 정렬)
     * @param classNo
     * @param cp
     * @param sort
     * @return map
     */
    @GetMapping("{classNo:[2-3]+}/list")
    public ResponseEntity<?> selectClassList(
        @PathVariable("classNo") int classNo,
         @RequestParam("cp") int cp, 
         @RequestParam("sort") String sort) {
        return ResponseEntity.ok(service.selectClassList(classNo, cp, sort));
    }
}
