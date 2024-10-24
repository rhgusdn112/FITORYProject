package edu.kh.fit.trainer.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.trainer.dto.Trainer;

@Mapper
public interface TrainerMapper {
	
	
	// (강사) 로그인 서비스
	Trainer trainerLogin(String trainerEmail);

	// (강사) 회원가입
	int signUp(Trainer inputTrainer);

	// 강사 정보 수정 비밀번호 확인
	String trainerCheckPw(int trainerNo);

	// 강사 정보 수정
	int updateTrainer(Trainer inputTrainer);

	// 강사 사진 수정
	int profile(Object object, int trainerNo);

}
