package edu.kh.fit.board.search.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.fit.board.dto.Board;
import lombok.RequiredArgsConstructor;

public interface SearchService {

	Map<String, Object> searchByCategoryAndKeyword( String keyword);


}