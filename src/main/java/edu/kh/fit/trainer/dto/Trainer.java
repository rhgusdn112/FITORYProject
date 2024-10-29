package edu.kh.fit.trainer.dto;

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
public class Trainer {
	
	private int trainerNo; // 강사 번호
	private String trainerEmail; // 강사 이메일
	private String trainerPw; // 강사 비밀번호(암호화)
	private String trainerNickname; // 강사명
	private String trainerTel; // 강사 전화번호
	private int    sales; // 매출
	private String enrollDate; // 가입일
	private String trainnerDelFl; // 탈퇴여부('Y', 'N')
	private String gender; // 성별(남, 여)

//댓글 관련 필드 선언
 private int countComment; // 작성한 댓글 수
 private int commentNo;
 private String reviewContent;   // 댓글 
 
 // 신고 관련 필드 선언
 private int countReport;
 private int reportNo;
 private String reportDetail;

	// 강사 자격정보 생성을 위한 필드생성
	private int qualificationNo; // 자격 정보 번호
	private String qualification; // 자격 정보
	private String qualificationDate; // 자격 취득 날짜

	
	// 강사가 작성한 게시글 생성을 위한 필드 생성
	private int countBoardNo;
	private int boardNo;
	private int countReportNo;
	
	// 트레이너 프로필 이미지 관련 테이블
	private String trainerImgMain; // 필수 사진1
	private String trainerImgMainSub; // 필수 사진2
	private String trainerImgSub; // 선택 사진1
	private String trainerImgSubSub; // 선택 사진2
	
	
}
