package edu.kh.fit.trainer.controller;

import java.util.List;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.trainer.dto.Trainer;
import edu.kh.fit.trainer.service.TrainerService;
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
			@RequestParam("email") String trainerEmail,
			@RequestParam("password")		 String trainerPw,
			Model model,
			RedirectAttributes ra
			) {
		
		Trainer trainerLogin = service.trainerLogin(trainerEmail, trainerPw);
		
		if(trainerLogin == null) { 
			ra.addFlashAttribute("message", "해당하는 정보가 없습니다.");
		}else {

			model.addAttribute("trainerLogin", trainerLogin);
			
		}
		return "redirect:/main"; // 메인페이지 리다이렉트
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
	
	@GetMapping("trainer")
	public String trainerPage() {
		return "trainer/trainer";
	}
	
	/** 비밀번호 확인페이지 이동
	 * @return
	 */
	@GetMapping("checkPw")
	public String trainerPw() {
		return "trainer/trainerCheckPw";
	}
	
	/** 회원 정보 수정 비밀번호 확인
	 * @param trainerLogin
	 * @param trainerPw
	 * @param ra
	 * @return
	 */
	@PostMapping("checkPw")
	public String memberCheckPw(@SessionAttribute("trainerLogin") Trainer trainerLogin,
															@RequestParam("trainerPw") String trainerPw,
															 RedirectAttributes ra) {
		boolean check = service.trainerCheckPw(trainerLogin.getTrainerNo(), trainerPw);
		String path = null;
		String message = null;
		if(check) {
			path = "trainerMyPage";
			message = "성공";
		} else {
			path = "checkPw";
			message = "실패";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}
	/** 강사 마이페이지 바로가기
	 * @param model
	 * @param trainerLogin
	 * @return
	 */
	@GetMapping("trainerMyPage")
	public String myPage(
			Model model, 
			@SessionAttribute("trainerLogin") Trainer trainerLogin
			) {
		model.addAttribute("currentPage", "trainerMyPage");
		
	  model.addAttribute("isLoggedIn", true);
		return "/myPage/trainerMyPage";
	}

	/** 강사 페이지 수정
	 * @param inputTrainer
	 * @param trainerLogin
	 * @param ra
	 * @return
	 */
	@PostMapping("trainerMyPage")
	public String updateInfo(@ModelAttribute Trainer inputTrainer, @SessionAttribute("trainerLogin") Trainer trainerLogin,
			RedirectAttributes ra) {

		int trainerNo = inputTrainer.getTrainerNo();
		inputTrainer.setTrainerNo(trainerNo);

		int result = service.updateTrainer(inputTrainer);

		String message = null;
		if (result > 0) {
			message = "수정이 되었습니다.";
			inputTrainer.setTrainerNickname(inputTrainer.getTrainerNickname());
			inputTrainer.setTrainerTel(inputTrainer.getTrainerTel());
			
		} else
			message = "수정에 실패하였습니다.";

		ra.addFlashAttribute("message", message);

		return "/myPage/trainerMyPage";
	}
	
	@GetMapping("profile")
	public String profile() {
		return "myPage/profile";
	}
	
	/** 프로필 이미지 수정
	 * @param profileImg
	 * @param trainerLogin
	 * @param ra 
	 * @return
	 */
	@PostMapping("profile")
	public String profile(@RequestParam("profileImg") MultipartFile profileImg,
												@SessionAttribute("trainerLogin") Trainer trainerLogin,
												RedirectAttributes ra) {
		
		// 1) login한 회원의 회원 번호 얻어오기
		int trainerNo = trainerLogin.getTrainerNo();
		String filePath = service.profile(profileImg, trainerNo);
		String message = null;
		message = "프로필 이미지가 변경되었습니다.";
		trainerLogin.setProfileImg(filePath);
		ra.addFlashAttribute("message", message);
		
		return "/myPage/profile";
	}

}