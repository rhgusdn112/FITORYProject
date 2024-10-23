package edu.kh.fit.mypage.controller;

import org.springframework.stereotype.Controller;

import edu.kh.fit.mypage.service.TrainerMyPageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TrainerMyPageController {
	private final TrainerMyPageService service;
}
