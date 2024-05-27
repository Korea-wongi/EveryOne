package controller;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.NaverShopSearchService;

@WebServlet("/naverSearch.do")
public class NaverShopSearchController extends HttpServlet {
	
	private NaverShopSearchService naverSearchService;
	
	@Override
	public void init() throws ServletException {
		naverSearchService = new NaverShopSearchService();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String responseString = naverSearchService.service(request, response); 
		String center = request.getParameter("center");
		
		request.setAttribute("responseString", responseString);
		request.setAttribute("center", center);
		
		String nextPage = "/index.jsp";
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}
	

	
}