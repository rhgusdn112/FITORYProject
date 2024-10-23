package edu.kh.fit.payment.service;

import edu.kh.fit.payment.dto.Payment;

public interface PaymentService {
    Payment selectPaymentClass(int boardNo);
    void updatePaymentStatus(int boardNo, String status);
}