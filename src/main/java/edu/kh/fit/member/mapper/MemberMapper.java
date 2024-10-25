package edu.kh.fit.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.member.dto.Member;

@Mapper
public interface MemberMapper {

	// (회원) 로그인 서비스
	Member memberLogin(String memberEmail);

	// (회원) 회원가입
	int signUp(Member inputMember);

	// 이메일 중복검사
	int emailCheck(String email);

	// 회원 정보 수정 비밀번호 확인
	String memberCheckPw(int memberNo);
	
	// 회원 정보 수정
	int updateMember(Member inputMember);

}
