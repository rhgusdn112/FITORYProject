package edu.kh.fit.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.admin.service.AdminService;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
@SessionAttributes("adminLogin")
public class AdminController {
	
	private final AdminService service;
	
	
	@GetMapping("contents")
	public String Contents() {
		return "admin/contents";
	}
	
  @GetMapping("login")
  public String adminLogin(
  		) {
      return "admin/adminLogin";
  }
  
  @GetMapping("member")
  public String member() {
  	return "admin/member";
  }

  @GetMapping("trainer")
  public String trainer() {
  	return "admin/trainer";
  }
  
  @GetMapping("myPage")
  public String myPage(
  		@SessionAttribute("adminLogin") Admin adminLogin,
  		Model model
  		) {
  	
  	String path = null;
  	String message = null;
  	
  	if(adminLogin != null) {
  			path = "/admin/myPage";
  	}else {
  		path = "redirect:/admin/login";
  		message = "로그인 후 이용해주세요";
  	}
  	
  	model.addAttribute("message", message);
  	
  	return path;
  	
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
		String path = null;
		
		Admin adminLogin = service.adminLogin(adminEmail, adminPw);
		
		if(adminLogin == null) {
			ra.addFlashAttribute("message",
												"아이디 혹은 패스워드가 일치하지 않습니다.");
			return "/admin";
		}else {
			model.addAttribute("adminLogin", adminLogin);
			if(adminLogin.getAuthorityNo() == 1) {
				path = "/admin/member";
			}else 
				{ if( adminLogin.getAuthorityNo() == 2) {
					path = "/admin/trainer";
				}else {
					path = "/admin";
				}
			}
			return "redirect:" + path; 
		}
	}
	
	/**
	 * 비동기로 회원 목록 조회 
	 * @return
	 */
	@GetMapping("memberList")
	@ResponseBody
	public List<Member> memberList(){
		return  service.memberList();
	}
	
	@GetMapping("active")
	public String memberActiveList() {
		return "/admin/memberActive";
	}
		
	/**
	 * 비동기로 회원 목록 조회
	 * @return
	 */
	@ResponseBody
	@GetMapping("trainerList")
	public List<Trainer> trainerList(){
		return  service.trainerList();
	}
}
	
	
	
	
