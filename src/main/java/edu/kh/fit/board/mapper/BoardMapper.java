package edu.kh.fit.board.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.board.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시글 상세 조회
	 * @param map 
	 * @return board
	 */
	Board selectDetail(Map<String, Integer> map);

}
