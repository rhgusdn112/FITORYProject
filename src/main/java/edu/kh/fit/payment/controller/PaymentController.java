package edu.kh.fit.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    /** 결제 정보 조회 및 결제 페이지로 이동
     * @param loginMember : 로그인 한 회원 정보
     * @param boardNo : 결제할 게시물 번호
     * @param redirectAttributes : 페이지 리다이렉션 시 필요한 데이터
     * @return String : 결제 페이지로 이동
     */
    @GetMapping("/payment")
    public String paymentClass(
        @SessionAttribute("loginMember") Member loginMember,
        @PathVariable("boardNo") int boardNo,
        RedirectAttributes redirectAttributes) {
        
        // 주문 정보 불러오기
        Payment payment = service.selectPaymentClass(boardNo);
        payment.setCustomerName(loginMember.getMemberName());
        payment.setCustomerEmail(loginMember.getMemberEmail());

        // 주문 정보와 사용자 정보를 리다이렉션에 추가
        redirectAttributes.addFlashAttribute("payment", payment);
        
        return "redirect:/payment/payment"; // 결제 페이지로 리다이렉션
    }

    /** 카카오페이 결제 요청 */
    @GetMapping("/payment/kakaopay")
    public String kakaoPayRequest(@RequestParam("orderNo") int orderNo, @SessionAttribute("loginMember") Member loginMember) {
        // 주문 정보 및 결제 금액 로드
        Payment payment = service.selectPaymentClass(orderNo);

        // 카카오페이 결제 요청 로직 (API 호출 등)
        // TODO: 카카오페이 API 연동 코드 추가
        return "payment/kakaoPayPopup"; // 결제 요청 팝업 페이지 반환
    }
}
