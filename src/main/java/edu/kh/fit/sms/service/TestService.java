package edu.kh.fit.sms.service;

import org.springframework.http.ResponseEntity;


public interface TestService {

	
	ResponseEntity<String> sendSms(String to);
}
