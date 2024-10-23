package edu.kh.fit.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper mapper;

    @Override
    public Payment selectPaymentClass(int boardNo) {
        return mapper.selectPaymentClass(boardNo);
    }

    @Override
    @Transactional
    public void updatePaymentStatus(int boardNo, String status) {
        mapper.updatePaymentStatus(boardNo, status);
    }
}