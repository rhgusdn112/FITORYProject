package edu.kh.fit.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder encoder;
	
	
	// (회원) 로그인 서비스
	@Override
		public Member memberLogin(String memberEmail, String memberPw) {
			
			Member memberLogin = mapper.memberLogin(memberEmail);
			
			if(memberLogin == null)	return null;

			if( !encoder.matches(encoder.encode(memberPw), memberLogin.getMemberPw()) ){
				return null;
			}
				
			return memberLogin;
		}

}
