package edu.kh.fit.trainner.controller;

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

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import edu.kh.fit.trainner.dto.Trainner;
import edu.kh.fit.trainner.service.TrainnerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainner")
@SessionAttributes("trainnerLogin")
public class TrainnerController {
	
	private final TrainnerService service;
	
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
			@RequestParam("trainnerEmail") String trainnerEmail,
			@RequestParam("trainnerPw")		 String trainnerPw,
			@RequestParam(name = "saveEmail", required = false) String saveEmail,
			Model model,
			RedirectAttributes ra,
			HttpServletResponse resp
			) {
		
		Trainner trainnerLogin = service.trainnerLogin(trainnerEmail, trainnerPw);
		
		if(trainnerLogin == null) { 
			ra.addFlashAttribute("message", "해당하는 정보가 없습니다.");
		}else {

			model.addAttribute("trainnerLogin", trainnerLogin);
			
			
			//----------------------------------------------------------------
			/* 이메일 저장 코드(Cookie) */
			Cookie cookie = new Cookie("saveEmail", trainnerEmail);
			
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
			@ModelAttribute Trainner inputTrainner,
			RedirectAttributes ra	) {
		
		int result = service.signUp(inputTrainner);
		
		String message = null;
		String path    = null;
		
		if(result > 0) {
			path = "/";
			message 
				= inputTrainner.getTrainnerNickname() + "님의 가입을 환영합니다";
		} else {
			path = "signUp";
			message = "회원 가입 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
}
