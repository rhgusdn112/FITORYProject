package edu.kh.fit.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Comment {
	private int 		commentNo; // 댓글 번호
	private String 	reviewContent; // 리뷰 내용
	private String 	reviewWriteDate; // 리뷰 작성일
	private String 	reviewtDelFl; // 리뷰 삭제 여부 플래그
	private int 		number; // 별점
	private int 		memberNo; // 작성자 회원 번호
	private int 		boardNo; // 게시글 번호
	private String 	type;
	
	/* 댓글에 포함될 작성자명 */
	private String 	memberNickname; // 작성자 닉네임
	
	// 게시판 번호 추가
	private int 		classNo; // 게시판번호
	
}
