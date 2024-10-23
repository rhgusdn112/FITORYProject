package edu.kh.fit.board.service;

import java.util.Map;

import org.springframework.stereotype.Controller;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	@Override
		public Board selectDetail(Map<String, Integer> map) {
		
			return mapper.selectDetail(map);
		}
}
