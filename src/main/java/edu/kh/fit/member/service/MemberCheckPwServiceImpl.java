package edu.kh.fit.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.mapper.MemberCheckPwMapper;
import lombok.RequiredArgsConstructor;

@Service
public class MemberCheckPwServiceImpl implements MemberCheckPwService{
	@Autowired
	private MemberCheckPwMapper mapper;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public String memberCheckPw(String memberPw) {
		 return mapper.memberCheckPw(memberPw);
	}
}
