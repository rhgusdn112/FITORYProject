package edu.kh.fit.authkey.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.fit.authkey.email.service.EmailService;
import edu.kh.fit.common.util.RedisUtil;

@Controller
@RequestMapping("email")
public class EmailController {

	@Autowired // DI 의존성 주입
	public RedisUtil redisUtil;
	
	@Autowired
	public EmailService service;
	
	
	// 레디스 확인하기
	@ResponseBody
	@GetMapping("redisTest")
	public int redisTest(
			@RequestParam("key") String key,
			@RequestParam("value") String value
			) {
		
		// 전달받은 key/value를 redis에 set하기
		redisUtil.setValue(key, value, 60); // 60초 후에 사라짐
		
		return 1;
	}
	
	/** 인증번호 발송
	 * @param email : 입력받은 이메일
	 * @return : 성공 - 1, 실패 - 0
	 */
	@ResponseBody
	@PostMapping("sendAuthKey")
	public int sendAuthKey(
			@RequestBody String email
			) {
		
		return service.sendEmail("signUp", email);
	}
	
	
	/** 비밀번호 찾기 이메일 인증
	 * @param email
	 * @return
	 */
	@ResponseBody
	@PostMapping("sendFindPwAuthKey")
	public int sendFindPwAuthKey(
			@RequestBody String email
			) {
		
		return service.sendEmail("findPw", email);
	}
	/** 비밀번호 이메일 보내기
	 * @param email
	 * @return
	 */
	@ResponseBody
	@PostMapping("sendPwAuthKey")
	public int sendPwAuthKey(
			@RequestBody String email,
			@RequestBody String password
			) {
		
		return service.sendEmail("sendPw", email, password);
	}
	
	/** 인증번호 확인
	 * @param map : 입력받은 email, authKey가 저장된 map
	 * 				HttpMessageConverter에 의해 JSON -> Map 자동 변환
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public boolean checkAuthKey(
			@RequestBody Map<String, String> map 
			) {
		return service.checkAuthKey(map);
	}
	
	
	
	
	
	
}
