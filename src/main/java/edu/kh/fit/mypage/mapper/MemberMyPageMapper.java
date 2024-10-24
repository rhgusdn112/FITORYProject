package edu.kh.fit.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.member.dto.Member;

@Mapper
public interface MemberMyPageMapper {

	/** 회원 정보 수정
	 * @param updateMember
	 * @return
	 */
	int memberUpdate(Member updateMember);

}
