package edu.kh.fit.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.payment.mapper.PaymentMapper;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper mapper;

    // 주문 정보 확인
    @Override
    public Payment selectPaymentClass(int orderNo) {
    	return mapper.selectPaymentClass(orderNo);
    }
    
    // 결제상태 확인
    @Override
    public void updatePaymentStatus(String paymentId, String status) {
    }
    
    // 카카오 페이 연동
    @Override
    public Payment selectPayment(int boardNo, int orderNo) {
    	return mapper.selectPayment(boardNo, orderNo);
    }
    
    @Override
    public String getOrderNo() {
    	return mapper.getOrderNo();
    }
    
    @Override
    public Payment getPaymentDetail(String paymentId) {
    	return mapper.getPaymentDetail(paymentId);
    }
}