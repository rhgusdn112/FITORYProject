package edu.kh.fit.trainer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.fit.board.dto.Board;
import edu.kh.fit.board.dto.Pagination;
import edu.kh.fit.trainer.dto.Trainer;
import edu.kh.fit.trainer.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("trainer")
@SessionAttributes("trainerLogin")
public class TrainerController {
	
	private final TrainerService service;
	
	/** (강사)로그인 서비스
	 * @param trainerEmail
	 * @param trainerPw
	 * @param model
	 * @param ra
	 * @param request
	 * @param session
	 * @return 이전 페이지 또는 메인 페이지로 이동
	 */
	@PostMapping("login")
	public String trainerLogin(
	        @RequestParam("email") String trainerEmail,
	        @RequestParam("password") String trainerPw,
	        Model model,
	        RedirectAttributes ra,
	        HttpServletRequest request,
	        HttpSession session
	) {
	    // 첫 로그인 시도 시 이전 페이지 URL을 세션에 저장 (login 페이지는 무시)
	    if (session.getAttribute("prevPage") == null) {
	        String referer = request.getHeader("Referer");
	        if (referer != null && !referer.contains("/login")) { 
	            session.setAttribute("prevPage", referer);
	        }
	    }

	    Trainer trainerLogin = service.trainerLogin(trainerEmail, trainerPw);

	    if (trainerLogin == null) {
	        ra.addFlashAttribute("message", "해당하는 정보가 없습니다.");
	        return "redirect:/login";  // 로그인 실패 시 로그인 페이지로 리다이렉트
	    } else {
	        model.addAttribute("trainerLogin", trainerLogin);

	        // 로그인 성공 시 이전 페이지로 리다이렉트
	        String redirectUrl = (String) session.getAttribute("prevPage");
	        session.removeAttribute("prevPage");  // 이전 페이지 정보 제거
	        return redirectUrl != null ? "redirect:" + redirectUrl : "redirect:/main";  // 이전 페이지 또는 메인 페이지로 이동
	    }
	}


	/** 로그아웃
	 * @param status
	 * @param session
	 * @param request
	 * @return referer가 없으면 기본적으로 메인 페이지로 이동
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status, HttpSession session, HttpServletRequest request) {
	    // 세션에 저장된 로그인 정보 제거
	    status.setComplete();

	    // 이전 페이지 URL(prevPage)도 세션에서 제거
	    session.removeAttribute("prevPage");
	    
	    // 로그아웃 요청이 발생한 페이지로 리다이렉트
	    String referer = request.getHeader("Referer");
	    return referer != null ? "redirect:" + referer : "redirect:/main";  // referer가 없으면 기본적으로 메인 페이지로 이동
	}

	
	
	
	/** 회원가입
	 * @param inputMember
	 * @param ra
	 * @return
	 */
	@PostMapping("signUp")
	public String signUp(
			@ModelAttribute Trainer inputTrainer,
			RedirectAttributes ra	) {
		
		int result = service.signUp(inputTrainer);
		
		String message = null;
		String path    = null;
		
		if(result > 0) {
			path = "/";
			message 
				= inputTrainer.getTrainerNickname() + "님의 가입을 환영합니다";
		} else {
			path = "signUp";
			message = "회원 가입 실패...";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	@GetMapping("trainer")
	public String trainerPage() {
		return "trainer/trainer";
	}
	
	/** 비밀번호 확인페이지 이동
	 * @return
	 */
	@GetMapping("checkPw")
	public String trainerPw() {
		return "trainer/trainerCheckPw";
	}
	
	/** 회원 정보 수정 비밀번호 확인
	 * @param trainerLogin
	 * @param trainerPw
	 * @param ra
	 * @return
	 */
	@PostMapping("checkPw")
	public String memberCheckPw(@SessionAttribute("trainerLogin") Trainer trainerLogin,
															@RequestParam("trainerPw") String trainerPw,
															 RedirectAttributes ra) {
		boolean check = service.trainerCheckPw(trainerLogin.getTrainerNo(), trainerPw);
		String path = null;
		String message = null;
		if(check) {
			path = "trainerMyPage";
			message = "성공";
		} else {
			path = "checkPw";
			message = "실패";
		}
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}
	/** 강사 마이페이지 바로가기
	 * @param model
	 * @param trainerLogin
	 * @return
	 */
	@GetMapping("trainerMyPage")
	public String myPage(
			Model model, 
			@SessionAttribute("trainerLogin") Trainer trainerLogin
			) {
		model.addAttribute("currentPage", "trainerMyPage");
		
	  model.addAttribute("isLoggedIn", true);
		return "/myPage/trainerMyPage";
	}
	
	/** 강사 페이지 수정
	 * @param inputTrainer
	 * @param trainerLogin
	 * @param ra
	 * @return
	 */
	@PostMapping("trainerMyPage")
	public String updateInfo(
			@ModelAttribute Trainer inputTrainer, 
			@SessionAttribute("trainerLogin") Trainer trainerLogin,
			@RequestParam("imgProfile") List<MultipartFile> imgProfileList, 
			RedirectAttributes ra) {

		int trainerNo = trainerLogin.getTrainerNo();
		inputTrainer.setTrainerNo(trainerNo);

		int result = service.updateTrainer(inputTrainer);

		String message = null;
		if (result > 0) {
			message = "수정이 되었습니다.";
			trainerLogin.setTrainerNickname(inputTrainer.getTrainerNickname());
			trainerLogin.setTrainerTel(inputTrainer.getTrainerTel());
			trainerLogin.setProfileImg(inputTrainer.getProfileImg());
		} else
			message = "수정에 실패하였습니다.";

		ra.addFlashAttribute("message", message);

		return "redirect:/trainer/trainerMyPage";
	}
	
	/** 프로필 이미지 수정
	 * @param profileImg
	 * @param trainerLogin
	 * @param ra 
	 * @return
	 */
	@PostMapping("profile")
	public String profile(@RequestParam("imgProfile") MultipartFile imgProfileList,
												@SessionAttribute("trainerLogin") Trainer trainerLogin,
												RedirectAttributes ra) {
		
		// 1) login한 회원의 회원 번호 얻어오기
		int trainerNo = trainerLogin.getTrainerNo();
		String filePath = service.profile(imgProfileList, trainerNo);
		String message = null;
		message = "프로필 이미지가 변경되었습니다.";
		trainerLogin.setProfileImg(filePath);
		ra.addFlashAttribute("message", message);
		
		return "/myPage/trainerMyPage";
	}
	
	/** 강사 강의 목록 조회
	 * @param trainerLogin
	 * @param cp
	 * @param model
	 * @return
	 */
  @GetMapping("trainerClassList")
  public String trainerClassList(
          @SessionAttribute("trainerLogin") Trainer trainerLogin,
          @RequestParam(value="cp", required = false, defaultValue = "1") int cp ,
          Model model) {
      
      // 내 강의 조회 서비스
      int trainerNo = trainerLogin.getTrainerNo();
      List<Board> orderList = service.classList(trainerNo);
      
      model.addAttribute("orderList", orderList);
      
      return "/classList/trainerClassList";
  }	
  
  /* 강사 상세 정보 조회 */
  @GetMapping("trainerDetail/{trainerNo}")
  public String detailTrainer(@PathVariable("trainerNo") int trainerNo,
  														@RequestParam(value="cp", required = false, defaultValue = "1") int cp, Model model) {
  	Map<String, Object> map = service.detailTrainer(trainerNo, cp);
		model.addAttribute("qualificationList", (List<Trainer>)map.get("qualificationList"));
		model.addAttribute("pagination", (Pagination)map.get("pagination"));
		
      return "/trainer/trainerDetail";
  }
  


}