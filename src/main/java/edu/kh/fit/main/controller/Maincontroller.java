package edu.kh.fit.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.fit.main.service.MainService;
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
	
	@GetMapping("main")
	public String main() {
		return "common/main";
	}
	@GetMapping("login")
 	public String login() {
	 	return "login/login";
 	}
	
 	@GetMapping("signUp")
 	public String signUp() {
 		return "login/signUp";
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
 		return "member/cart";
 	}
 	
 	@GetMapping("admin")
 	public String admin() {
 		return "admin/admin";
 	}
 	
}

