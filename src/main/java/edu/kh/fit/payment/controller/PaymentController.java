package edu.kh.fit.payment.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.model.Model;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.PaymentDTO;
import edu.kh.fit.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;


@Controller
@ResponseBody
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService service;
	
	/** 결제금액 전달
	 * @param loginMember : 로그인 한 회원 정보
	 * @param amount : 결제금액
	 * @return
	 */
	@PostMapping("")
	public PaymentDTO paymentClass(
		@SessionAttribute("loginMember") Member loginMember,
		@PathVariable("boardNo") int boardNo) {
		
		PaymentDTO payment = service.selectPaymentClass(boardNo);
		payment.setCustomerName(loginMember.getMemberName());
		
		return payment;
		
		}
}
	
	
