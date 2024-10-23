package edu.kh.fit.payment.mapper;


import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.payment.dto.Payment;
import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface PaymentMapper {
    
	// 주문 정보 조회
	Payment selectOrderInfoById(int orderNo);
	
	// 결제 상태 업데이트
    void updatePaymentStatus(
    		@Param("orderNo") int orderNo, 
    		@Param("status") String status);
    
    // 결제 요청
	Payment selectPaymentClass(int orderNo);
}

