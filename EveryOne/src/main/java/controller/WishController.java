package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import service.WishService;


@WebServlet("/Wish/*")
public class WishController extends HttpServlet {
	
	private WishService wishService;
	PrintWriter out = null;

	@Override
	public void init(ServletConfig config) throws ServletException {
		wishService = new WishService();
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
		ArrayList list = null;
		JSONArray jsonArray = null;
		
		String action = request.getPathInfo();
		
		switch (action) {
		case "/likeAll.do" : //좋아요 전체 조회
			response.setContentType("application/json");
			response.getWriter();
			
			list = wishService.serviceWishAll(request);
			
			jsonArray = new JSONArray();
		    for (Object obj : list) {
		        jsonArray.add(obj);
		    }
			
			request.setAttribute("list", list);
			
			out.print(jsonArray.toString());
	        out.flush();
	        out.close();
	        
			return;
			
		
		case "/delete.do": //이미 좋아요를 누른 경우 기존 좋아요 삭제	
			
			int result = wishService.serviceDeleteWish(request);
			if(result == 1) {
				out = response.getWriter();
				out.write(result);
				
				return;
			}
			
			break;
			
		case "/addWishList.do": //좋아요 누르면 디비추가
			
			int result_ = wishService.serviceAddWishList(request);
			
				out = response.getWriter();
			if(result_==1) {	
				out.write("성공");
				return;
			}
			break;
			
		default:
			break;
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}

}//class LikeController()
