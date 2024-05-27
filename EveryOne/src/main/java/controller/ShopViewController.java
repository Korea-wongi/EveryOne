package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Shop/*")
public class ShopViewController extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
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
		
		String action = request.getPathInfo();
		
		switch (action) {
		case "/home": 	// 중간 네비게이션 home 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
		case "/blog":	// 중간 네비게이션 blog 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;	
			
		case "/readme":		// 메인페이지 readmore 클릭시 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/gall":		// 메인페이지 하단 이미지 클릭시 gallery 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/gallsingle": // 갤러리페이지 상세보기 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/blogsinge":	// blog 페이지 상세보기 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/logo": 	// 메인 로고 home 페이지 요청
			
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
