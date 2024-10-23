package edu.kh.fit.mypage.service;

import edu.kh.fit.mypage.dto.MemberMyPage;

public interface MemberMyPageService {

	/** 회원 정보 수정
	 * @param updateMember
	 * @return
	 */
	int memberUpdate(MemberMyPage updateMember);

}
