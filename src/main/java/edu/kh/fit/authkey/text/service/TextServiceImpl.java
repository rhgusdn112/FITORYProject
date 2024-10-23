package edu.kh.fit.authkey.text.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.kh.fit.authkey.text.provider.TextProvider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService{
    
    private final TextProvider textProvider;
    
    @Override
    public ResponseEntity<String> sendSms(String to) {

      try {
        
        boolean result = textProvider.sendSms(to);
        
        if(!result) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메세지 전송 실패");
        
        
        
    }catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메세지 전송중 예외 발생");
    }
    
    
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("메세지 전송 성공");

}
    
      
}
