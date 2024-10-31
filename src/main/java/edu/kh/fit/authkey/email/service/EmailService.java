package edu.kh.fit.authkey.email.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송 서비스
	 * @param string
	 * @param email
	 * @return
	 */
	int sendEmail(String htmlName, String email);

	
	/** 인증번호 확인
	 * @param map
	 * @return
	 */
	boolean checkAuthKey(Map<String, String> map);


	/** 비밀번호 전송
	 * @param string
	 * @param email
	 * @param password
	 * @return
	 */
	int se1ndEmail(String htmlName, String email, String password);


	int sendEmail(String string, String email, String password);







	
	
	
}