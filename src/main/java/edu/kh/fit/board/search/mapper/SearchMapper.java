package edu.kh.fit.board.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.board.dto.Board;

@Mapper
public interface SearchMapper {

	List<Board> searchByCategoryAndKeyword(String string, String keyword);
	
}
