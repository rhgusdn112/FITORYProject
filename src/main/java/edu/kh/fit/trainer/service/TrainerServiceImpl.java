package edu.kh.fit.trainer.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.common.exception.FileUploadFailException;
import edu.kh.fit.common.util.FileUtil;
import edu.kh.fit.payment.dto.Order;
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
		return encorder.matches(trainerPw, encodePw);
	}

	// 강사 정보 수정
	@Override
	public int updateTrainer(Trainer inputTrainer) {
		return mapper.updateTrainer(inputTrainer);
	}

	// 프로필 사진 수정
	@Override
	public List<String> profile(List<MultipartFile> imgProfileList, int trainerNo) {
	    List<String> filePaths = new ArrayList<>();

	    File folder = new File(profileFolderPath);
	    if (!folder.exists()) {
	        folder.mkdirs(); // 저장 경로가 없으면 생성
	    }

	    try {
	        for (MultipartFile imgFile : imgProfileList) {
	            if (!imgFile.isEmpty()) {
	                // 고유 파일명 생성
	                String rename = FileUtil.rename(imgFile.getOriginalFilename());
	                String filePath = profileFolderPath + rename; // 서버 저장 경로
	                String url = profileWebPath + rename; // 웹 접근 경로

	                // 파일 저장
	                imgFile.transferTo(new File(filePath));
	                System.out.println("저장된 파일 경로: " + filePath);
	                filePaths.add(url); // 웹 경로 추가
	            }
	        }

	        // 필요한 파일 경로 수를 맞추기 위해 null 추가
	        while (filePaths.size() < 2) {
	            filePaths.add(null);
	        }

	        System.out.println("DB에 업데이트할 파일 경로들: " + filePaths);

	        // DB 업데이트
	        if (!filePaths.isEmpty()) {
	            mapper.updateProfileImages(filePaths, trainerNo);
	        } else {
	            System.out.println("업데이트할 파일이 없습니다.");
	            mapper.updateProfileImages(null, trainerNo);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new FileUploadFailException("프로필 이미지 수정에 실패하였습니다. 에러: " + e.getMessage());
	    }

	    return filePaths;
	}




	
	/* 강사 강의 목록 조회 */
	@Override
	public List<Board> classList(int trainerNo) {
		return mapper.classList(trainerNo);
	}

	/* 강사 상세정보 조회 */
	@Override
	public List<Trainer> detailTrainer(Trainer trainerNo) {
		return mapper.detailTrainer(trainerNo);
	}

}
