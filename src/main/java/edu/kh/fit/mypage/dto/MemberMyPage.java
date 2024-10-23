package edu.kh.fit.mypage.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class MemberMyPage {
 private int memberNo;
 private String memberEmail;
 private String memberPw;
 private String memberName;
 private String memberTel;
 private String memberBirthday;
 private int memberHeight;
 private int memberWeight;
 private String enrollDate;
 private String memberDelFl;
 private int memberFlag;
}
