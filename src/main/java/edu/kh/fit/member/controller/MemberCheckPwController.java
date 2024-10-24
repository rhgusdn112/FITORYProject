package edu.kh.fit.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberCheckPwService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member")
public class MemberCheckPwController {
	@Autowired
	private MemberCheckPwService service;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("checkPw")
	public String myPage() {
		return "member/memberCheckPw";
	}
	
	@PostMapping("checkPw")
	public String memberCheckPw(@SessionAttribute("memberLogin") Member memberLogin,
															@RequestParam("memberPw") String memberPw,
															@RequestParam("memberNo") int memberNo,
															 RedirectAttributes ra) {
		int result = memberLogin.getMemberNo();
		String password = service.memberCheckPw(memberPw);
		String path = null;
		String message = null;
		if(encoder.matches(password, memberPw)) {
			path = "myPage/memberMyPage";
			message = "성공";
		} else {
			path = "redirect:/";
			message = "실패";
		}
		ra.addAttribute("message", message);
		return "redirect:" + path;
	}
	
}
