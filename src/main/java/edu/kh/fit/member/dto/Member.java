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
	private String enrollDate;
	private String memberDelFl;
	private int    memberFlag;
	
  // 댓글 관련 필드 선언
  private int countComment; // 작성한 댓글 수
  private int commentNo;
  private String reviewContent;   // 
  
  // 신고 관련 필드 선언
  private int countReport;
  private int reportNo;
  private String reportDetail;
  
  
  
}
