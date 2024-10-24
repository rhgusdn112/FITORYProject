package edu.kh.fit.member.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberCheckPwMapper {

	/** 비밀번호 인증
	 * @param memberPw
	 * @return
	 */
	String memberCheckPw(String memberPw);

}
