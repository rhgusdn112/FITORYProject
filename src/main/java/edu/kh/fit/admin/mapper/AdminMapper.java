package edu.kh.fit.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.admin.dto.Admin;

@Mapper
public interface AdminMapper {

	// 관리자 로그인
	Admin adminLogin(String adminEmail);


}
