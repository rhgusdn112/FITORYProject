package edu.kh.fit.authkey.text.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.fit.authkey.text.service.TextService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("signUp")
public class TextController {
    
    private final TextService textService;
    
    @GetMapping("/send-sms/{to}")
    public ResponseEntity<String> sendSms(
            @RequestParam("memberTel") String to
            ){
        ResponseEntity<String> response = textService.sendSms(to);
        
        return response;
    }
    
}
