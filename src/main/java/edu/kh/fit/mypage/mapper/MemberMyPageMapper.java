package edu.kh.fit.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.mypage.dto.MemberMyPage;

@Mapper
public interface MemberMyPageMapper {

	/** 회원 정보 수정
	 * @param updateMember
	 * @return
	 */
	int memberUpdate(MemberMyPage updateMember);

}
