package edu.kh.fit.payment.service;

import org.springframework.ui.Model;

import edu.kh.fit.payment.dto.Payment;

public interface PaymentService {
    
    // 주문/결제 정보 확인 페이지로 이동
	Payment selectPaymentClass(int orderNo);

	// 결제 상태 확인
	void updatePaymentStatus(String paymentId, String status);

	// 카카오페이 연동
	Payment selectPayment(int boardNo, int orderNo);

	String getOrderNo();

	/** 결제 정보
	 * @param paymentId
	 * @return
	 */
	Payment getPaymentDetail(String paymentId);
}
