package edu.kh.fit.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    
    // 주문 정보 조회
    /**
     * @param orderNo : 주문번호
     * @return 
     */
    @GetMapping("/{boardTypeNo}/{boardNo}")
    public ResponseEntity<Payment> getOrderInfoById(
    		@PathVariable("orderNo") int orderNo) {
        Payment payment = service.selectOrderInfo(orderNo);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /** 카카오페이 결제 요청 */
    @GetMapping("kakaopay")
    public String kakaoPayRequest(
            @RequestParam("orderNo") int orderNo,
            @SessionAttribute("memberLogin") Member memberLogin) {
        
        // 주문 정보 및 결제 금액 로드
        Payment payment = service.selectPaymentClass(orderNo);

        if (payment == null) {
            return "errorPage"; // 주문 정보를 찾지 못한 경우
        }

        // 결제 상태를 PENDING으로 설정 (결제 진행 중 상태)
        service.updatePaymentStatus(orderNo, "PENDING");

        // 카카오페이 결제 요청 로직 (API 호출 등)
        // TODO: 카카오페이 API 연동 코드 추가
        boolean paymentSuccess = false; // 카카오페이 API 결과에 따라 설정 (예시)

        // 결제 요청 성공 여부에 따라 상태 업데이트
        if (paymentSuccess) {
            service.updatePaymentStatus(orderNo, "SUCCESS");
        } else {
            service.updatePaymentStatus(orderNo, "FAILED");
        }

        return "payment/kakaoPayPopup"; // 결제 요청 팝업 페이지 반환
    }

}
