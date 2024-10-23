package edu.kh.fit.payment.service;

import java.util.List;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.PaymentDTO;

public interface PaymentService {

	/** 결제정보 얻어오기
	 * @param boardNo
	 * @return 
	 */
	PaymentDTO selectPaymentClass(int boardNo);



}
