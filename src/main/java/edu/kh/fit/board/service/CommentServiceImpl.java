package edu.kh.fit.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	/**
	 * 댓글 등록
	 * 
	 * @param comment 댓글 정보
	 * @return 삽입된 댓글 번호
	 */
	@Override
	public int commentInsert(Comment comment) {
		return commentMapper.insertComment(comment);
	}

	/**
	 * 댓글 수정
	 * 
	 * @param comment 댓글 정보
	 * @return 수정된 댓글 번호
	 */
	@Override
	public int commentUpdate(Comment comment) {
		return commentMapper.updateComment(comment);
	}

	/**
	 * 댓글 삭제
	 * 
	 * @param commentNo 댓글 번호
	 * @param memberNo  회원 번호
	 * @return 삭제 결과
	 */
	@Override
	public int commentDelete(int commentNo, int memberNo) {
		return commentMapper.deleteComment(commentNo, memberNo);
	}
}
