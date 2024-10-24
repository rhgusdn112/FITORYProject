package edu.kh.fit.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardMapper mapper;
	
	// 게시글 상세 조회
	@Override
		public Board selectDetail(Map<String, Object> map) {
			return mapper.selectDetail(map);
		}
	
  // 리뷰 목록 가져오기
  @Override
  public List<Comment> selectCommentList(int boardNo) {
      return mapper.selectCommentList(boardNo);
  }
  
  // 사용자가 해당 게시글에 이미 리뷰를 작성했는지 확인
  @Override
  public boolean hasReviewed(Map<String, Object> params) {
      return mapper.hasReviewed(params) > 0;
  }
}
