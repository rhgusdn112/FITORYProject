package edu.kh.fit.board.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.search.service.SearchService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService service;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
    	
        // 홈짐 관련 영상 게시물 검색
        // 홈트레이닝 관련 영상 게시물 검색
    	Map<String, Object> map = service.searchByCategoryAndKeyword(keyword);
        
        model.addAttribute("homeGymVideos", map.get("homeGymVideos"));
        model.addAttribute("homeTrainingVideos", map.get("homeTrainingVideos"));
        model.addAttribute("keyword", keyword);

        return "search/results";
    }
}