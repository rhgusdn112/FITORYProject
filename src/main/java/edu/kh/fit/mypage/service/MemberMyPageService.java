package edu.kh.fit.mypage.service;

import edu.kh.fit.member.dto.Member;

public interface MemberMyPageService {

	/** 회원 정보 수정
	 * @param updateMember
	 * @return
	 */
	int memberUpdate(Member updateMember);

}
