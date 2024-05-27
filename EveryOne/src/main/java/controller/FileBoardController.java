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

import service.FileBoardService;
import service.MemberService;
import vo.NewsVo;

@WebServlet("/FileBoard/*")
public class FileBoardController extends HttpServlet {
	
	private MemberService memberService;
	private FileBoardService fileBoardService;
	private NewsVo newsVo;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberService = new MemberService();
		fileBoardService = new FileBoardService();
		newsVo = new NewsVo();
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
		
		case "/list.do": // 공지사항 모든 글 조회
			
			list = fileBoardService.serviceBoardListAll();				
			
			//조회한 글의 갯수 
			count = list.size();
			
			center = "news/FileBoardlist.jsp";
			
			//request에 바인딩
			request.setAttribute("center", center); 
			request.setAttribute("list", list); //조회된 글목록 
			request.setAttribute("count", count);//조회된 글갯수 
			//조회후 보여질 글목록의 페이지번호위치값 
			request.setAttribute("nowPage", request.getParameter("nowPage"));
			//조회후 보여질 글목록의 페이지번호가 속한 블럭위치값 
			request.setAttribute("nowBlock", request.getParameter("nowBlock"));
			
			nextPage = "/index.jsp";
			break;
		
		case "/write.do": // 공지사항 글쓰기 화면 요청 
			
			center = "news/FileBoardwrite.jsp";
							
			request.setAttribute("center", center);
			request.setAttribute("nowPage", request.getParameter("nowPage"));
			request.setAttribute("nowBlock", request.getParameter("nowBlock"));
			
			nextPage = "/index.jsp";
			
			break;	
			
		case "/writePro.do": //업로드할 파일이 포함된 글쓰기 요청!
			
			//파일을 업로드시키고 + 업로드한파일명을 DB에 저장시키고 +  업로드시킨 파일의 글번호를 조회해서 가져오자
			int num;//조회된 글번호가 저장될 변수 (글번호폴더를 생성해서 업로드한 파일관리)
			
			try {
				num = fileBoardService.serviceInsertBoard(request,response);
				out = response.getWriter();
				
				out.print("<script>");
				out.print(" location.href='"+contextPath+"/FileBoard/list.do'");
				out.print("</script>");
				
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		case "/read.do": // 공지사항 글 제목을 클릭 시 글 조회
			
			newsVo = fileBoardService.serviceBoardRead(request);
			
			center = "news/FileBoardRead.jsp";
			
			request.setAttribute("center", center);
			request.setAttribute("vo", newsVo);
			request.setAttribute("nowPage", request.getParameter("nowPage"));
			request.setAttribute("nowBlock", request.getParameter("nowBlock"));
			request.setAttribute("n_idx", request.getParameter("n_idx"));
			
			nextPage = "/index.jsp";
		
			break;
			
			//파일 다운로드
		case "/Download.do":	
			
			fileBoardService.serviceDownload(request,response);
		
			return;
		
		case "/deleteBoard.do": //글삭제 요청	
			
			String result__;
			
			try {
				 result__ = fileBoardService.serviceDeleteBoard(request);
				
				 out = response.getWriter();
				 out.write(result__); 
				 
				 return; 
			} catch (Exception e) {
				e.printStackTrace();
			}
	
		
		case "/updateBoard.do":	// 글 수정 요청 받았을떄
			
			int result_ = fileBoardService.serviceUpdateBoard(request);
			
			out = response.getWriter();
			
			if(result_ == 1) {
				out.write("수정성공");
				return;
			}else {
				out.write("수정실패");
				return;
			}
			
		case "/fileUpdate.do" : // 파일 수정 처리
			
			//파일을 업로드시키고 + 업로드한파일명을 DB에 저장시킨다
			int num1; 
			
			try {
				//수정한 글의 글 번호를 반환받아 저장한다.
				num1 = fileBoardService.serviceUpdatFileeBoard(request,response);
				out = response.getWriter();
				out.print("<script>");
				out.print(" alert('파일 수정 성공!');");
				out.print(" location.href='"+contextPath+"/FileBoard/read.do?nowBlock=0&nowPage=0&n_idx=" + num1 + "'");
				out.print("</script>");
				
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			break;
		
		case "/searchList.do": //검색 기준값과 검색어를 입력해서 검색요청이 들어 왔을때
			
			//글조회
			list = fileBoardService.serviceBoardList(request);
			//검색된 글갯수
			count = list.size();
							
			//중앙 VIEW화면 주소 request에 바인딩
			request.setAttribute("center", "news/FileBoardlist.jsp");
			//조회된 글목록 정보가 저장된 ArrayList배열, 조회된 글 갯수 를 request에 바인딩
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			
			request.setAttribute("nowPage", request.getParameter("nowPage"));
			request.setAttribute("nowBlock", request.getParameter("nowBlock"));
			
			nextPage = "/index.jsp";
			
			break;	
			
		default:
			break;
		}
		
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
	}
	

}
