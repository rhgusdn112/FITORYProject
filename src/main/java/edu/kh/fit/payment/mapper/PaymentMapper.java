package edu.kh.fit.payment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.payment.dto.PaymentDTO;

@Mapper
public interface PaymentMapper {

	/** 결제정보 얻어오기
	 * @param boardNo
	 * @return
	 */
	PaymentDTO selectPaymentClass(int boardNo);


	
}
