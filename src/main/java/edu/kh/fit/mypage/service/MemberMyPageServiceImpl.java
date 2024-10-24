package edu.kh.fit.mypage.service;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.mypage.mapper.MemberMyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberMyPageServiceImpl implements MemberMyPageService{
	private final MemberMyPageMapper mapper;

	@Override
	public int memberUpdate(Member updateMember) {
		return mapper.memberUpdate(updateMember);
	}
}
