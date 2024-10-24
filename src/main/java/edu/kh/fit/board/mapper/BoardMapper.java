package edu.kh.fit.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;

@Mapper
public interface BoardMapper {

	/** 게시글 상세 조회
	 * @param map 
	 * @return board
	 */
	Board selectDetail(Map<String, Object> map);
	
  // 댓글 목록 조회
  List<Comment> selectCommentList(int boardNo);

  // 사용자가 해당 게시글에 이미 리뷰를 작성했는지 확인
	int hasReviewed(Map<String, Object> params);
}
