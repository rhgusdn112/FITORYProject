package edu.kh.fit.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.member.dto.Member;

@Mapper
public interface MemberMapper {

	// (회원) 로그인 서비스
	Member memberLogin(String memberEmail);

	// (회원) 회원가입
	int signUp(Member inputMember);

}
