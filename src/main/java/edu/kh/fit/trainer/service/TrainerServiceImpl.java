package edu.kh.fit.trainer.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.common.exception.FileUploadFailException;
import edu.kh.fit.common.util.FileUtil;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.trainer.dto.Trainer;
import edu.kh.fit.trainer.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;

@Service
public class TrainerServiceImpl implements TrainerService{
	
	@Autowired
	private TrainerMapper mapper;
	@Autowired
	private BCryptPasswordEncoder encorder;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // 웹 접근 경로
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // 이미지 저장 서버 경로
	
	// (강사) 로그인 서비스
	@Override
		public Trainer trainerLogin(String trainerEmail, String trainerPw) {
			
			Trainer trainerLogin = mapper.trainerLogin(trainerEmail);
			
			if(trainerLogin == null)	return null;

//			if( !encorder.matches(encorder.encode(trainerPw), trainerLogin.getTrainerPw())){
//				return null;
//			}
				
			return trainerLogin;
		}
	
	// (강사) 회원 가입
	@Override
	public int signUp(Trainer inputTrainer) {
		
		String encPw = encorder.encode(inputTrainer.getTrainerPw());
		inputTrainer.setTrainerPw(encPw);
		
		
		int result = mapper.signUp(inputTrainer);
						
		return result;
	}

	// 강사 정보 수정 비밀번호 확인
	@Override
	public boolean trainerCheckPw(int trainerNo, String trainerPw) {
		String encodePw = mapper.trainerCheckPw(trainerNo);
		// 강사 자격사항 mapper 추가
		
		return encorder.matches(trainerPw, encodePw);
	}

	// 강사 정보 수정
	@Override
	public int updateTrainer(Trainer inputTrainer) {
		return mapper.updateTrainer(inputTrainer);
	}

	// 프로필 사진 수정
	@Override
	public String profile(MultipartFile imgProfileList, int trainerNo) {
		if(imgProfileList.isEmpty()) {
			int result = mapper.profile(null, trainerNo);
			return null;
		}
		String rename = FileUtil.rename(imgProfileList.getOriginalFilename());
		String url = profileWebPath + rename;
		
		int result = mapper.profile(url, trainerNo);
		if(result == 0) return null; 
		try {
			File folder = new File(profileFolderPath);
			if(!folder.exists()) folder.mkdirs();
			imgProfileList.transferTo(new File(profileFolderPath + rename));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileUploadFailException("프로필 이미지 수정에 실패하였습니다.");
		}
		return profileWebPath + rename;
	}

	/* 강사 강의 목록 조회 */
	@Override
	public List<Board> classList(int trainerNo) {
		return mapper.classList(trainerNo);
	}

	/* 강사 상세정보 조회 */
	@Override
	public Map<String, Object> detailTrainer(int trainerNo, int cp) {
		// 전체 리뷰/문의 개수 조회
		int listCount = mapper.qualificationList(trainerNo);
		
		// 페이지네이션 계산
		Pagination pagination = new Pagination(cp, listCount, 10, 10);
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		
		List<Trainer> reviewList = mapper.detailTrainer(trainerNo, bounds);
		
		
		Map<String, Object> map = Map.of("reviewList", reviewList, "pagination", pagination);
		return map;
	}

}
