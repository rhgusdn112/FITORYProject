package edu.kh.fit.homeGym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import edu.kh.fit.homeGym.service.HomeGymService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeGymController {

	private final HomeGymService service;
	
  // 클래스 목록을 보여주는 페이지
  @GetMapping("/homeGym")
  public String getClassList() {
      return "home-gym";
  }
}