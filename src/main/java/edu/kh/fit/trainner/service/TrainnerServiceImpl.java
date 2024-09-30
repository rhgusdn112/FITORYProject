package edu.kh.fit.trainner.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.trainner.dto.Trainner;
import edu.kh.fit.trainner.mapper.TrainnerMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainnerServiceImpl implements TrainnerService{
	
	private final TrainnerMapper mapper;
	private final BCryptPasswordEncoder encoder;
	
	// (강사) 로그인 서비스
	@Override
		public Trainner trainnerLogin(String trainnerEmail, String trainnerPw) {
			
			Trainner trainnerLogin = mapper.trainnerLogin(trainnerEmail);
			
			if(trainnerLogin == null)	return null;

			if( !encoder.matches(encoder.encode(trainnerPw), trainnerLogin.getTrainnerPw())){
				return null;
			}
				
			return trainnerLogin;
		}
}
