package edu.kh.fit.admin.service;

import java.util.List;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.member.dto.Member;

public interface AdminService {

	/** 관리자 로그인
	 * @param adminEmail
	 * @param adminPw
	 * @return
	 */
	Admin adminLogin(String adminEmail, String adminPw);

	/** 회원 목록 조회
	 * @return
	 */
	List<Member> memberList();
	
}
