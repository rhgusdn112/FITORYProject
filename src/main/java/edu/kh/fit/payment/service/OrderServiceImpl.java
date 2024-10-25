package edu.kh.fit.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.payment.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
	
	private final OrderMapper mapper;
	
	// 결제 정보 호출하기
	@Override
	public boolean insertOrder(Order order) {
		int result = mapper.insertOrder(order);
		return result> 0;
	}

}
