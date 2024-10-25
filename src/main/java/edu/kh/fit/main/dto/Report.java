package edu.kh.fit.main.dto;

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
public class Report {

	private int reportNo;
	private String reportDetail;
	private String reportDate;
	private String reporterType;
	private int reporterPk;
	private String assailantType;
	private int assailantPk;
	private int status;
}
