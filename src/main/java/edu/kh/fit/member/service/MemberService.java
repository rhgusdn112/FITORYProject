package edu.kh.fit.member.service;

import edu.kh.fit.member.dto.Member;

public interface MemberService {
	
	/** (회원) 로그인 서비스
	 * @param memberEmail
	 * @param memberPw
	 * @return
	 */
	Member memberLogin(String memberEmail, String memberPw);

	/** (회원) 회원가입
	 * @param inputMember
	 * @return
	 */
	int signUp(Member inputMember);

	/** 이메일 중복 검사
	 * @param email
	 * @return
	 */
	int emailCheck(String email);

	/** 회원 정보 수정 비밀번호 확인
	 * @param memberNo
	 * @param memberPw
	 * @return
	 */
	boolean memberCheckPw(int memberNo, String memberPw);

	/** 회원 업데이트
	 * @param inputMember
	 * @return
	 */
	int updateMember(Member inputMember);

}
