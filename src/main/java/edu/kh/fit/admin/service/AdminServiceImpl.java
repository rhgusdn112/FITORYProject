package edu.kh.fit.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.admin.dto.Query;
import edu.kh.fit.admin.mapper.AdminMapper;
import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.main.dto.Report;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.payment.dto.Order;
import edu.kh.fit.trainer.dto.Trainer;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper mapper;

	// 관리자 로그인
	@Override
		public Admin adminLogin(String adminEmail, String adminPw) {

		
		Admin adminLogin = mapper.adminLogin(adminEmail);
		
		if(adminLogin == null)	return null;

//		if( !encorder.matches(encorder.encode(adminPw), adminLogin.getPw()) ){
//			return null;
//		}
			
		return adminLogin;
		}
	
	// 회원 목록 조회
	@Override
	public List<Member> memberList() {
		return mapper.memberList();
	}
	
	// 강사 목록 조회
	@Override
	public List<Trainer> trainerList() {
		return mapper.trainerList();
	}
	
	// 주문내역 조회
	@Override
	public List<Order> selectOrderList(int memberNo) {
		return mapper.selectOrderList(memberNo);
	}
	
	// 댓글 목록 조회
	@Override
	public List<Comment> selectCommentList(int memberNo) {
		return mapper.selectCommentList(memberNo);
	}
	
	// 문의 내역 조회
	@Override
	public String selectQueryList(int memberNo) {
		return mapper.selectQueryList(memberNo);
	}
	
	// 신고 내역 조회
	@Override
	public List<Report> selectReportList(int memberNo) {
		return mapper.selectReportList(memberNo);
	}
	
	// 강사 문의 내역 조회
	@Override
	public List<Query> queryList(int trainerNo) {
		return mapper.queryList(trainerNo);
	}
	
	// 강사 신고 내역 조회
	@Override
	public List<Report> reportList(int trainerNo) {
		return mapper.reportList(trainerNo);
	}

	// 강사 게시물 조회
	@Override
	public List<Board> selectBoardList(int trainerNo) {
		return mapper.selectBoardList(trainerNo);
	}
	
	// 강사 자격정보 조회
	@Override
	public List<Trainer> selectQualificationList(int trainerNo) {
		return mapper.selectQualificationList(trainerNo);
	}
}
