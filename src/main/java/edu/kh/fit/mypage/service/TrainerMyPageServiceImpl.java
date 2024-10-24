package edu.kh.fit.mypage.service;

import java.io.File;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.common.exception.FileUploadFailException;
import edu.kh.fit.common.util.FileUtil;
import edu.kh.fit.mypage.mapper.TrainerMyPageMapper;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class TrainerMyPageServiceImpl implements TrainerMyPageService{
	private final TrainerMyPageMapper mapper;
	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // 웹 접근 경로
	@Value("${my.profile.folder-path}")
	private String profileFolderPath; // 이미지 저장 서버 경로

	/* 강사 정보 수정 */
	@Override
	public int trainerUpdate(Trainer updateTrainer) {
		return mapper.trainerUpdate(updateTrainer);
	}

	/* 프로필 사진 수정 */
	@Override
	public String profile(MultipartFile profileImg, int trainerNo) {
		if(profileImg.isEmpty()) {
			int result = mapper.profile(null, trainerNo);
			return null;
		}
		String rename = FileUtil.rename(profileImg.getOriginalFilename());
		String url = profileWebPath + rename;
		int result = mapper.profile(url, trainerNo);
		if(result == 0) return null;
		try {
			File folder = new File(profileFolderPath);
			if(!folder.exists()) folder.mkdirs();
			profileImg.transferTo(new File(profileFolderPath + rename));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileUploadFailException("프로필 이미지 수정에 실패하였습니다.");
		}

		return profileWebPath + rename;
	}

	@Override
	public int trainerLogin(Trainer updateTrainer) {
		return 0;
	}
}
