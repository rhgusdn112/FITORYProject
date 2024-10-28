package edu.kh.fit.board.dto;

import java.util.List;

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
public class Board {

	// 행 번호
	private int rnum;
	
	// BOARD 테이블 컬럼과 매핑되는 필드
	private int 		boardNo; 						// 강의 번호
	private String 	thumbnail; 					// 영상 썸네일
	private String	title; 							// 강의 제목
	private String	detail;							// 강의 내용
	private String 	uploadDate;					// 작성일
	private String 	billingDate;				// 마지막 수정일
	private int 	 	hits;								// 조회수
	private String 	boardDelFl;					// 강의 삭제 여부(Y,N)
	private int 	 	trainerNo;					// 작성자(강사) 번호
	private int	   	classNo;						// 게시판 번호
	private float 	grade;							// 평점
	private int			payment;								// 가격
	
	// TRAINER 테이블 JOIN 컬럼
	private String trainerNickname;
	
	
	// 필요 시 추가 예정
	
}
