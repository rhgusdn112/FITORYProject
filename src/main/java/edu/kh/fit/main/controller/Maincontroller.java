package edu.kh.fit.main.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.fit.main.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Maincontroller {

	private final MainService service;
	
	@GetMapping("login")
	public String login() {
	  return "login/login";
 }  
	
	@GetMapping("signUp")
	public String signUp() {
		return "common/signUp";
	}
	

	
	


}

