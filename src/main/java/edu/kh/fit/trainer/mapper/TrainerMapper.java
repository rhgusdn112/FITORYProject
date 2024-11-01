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
	
	
	/** 강사 로그인 서비스
	 * @param trainerEmail
	 * @return
	 */
	Trainer trainerLogin(String trainerEmail);

	/** 강사 회원 가입
	 * @param inputTrainer
	 * @return
	 */
	int signUp(Trainer inputTrainer);

	/** 강사 정보 마이페이지 비밀번호 확인
	 * @param trainerNo
	 * @return
	 */
	String trainerCheckPw(int trainerNo);

	/** 강사 정보 수정
	 * @param inputTrainer
	 * @return
	 */
	int updateTrainer(Trainer inputTrainer);
	
	/** 강사 자격정보 삭제
	 * @param trainerNo
	 */
	void deleteQulification(int trainerNo);
	
	/** 강사 자격정보 추가
	 * @param qList
	 * @return
	 */
	int insertQulification(List<Qualification> qList);

	/** 강사 이미지 삭제
	 * @param trainerNo
	 */
	void deleteTrainerImage(int trainerNo);

	/** 강사 이미지 추가
	 * @param renameList
	 * @param trainerNo
	 * @return
	 */
	int insertTrainerImage(@Param("renameList") List<String> renameList, @Param("trainerNo") int trainerNo);
	
	/** 강사 프로필 상세 조회
	 * @param trainerNo
	 * @return
	 */
	Trainer detailTrainer(int trainerNo);
	
	/** 강사 자격사항 개수 조회
	 * @param trainerNo
	 * @return
	 */
	int qualiCount(int trainerNo);
	
	/** 강사 자격사항 조회 및 페이지 네이션
	 * @param trainerNo
	 * @param bounds
	 * @return
	 */
	List<Qualification> qualiList(int trainerNo, RowBounds bounds);

	/** 강사 강의 목록 조회
	 * @param trainerNo
	 * @param bounds
	 * @return
	 */
	List<Board> classList(int trainerNo, RowBounds bounds);
	

	// 페이지 네이션
	int qualificationList(int trainerNo);
	
	/** 강의 목록 수 조회
	 * @param trainerNo
	 * @return
	 */
	int classListCount(int trainerNo);

	/** 선택 강사 강의 목록 조회
	 * @param trainerNo
	 * @param bounds
	 * @return
	 */
	List<Board> trainerVideoDetail(int trainerNo, RowBounds bounds);

	/** 선택 강사 강의 목록 조회 페이지 네이션
	 * @param trainerNo
	 * @return
	 */
	int pageList(int trainerNo);

	List<Board> trainerVideoDetail(@Param("trainerNo") int trainerNo,@Param("sort") String sort, RowBounds bounds);

	/** 이메일 중복체크
	 * @param email
	 * @return
	 */
	int emailCheck(String email);

	/** 회원 탈퇴
	 * @param trainerNo
	 * @return
	 */
	int statusChange(int trainerNo);

/** 비밀번호 찾기
 * @param email 
 * @return
 */
	String findPw(String email);

	int updatePw(String password);


	// 강의 매출 가져오기
	List<Board> classList1(int trainerNo, RowBounds bounds);
	int classListCount1( int trainerNo); 
	
	List<Board> boardList(int trainerNo, RowBounds bounds);
	int boardListCount(int trainerNo);

}
