package edu.kh.fit.trainner.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.trainner.dto.Trainner;

@Mapper
public interface TrainnerMapper {
	
	
	// (강사) 로그인 서비스
	Trainner trainnerLogin(String trainnerEmail);

}
