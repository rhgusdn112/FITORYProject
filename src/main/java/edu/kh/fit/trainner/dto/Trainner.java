package edu.kh.fit.trainner.dto;

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
public class Trainner {
	
	private int trainnerNo;       
	private String trainnerEmail;    
	private String trainnerPw;
	private String trainnerNickname;
	private String trainnerTel;
	private String trainnerAddress;  
	private String profileImg;
	private int    sales;
	private String enrollDate;
	private String trainnerDelFl;
	private String gender;
	
	
	
	// 강사 자격정보 생성을 위한 필드생성
	private int qualificationNo;
	private String qualification;
	private String award;
	private String career;
}
