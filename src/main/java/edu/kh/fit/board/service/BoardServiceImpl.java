package edu.kh.fit.board.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.board.mapper.BoardMapper;
import edu.kh.fit.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:/config.properties")
public class BoardServiceImpl implements BoardService {

    private final BoardMapper mapper;
    
  	@Value("${my.board.web-path}")
  	private String webPath;
  	
  	@Value("${my.board.folder-path}")
  	private String folderPath;

    /**
     * 게시글 상세 조회 서비스 메서드
     * @param map 게시글 조회에 필요한 파라미터 (게시글 번호, 클래스 번호 등)
     * @return 조회된 게시글 객체
     */
    @Override
    public Board selectDetail(Map<String, Object> map) {
        return mapper.selectDetail(map);
    }

    /**
     * 댓글 목록 조회 서비스 메서드
     * @param boardNo 게시글 번호
     * @return 해당 게시글의 댓글 목록
     */
    @Override
    public List<Comment> selectCommentList(int boardNo) {
        return mapper.selectCommentList(boardNo);
    }

    /**
     * 사용자가 이미 리뷰를 작성했는지 확인하는 서비스 메서드
     * @param params 리뷰 작성 확인에 필요한 파라미터 (회원 번호, 게시글 번호 등)
     * @return 리뷰 작성 여부 (true: 작성함, false: 작성하지 않음)
     */
    @Override
    public boolean hasReviewed(Map<String, Object> params) {
        return mapper.hasReviewed(params) > 0;
    }

    /**
     * 사용자가 해당 게시글에 대해 결제했는지 여부를 확인하는 서비스 메서드
     * @param memberNo 회원 번호
     * @param boardNo 게시글 번호
     * @return 결제 여부 (true: 결제함, false: 결제하지 않음)
     */
    @Override
    public boolean checkPaymentStatus(int memberNo, int boardNo) {
      Map<String, Object> params = Map.of("memberNo", memberNo, "boardNo", boardNo);
      int paymentCount = mapper.hasPaidForBoard(params);
      return paymentCount > 0; // 결제가 있으면 true 반환
  }


    // ------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------


    /**
     * 게시글 목록 조회
     * @param classNo
     * @return map
     */
    @Override
    public Map<String, Object> selectBoardMain(int classNo) {

        // 인기 클래스(평점 높은 순 8개)
        List<Board> popularClass = mapper.selectPopularClass(classNo);


        // 최근 클래스(최근 등록일 순 8개)
        List<Board> recentClass = mapper.selectRecentClass(classNo);


        // 나머지 클래스(등록일 순)
        int listCount = mapper.getClassListCount(classNo);
        Pagination pagination = new Pagination(1, listCount, 8, 10);

        log.debug("pagination : {}", pagination);

        RowBounds rowBounds = new RowBounds(0, 8);
        List<Board> classList = mapper.selectClassList(classNo, "latest", rowBounds);


        Map<String, Object> map = new HashMap<>();
        map.put("popularClass", popularClass);
        map.put("recentClass", recentClass);
        map.put("classList", classList);
        map.put("pagination", pagination);

        return map;
    }

    /**
     * 실시간 클래스 비동기 조회(페이지, 정렬)
     * @param classNo
     * @param cp
     * @param sort
     * @return map
     */
    @Override
    public Map<String, Object> selectClassList(int trainerNo, int cp, String sort) {

      // 나머지 클래스(등록일 순)
      int listCount = mapper.getClassListCount(trainerNo);
      Pagination pagination = new Pagination(cp, listCount, 8, 10);

      log.debug("pagination : {}", pagination);

      int limit = 8;
      int offset = (cp- 1) * limit;
      RowBounds rowBounds = new RowBounds(offset, limit);
      List<Board> classList = mapper.selectClassList(trainerNo, sort, rowBounds);

      Map<String, Object> map = new HashMap<>();
      map.put("classList", classList);
      map.put("pagination", pagination);

      return map;
    	
    }
    

    /** 게시물 등록
     * @param classNo
     * @return result
     */
    @Override
    public int insertBoard( Board inputBoard, MultipartFile images) {
    	
    	String rename = FileUtil.rename(images.getOriginalFilename());
    	
    	inputBoard.setThumbnail(webPath+rename);
    	
    	int result = mapper.insertBoard(inputBoard);
    	
    	if(result > 0) {
    		try {
    			images.transferTo(new File(folderPath+rename));
    		}catch (Exception e) {
    			e.printStackTrace();
    			throw new RuntimeException("실패");
    		}
    	}
    	
    	return result;
    }

    	
}
