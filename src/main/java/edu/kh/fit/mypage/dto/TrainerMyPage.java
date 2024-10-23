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
public class TrainerMyPage {
 private int trainerNo;
 private String trainerEmail;
 private String trainerPw;
 private String trainerNickname;
 private String trainerTel;
 private String profileImg;
 private int sales;
 private String enrollDate;
 private String trainerDelFl;
 private String gender;
 
/* 트레이너 자격 정보 */
 private int qualificationNo;
 private String qualification;
 private String award;
 private String career;
}
