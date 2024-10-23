package edu.kh.fit.payment.service;

import edu.kh.fit.payment.dto.Payment;

public interface PaymentService {
    
	// 주문 정보 조회
	Payment selectOrderInfo(int orderNo);
    
	// 결제 상태 업데이트
    void updatePaymentStatus(int orderNo, String status);
    
    // 결제 요청
	Payment selectPaymentClass(int orderNo);
}
