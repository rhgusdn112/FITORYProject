package edu.kh.fit.trainner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.member.dto.Member;
import edu.kh.fit.member.service.MemberService;
import edu.kh.fit.trainner.dto.Trainner;
import edu.kh.fit.trainner.service.TrainnerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainner")
@SessionAttributes("trainnerLogin")
public class TrainnerController {
	
	private final TrainnerService service;
	
	/**
	 * (강사)로그인 서비스
	 * @param trainnerEmail
	 * @param trainnerPw
	 * @param saveEmail
	 * @param model
	 * @param ra
	 * @param resp
	 * @return
	 */
	@PostMapping("login")
	public String trainnerLogin(
			@RequestParam("trainnerEmail") String trainnerEmail,
			@RequestParam("trainnerPw")		 String trainnerPw,
			@RequestParam(name = "saveEmail", required = false) String saveEmail,
			Model model,
			RedirectAttributes ra,
			HttpServletResponse resp
			) {
		
		Trainner trainnerLogin = service.trainnerLogin(trainnerEmail, trainnerPw);
		
		if(trainnerLogin == null) { // 로그인 실패
			ra.addFlashAttribute("message", "해당하는 정보가 없습니다.");
		}else { // 로그인 성공

					// loginMember를 session scope에 추가
					// 방법 1) HttpSession 이용
					// 방법 2) @SessionAttirbutes + Model 이용 방법
			
			/* Model을 이용해서 Session scope에 값 추가하는 방법 */
			// 1. model에 값 추가
			model.addAttribute("trainnerLogin", trainnerLogin);
			
			// 2. 클래스 선언부 위에 @SessionAttributes({"key"}) 추가
			//  -> key 값은 model에 추가된 key 값 "loginMember" 작성
			//   (request -> session)
			
			// @SessionAttributes :
			// Model에 추가된 값 중 session scope로 올리고 싶은 값의
			// key를 작성하는 어노테이션
			
			//----------------------------------------------------------------
			/* 이메일 저장 코드(Cookie) */
			
			// 1. Cookie 객체 생성
			Cookie cookie = new Cookie("saveEmail", trainnerEmail);
			
			// 2. 만들어진 Cookie가 사용된 경로(url)
			cookie.setPath("/"); // localhost 이하 모든 주소
			
			// 3. Cookie가 유지되는 시간(수명) 설정
			if(saveEmail == null) { // check X
				cookie.setMaxAge(0); // 만들어지자마자 만료
														 // == 기존에 만들어진 쿠키가 있으면 덮어씌우고 없어짐 
			}else { // check O
				cookie.setMaxAge(60 * 60 * 24 * 30); // 초단위로 작성
			}
		
			// 4. resp 객체에 추가해서 클라이언트에 전달
			resp.addCookie(cookie);
			
			
			//----------------------------------------------------------------
		}
		
		
		return "redirect:/"; // 메인페이지 리다이렉트
	}
}
