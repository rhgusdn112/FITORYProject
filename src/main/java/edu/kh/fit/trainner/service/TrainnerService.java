package edu.kh.fit.trainner.service;

import edu.kh.fit.trainner.dto.Trainner;

public interface TrainnerService {
	
	/** (강사) 로그인 서비스
	 * @param trainnerEmail
	 * @param trainnerPw
	 * @return
	 */
	Trainner trainnerLogin(String trainnerEmail, String trainnerPw);

}
