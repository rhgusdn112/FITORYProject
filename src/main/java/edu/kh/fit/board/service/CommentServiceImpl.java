package edu.kh.fit.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    /**
     * 댓글 등록
     *
     * @param comment 댓글 정보
     * @return 삽입된 댓글 번호
     */
    @Override
    public int commentInsert(Comment comment) {
      return commentMapper.insertComment(comment);
    }

    /**
     * 댓글 수정
     *
     * @param comment 댓글 정보
     * @return 수정된 댓글 번호
     */
    @Override
    public int commentUpdate(Comment comment) {
        // 비즈니스 로직 추가 : 리뷰 작성자 본인이 맞는지 확인
        int writerNo = commentMapper.selectWriterByCommentNo(comment.getCommentNo());
        if (writerNo != comment.getMemberNo()) {
            throw new IllegalStateException("댓글 작성자가 아니므로 수정할 수 없습니다.");
        }
        return commentMapper.updateComment(comment);
    }

    /**
     * 댓글 삭제
     *
     * @param commentNo 댓글 번호
     * @param memberNo  회원 번호
     * @return 삭제 결과
     */
    @Override
    public int commentDelete(int commentNo, int memberNo) {
        // 비즈니스 로직 추가: 댓글 작성자 본인이 맞는지 확인
        int writerNo = commentMapper.selectWriterByCommentNo(commentNo);
        if (writerNo != memberNo) {
            throw new IllegalStateException("댓글 작성자가 아니므로 삭제할 수 없습니다.");
        }
        return commentMapper.deleteComment(commentNo, memberNo);
    }

}