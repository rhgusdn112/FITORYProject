package edu.kh.fit.member.service;

import java.util.List;
import java.util.Map;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.Order;

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

  /** 회원 결제 강의 목록 조회
   * @param memberNo
   * @return
   */
  List<Order> classList(int memberNo);

  /** 내 활동 내역
   * @param map
   * @return
   */
	int memberActivities(Map<String, Object> map);

}
