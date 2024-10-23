package edu.kh.fit.authkey.text.provider;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Component
@PropertySource("classpath:/config.properties")
public class TextProvider {
    
    private final DefaultMessageService messageService;
    
    @Value("${coolsms.senderNumber}")
    String sendNumber;
    
        
    public TextProvider (
            @Value("${coolsms.api.key}") String API_KEY,
            @Value("${coolsms.api.secret}") String SECRET_KEY,
            @Value("${coolsms.domain}") String domain
            ) {
        
        this.messageService = NurigoApp.INSTANCE.initialize(API_KEY, SECRET_KEY, domain);
    }
    
    public boolean sendSms(String to) {
    	
    		String authKey = createAuthKey();
    	
        Message message = new Message();
        message.setFrom(sendNumber);
        message.setTo(to);
        message.setText(
        		"인증번호 : " + createAuthKey() );
        
        SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
        
        String statusCode = response.getStatusCode();
        boolean result = statusCode.equals("2000");
        
        return result;
    }
    
    
    
    
    /** 인증번호 생성 (영어 대문자 + 소문자 + 숫자 6자리)
     * @return authKey
     */
    public String createAuthKey() {
    	String key = "";
      for(int i=0 ; i< 6 ; i++) {
          
        int sel1 = (int)(Math.random() * 3); // 0:숫자 / 1,2:영어
        
        if(sel1 == 0) {
          int num = (int)(Math.random() * 10); // 0~9
          key += num;
        }else {
          
        	char ch = (char)(Math.random() * 26 + 65); // A~Z
          int sel2 = (int)(Math.random() * 2); // 0:소문자 / 1:대문자

          if(sel2 == 0) {
              ch = (char)(ch + ('a' - 'A')); // 대문자로 변경
          }
          key += ch;
        }
      }
      return key;
    }

//
//  	// HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
//  	public String loadHtml(String authKey, String htmlName) {
//  		
//  		// org.tyhmeleaf.Context 선택!!
//  		Context context = new Context();
//  		
//  		//타임리프가 적용된 HTML에서 사용할 값 추가
//  		context.setVariable("authKey", authKey);
//  		
//  		// templates/email 폴더에서 htmlName과 같은 
//  		// .html 파일 내용을 읽어와 String으로 변환
//  		return TemplateEngine.process("tel/" + htmlName, context);
//  		
//  	}
//    
//  	// 인증번호 확인
//  	@Override
//  	public boolean checkAuthKey(Map<String, String> map) {
//
//  		// map에 저장된 값 꺼내오기
//  		String tel = map.get("tel");
//  		String authKey = map.get("authKey");
//  		
//  		
//  		// 1) Redis에 key가 입력된 email과 같은 데이터가 있는지 확인
//  		if(RedisUtil.hasKey(tel) == false ) { // 없을 경우
//  			return false;
//  		}
//  		
//  		// 2) Redis에 같은 key가 있으면 value를 얻어와
//  		// 		입력받은 인증번호와 비교
//  		return RedisUtil.getValue(tel).equals(authKey);
//  	}
}