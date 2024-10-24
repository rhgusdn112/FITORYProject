package edu.kh.fit.board.service;

import java.util.List;
import java.util.Map;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;

public interface BoardService {

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectDetail(Map<String, Object> map);
	
  // 리뷰 목록 가져오기
  List<Comment> selectCommentList(int boardNo);


	// 사용자가 해당 게시글에 이미 리뷰를 작성했는지 확인
	boolean hasReviewed(Map<String, Object> params);
  

}
