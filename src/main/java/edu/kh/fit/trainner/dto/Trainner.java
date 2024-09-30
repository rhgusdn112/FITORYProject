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
	private String qualification;
	private String enrollDate;
	private String trainnerDelFl;
	private String authorityTrainner;
}
