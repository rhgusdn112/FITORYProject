package edu.kh.fit.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.payment.dto.Order;

@Mapper
public interface OrderMapper {

	// 결제 정보 호출하기 
	int insertOrder(Order order);

}
