package edu.kh.fit.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.main.mapper.MainMapper;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;
	
	
	/**
	 * 메인 페이지 조회
	 */
	@Override
	public Map<String, Object> mainPage() {

		
		List<Trainer> trainerList = mapper.selectNewTrainerList();
		List<Board> homeGymList = mapper.selectBoardList(2);
		List<Board> homeTrainingList = mapper.selectBoardList(3);
		
		Map<String, Object> map = Map.of("trainerList", trainerList,
											"homeTrainingList", homeTrainingList,
											"homeGymList", homeGymList);


	return map;
	}
}
