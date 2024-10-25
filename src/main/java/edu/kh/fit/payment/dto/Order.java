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

	private String orderNo;			// 주문 번호
	private String paymentId;		// 결제 고유 ID
	private String classTitle;		// 영상 제목 또는 주문명
	private int paymentAmount;		// 결제 금액
	private String currency;		// 통화 (KRW)
	private String customerName;	// 결제자명
}
