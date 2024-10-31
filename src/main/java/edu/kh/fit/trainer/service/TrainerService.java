package edu.kh.fit.trainer.service;

import java.util.List;
import java.util.Map;

import edu.kh.fit.board.dto.Board;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.trainer.dto.Trainer;

public interface TrainerService {

	/** (강사) 로그인 서비스
	 * @param trainnerEmail
	 * @param trainnerPw
	 * @return
	 */
	Trainer trainerLogin(String trainerEmail, String trainerPw);

	/** (강사) 회원가입
	 * @param inputTrainner
	 * @return
	 */
	Map<String, Object> singUp(Trainer inputTrainer, List<String> qNameList, List<String> qDateList);

	/** 강사 정보 수정 비밀번호 확인
	 * @param trainerNo
	 * @param trainerPw
	 * @return
	 */
	boolean trainerCheckPw(int trainerNo, String trainerPw);

	/** 강사 정보 수정
	 * @param inputTrainer
	 * @param qDateList
	 * @param qNameList
	 * @param imgProfileList
	 * @return
	 */
	Map<String, Object> updateTrainer(Trainer inputTrainer, List<MultipartFile> imgProfileList, List<String> qNameList,
			List<String> qDateList);

	/** 강사 상세 정보 조회
	 * @param trainerNo
	 * @param cp
	 * @return
	 */
	Map<String, Object> detailTrainer(int trainerNo, int cp);

	/** 강사 상세 정보 조회 후 해당 강사 영상목록 조회
	 * @param trainerNo
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectClassList(int trainerNo, int cp, String sort);

	/** 강사 강의 목록 조회
	 * @param trainerNo
	 * @param cp
	 * @return
	 */
	Map<String, Object> trainerClassList(int trainerNo, int cp);

	/** 이메일 중복체크
	 * @param email
	 * @return
	 */
	int emailCheck(String email);

}
