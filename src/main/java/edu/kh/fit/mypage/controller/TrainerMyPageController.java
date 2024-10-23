package edu.kh.fit.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.mypage.service.TrainerMyPageService;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("trainerMyPage")
public class TrainerMyPageController {
	private final TrainerMyPageService service;
	
	@GetMapping("")
	public String myPage() {
		return "myPage/trainerMyPage";
	}
	
	@PostMapping("info")
	public String trainerMyPage(@SessionAttribute("trainerLogin") Trainer trainerLogin, @ModelAttribute Trainer updateTrainer, RedirectAttributes ra) {
		int memberNo = trainerLogin.getTrainerNo();
		updateTrainer.setTrainerNo(memberNo);
		int update = service.trainerLogin(updateTrainer);
		
		String message = null;
		
		if(update > 0) {
		trainerLogin.setTrainerNickname(updateTrainer.getTrainerNickname());
		trainerLogin.setTrainerTel(updateTrainer.getTrainerTel());
		message = "정보가 수정되었습니다.";
		} else {
			message = "정보 수정이 실패하였습니다.";
			return "redirect:info";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:info";
	}
	
	@GetMapping("profile")
	public String profile() {
		return "myPage/trainerMyPage";
	}
	@PostMapping("profile")
	public String profile(@RequestParam("profileImg") MultipartFile profileImg,
												@SessionAttribute("trainerLogin") Trainer trainerLogin,
												RedirectAttributes ra) {
		
		int trainerNo = trainerLogin.getTrainerNo();
		String filePath = service.profile(profileImg,trainerNo);
		String message = null;
		message = "프로필 이미지가 변경되었습니다.";
		trainerLogin.setProfileImg(filePath);
		ra.addFlashAttribute("message", message);
		
		return "redirect:profile";
	}
}
