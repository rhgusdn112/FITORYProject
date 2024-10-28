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
	private int   	commentNo; // 댓글 번호
	private String 	reviewContent; // 댓글 내용
	private String 	reviewWriteDate; // 댓글 작성일
	private String 	reviewtDelFl; // 삭제 여부
	private float 		number; // 평점
	private int    	memberNo; // 회원 번호
	private int 		boardNo; // 게시글 번호
	
	/* 댓글에 포함될 작성자명 */
	private String memberNickname;
}
