package edu.kh.fit.trainer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.trainer.dto.Trainer;
import edu.kh.fit.trainer.service.TrainerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainer")
@SessionAttributes("trainerLogin")
public class TrainerController {
	
	private final TrainerService service;
	
	/**
	 * (강사)로그인 서비스
	 * @param trainnerEmail
	 * @param trainnerPw
	 * @param saveEmail
	 * @param model
	 * @param ra
	 * @param resp
	 * @return
	 */
	@PostMapping("login")
	public String trainerLogin(
			@RequestParam("trainerEmail") String trainerEmail,
			@RequestParam("trainerPw")		 String trainerPw,
			@RequestParam(name = "saveEmail", required = false) String saveEmail,
			Model model,
			RedirectAttributes ra,
			HttpServletResponse resp
			) {
		
		Trainer trainerLogin = service.trainerLogin(trainerEmail, trainerPw);
		
		if(trainerLogin == null) { 
			ra.addFlashAttribute("message", "해당하는 정보가 없습니다.");
		}else {

			model.addAttribute("trainerLogin", trainerLogin);
			
			
			//----------------------------------------------------------------
			/* 이메일 저장 코드(Cookie) */
			Cookie cookie = new Cookie("saveEmail", trainerEmail);
			
			cookie.setPath("/"); 
			
			if(saveEmail == null) { 
				cookie.setMaxAge(0); 
			}else { 
				cookie.setMaxAge(60 * 60 * 24 * 30);
			}
		
			resp.addCookie(cookie);
			//----------------------------------------------------------------
		}
		return "redirect:/"; // 메인페이지 리다이렉트
	}

	/** 로그아웃
	 * @return
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:/";
	}
	
	
	
	/** 회원가입
	 * @param inputMember
	 * @param ra
	 * @return
	 */
	@PostMapping("signUp")
	public String signUp(
			@ModelAttribute Trainer inputTrainer,
			RedirectAttributes ra	) {
		
		int result = service.signUp(inputTrainer);
		
		String message = null;
		String path    = null;
		
		if(result > 0) {
			path = "/";
			message 
				= inputTrainer.getTrainerNickname() + "님의 가입을 환영합니다";
		} else {
			path = "signUp";
			message = "회원 가입 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/** NEW TRAINER PROFILE 화면 이동
	 * @return
	 */
	@GetMapping("")
  public String trainerPage() {
      return "trainer/trainer";  // templates/teacher/teacher.html로 이동
  }
}
