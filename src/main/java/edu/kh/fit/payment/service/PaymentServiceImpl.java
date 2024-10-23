package edu.kh.fit.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper mapper;

    @Override
    public Payment selectOrderInfo(int orderNo) {
        return mapper.selectOrderInfoById(orderNo);
    }

    @Override
    public void updatePaymentStatus(int orderNo, String status) {
        mapper.updatePaymentStatus(orderNo, status);
    }
    
    @Override
    public Payment selectPaymentClass(int orderNo) {
    	return mapper.selectPaymentClass(orderNo);
    }
}