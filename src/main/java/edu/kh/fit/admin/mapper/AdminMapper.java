package edu.kh.fit.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.trainer.dto.Trainer;

@Mapper
public interface AdminMapper {

	// 관리자 로그인
	Admin adminLogin(String adminEmail);

	// 회원 목록 조회
	List<Member> memberList();

	// 강사 목록 조회
	List<Trainer> trainerList();


}
