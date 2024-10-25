package edu.kh.fit.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("member")
/* @RequestMapping("member") */
@SessionAttributes("memberLogin")
public class MemberController {
	@Autowired
	private MemberService service;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
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
			@RequestParam("email")    String memberEmail,
			@RequestParam("password") String memberPw,
			Model model,
			RedirectAttributes ra
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
		
		return "redirect:/main";
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
		
		/** 이메일 중복 검사(비동기)
		 * @param email : 입력된 이메일
		 * @return 0 : 중복X / 1: 중복
		 */
		@ResponseBody // 반환 값을 응답 본문(ajax 코드)로 반환
		@GetMapping("emailCheck")
		public int emailCheck(
				@RequestParam("email") String email
				) {
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
		
		
		/** 비밀번호 확인
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
																@RequestParam("memberPw") String memberPw,
																 RedirectAttributes ra) {
			boolean check = service.memberCheckPw(memberLogin.getMemberNo(), memberPw);
			String path = null;
			String message = null;
			if(check) {
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
		public String myPage(
				Model model, 
				@SessionAttribute("memberLogin") Member memberLogin
				) {
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
		public String updateInfo(
				@ModelAttribute Member inputMember, 
				@SessionAttribute("memberLogin") Member memberLogin,
				RedirectAttributes ra) {

			int memberNo = memberLogin.getMemberNo();
			inputMember.setMemberNo(memberNo);

			int result = service.updateMember(inputMember);

			String message = null;
			if (result > 0) {
				message = "수정이 되었습니다.";
				memberLogin.setMemberName(inputMember.getMemberName());
				memberLogin.setMemberTel(inputMember.getMemberTel());
			} else message = "수정에 실패하였습니다.";

			ra.addFlashAttribute("message", message);

			return "redirect:/member/memberMyPage";
		}
		
		/* 내 강의 관리 바로가기 */
		@GetMapping("")
		public String memberClassList(@SessionAttribute("memberLigin") Member memberLogin,
																	@RequestParam("paymentBoard") Payment paymentBoard,
																	@RequestParam("trainerNo") Trainer trainerNo,
																	@RequestParam("title") Board title,
																	@RequestParam("detail") Board detaile,
																	RedirectAttributes ra) {
			
			
			
			return "/classList/memberClassList";
		}
		
//		/* 내 활동 내역 바로가기 */
//		@GetMapping("")
//		public String memberMyActivities() {
//			return "/myPage/memberMyActivities";
//		}
}
