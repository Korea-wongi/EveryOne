package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.MemberService;

@WebServlet("/Member/*")
public class MemberController extends HttpServlet {
	
	private MemberService memberService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberService = new MemberService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	public void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType("text/html; charset=utf-8");
		String nextPage = null;
		String center = null;
		PrintWriter out = null;
		
		String action = request.getPathInfo();
		
		switch (action) {
		case "/login": // 로그인 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/memjoin": // 회원가입 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/memjoin.do": // 회원가입 처리 요청
			
			memberService.memberJoin(request);
			
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
			
		case "/checkCode":	//메일 인증번호 페이지 요청(이동)
			
			center = request.getParameter("center");
			request.setAttribute("center", center);	
			
			nextPage = "/index.jsp";
			break;
			
		case "/memberForgotPwPro.do": //메일 인증번호 받기	
									  //checkCode.jsp
									  //새 비밀번호 변경 페이지 요청
			
			// 1. 요청 주소 파악 
			String requestURI = request.getRequestURI();
			String contextPath = request.getContextPath();	
			String command = requestURI.substring(contextPath.length());
			
			String redirect = null;
			// 2. 각 요청 주소의 매핑 처리 
			try {
			redirect = memberService.serviceForgotPw(request,response);
			request.setAttribute("redirect", redirect);
			
			} catch (Exception e) {
			e.printStackTrace();
			}			
			nextPage = "/Member/pwdChange.do";  //  "/Member/memberChangePw.do"	
			break;
			
		case "/memberChangePw.do": // 인증코드 이용, 새 비밀번호 변경 페이지 요청
			 try {
				    memberService.serviceAuthenCode(request, response);
				    	
			    } catch (Exception e) {
			        e.printStackTrace();
			    }
			 	return;
			
			 	
		case "/pwdChange.do" :	// 비밀번호 변경 페이지 요청
			
			request.setAttribute("center", "member/pwdChange.jsp");
			
			nextPage ="/index.jsp";
			break;
			
		
		case "/joinIdCheck.me":  // 아이디 중복성 체크
			out = response.getWriter();
			
			// true = 중복,   false 가입 가능
			boolean result = memberService.overLappedId(request);
			
			if(result) {
				out.write("not_usable");
				return;
			}else {
				out.write("usable");
				return;
			}
		
		case "/loginPro.do": //로그인 처리 요청	
			
			out = response.getWriter();
			
			int check = memberService.serviceLogin(request);
			
			  if(check == 0) { //아이디 맞고 비밀번호 틀림
				  out.print("<script>");
				  out.print("window.alert('비밀번호 틀림');");
				  out.print("history.go(-1);");
				  out.print("</script>");
				  return; 
			  }else if(check == -1) {//아이디 틀림
				  out.print("<script>");
				  out.print("window.alert('아이디 틀림');");
				  out.print("history.go(-1);");
				  out.print("</script>");
				  return; 				  			  
			  }
			
			nextPage = "/index.jsp";
			break;
			
			
		case "/logout.do": //로그아웃 요청
			
			memberService.serviceLogOut(request);
			
			nextPage = "/index.jsp";
			break;	
		
		case "/kakaoJoin.do": // 카카오 아이디 있을 시, DB 등록 후 자동 로그인
			
			center = "main.jsp";
			
			String m_id = request.getParameter("m_id");
			String m_name = request.getParameter("m_name");
			String m_email = request.getParameter("m_email");
			
			memberService.serviceKakaoLogin(m_id,m_name,m_email,request);

			request.setAttribute("center", center);
			nextPage = "/index.jsp";
			break;
			
		case "/kakaoJoin2.do": // 카카오 로그인 추가 정보 처리
			memberService.serviceKakaoJoin(request);
			
			center = request.getParameter("center");
			
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/NaverJoin.do": // 네이버 로그인 추가 정보 처리
							
			memberService.serviceNaverJoin(request);
			
			center = request.getParameter("center");
			
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		default:
			break;
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}
	
	

}
