package edu.kh.fit.admin.service;

import java.util.List;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.admin.dto.Query;
import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.main.dto.Report;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.trainer.dto.Trainer;

public interface AdminService {

	/** 관리자 로그인
	 * @param adminEmail
	 * @param adminPw
	 * @return
	 */
	Admin adminLogin(String adminEmail, String adminPw);

	/** 회원 목록 조회
	 * @return
	 */
	List<Member> memberList();

	/** 강사 목록 조회
	 * @return
	 */
	List<Trainer> trainerList();

	/** 주문내역 조회
	 * @param memberNo
	 * @return
	 */
	List<Order> selectOrderList(int memberNo);

	/** 댓글 목록 조회
	 * @param memberNo
	 * @return commentList
	 */
	List<Comment> selectCommentList(int memberNo);

	/** 문의 내역 조회
	 * @param memberNo
	 * @return
	 */
	String selectQueryList(int memberNo);

	/** 신고 내역 조회
	 * @param memberNo
	 * @return
	 */
	List<Report> selectReportList(int memberNo);

	
	/** 강사 문의 내역 조회
	 * @param trainerNo
	 * @return
	 */
	List<Query> queryList(int trainerNo);

	/** 강사 신고내역 조회
	 * @param trainerNo 
	 * @return
	 */
	List<Report> reportList(int trainerNo);

	/** 강사 게시물 
	 * @param trainerNo
	 * @return
	 */
	List<Board> selectBoardList(int trainerNo);

	// 강사 자격 정보 조회
	List<Trainer> selectQualificationList(int trainerNo);

	
	
	
}
