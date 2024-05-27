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

import service.ReviewService;
import service.MemberService;
import vo.ReviewVo;

@WebServlet("/Review/*")
public class ReviewController extends HttpServlet {
	
	private MemberService memberService;
	private ReviewService reviewService;
	private ReviewVo reviewVo;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberService = new MemberService();
		reviewService = new ReviewService();
		reviewVo = new ReviewVo();
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
		int count = 0;
		
		String action = request.getPathInfo();
		String contextPath = request.getContextPath();

		
		switch (action) {
					
		case "/write.do": //업로드할 파일이 포함된 글쓰기 요청!
			
			//파일을 업로드시키고 + 업로드한파일명을 DB에 저장시키고 +  업로드시킨 파일의 글번호를 조회해서 가져오자
			int num;//조회된 글번호가 저장될 변수 (글번호폴더를 생성해서 업로드한 파일관리)
			
			try {
				
				num = reviewService.serviceInsertBoard(request,response);
				out = response.getWriter();
				out.print(" location.href='"+contextPath+"/Shirts/list.do'");
				out.print("</script>");
							
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		
		case "/read.do": // 공지사항 글 제목을 클릭 시 글 조회
			reviewVo = reviewService.serviceBoardRead(request);
			
			JSONObject jsonobject = new JSONObject(); // {   } 
					   
			jsonobject.put("R_mid", reviewVo.getR_mid());
			jsonobject.put("R_title", reviewVo.getR_title());
			jsonobject.put("R_content", reviewVo.getR_content());
			jsonobject.put("R_sfile", reviewVo.getR_sfile());
			
			String r_date = String.valueOf(reviewVo.getR_date());
			
			jsonobject.put("R_date", r_date);			
			
			
			out = response.getWriter();
			
			if(reviewVo != null) {
				out.print(jsonobject);
				return;
			}else {
				out.print("조회실패");
				return;
			}
	
		case "/delete.do": //글삭제 요청	
			
			String result__;
			
			try {
				 result__ = reviewService.serviceDeleteBoard(request);
				 out = response.getWriter();
				
				 if(result__.equals("삭제성공")) {
					 out.write("삭제성공");
						return;
				 }else {
					 out.write("삭제실패");
						return;
				 }
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
	
		default:
			break;
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}
	
	

}
