package edu.kh.fit.trainer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.payment.dto.Order;
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
	int profile(String url, int trainerNo);

	// 강사 강의 목록 조회
	List<Board> classList(int trainerNo);

	List<Trainer> detailTrainer(Trainer trainerNo);



}
