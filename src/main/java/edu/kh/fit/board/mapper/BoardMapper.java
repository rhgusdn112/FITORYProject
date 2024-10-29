package edu.kh.fit.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;

@Mapper
public interface BoardMapper {

  /**
   * 게시글 상세 조회 Mapper 메서드
   * @param map 게시글 조회에 필요한 파라미터 (게시글 번호, 클래스 번호 등)
   * @return 조회된 게시글 객체
   */
  Board selectDetail(Map<String, Object> map);

  /**
   * 댓글 목록 조회 Mapper 메서드
   * @param boardNo 게시글 번호
   * @return 해당 게시글의 댓글 목록
   */
  List<Comment> selectCommentList(int boardNo);

  /**
   * 사용자가 이미 리뷰를 작성했는지 확인하는 Mapper 메서드
   * @param params 리뷰 작성 확인에 필요한 파라미터 (회원 번호, 게시글 번호 등)
   * @return 리뷰 작성 여부 (작성한 리뷰 수)
   */
  int hasReviewed(Map<String, Object> params);

  /**
   * 사용자가 해당 게시글에 대해 결제했는지 여부를 확인하는 Mapper 메서드
   * @param params 결제 확인에 필요한 파라미터 (회원 번호, 게시글 번호 등)
   * @return 결제 여부 플래그 ('Y' 또는 'N')
   */
  String hasPaidForBoard(Map<String, Object> params);


  // ------------------------------------------------------------------------------------------------------
  // ------------------------------------------------------------------------------------------------------

  /**
   * 인기 클래스 조회 Mapper 메서드
   * @param classNo 클래스 번호
   * @return 인기 클래스 목록
   */
  List<Board> selectPopularClass(int classNo);

  /**
   * 최근 클래스 조회 Mapper 메서드
   * @param classNo 클래스 번호
   * @return 최근 클래스 목록
   */ 
  List<Board> selectRecentClass(int classNo);

  /**
   * 나머지 클래스 조회 Mapper 메서드
   * @param classNo 클래스 번호
   * @return 나머지 클래스 목록
   */
  List<Board> selectClassList(@Param("classNo") int classNo, @Param("sort") String sort, RowBounds rowBounds);

  /**
   * 클래스 목록 총 개수 조회 Mapper 메서드
   * @param classNo
   * @return
   */
  int getClassListCount(int classNo);
  
  
}