package edu.kh.fit.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.main.service.MainService;
import edu.kh.fit.trainer.dto.Trainer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class Maincontroller {
	private final MainService service;

	@GetMapping("")
	public String index() {
		return "index";
	}

	// @GetMapping("main")
	// public String main() {
	// 	return "common/main";
	// }

	@GetMapping("login")
	public String loginPage(HttpServletRequest request, HttpSession session) {
		// 세션에 이전 페이지가 저장되어 있지 않으면 저장
		if (session.getAttribute("prevPage") == null) {
			String referer = request.getHeader("Referer");
			if (referer != null && !referer.contains("/login")) { // /login 페이지가 아닌 경우에만 저장
				session.setAttribute("prevPage", referer);
			}
		}
		return "login/login"; // 로그인 페이지로 이동
	}

	@GetMapping("signUp")
	public String signUp() {
		return "/login/signUp";
	}

	@GetMapping("find-id")
	public String findId() {
		return "login/findId";
	}

	@GetMapping("find-password")
	public String findPw() {
		return "login/findPw";
	}

	@GetMapping("cart")
	public String cart() {
		return "cart/cart";
	}

	@GetMapping("admin")
	public String admin() {
		return "admin/admin";
	}
	
	@GetMapping("afterFindPw")
	public String afterFindPw() {
		return "login/afterFindPw";
	}


	/**
	 * 메인 페이지 (신버전)
	 * @return
	 */
	@GetMapping("main")
	public String mainPage(Model model) {
		
		Map<String, Object> map = service.mainPage();

		List<Trainer> trainerList = (List<Trainer>) map.get("trainerList");
		List<Board> homeTrainingList = (List<Board>) map.get("homeTrainingList");
		List<Board> homeGymList = (List<Board>) map.get("homeGymList");

		model.addAttribute("trainerList", trainerList);
		model.addAttribute("homeTrainingList", homeTrainingList);
		model.addAttribute("homeGymList", homeGymList);

		return "common/main";
	}
}
