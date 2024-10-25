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
	private String profileImg; // 프로필 이미지 경로
	private int    sales; // 매출
	private String enrollDate; // 가입일
	private String trainnerDelFl; // 탈퇴여부('Y', 'N')
	private String gender; // 성별(남, 여)
	
	// 강사 자격정보 생성을 위한 필드생성
	private int qualificationNo; // 자격 정보 번호
	private String qualification; // 자격 정보
	private String award; // 수상 내역
	private String career; // 이력
}
