package edu.kh.fit.board.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.service.BoardService;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.trainer.dto.Trainer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
  @GetMapping("/{classNo}")
  public String getBoardByClassNo(
  		@PathVariable("classNo") int classNo,
  		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
  		Model model) {
  	if(classNo == 1) return "board/homeTraining";
  	else if(classNo == 2) return "board/homeGym";
  	else return "error/404";
  }
	
	/**
	 * @param classNo
	 * @param boardNo
	 * @param model
	 * @param ra
	 * @param loginMember
	 * @param loginTrainer
	 * @return
	 * @throws ParseException
	 */
  @GetMapping("{classNo:[0-9]+}/{boardNo:[0-9]+}")
  public String boardDetail(
          @PathVariable("classNo") int classNo,
          @PathVariable("boardNo") int boardNo,
          Model model,
          RedirectAttributes ra,
          @SessionAttribute(value = "memberLogin", required = false) Member memberLogin,
          @SessionAttribute(value = "trainerLogin", required = false) Trainer trainerLogin
  ) throws ParseException {

      // SQL 수행에 필요한 파라미터들 Map으로 묶기
      Map<String, Object> map = new HashMap<>();
      map.put("classNo", classNo);
      map.put("boardNo", boardNo);

      /* 강사가 로그인이 되어있는 경우 */
      if (trainerLogin != null) {
          map.put("trainerNo", trainerLogin.getTrainerNo());
          model.addAttribute("userType", "trainer");
          model.addAttribute("user", trainerLogin);
      }
      /* 회원이 로그인이 되어있는 경우 */
      else if (memberLogin != null) {
          map.put("memberNo", memberLogin.getMemberNo());
          model.addAttribute("userType", "member");
          model.addAttribute("user", memberLogin);
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
}
