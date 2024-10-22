package edu.kh.fit.homeTraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeTrainingController {

  // 클래스 목록을 보여주는 페이지
  @GetMapping("/hometraining")
  public String getClassList() {
      return "home-training";
  }
}