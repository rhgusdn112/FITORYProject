package edu.kh.fit.homeTraining.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/homeTraining")
@Controller
public class HomeTrainingController {

  // 클래스 목록을 보여주는 페이지
  @GetMapping("")
  public String getClassList() {
      return "homeTraining/home-training";
  }
}