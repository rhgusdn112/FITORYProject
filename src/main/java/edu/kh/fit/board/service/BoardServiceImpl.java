package edu.kh.fit.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper mapper;

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
        String paymentFlag = mapper.hasPaidForBoard(params);
        return "Y".equals(paymentFlag);
    }
}
