package edu.kh.fit.trainer.service;

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



}