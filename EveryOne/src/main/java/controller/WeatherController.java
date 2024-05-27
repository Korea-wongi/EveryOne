package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.google.gson.Gson;

import service.MemberService;
import service.QnaService;
import service.WeatherService;
import vo.ImgVo;
import vo.ProductVo;
import vo.QnaVo;

@WebServlet("/temp/*")
public class WeatherController extends HttpServlet {
	
	private WeatherService weather=null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		weather=new WeatherService();
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
		List list = null;
		
		String action = request.getPathInfo();
		
		switch (action) {

		case "/codyset.do": 
			String temp=request.getParameter("P_CODYTEMP"); //현재 기온
			
			double tempDouble = Double.parseDouble(temp);
			int cody_temp = (int)Math.ceil(tempDouble);
			
			if(cody_temp<=4) {
				cody_temp=4;
			}else if(5<=cody_temp && cody_temp<=8){
				cody_temp=8;
			}else if(9<=cody_temp && cody_temp<=11){
				cody_temp=11;
			}else if(12<=cody_temp && cody_temp<=16){
				cody_temp=16;
			}else if(17<=cody_temp && cody_temp<=19){
				cody_temp=19;
			}else if(20<=cody_temp && cody_temp<=22){
				cody_temp=22;
			}else if(23<=cody_temp && cody_temp<=27){
				cody_temp=27;
			}else if(28<=cody_temp){
				cody_temp=28;
			}

			list=weather.codySet_temp(cody_temp); //productVO가 list배열에 저장
			

			request.setAttribute("list", list);
			request.setAttribute("center", "weather/codyresult.jsp");
			nextPage="/index.jsp";
			break;
			
		default:
			break;
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}
	
	

}
