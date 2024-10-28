package edu.kh.fit.member.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.fit.admin.dto.Query;
import edu.kh.fit.board.dto.Comment;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.mapper.MemberMapper;
import edu.kh.fit.payment.dto.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper mapper;
	private final BCryptPasswordEncoder encorder;
	
	// (회원) 로그인 서비스
	@Override
		public Member memberLogin(String memberEmail, String memberPw) {
			
			Member memberLogin = mapper.memberLogin(memberEmail);
			
			if(memberLogin == null)	return null;

//			if( !encorder.matches(encorder.encode(memberPw), memberLogin.getMemberPw()) ){
//				return null;
//			}
				
			return memberLogin;
		}

	// (회원) 회원가입
	@Override
	public int signUp(Member inputMember) {

		String encPw = encorder.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		
		int result = mapper.signUp(inputMember);
		
		
		return result;
	}
	
	// 이메일 중복검사
	@Override
	public int emailCheck(String email) {
		return mapper.emailCheck(email);
	}

	
	
	// 회원 정보 수정 비밀번호 확인
	@Override
	public boolean memberCheckPw(int memberNo, String memberPw) {
		// log.debug("암호화 : {}", encorder.encode(memberPw));
		
		String encodePw = mapper.memberCheckPw(memberNo);
		return encorder.matches(memberPw, encodePw);
	}

	// 회원 정보 수정
	@Override
	public int updateMember(Member inputMember) {
		return mapper.updateMember(inputMember);
	}
	
  // 회원 결제 강의 목록 조회
  @Override
  public List<Order> classList(int memberNo) {
      return mapper.classList(memberNo);
  }

	@Override
	public Map<String, Object> memberMyActivities(int memberNo, int cp) {
		
		// 전체 리뷰/문의 개수 조회
		int listCount = mapper.getMyReviewCount(memberNo);
		
		// 페이지네이션 계산
		Pagination pagination = new Pagination(cp, listCount, 10, 10);
		
		// RowBounds 계산(앞에서 몇 행 건너 뛰고, 그 다음 몇 행 조회할 지 지정하는 객체)
		int limit = pagination.getLimit();
		int offset = (cp-1) * limit;
		
		RowBounds bounds = new RowBounds(offset, limit);
		
		
		List<Comment> reviewList = mapper.selectMyReviewList(memberNo, bounds);
		
		
		Map<String, Object> map = Map.of("reviewList", reviewList, "pagination", pagination);
		
		
		return map;
	}
	


}
