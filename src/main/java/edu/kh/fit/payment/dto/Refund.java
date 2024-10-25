package edu.kh.fit.payment.dto;

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
public class Refund {
	
	private String refundNo;
	private String processingStatus;
	private String reqestDate;
	private String refundReason;
	private String refundDate;
	private int    refundMember;
	private int    refundBoard;
	private int    orderNo;
}
