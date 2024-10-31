package edu.kh.fit.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("customerService")
public class CustomerController {

	@GetMapping("faq")
	public String faq() {
		return "/customerService/faq";
	}
}
