package edu.kh.fit.board.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.board.dto.Board;
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
		@SessionAttribute(value="memberLogin", 
		 required=false) Member memberLogin,
		@SessionAttribute(value="trainerLogin", 
		required=false) Trainer trainerLogin
		) throws ParseException {
		
		// SQL 수행에 필요한 파라미터들 Map으로 묶기
		Map<String, Object> map = new HashMap<>();
		map.put("classNo", classNo);
		map.put("boardNo", boardNo);
		
		/* 강사가 로그인이 되어있는 경우
		 * trainerNo를 map에 추가
		 * 로그인된 트레이너 정보와 함께 'trainer' 타입을 전달
		 */
		if(trainerLogin != null) {
			map.put("trainerNo", trainerLogin.getTrainerNo());
		  model.addAttribute("userType", "trainer");
      model.addAttribute("user", trainerLogin);
		}
		/* 회원이 로그인이 되어있는 경우
		 * memberNo를 map에 추가 
		 * 로그인된 회원 정보와 함께 'member' 타입을 전달
		 */
		else if(memberLogin != null) {
			map.put("memberNo", memberLogin.getMemberNo());
		  model.addAttribute("userType", "member");
      model.addAttribute("user", memberLogin);
		}	
		/* 비회원일 경우
		 * userType을 guest로 전달
		 */
		else {
			 model.addAttribute("userType", "guest");
		}
		
	// 2) 서비스 호출 후 결과 반환 받기
			Board board = service.selectDetail(map);
			
		/* 게시글 상세조회 결과가 없을 경우*/
			if(board == null) {
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
			// return "redirect:/board/" + classNo; 추후 변경 임시로 메인페이지 리다이렉트
			return "redirect:/main";			
		}	
		
		model.addAttribute("board", board);
			
		
		return "board/boardDetail";
	}
}
