package edu.kh.fit.board.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.search.mapper.SearchMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService{

	private final SearchMapper mapper;
	
	@Override
	public Map<String, Object> searchByCategoryAndKeyword(String keyword) {
		// homeGymVideos, homeTrainingVideos
		List<Board> homeGymVideos = mapper.searchByCategoryAndKeyword("홈짐",keyword);
		List<Board> homeTrainingVideos = mapper.searchByCategoryAndKeyword("홈트레이닝",keyword);
		
		Map<String, Object> map
			= Map.of("homeGymVideos", homeGymVideos, "homeTrainingVideos", homeTrainingVideos);
		return map;
	}
	
}
