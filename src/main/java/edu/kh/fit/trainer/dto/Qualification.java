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
public class Qualification {
	private int qualificationNo; // 자격 정보 번호
	private String qualification; // 자격 정보
	private String qualificationDate; // 자격 취득 날짜
	private int trainerNo; // 강사 번호

}
