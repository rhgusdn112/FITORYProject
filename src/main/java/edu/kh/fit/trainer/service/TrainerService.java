package edu.kh.fit.trainer.service;

import org.springframework.web.multipart.MultipartFile;

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
	String profile(MultipartFile profileImg, int trainerNo);





}
