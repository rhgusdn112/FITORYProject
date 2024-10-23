package edu.kh.fit.mypage.service;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.mypage.dto.TrainerMyPage;
import edu.kh.fit.trainer.dto.Trainer;

public interface TrainerMyPageService {

	/** 강사 정보 수정
	 * @param updateTrainer
	 * @return
	 */
	int trainerUpdate(Trainer updateTrainer);

	/** 프로필 수정 파일
	 * @param profileImg
	 * @param trainerNo
	 * @return
	 */
	String profile(MultipartFile profileImg, int trainerNo);

	int trainerLogin(Trainer updateTrainer);

}
