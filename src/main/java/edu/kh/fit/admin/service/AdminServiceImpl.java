package edu.kh.fit.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.admin.dto.Admin;
import edu.kh.fit.admin.dto.Query;
import edu.kh.fit.admin.mapper.AdminMapper;
import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
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
	public List<Query> selectQueryList(int memberNo) {
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
	
	@Override
	public int changeStatus(int memberNo) {
		return mapper.changeStatus(memberNo);
	}
	
	
	// 강의 매출 가져오기
	@Override
	public Map<String, Object> classList(int cp) {
		
		int listCount = mapper.classListCount();
		Pagination pagination = new Pagination(cp, listCount, 10, 5);
		
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		List<Board> classList = mapper.classList(bounds);
		
		Map<String, Object> map = new HashMap<>();
    map.put("classList", classList);
    map.put("pagination", pagination);
		
		return map;
	}
	@Override
	public Map<String, Object> boardList(int cp) {
		int listCount = mapper.boardListCount();
		Pagination pagination = new Pagination(cp, listCount, 10, 5);
		
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		List<Board> boardList = mapper.boardList(bounds);
		
		Map<String, Object> map = new HashMap<>();
	  map.put("boardList", boardList);
	  map.put("pagination", pagination);
		
		return map;
	}

}
