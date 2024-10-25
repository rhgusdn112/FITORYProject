package edu.kh.fit.payment.service;

import edu.kh.fit.payment.dto.Order;

public interface OrderService {

	// 결제정보 호출하기
	boolean insertOrder(Order order);

	// 주문 결과 불러오기
	Order selectOrder(String orderNo);

}
