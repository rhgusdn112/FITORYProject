package edu.kh.fit.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentsController {

	@GetMapping("admin/contents")
	public String getContentsList() {
		
		return "/admin/contents";
	}
}
