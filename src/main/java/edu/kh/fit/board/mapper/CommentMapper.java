package edu.kh.fit.board.mapper;

import edu.kh.fit.board.dto.Comment;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {

    /**
     * 댓글 등록
     * 
     * @param comment 댓글 정보
     * @return 삽입된 댓글 번호
     */
    int insertComment(Comment comment);

    /**
     * 댓글 수정
     * 
     * @param comment 댓글 정보
     * @return 수정된 댓글 번호
     */
    int updateComment(Comment comment);

    /**
     * 댓글 삭제
     * 
     * @param commentNo 댓글 번호
     * @param memberNo  회원 번호
     * @return 삭제 결과
     */
    int deleteComment(Comment comment);

    /**
     * 댓글 작성자 번호 조회
     *
     * @param commentNo 댓글 번호
     * @return 작성자 회원 번호
     */
    int selectWriterByCommentNo(int commentNo);

}
