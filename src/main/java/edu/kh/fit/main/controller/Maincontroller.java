package edu.kh.fit.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.fit.main.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class Maincontroller {
	private final MainService service;
	
 @GetMapping("login")
 public String login() {
	 return "login/login";
 }
	


}
   private final MainService service;
   
 @GetMapping("login")
 public String login() {
    return "login/login";
 }
   


}
>>>>>>> origin/feature/login-html-css
=======
>>>>>>> 8d9942b495acc98767b51987c665bedaf38a0053
