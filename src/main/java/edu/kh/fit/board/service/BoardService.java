package edu.kh.fit.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;

public interface BoardService {

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Board selectDetail(Map<String, Object> map);
	
  // 리뷰 목록 가져오기
  List<Comment> selectCommentList(int boardNo);


	// 사용자가 해당 게시글에 이미 리뷰를 작성했는지 확인
	boolean hasReviewed(Map<String, Object> params);

	
  /**
   * 사용자가 해당 게시글에 대해 결제했는지 여부 확인
   * @param memberNo
   * @param boardNo
   * @return 결제 여부 (true/false)
   */
  boolean checkPaymentStatus(int memberNo, int boardNo);


  /**
   * 게시글 목록 조회
   * @param classNo
   * @return map
   */
  Map<String, Object> selectBoardMain(int classNo);

  /**
   * 실시간 클래스 비동기 조회(페이지, 정렬)
   * @param classNo
   * @param cp
   * @param sort
   * @return map
   */
  Map<String, Object> selectClassList(int classNo, int cp, String sort);

  /** 게시물 등록
   * @param classNo
   * @param inputBoard
   * @param images
   * @return
   */
	int insertBoard(Board inputBoard, MultipartFile images);
}


