package edu.kh.fit.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@SessionAttributes("member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService service;
	
	/** (회원) 로그인 서비스
	 * @param memberEmail
	 * @param memberPw
	 * @param saveEmail
	 * @param model
	 * @param ra
	 * @param resp
	 * @return redirect:/ (메인페이지로 리다이렉트)
	 */
	@PostMapping("login")
	public String memberLogin(
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("password")	String memberPw,
			@RequestParam(name = "saveEmail", required = false) String saveEmail,
			Model model,
			RedirectAttributes ra,
			HttpServletResponse resp
			) {
		
		Member memberLogin = service.memberLogin(memberEmail, memberPw);
		
		if(memberLogin == null) {
			ra.addFlashAttribute("message",
												"아이디 혹은 패스워드가 일치하지 않습니다.");
		}else {
			model.addAttribute("memberLogin", memberLogin);
			
		}
		return "redirect:/main"; 
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
			@ModelAttribute Member inputMember,
			RedirectAttributes ra	) {
		
		int result = service.signUp(inputMember);
		
		String message = null;
		String path    = null;
		
		if(result > 0) {
			path = "/";
			message 
				= inputMember.getMemberName() + "님의 가입을 환영합니다";
		} else {
			path = "signUp";
			message = "회원 가입 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
		/** 회원 페이지로 이동
		 * @return
		 */
		@GetMapping("/MyPage")
		public String memberMyPage(
				@SessionAttribute("memberLogin") Member memberLogin
				) {
			
			return "/member/MyPage";
		}
		
		

}
