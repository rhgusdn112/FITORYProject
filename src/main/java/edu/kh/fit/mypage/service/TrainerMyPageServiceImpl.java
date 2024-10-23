package edu.kh.fit.mypage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.trainer.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainerMyPageServiceImpl implements TrainerMyPageService{
	private final TrainerMapper mapper;
}
