package edu.kh.fit.payment.mapper;


import org.apache.ibatis.annotations.Mapper;

import edu.kh.fit.payment.dto.Payment;

@Mapper
public interface PaymentMapper {

	/** 결제정보 얻어오기
	 * @param boardNo
	 * @return
	 */
	Payment selectPaymentClass(int boardNo);
	void updatePaymentStatus(int boardNo, String status);
	
}
