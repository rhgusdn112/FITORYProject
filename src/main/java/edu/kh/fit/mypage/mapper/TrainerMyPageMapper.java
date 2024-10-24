package edu.kh.fit.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.trainer.dto.Trainer;

@Mapper
public interface TrainerMyPageMapper {
	/** 강사 정보 수정
	 * @param updateTrainer
	 * @return
	 */
	int trainerUpdate(Trainer updateTrainer);

	/**
	 * @param object
	 * @param trainerNo
	 * @return
	 */
	int profile(Object object, int trainerNo);
}
