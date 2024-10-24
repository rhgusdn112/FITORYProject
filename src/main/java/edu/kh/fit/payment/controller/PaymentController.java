package edu.kh.fit.payment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.service.BoardService;
import edu.kh.fit.payment.dto.Payment;
import edu.kh.fit.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("payment")
public class PaymentController {

	private final PaymentService service;
	private final BoardService boardService;
	
	
	// 주문 / 결제 정보 조회
	@GetMapping("{classNo:[0-9]+}/{boardNo:[0-9]+}")
	public String orderPayment(
			@PathVariable("classNo") int classNo,
			@PathVariable("boardNo") int boardNo,
			Model model) {
		Map<String, Object> map = new HashMap<>();
		map.put("classNo", classNo);
		map.put("boardNo", boardNo);
		
		Board board = boardService.selectDetail(map);
		model.addAttribute("board", board);
		
		return "payment/order-payment";
	}
	
	@PostMapping("{classNo:[0-9]+}/{boardNo:[0-9]+}")
	public String getKakaoPayPage(
			@PathVariable("classNo") int classNo,
			@PathVariable("boardNo") int boardNo, 
			Model model) {
	    
		Map<String, Object> map = new HashMap<>();
		map.put("classNo", classNo);
		map.put("boardNo", boardNo);
		
		Board board = boardService.selectDetail(map);
		model.addAttribute("className", board.getTitle());
		model.addAttribute("classAmount", board.getPayment());
		
		String orderNo = service.getOrderNo();
		model.addAttribute("orderNo", orderNo);
		
		
		return "payment/kakaoPayPopup"; // kakaoPayPopup.html
	}
	
	// 주문/결제정보 이동
	@PostMapping("/request-payment")
	public ResponseEntity<?> requestPayment() {
		
	    // 민감한 정보를 서버에서 추가하여 PortOne API 호출
	    String storeId = "store-ba2da415-5565-47a5-a9fc-9b9cddb04d32";
	    String channelKey = "channel-key-f6cb3945-2949-4400-994b-c6315da5140b";

	    // PortOne API 호출 로직 추가(RestTemplate이나 WebClient를 사용하여 API 호출)
	    
	    Map<String, String> map
	     = Map.of("storeId", storeId, "channelKey", channelKey);
	    
	    
	    return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
	}

	// 결제 내역 정보
	@GetMapping("/paymentDetail")
    public String getPaymentDetail(@RequestParam("paymentId") String paymentId, Model model) {
        
		// 결제 ID를 사용하여 결제 정보를 가져옵니다.
        Payment paymentDetail = service.getPaymentDetail(paymentId);
        
        // 가져온 결제 정보를 모델에 추가합니다.
        model.addAttribute("paymentId", paymentDetail.getPaymentId());
        model.addAttribute("orderName", paymentDetail.getClassTitle());
        model.addAttribute("totalAmount", paymentDetail.getPaymentAmount());
        model.addAttribute("currency", paymentDetail.getCurrency());
        model.addAttribute("customerName", paymentDetail.getCustomerName());
        
        return "paymentDetail";
    }
	
}
