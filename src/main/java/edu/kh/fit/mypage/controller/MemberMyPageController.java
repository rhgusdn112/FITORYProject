package edu.kh.fit.mypage.controller;

import java.util.Map;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.mypage.service.MemberMyPageService;
import lombok.RequiredArgsConstructor;

@SessionAttributes({"memberLogin"})
@RequiredArgsConstructor
@Controller
@RequestMapping("memberMyPage")
public class MemberMyPageController {
	private final MemberMyPageService service;
	
	
	@GetMapping("")
	public String myPage() {
		return "/myPage/memberMyPage";
	}
	
	
	/** 회원 정보 수정
	 * @param loginMember
	 * @param updateMember : memberTel, memberName
	 * @param ra
	 * @return
	 */
	@PostMapping("info")
	public String memberMyPage(@SessionAttribute("memberLogin") Member memberLogin, @ModelAttribute Member updateMember, RedirectAttributes ra) {

		int memberNo = memberLogin.getMemberNo();
		updateMember.setMemberNo(memberNo);
		int update = service.memberUpdate(updateMember);
		
		String message = null;
		
		if(update > 0) {
		memberLogin.setMemberName(updateMember.getMemberName());
		memberLogin.setMemberTel(updateMember.getMemberTel());
		message = "정보가 수정되었습니다.";
		} else {
			message = "정보 수정이 실패하였습니다.";
			return "redirect:info";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:info";
	}
	
	
}
