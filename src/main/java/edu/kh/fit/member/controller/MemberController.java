package edu.kh.fit.member.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.admin.dto.Query;
import edu.kh.fit.authkey.email.service.EmailService;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import edu.kh.fit.payment.dto.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Slf4j
@Controller
@RequestMapping("member")
/* @RequestMapping("member") */
@SessionAttributes({ "memberLogin" })
public class MemberController {
	@Autowired
	private MemberService service;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	public EmailService emailService;


	/** (회원) 로그인 서비스
	 * @param memberEmail
	 * @param memberPw
	 * @param model
	 * @param ra
	 * @param session
	 * @return 이전 페이지 또는 메인 페이지로 이동
	 */
	@PostMapping("login")
	public String memberLogin(@RequestParam("email") String memberEmail, 
	                          @RequestParam("password") String memberPw,
	                          Model model, RedirectAttributes ra, 
	                          HttpSession session) {

	    Member memberLogin = service.memberLogin(memberEmail, memberPw);

	    if (memberLogin == null) {
	        ra.addFlashAttribute("message", "아이디 혹은 패스워드가 일치하지 않습니다.");
	        return "redirect:/login";  // 로그인 실패 시 /login으로 리다이렉트
	    } else {
	        model.addAttribute("memberLogin", memberLogin);

	        // 로그인 성공 시 이전 페이지로 리다이렉트
	        String redirectUrl = (String) session.getAttribute("prevPage");
	        session.removeAttribute("prevPage");  // 이전 페이지 정보 제거
	        return redirectUrl != null ? "redirect:" + redirectUrl : "redirect:/main";  // 이전 페이지 또는 메인 페이지로 이동
	    }
	}


	/** 로그아웃
	 * @param status
	 * @param session
	 * @param request
	 * @return referer가 없으면 기본적으로 메인 페이지로 이동
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status, HttpSession session, HttpServletRequest request) {
	    // 세션에 저장된 로그인 정보 제거
	    status.setComplete();
	    
	    // 이전 페이지 URL(prevPage)도 세션에서 제거
	    session.removeAttribute("prevPage");

	    // 로그아웃 요청이 발생한 페이지로 리다이렉트
	    String referer = request.getHeader("Referer");
	    return referer != null ? "redirect:" + referer : "redirect:/main";  // referer가 없으면 기본적으로 메인 페이지로 이동
	}


	/**
	 * 회원가입
	 * 
	 * @param inputMember
	 * @param ra
	 * @return
	 */
	@PostMapping("signUp")
	public String signUp(@ModelAttribute Member inputMember,
												RedirectAttributes ra) {
		
		int result = service.signUp(inputMember);

		String message = null;
		String path = null;

		if (result > 0) {
			path = "/main";
			message = inputMember.getMemberName() + "님의 가입을 환영합니다";
		} else {
			path = "member/signUp";
			message = "회원 가입 실패...";
		}

		ra.addFlashAttribute("message", message);

		return "redirect:" + path;
	}

	/**
	 * 이메일 중복 검사(비동기)
	 * 
	 * @param email : 입력된 이메일
	 * @return 0 : 중복X / 1: 중복
	 */
	@ResponseBody // 반환 값을 응답 본문(ajax 코드)로 반환
	@GetMapping("emailCheck")
	public int emailCheck(@RequestParam("email") String email) {
		return service.emailCheck(email);
	}

//		/** 전화번호 검사(비동기)
//		 * @param tel
//		 * @return
//		 */
//		@ResponseBody
//		@GetMapping("telCheck")
//		public int telcheck(
//				@RequestParam("tel") String tel) {
//			return service.telCheck(tel);
//		}

	/**
	 * 비밀번호 확인
	 * 
	 * @return
	 */
	@GetMapping("checkPw")
	public String memberPw() {
		return "member/memberCheckPw";
	}

	/** 회원 정보 수정 비밀번호 확인
	 * @param memberLogin
	 * @param memberPw
	 * @param ra
	 * @return
	 */
	@PostMapping("checkPw")
	public String memberCheckPw(@SessionAttribute("memberLogin") Member memberLogin,
			@RequestParam("memberPw") String memberPw, RedirectAttributes ra) {
		boolean check = service.memberCheckPw(memberLogin.getMemberNo(), memberPw);
		String path = null;
		String message = null;
		if (check) {
			path = "memberMyPage";
			message = "성공";
		} else {
			path = "checkPw";
			message = "실패";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}

	/* 마이페이지 이동 */
	@GetMapping("memberMyPage")
	public String myPage(Model model, @SessionAttribute("memberLogin") Member memberLogin) {
		model.addAttribute("currentPage", "memberMyPage");

		model.addAttribute("isLoggedIn", true);
		return "/myPage/memberMyPage";
	}

	/** 회원 정보 수정
	 * @param loginMember
	 * @param updateMember : memberTel, memberName
	 * @param ra
	 * @return
	 */
	@PostMapping("memberMyPage")
	public String updateInfo(@ModelAttribute Member inputMember, @SessionAttribute("memberLogin") Member memberLogin,
			RedirectAttributes ra) {

		int memberNo = memberLogin.getMemberNo();
		inputMember.setMemberNo(memberNo);

		int result = service.updateMember(inputMember);

		String message = null;
		if (result > 0) {
			message = "수정이 되었습니다.";
			memberLogin.setMemberName(inputMember.getMemberName());
			memberLogin.setMemberTel(inputMember.getMemberTel());
		} else
			message = "수정에 실패하였습니다.";

		ra.addFlashAttribute("message", message);

		return "redirect:/member/memberMyPage";
	}

	/** 회원 결제 강의 목록 조회
	 * @param memberLogin
	 * @param cp
	 * @param model
	 * @return
	 */
	@GetMapping("memberClassList")
	public String memberClassList(@SessionAttribute("memberLogin") Member memberLogin,
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp, Model model) {

		// 내 강의 조회 서비스
		int memberNo = memberLogin.getMemberNo();
		Map<String, Object> map = service.classList(memberNo, cp);
		
		model.addAttribute("orderList", (List<Order>)map.get("orderList"));
		model.addAttribute("pagination", (Pagination)map.get("pagination"));
		model.addAttribute("currentPage", "memberMyPage");

		model.addAttribute("isLoggedIn", true);

		return "classList/memberClassList";
	}
	
	/** 내 활동 내역
	 * @param memberLogin
	 * @param model
	 * @param cp
	 * @return
	 */
	@GetMapping("memberMyActivities")
	public String memberMyActivities(@SessionAttribute("memberLogin") Member memberLogin, Model model,
			@RequestParam(value = "cp", required = false, defaultValue = "1") int cp) {
		Map<String, Object> map = service.memberMyActivities(memberLogin.getMemberNo(), cp);
		
		model.addAttribute("reviewList", (List<Comment>)map.get("reviewList"));
		model.addAttribute("pagination", (Pagination)map.get("pagination"));
		model.addAttribute("currentPage", "memberMyPage");

		model.addAttribute("isLoggedIn", true);

		return "myPage/memberMyActivities";
	}
	
	
	@GetMapping("statusChange")
	public String statusChange() {
		return "member/statusChange";
	}
	
	/** 회원 탈퇴
	 * @param memberLogin
	 * @return
	 */
	@PostMapping("statusChange")
	public String statusChange(
			@SessionAttribute("memberLogin") Member memberLogin,
			SessionStatus status,
			RedirectAttributes ra) {
		
		String message = null;
		String path = null;
		
		int memberNo = memberLogin.getMemberNo();
		
		int result = service.statusChange(memberNo);
		if(result > 0) {
			message = "탈퇴 되었습니다.";
			path    = "/main";
			status.setComplete(); // 세션만료 -> 로그아웃			
		}else {
			message = "비밀번호가 일치하지 않습니다.";
			path    = "statusChange";
		}
		
		ra.addFlashAttribute("message", message);

		return "redirect:" + path;
	}

	@PostMapping("findPw")
	public int findPw(@RequestBody String email) {
		
		String password = service.findPw(email);
		
		int result = 0;
		if(password != null) {
			result = emailService.sendEmail("sendPw", email, password);
		}
		
		return result;
	}
	
	@ResponseBody
	@PutMapping("afterFindPW")
	public String afterFindPW(
			@RequestBody String password,
			RedirectAttributes ra
			) {
		
		int result = service.updatePw(password);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "비밀번호 변경완료";
			path = "/main";
		}else {
			message = "비밀번호 변경을 실패하였습니다.";
			path = "findPw";
		}
		
		return "redirect:" + path;
	}
	
	@GetMapping("query")
	public String query() {
		return "myPage/query";
	}

}

