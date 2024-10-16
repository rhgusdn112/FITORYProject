package edu.kh.fit.member.dto;

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
public class Member {
	
	private int 	 memberNo;
	private String memberEmail;
	private String memberPw;
	private String memberName;
	private String memberTel;
	private String memberBirthday;
	private String profileImg;
	private int    height;
	private int    weight;
	private String enroolDate;
	private String memberDelFl;
	private int    memberFlag;
}
