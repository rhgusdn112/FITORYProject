package edu.kh.fit.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.PaymentDTO;
import edu.kh.fit.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

	private final PaymentMapper mapper;
	
	// 결제정보 얻어오기
	@Override
	public PaymentDTO selectPaymentClass(int boardNo) {
		return mapper.selectPaymentClass(boardNo);
	}
	
}
	
