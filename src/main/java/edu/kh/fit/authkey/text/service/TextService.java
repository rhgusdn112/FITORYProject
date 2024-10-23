package edu.kh.fit.authkey.text.service;

import org.springframework.http.ResponseEntity;


public interface TextService {

    
    ResponseEntity<String> sendSms(String to);
}
