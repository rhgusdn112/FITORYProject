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
<<<<<<< HEAD
=======

>>>>>>> bf5ba2e6531d460967d1ccba2f6f3cc9d7bf8768
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
<<<<<<< HEAD
   private final MainService service;
   
 @GetMapping("login")
 public String login() {
    return "login/login";
 }
   
=======
>>>>>>> bf5ba2e6531d460967d1ccba2f6f3cc9d7bf8768

