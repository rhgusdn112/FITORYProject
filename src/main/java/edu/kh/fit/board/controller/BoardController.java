package edu.kh.fit.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")

public class BoardController {

	
  @GetMapping("homeGym")
  public String getHomeGymList() {
      return "board/homeGym";
  }
  
  // 클래스 목록을 보여주는 페이지
  @GetMapping("homeTraining")
  public String getHomeTrainingList() {
      return "board/homeTraining";
  }

	@GetMapping("boardDetail")
	public String boardDetail() {

		return "board/boardDetail";
	}
}
