package edu.kh.fit.board.search.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import edu.kh.fit.board.dto.Board;

@Mapper
public interface SearchMapper {

	List<Board> searchByCategoryAndKeyword(@Param("string") String string, 
			@Param("keyword") String keyword);
	
}
