package edu.kh.fit.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.fit.admin.dto.Query;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.Order;

@Mapper
public interface MemberMapper {

	/** 로그인 (회원)
	 * @param memberEmail
	 * @return
	 */
	Member memberLogin(String memberEmail);

	/** 회원 가입(회원)
	 * @param inputMember
	 * @return
	 */
	int signUp(Member inputMember);

	/** 이메일 중복검사
	 * @param email
	 * @return
	 */
	int emailCheck(String email);

	/** 회원 정보 수정 비밀번호 확인
	 * @param memberNo
	 * @return
	 */
	String memberCheckPw(int memberNo);
	
	/** 회원 정보 수정
	 * @param inputMember
	 * @return
	 */
	int updateMember(Member inputMember);
	
	/** 회원 결제 강의 개수
	 * @param memberNo
	 * @return
	 */
	int getMyClassListCount(int memberNo);
	
	/** 회원 결제 강의 목록 조회
	 * @param memberNo
	 * @param bounds
	 * @return
	 */
  List<Order> classList(int memberNo, RowBounds bounds);

  /** 리뷰 리스트
   * @param memberNo
   * @return
   */
	List<Comment> selectMyReviewList(int memberNo, RowBounds bounds);

	/** 전체 리뷰/문의 개수 조회
	 * @param memberNo
	 * @return
	 */
	int getMyReviewCount(int memberNo);

	// 회원 탈퇴
	int statusChange(int memberNo);

	// 비밀번호 찾기
	String findPw(String email);

	// 비밀번호 변경
	int updatePw(String password);

	
}
