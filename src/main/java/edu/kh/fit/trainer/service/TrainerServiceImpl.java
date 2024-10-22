package edu.kh.fit.trainer.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.fit.trainer.dto.Trainer;
import edu.kh.fit.trainer.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrainerServiceImpl implements TrainerService{
	
	private final TrainerMapper mapper;
	private final BCryptPasswordEncoder encorder;
	
	// (강사) 로그인 서비스
	@Override
		public Trainer trainerLogin(String trainerEmail, String trainerPw) {
			
			Trainer trainerLogin = mapper.trainerLogin(trainerEmail);
			
			if(trainerLogin == null)	return null;

			if( !encorder.matches(encorder.encode(trainerPw), trainerLogin.getTrainerPw())){
				return null;
			}
				
			return trainerLogin;
		}
	
	// (강사) 회원 가입
	@Override
	public int signUp(Trainer inputTrainer) {
		
		String encPw = encorder.encode(inputTrainer.getTrainerPw());
		inputTrainer.setTrainerPw(encPw);
		
		if(inputTrainer.getTrainerAddress().equals(",,")) {
			inputTrainer.setTrainerAddress(null);
		}
		
		
		int result = mapper.signUp(inputTrainer);
						
		return result;
	}
}
