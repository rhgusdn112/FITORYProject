package edu.kh.fit.payment.dto;

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
public class Order {

	private String orderNo; // 주문 번호
	private int orderClassAmount; // 가격
	private String orderCurrency; // 단위
	private int orderMemberNo; // 주문자 번호
	private int orderClassNo; // 강의 번호
	
	private String title;
	private String detail;
	private String trainerNickname;
	private String boardTypeName;
	private String paymentDate; // 결제일
	
	private int boardTypeNo; // 게시판 번호
	
}
