package edu.kh.fit.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Query {
	private int queryNo;         // 문의 번호
	private String queryDetail;  // 문의 내용 
	private String queryDate;    // 문의 일자
	private String queryType;    // 문의 종류
	private int 	 queryPk;      // 문의자 번호
	private int 	 status;       // 진행 상태
}
