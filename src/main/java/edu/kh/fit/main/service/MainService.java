package edu.kh.fit.main.service;

import java.util.Map;

public interface MainService {

  /**
   * 메인 페이지 조회
   * @return
   */
  Map<String, Object> mainPage();

  // 비밀번호 변경
	int update(String email, String password);

}
