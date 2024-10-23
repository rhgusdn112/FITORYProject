package edu.kh.fit.board.service;

import java.util.Map;

import edu.kh.fit.board.dto.Board;

public interface BoardService {

	/**
	 * @param map
	 * @return
	 */
	Board selectDetail(Map<String, Object> map);

}
