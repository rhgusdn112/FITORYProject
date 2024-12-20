package edu.kh.fit.board.service;

import edu.kh.fit.board.dto.Comment;

/**
 * 댓글 서비스 인터페이스
 */
public interface CommentService {

	/**
	 * 댓글 등록
	 * 
	 * @param comment 댓글 정보
	 * @return 삽입된 댓글 번호
	 */
	int commentInsert(Comment comment);

	/**
	 * 댓글 수정
	 * 
	 * @param comment 댓글 정보
	 * @return 수정된 댓글 번호
	 */
	int commentUpdate(Comment comment);

	// 댓글 삭제
	int commentDelete(Comment comment);

}