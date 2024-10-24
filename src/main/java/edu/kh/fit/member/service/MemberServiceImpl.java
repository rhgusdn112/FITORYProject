package edu.kh.fit.member.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder encorder;
	
	
	
	// (회원) 로그인 서비스
	@Override
		public Member memberLogin(String memberEmail, String memberPw) {
			
			Member memberLogin = mapper.memberLogin(memberEmail);
			
			if(memberLogin == null)	return null;

//			if( !encorder.matches(encorder.encode(memberPw), memberLogin.getMemberPw()) ){
//				return null;
//			}
				
			return memberLogin;
		}

	// (회원) 회원가입
	@Override
	public int signUp(Member inputMember) {

		String encPw = encorder.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		
		int result = mapper.signUp(inputMember);
		
		
		return result;
	}
	
	// 이메일 중복검사
	@Override
	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}

	// 회원 정보 수정 비밀번호 확인
	@Override
	public boolean memberCheckPw(int memberNo, String memberPw) {
		// log.debug("암호화 : {}", encorder.encode(memberPw));
		
		String encodePw = mapper.memberCheckPw(memberNo);
		return encorder.matches(memberPw, encodePw);
	}

	// 회원 정보 수정
	@Override
	public int memberUpdate(Member updateMember) {
		return mapper.memberUpdate(updateMember);
	}
}
