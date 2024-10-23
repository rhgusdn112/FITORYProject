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
public class PaymentDTO {

	 private int paymentMember;         // 결제하는 회원의 ID
	 private int paymentBoard;          // 결제 관련된 항목의 ID (예: 상품, 게시물 등)
	 private String paymentDate;        // 결제 일자
	 private String paymentId;          // 결제 고유 ID (결제 서비스에서 발급)
	 private String paymentMethod;      // 결제 방법 (카드, 계좌이체 등)
	 private String paymentStatus;      // 결제 상태 (예: PENDING, SUCCESS, FAILED)
	 private int paymentAmount;      // 결제 금액
	 private String currency;           // 통화 (예: KRW, USD)
	 private String customerName;       // 결제자 이름
	 private String customerEmail;      // 결제자 이메일
	 private String customerPhone;      // 결제자 전화번호
}
