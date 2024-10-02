package edu.kh.fit.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.fit.main.service.MainService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class Maincontroller {
	
	private final MainService service;
	
	
	
}
