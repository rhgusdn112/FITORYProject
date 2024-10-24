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
	private int   	commentNo;
	private String 	reviewContent;
	private String 	reviewWriteDate;
	private String 	reviewtDelFl;
	private float 		number;
	private int    	memberNo;
	private int 		boardNo;
	
	/* 댓글에 포함될 작성자명 */
	private String memberNickname;
}
