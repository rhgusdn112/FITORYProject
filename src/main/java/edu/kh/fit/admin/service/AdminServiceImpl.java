package edu.kh.fit.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.admin.mapper.AdminMapper;
import edu.kh.fit.member.dto.Member;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;

	// 관리자 로그인
	@Override
		public Admin adminLogin(String adminEmail, String adminPw) {

		
		Admin adminLogin = mapper.adminLogin(adminEmail);
		
		if(adminLogin == null)	return null;

//		if( !encorder.matches(encorder.encode(adminPw), adminLogin.getPw()) ){
//			return null;
//		}
			
		return adminLogin;

		
		}
}
