package edu.kh.fit.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService service;
    
    // 결제 정보 조회
    @GetMapping("/order/{orderNo}")
    public ResponseEntity<Payment> getPaymentById(
    		@PathVariable("orderNo") int orderNo) {
        Payment payment = service.selectPaymentClass(orderNo);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    public String getPaymentForm() {
    	return "kakaoPayPopup";
    }
    
}
