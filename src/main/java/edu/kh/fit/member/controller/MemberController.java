package edu.kh.fit.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member")
@SessionAttributes("memberLogin")
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
			@RequestParam("memberPw")		 String memberPw,
			@RequestParam(name = "saveEmail", required = false) String saveEmail,
			Model model,
			RedirectAttributes ra,
			HttpServletResponse resp
			) {
		
		Member memberLogin = service.memberLogin(memberEmail, memberPw);
		
		if(memberLogin == null) { // 로그인 실패
			ra.addFlashAttribute("message", "해당하는 정보가 없습니다.");
		}else { // 로그인 성공

			model.addAttribute("memberLogin", memberLogin);
			
			//----------------------------------------------------------------
			/* 이메일 저장 코드(Cookie) */
			
			Cookie cookie = new Cookie("saveEmail", memberEmail);
			
			cookie.setPath("/"); 
			
			if(saveEmail == null) { 
				cookie.setMaxAge(0); 
														  
			}else { 
				cookie.setMaxAge(60 * 60 * 24 * 30); 
			}
		
			resp.addCookie(cookie);
			
			
			//----------------------------------------------------------------
		}
		
		
		return "redirect:/"; 
	}
}
