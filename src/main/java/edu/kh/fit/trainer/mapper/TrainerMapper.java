package edu.kh.fit.trainer.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.trainer.dto.Qualification;
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

	// 강사 강의 목록 조회
	List<Board> classList(int trainerNo);
	
	// 강사 프로필 상세조회
	Trainer detailTrainer(int trainerNo);
	

	// 페이지 네이션
	int qualificationList(int trainerNo);
	
	// 자격 정보 삭제
	void deleteQulification(int trainerNo);
	
	// 자격 정보 insert
	int insertQulification(List<Qualification> qList);

	// 강사 이미지 삭제
	void deleteTrainerImage(int trainerNo);

	// 강사 이미지 삽입
	int insertTrainerImage(@Param("renameList") List<String> renameList, @Param("trainerNo") int trainerNo);

	
	// 강의 목록 수 조회
	int classListCount(int trainerNo);

	// 강의 조회
	List<Board> classList(int trainerNo, RowBounds bounds);


	// 자격 사항 개수 조회
	int qualiCount(int trainerNo);
	
	// 강사 자격사항 조회
	List<Qualification> qualiList(int trainerNo, RowBounds bounds);
	




}
