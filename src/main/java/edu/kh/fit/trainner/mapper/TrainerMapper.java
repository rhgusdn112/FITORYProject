package edu.kh.fit.trainner.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.trainner.dto.Trainer;

@Mapper
public interface TrainerMapper {
	
	
	// (강사) 로그인 서비스
	Trainer trainerLogin(String trainerEmail);

	// (강사) 회원가입
	int signUp(Trainer inputTrainer);

}
