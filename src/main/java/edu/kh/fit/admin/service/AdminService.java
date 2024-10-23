package edu.kh.fit.admin.service;

import edu.kh.fit.admin.dto.Admin;

public interface AdminService {

	/** 관리자 로그인
	 * @param adminEmail
	 * @param adminPw
	 * @return
	 */
	Admin adminLogin(String adminEmail, String adminPw);
	
}
