package edu.kh.fit.trainer.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.trainer.dto.Trainer;

public interface TrainerService {
	
	/** (강사) 로그인 서비스
	 * @param trainnerEmail
	 * @param trainnerPw
	 * @return
	 */
	Trainer trainerLogin(String trainerEmail, String trainerPw);

	
	/** (강사) 회원가입
	 * @param inputTrainner
	 * @return
	 */
	int signUp(Trainer inputTrainer);


	/** 강사 정보 수정 비밀번호 확인
	 * @param trainerNo
	 * @param trainerPw
	 * @return
	 */
	boolean trainerCheckPw(int trainerNo, String trainerPw);

	/** 강사 정보 수정
	 * @param inputTrainer
	 * @return
	 */
	int updateTrainer(Trainer inputTrainer);

	/** 프로필 변경
	 * @param profileImg
	 * @param trainerNo
	 * @return
	 */
	String profile(MultipartFile imgProfileList, int trainerNo);

/** 강사 강의 목록 조회
 * @param trainerNo
 * @return
 */
	List<Board> classList(int trainerNo);

/** 강사 상세조회
 * @param trainerNo
 * @param boardNo
 * @return
 */
List<Trainer> detailTrainer(Trainer trainerNo);





}
