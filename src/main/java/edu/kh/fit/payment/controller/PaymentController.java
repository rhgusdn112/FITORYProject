package edu.kh.fit.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("{orderNo:[0-2147483647]+}")
    public ResponseEntity<Payment> getPaymentById(
    		@PathVariable("orderNo") int orderNo) {
        Payment payment = service.selectPaymentClass(orderNo);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("{boardNo}/{orderNo}")
    public String getPaymentForm(Model model) {
        Payment payment = service.selectPaymentClass(1); 
        // 예시, 실제로는 DB에서 조회한 결제 정보 사용
        model.addAttribute("payment", payment);
        return "kakaoPayPopup"; 
        // Thymeleaf 템플릿 파일 이름
    }

    
}
