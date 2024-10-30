package edu.kh.fit.board.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttribute;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.service.CommentService;
import edu.kh.fit.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 등록
     * @param comment : 요청 시 body에 JSON 형태로 담겨져 제출된 데이터를 DTO로 변환한 객체
     * @param loginMember : 로그인한 회원 정보
     * @return commentNo : 삽입된 댓글 번호
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> commentInsert(@RequestBody Comment comment, @SessionAttribute("memberLogin") Member loginMember) {
      comment.setMemberNo(loginMember.getMemberNo());
      log.info("댓글 등록 요청: {}", comment);
      int result = commentService.commentInsert(comment);

      Map<String, Object> response = new HashMap<>();
      if(result > 0) {
          response.put("message", "댓글 등록 성공");
          response.put("commentNo", result);
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
      } else {
          response.put("message", "댓글 등록 실패");
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }
  }


    /**
     * 댓글 수정
     * @param comment : 수정할 댓글 내용
     * @param loginMember : 로그인한 회원 정보
     * @return 수정된 댓글 번호
     */
    @PutMapping
    public ResponseEntity<Map<String, Object>> commentUpdate(@RequestBody Comment comment, @SessionAttribute("memberLogin") Member loginMember) {
      log.info("댓글 수정 요청: {}", comment); // 수정된 댓글 정보 로그
      log.info("댓글 번호: {}, 수정된 내용: {}, 수정된 별점: {}", comment.getCommentNo(), comment.getReviewContent(), comment.getNumber());
      comment.setMemberNo(loginMember.getMemberNo());
      int result = commentService.commentUpdate(comment);

      Map<String, Object> response = new HashMap<>();
      if(result > 0) {
          response.put("message", "댓글 수정 성공");
          response.put("commentNo", comment.getCommentNo());
          response.put("updatedRating", comment.getNumber());  // 별점 업데이트 확인용 응답에 포함
          return ResponseEntity.status(HttpStatus.OK).body(response);
      } else {
          response.put("message", "댓글 수정 실패");
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }
    }


    /**
     * 댓글 삭제
     * @param commentNo : 삭제하려는 댓글 번호
     * @param loginMember : 로그인한 회원 정보
     * @return 삭제 결과
     */
    @DeleteMapping
    public ResponseEntity<Map<String, Object>> commentDelete(@RequestBody int commentNo, @SessionAttribute("memberLogin") Member loginMember) {
      log.info("댓글 삭제 요청 - 댓글 번호: {}, 회원 번호: {}", commentNo, loginMember.getMemberNo());
      int result = commentService.commentDelete(commentNo, loginMember.getMemberNo());

      Map<String, Object> response = new HashMap<>();
      if(result > 0) {
          response.put("message", "댓글 삭제 성공");
          return ResponseEntity.status(HttpStatus.OK).body(response);
      } else {
          response.put("message", "댓글 삭제 실패");
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
      }
  }

}
