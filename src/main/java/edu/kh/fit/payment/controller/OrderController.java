package edu.kh.fit.payment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.payment.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

	private final OrderService service;

	@GetMapping("/orderResult")
	public String orderResult(
			@RequestParam("orderNo") String orderNo,
			Model model) {
		
		Order order = service.selectOrder(orderNo);
		
		model.addAttribute("order", order);
		
		return "/payment/orderResult";
	}
	
	
	@PostMapping("/success")
	public ResponseEntity<Map<String, Object>> orderSuccess(
		@RequestBody Order order,
		@SessionAttribute("memberLogin") Member memberLogin){
		
		Map<String, Object> response = new HashMap<>();
		
		order.setOrderMemberNo(memberLogin.getMemberNo());
		
		try {
			// 주문 정보 insert (서비스 호출하기)
			boolean success = service.insertOrder(order);
			
			if(success) {
				response.put("success", true);
				response.put("redirectUrl", "/order/orderResult?orderNo=" + order.getOrderNo());
			} else {
				response.put("success", false);
				response.put("message", "주문 정보를 저장하는 데 실패 했습니다.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.put("success", false);
			response.put("message"," 서버 오류가 발생했습니다.");
		}
		return ResponseEntity.ok(response);
	}
		
	
	
}
