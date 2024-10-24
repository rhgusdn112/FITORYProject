package edu.kh.fit.member.service;

import edu.kh.fit.member.dto.Member;

public interface MemberCheckPwService {

	/** 비밀번호 인증
	 * @param memberPw
	 * @return
	 */
	String memberCheckPw(String memberPw);

}
