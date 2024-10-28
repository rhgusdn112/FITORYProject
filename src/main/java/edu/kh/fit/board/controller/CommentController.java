package edu.kh.fit.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.service.CommentService;
import edu.kh.fit.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

	private final CommentService commentService;

	/**
	 * 댓글 등록
	 * 
	 * @param comment     : 요청 시 body에 JSON 형태로 담겨져 제출된 데이터를 DTO로 변환한 객체
	 * @param loginMember : 로그인한 회원 정보
	 * @return commentNo : 삽입된 댓글 번호
	 */
	@PostMapping("/comment")
	public int commentInsert(@RequestBody Comment comment, @SessionAttribute("memberLogin") Member loginMember) {

		// 로그인된 회원 번호를 comment에 세팅
		comment.setMemberNo(loginMember.getMemberNo());
		return commentService.commentInsert(comment);
	}

	/**
	 * 댓글 수정
	 * 
	 * @param comment     : 수정할 댓글 내용
	 * @param loginMember : 로그인한 회원 정보
	 * @return 수정된 댓글 번호
	 */
	@PutMapping("/comments")
	public int commentUpdate(@RequestBody Comment comment, @SessionAttribute("memberLogin") Member loginMember) {

		comment.setMemberNo(loginMember.getMemberNo());
		return commentService.commentUpdate(comment);
	}

	/**
	 * 댓글 삭제
	 * 
	 * @param commentNo   : 삭제하려는 댓글 번호
	 * @param loginMember : 로그인한 회원 정보
	 * @return 삭제 결과
	 */
	@DeleteMapping("/comments")
	public int commentDelete(@RequestBody int commentNo, @SessionAttribute("memberLogin") Member loginMember) {

		return commentService.commentDelete(commentNo, loginMember.getMemberNo());
	}
}
