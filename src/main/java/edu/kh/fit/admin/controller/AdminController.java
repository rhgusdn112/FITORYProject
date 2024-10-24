package edu.kh.fit.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.admin.service.AdminService;
import edu.kh.fit.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AdminService service;
	
	
	@GetMapping("contents")
	public String Contents() {
		return "admin/contents";
	}
	
  @GetMapping("login")
  public String adminLogin() {
      return "admin/adminLogin";
  }
	
	
	/** 관리자 로그인
	 * @param adminEmail
	 * @param adminPw
	 * @param model
	 * @param ra
	 * @return
	 */
	@PostMapping("login")
	public String adminLogin(
			@RequestParam("email")    String adminEmail,
			@RequestParam("password") String adminPw,
			Model model,
			RedirectAttributes ra
			) {
	
		Admin adminLogin = service.adminLogin(adminEmail, adminPw);
		
		if(adminLogin == null) {
			ra.addFlashAttribute("message",
												"아이디 혹은 패스워드가 일치하지 않습니다.");
		}else {
			model.addAttribute("adminLogin", adminLogin);
		}
		return "redirect:/admin"; 
	}
		
}
	
	
	
	
