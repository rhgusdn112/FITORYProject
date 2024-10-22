package edu.kh.fit.sms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.fit.sms.service.TestService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("signUp")
public class TestController {
	
	private final TestService testService;
	
	@GetMapping("/send-sms/{to}")
	public ResponseEntity<String> sendSms(
			@RequestParam("memberTel") String to
			){
		ResponseEntity<String> response = testService.sendSms(to);
		
		return response;
	}
	
}
