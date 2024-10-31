package edu.kh.fit.trainer.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.common.exception.FileUploadFailException;
import edu.kh.fit.common.util.FileUtil;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.trainer.dto.Qualification;
import edu.kh.fit.trainer.dto.Trainer;
import edu.kh.fit.trainer.mapper.TrainerMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
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

			if( !encorder.matches(trainerPw, trainerLogin.getTrainerPw())){
				return null;
			}
				
			return trainerLogin;
		}
	
	// (강사) 회원 가입
	@Override
	public Map<String, Object> singUp(Trainer inputTrainer, List<String> qNameList, List<String> qDateList) {
		
		Map<String, Object> resultMap = new HashMap<>();
		
		String encPw = encorder.encode(inputTrainer.getTrainerPw());
		inputTrainer.setTrainerPw(encPw);
		
		int result = mapper.signUp(inputTrainer);
		
		if(result <= 0) throw new RuntimeException("강사 회원가입 오류");
		resultMap.put("result", result);
		
		if(qNameList.size() > 0 && qDateList.size() > 0) {
			List<Qualification> qList = new ArrayList<>();
			for(int i=0 ; i < qNameList.size() ; i++) {
				Qualification q 
					= Qualification.builder()
						.qualification(qNameList.get(i))
						.qualificationDate(qDateList.get(i))
						.trainerNo(inputTrainer.getTrainerNo()).build();
				qList.add(q);
			}
			mapper.deleteQulification(inputTrainer.getTrainerNo());
			result = mapper.insertQulification(qList);
			if(result <= 0) throw new RuntimeException("트레이너 정보 수정 오류");
			resultMap.put("result", result);
			resultMap.put("qList", qList);
		}
						
		return resultMap;
	}
	
	// 이메일 중복체크
	@Override
	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}
	
	/* 강사 마이페이지 강의 목록 조회 */
	@Override
	public Map<String, Object> trainerClassList(int trainerNo, int cp) {
		
		int listCount = mapper.classListCount(trainerNo);
		Pagination pagination = new Pagination(cp, listCount, 4, 5);
		
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		List<Board> classList = mapper.classList(trainerNo, bounds);
		
		Map<String, Object> map = new HashMap<>();
    map.put("classList", classList);
    map.put("pagination", pagination);

    return map;
	}

	// 강사 정보 수정 비밀번호 확인
	@Override
	public boolean trainerCheckPw(int trainerNo, String trainerPw) {
		String encodePw = mapper.trainerCheckPw(trainerNo);
		
		return encorder.matches(trainerPw, encodePw);
	}

	// 강사 정보 수정
	@Override
	public Map<String, Object>  updateTrainer(Trainer inputTrainer, List<MultipartFile> imgProfileList, List<String> qNameList, List<String> qDateList) {
		Map<String, Object> resultMap = new HashMap<>();
		
		// 강사 이름, 전화번호 수정
		int result =  mapper.updateTrainer(inputTrainer); 
		if(result <= 0) throw new RuntimeException("트레이너 정보 수정 오류");
		resultMap.put("result", result);

		// 강사 자격사항 수정
		if(qNameList.size() > 0 && qDateList.size() > 0) {
			List<Qualification> qList = new ArrayList<>();
			for(int i=0 ; i < qNameList.size() ; i++) {
				Qualification q 
					= Qualification.builder()
						.qualification(qNameList.get(i))
						.qualificationDate(qDateList.get(i))
						.trainerNo(inputTrainer.getTrainerNo()).build();
				qList.add(q);
			}
			mapper.deleteQulification(inputTrainer.getTrainerNo());
			result = mapper.insertQulification(qList);
			if(result <= 0) throw new RuntimeException("트레이너 정보 수정 오류");
			resultMap.put("result", result);
			resultMap.put("qList", qList);
		}
		
		// 새로 업로드된 이미지가 없다면 수정없이 기존 이미지 유지
		if(imgProfileList.get(0).isEmpty()) return resultMap;
		
		// 이미지 수정
		List<String> renameList = new ArrayList<>();
		for(MultipartFile file : imgProfileList) {
			String rename = FileUtil.rename(file.getOriginalFilename());
			renameList.add(profileWebPath + rename);
			
			// 이미지 서버에 저장
			try {
				file.transferTo(new File(profileFolderPath + rename));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("트레이너 정보 수정 오류");
			}
		}
		
		// renamList는 4칸으로 만들기(나머지 칸은 null)
		if(renameList.size() < 4) {
			for(int i = renameList.size() - 1 ; i < 4 ; i++) {
				renameList.add(null);
			}
		}
		
		mapper.deleteTrainerImage(inputTrainer.getTrainerNo());
		result = mapper.insertTrainerImage(renameList, inputTrainer.getTrainerNo());
		resultMap.put("result", result);
		resultMap.put("renameList", renameList);
		return resultMap;
	}

	/* 강사 상세정보 조회 */
	@Override
	public Map<String, Object> detailTrainer(int trainerNo, int cp) {		
		
		// 강사 정보 조회
		Trainer trainer = mapper.detailTrainer(trainerNo);
		
		// -----------------------------------
		
		// 전체 자격 사항 수 조회
		int listCount = mapper.qualiCount(trainerNo);
		
		// 페이지네이션 계산
		Pagination pagination = new Pagination(cp, listCount, 10, 10);
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		// 자격사항 관련 mapper
		List<Qualification> qualiList = mapper.qualiList(trainerNo, bounds);

		trainer.setQualificationList(qualiList);
		
		Map<String, Object> map = Map.of("trainer", trainer, "pagination", pagination);
		return map;
	}
	
	/* 강사 상세조회 후 강의 목록 조회 */
	@Override
	public Map<String, Object> selectClassList(int trainerNo, int cp, String sort) {
		int listCount = mapper.pageList(trainerNo);
		Pagination pagination = new Pagination(cp, listCount, 4, 5);
		
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		List<Board> trainerClassList = mapper.trainerVideoDetail(trainerNo, sort, bounds);
		
		Map<String, Object> map = new HashMap<>();
    map.put("trainerClassList", trainerClassList);
    map.put("pagination", pagination);

    return map;
	}
	

}
