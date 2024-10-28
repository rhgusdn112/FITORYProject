package edu.kh.fit.board.search.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.fit.board.search.service.SearchService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final SearchService service;

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        Map<String, Object> map = service.searchByCategoryAndKeyword(keyword);

        model.addAttribute("homeGymVideos", map.get("homeGymVideos"));
        model.addAttribute("homeTrainingVideos", map.get("homeTrainingVideos"));
        model.addAttribute("keyword", keyword);

        return "search/results";
    }
}
