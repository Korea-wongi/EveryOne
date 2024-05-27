package service;

import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import mail.sendMail;
import oracle.net.aso.b;
import vo.BuyVo;
import vo.KakaoUserVo;
import vo.MemberVo;
import vo.OrderVo;
import vo.PayVo;

public class MemberService {
	
	private MemberDao memberDao;
	private MemberVo memberVo;
	
	
	public MemberService() {
		memberDao = new MemberDao();
		memberVo = new MemberVo();
		
	}
	
	// 회원가입 처리 요청
	public void memberJoin(HttpServletRequest request) {
		memberVo = new MemberVo();
		String m_birth = request.getParameter("m_birth");
		
		LocalDate localDate = LocalDate.parse(m_birth);
		Date sqlDate = Date.valueOf(localDate);
		
		memberVo.setM_id(request.getParameter("m_id"));
		memberVo.setM_pass(request.getParameter("m_pass"));
		memberVo.setM_name(request.getParameter("m_name"));
		memberVo.setM_email(request.getParameter("m_email"));
		memberVo.setM_hp(request.getParameter("m_hp"));
		
		// 생년월일 수정 > sysdate 에서 input date 날짜로
		memberVo.setM_birth(sqlDate);
		memberVo.setM_gender(request.getParameter("m_gender"));
		memberVo.setM_address1(request.getParameter("address1")+" "+
				   		 request.getParameter("address2")+
				   		 request.getParameter("address3")+ ", " +
				   		 request.getParameter("address4")+
				   		 request.getParameter("address5")
						);
		memberVo.setM_admin(request.getParameter("m_admin"));
		
		
		memberDao.insertMember(memberVo);
	}// memberJoin()
	
	
	
	// 아이디 중복성 체크
	public boolean overLappedId(HttpServletRequest request) {
		
		String m_id = request.getParameter("m_id");
		return memberDao.overLappedId(m_id);
	}// overLappedId()

	
	//로그인처리 요청
	public int serviceLogin(HttpServletRequest request) {
		
		//요청한 값 얻기 
		String m_id = request.getParameter("m_id");
		String m_pass = request.getParameter("m_pass");
						
		int check = memberDao.userLogin(m_id,m_pass);
		memberVo = memberDao.MemberOne(m_id);
		String m_admin = memberVo.getM_admin();
						
		if(check == 1) {//아이디 OK, 비밀번호 OK
		HttpSession session = request.getSession();
		session.setAttribute("m_id", m_id);
		session.setAttribute("m_admin", m_admin);
		}
					
		return check;
	}//serviceLogin()

	
	//로그아웃 요청
	public void serviceLogOut(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		session.invalidate();
		
	}//serviceLogOut()
	
	// 비밀번호 체크 후 회원 수정 페이지 요청
	public Boolean servicePassCheck(HttpServletRequest request) {
		String m_id = request.getParameter("m_id");
		String m_pass = request.getParameter("m_pass");
		
		return memberDao.passCheck(m_id, m_pass);
	}
	
	// 회원 수정 페이지 뿌려줄 vo 가져오기
	public MemberVo serviceMemberOne(String m_id) {
		
		return memberDao.MemberOne(m_id);
	}// serviceMemberOne();

	
	// 회원정보 수정 후 mypage 요청
	public int serviceMemUpdate(HttpServletRequest request) {
		
		MemberVo memberVo = new MemberVo();
		
		memberVo.setM_id(request.getParameter("m_id"));
		memberVo.setM_pass(request.getParameter("m_pass"));
		memberVo.setM_name(request.getParameter("m_name"));
		memberVo.setM_email(request.getParameter("m_email"));
		memberVo.setM_hp(request.getParameter("m_hp"));
		
		memberVo.setM_address1(request.getParameter("address1")+" "+
				   		 request.getParameter("address2")+
				   		 request.getParameter("address3")+ ", " +
				   		 request.getParameter("address4")+
				   		 request.getParameter("address5")
						);
		
		return memberDao.memUpdate(memberVo);
		
	}//serviceMemUpdate()

	// 메일 인증번호 받기
	public String serviceForgotPw(HttpServletRequest request, HttpServletResponse response) throws Exception {

		/*
		 * 브라우저에서 건너온 세션 확인 로그인 상태에선 접근 못하게 설정
		 */
		HttpSession session = request.getSession(false);

		if ((String) session.getAttribute("m_id") != null){// 로그인된 상태
			
			session.invalidate();
			
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('접근 권한이 없습니다.');");
			out.println("location.href='"+request.getContextPath()+"/index.jsp';");
			out.println("</script>");
			
		}

		String m_email = request.getParameter("m_email");
		String m_id = request.getParameter("m_id");


		MemberService service = new MemberService();
		MemberVo member = service.getMember(m_id, m_email);
		if (member == null || !member.getM_email().equals(m_email)) {
			// 회원 정보가 일치하지 않은 경우
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원 정보가 존재하지 않습니다.');");
			out.println("history.back(-1);");
			out.println("</script>");
		} else {
			/* 메일 전송 */
			sendMail sendMail = new sendMail();
			String authenticationCode = sendMail.sendEmail(m_email);

			/* 포워딩 처리 */
			session.setAttribute("authenticationCode", authenticationCode);
			request.setAttribute("m_id", m_id);

		}
		return "/Member/memberChangePw.do";

	}// serviceForgotPw()

	
	/* 아이디와 이메일로 회원을 가져오는 메서드 */
	public MemberVo getMember(String m_id, String m_email) {


		return memberDao.selectMember(m_id, m_email);
	}

	// 인증코드 이용, 새 비밀번호 변경 페이지 요청
		public void serviceAuthenCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

			PrintWriter out = response.getWriter();

			HttpSession session = request.getSession(false);
			// 비로그인 상태 authenticationCode
			String authenCode1 = (String) session.getAttribute("authenticationCode"); // 받은 인증번호
			String newAuthenCode = request.getParameter("newAuthenCode"); // 입력된 인증번호 값


			if (authenCode1.equals(newAuthenCode)) {//인증번호가 맞을떄

				// 인증코드가 저장된 속성이 있는 경우
				String m_id = request.getParameter("m_id");
				String newPw = request.getParameter("newPw");


				int updateResult = 0;

				updateResult = memberDao.updatePw(m_id, newPw);
				if (updateResult == 1) {

					out.println("<script>");
					out.println("alert('정상적으로 수정되었습니다. 로그인 페이지로 돌아갑니다.');");
					out.println("location.href='"+request.getContextPath()+"/Member/login?center=member/login.jsp';");
					out.println("</script>");
				} else {

					out.println("<script>");
					out.println("alert('비밀번호 수정에 실패했습니다.');");
					out.println("history.back(-1);");
					out.println("</script>");
				}

			} else {//인증번호가 틀릴떄
				response.setContentType("text/html;charset=utf-8");

				out.println("<script>");
				out.println("alert('잘못된 접근 권한입니다.');");
				out.println("location.href='"+request.getContextPath()+"/index.jsp';");
				out.println("</script>");
			}

		}// serviceAuthenCode()

		// 탈퇴 후 main페이지 요청. 탈퇴 하기
		public String serviceMemDelete(HttpServletRequest request) {
			
			String m_id = request.getParameter("m_id");
			
			// 세션 삭제
		    HttpSession session = request.getSession(false);
		    if (session != null) {
		        session.invalidate(); // 세션 무효화
		    }
			
			return memberDao.memDelete(m_id);
			
		}// serviceMemDelete()

		// 카카오 아이디 있을 시, DB 등록 후 자동 로그인
		public void serviceKakaoLogin(String m_id, String m_name, String m_email, HttpServletRequest request) {
			
			KakaoUserVo kakaoUserVo = memberDao.kakaoLogin(m_id, m_name, m_email);
			
			long k_id = kakaoUserVo.getId();
			
			HttpSession session = request.getSession();
			session.setAttribute("m_id", String.valueOf(k_id));
			
		}
		
		// 카카오 로그인 추가 정보 처리
		public void serviceKakaoJoin(HttpServletRequest request) {
			memberVo = new MemberVo();
			
			String m_birth = request.getParameter("m_birth");
			LocalDate localDate = LocalDate.parse(m_birth);
			Date sqlDate = Date.valueOf(localDate);
			
			memberVo.setM_id(request.getParameter("m_id"));
			memberVo.setM_pass(request.getParameter("m_pass"));
			memberVo.setM_name(request.getParameter("m_name"));
			memberVo.setM_email(request.getParameter("m_email"));
			memberVo.setM_hp(request.getParameter("m_hp"));
			
			// 생년월일 수정 > sysdate 에서 input date 날짜로
			memberVo.setM_birth(sqlDate);
			memberVo.setM_gender(request.getParameter("m_gender"));
			memberVo.setM_address1(request.getParameter("address1")+" "+
					   		 request.getParameter("address2")+
					   		 request.getParameter("address3")+ ", " +
					   		 request.getParameter("address4")+
					   		 request.getParameter("address5")
							);
			memberVo.setM_admin(request.getParameter("m_admin"));
			
			HttpSession session = request.getSession();
			session.setAttribute("m_admin", memberVo.getM_admin());
			
			memberDao.KakaoJoin(memberVo);
		}
		
		//네이버 로그인 추가 정보 처리
		public void serviceNaverJoin(HttpServletRequest request) {
			memberVo = new MemberVo();
			
			String m_birth = request.getParameter("m_birth");
			LocalDate localDate = LocalDate.parse(m_birth);
			Date sqlDate = Date.valueOf(localDate);
			
			memberVo.setM_id(request.getParameter("m_id"));
			memberVo.setM_pass(request.getParameter("m_pass"));
			memberVo.setM_name(request.getParameter("m_name"));
			memberVo.setM_email(request.getParameter("m_email"));
			memberVo.setM_hp(request.getParameter("m_hp"));
			
			// 생년월일 수정 > sysdate 에서 input date 날짜로
			memberVo.setM_birth(sqlDate);
			memberVo.setM_gender(request.getParameter("m_gender"));
			memberVo.setM_address1(request.getParameter("address1")+" "+
					   		 request.getParameter("address2")+
					   		 request.getParameter("address3")+ ", " +
					   		 request.getParameter("address4")+
					   		 request.getParameter("address5")
							);
			memberVo.setM_admin(request.getParameter("m_admin"));
			
			HttpSession session = request.getSession();
	
			memberDao.NaverJoin(memberVo);
			
			session = request.getSession();
			session.setAttribute("m_id", request.getParameter("m_id"));
			session.setAttribute("m_admin", request.getParameter("m_admin"));
		}

	
}
