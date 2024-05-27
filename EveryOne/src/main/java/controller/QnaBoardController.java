package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import service.MemberService;
import service.QnaService;
import vo.QnaVo;

@WebServlet("/Qna/*")
public class QnaBoardController extends HttpServlet {
	
	private MemberService memberService;
	private QnaService qnaService;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberService = new MemberService();
		qnaService = new QnaService();
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
		QnaVo qnaVo = null;
		int count = 0;
		String nowBlock = null;
		String nowPage = null;
		String q_title = null;
		
		String action = request.getPathInfo();
		
		switch (action) {
		
		case "/list.do": // TBL_QNA 테이블에 등록된 전체 글 & 글 갯수 조회
			
			list = qnaService.serviceQnaListAll(); // Qna 글 조회
			count = qnaService.getTotalRecord(); // Qna 글 갯수
			nowBlock=request.getParameter("nowBlock");
			nowPage=request.getParameter("nowPage");
			center = request.getParameter("center");
			
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			request.setAttribute("center", center);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
		
		case "/ship": // 배송문의 페이지 요청, q_title 배송문의 뿌려주기
			q_title = request.getParameter("q_title");
			
			list = qnaService.serviceShipListAll(q_title); 
			count = qnaService.getShipRecord(q_title); // Qna 글 갯수
			nowBlock=request.getParameter("nowBlock");
			nowPage=request.getParameter("nowPage");
			center = request.getParameter("center");
			
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			request.setAttribute("center", center);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/product": // 배송문의 페이지 요청, q_title 상품문의 뿌려주기
			q_title = request.getParameter("q_title");
			
			list = qnaService.serviceShipListAll(q_title); 
			count = qnaService.getShipRecord(q_title); // Qna 글 갯수
			nowBlock=request.getParameter("nowBlock");
			nowPage=request.getParameter("nowPage");
			center = request.getParameter("center");
			
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			request.setAttribute("center", center);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/cancel": // 취소문의 페이지 요청, q_title 상품문의 뿌려주기
			q_title = request.getParameter("q_title");
			
			list = qnaService.serviceShipListAll(q_title); 
			count = qnaService.getShipRecord(q_title); // Qna 글 갯수
			nowBlock=request.getParameter("nowBlock");
			nowPage=request.getParameter("nowPage");
			center = request.getParameter("center");
			
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			request.setAttribute("center", center);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/pay": // 결제문의 페이지 요청, q_title 상품문의 뿌려주기
			q_title = request.getParameter("q_title");
			
			list = qnaService.serviceShipListAll(q_title); 
			count = qnaService.getShipRecord(q_title); // Qna 글 갯수
			nowBlock=request.getParameter("nowBlock");
			nowPage=request.getParameter("nowPage");
			center = request.getParameter("center");
			
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			request.setAttribute("center", center);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/passview.do" : //글 제목 클릭 후 비밀번호 입력창 열기 
			String q_idx=request.getParameter("q_idx");
			nowPage=request.getParameter("nowPage");
			nowBlock=request.getParameter("nowBlock");
			
			request.setAttribute("center", "qna/qnacheckpass.jsp");
			request.setAttribute("q_idx", q_idx);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage="/index.jsp";
			break;
			

		case "/password.do": // 글 제목 클릭 시 작성자 비번 일치 확인
		    qnaVo = qnaService.servicePassCheck(request);
		    nowPage = request.getParameter("nowPage");
		    nowBlock = request.getParameter("nowBlock");

		    if (qnaVo != null && qnaVo.getQ_content() != null) {
		        request.setAttribute("qnaVo", qnaVo);
		        request.setAttribute("center", "qna/qnaread.jsp");
		        request.setAttribute("nowPage", nowPage);
		        request.setAttribute("nowBlock", nowBlock);
		    } else { // 작성자 비번 틀림 또는 데이터가 없는 경우
		    	out = response.getWriter();
		        out.print("<script>");
		        out.print("window.alert('작성자의 비밀번호와 일치하지 않거나 데이터가 없습니다. 다시 시도해주세요.');");
		        out.print("history.go(-1);");
		        out.print("</script>");
		        return; 
		    }
		    
		    nextPage = "/index.jsp";
		    break;

		case "/updateQna.do" : // 고객 관점 : 글 수정 후 업데이트 요청 기능
			int updateresult=qnaService.serviceUpdateQna(request);
			out=response.getWriter();
			 if(updateresult==1) {
				 out.write("수정성공");
				 return;
			 }else {
				 out.write("수정실패");
				 return;
			 }	
		
		case "/updateQnaFile.do" : 	// 파일 수정 요청
			
			int upFileResult;//조회된 글번호가 저장될 변수 (글번호폴더를 생성해서 업로드한 파일관리)
			
			try {
				upFileResult=qnaService.serviceUpdateQnaFile(request,response);
					out = response.getWriter();
					out.print("<script>");
					out.print(" alert('"+ upFileResult+" 파일 수정 성공!');");
					out.print(" location.href='"+request.getContextPath()+"/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0'");
					out.print("</script>");
					
					return;

			} catch (Exception e) {
				e.printStackTrace();
			}
			
		case "/write.do" : // 문의사항 새 글 등록 화면 보여주기
			
			HttpSession  session_ = request.getSession();
			String loginId = (String)session_.getAttribute("m_id");
			
			list=qnaService.serviceWriteQnaView(loginId);
			
			request.setAttribute("center", "qna/qnawrite.jsp");
			request.setAttribute("buyList", list);
			request.setAttribute("nowPage", request.getParameter("nowPage"));
			request.setAttribute("nowBlock", request.getParameter("nowBlock"));
			
			nextPage = "/index.jsp";
			break;
			
		case "/writePro.do" : // 문의사항 새 글 작성 완료 후 등록요청
			int num;//조회된 글번호가 저장될 변수 (글번호폴더를 생성해서 업로드한 파일관리)
			
			try {
				num = qnaService.serviceInsertQna(request,response);
				out = response.getWriter();
				out.print("<script>");
				out.print(" alert('"+  num   +" 글 추가 성공!');");
				out.print(" location.href='"+request.getContextPath()+"/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0'");
				out.print("</script>");
				
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		case "/deleteQna.do" : // 문의사항 글 삭제 요청
			try {
				String delResult=qnaService.serviceDeleteQna(request);
				out=response.getWriter();
				out.write(delResult);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		case "/reply.do" : // 관리자 관점 : 답변달기 버튼 클릭 시 답변 달기 화면 보여주기
			request.setAttribute("center", "qna/qnareply.jsp");
			request.setAttribute("q_idx", request.getParameter("q_idx")); // 주글 번호
			request.setAttribute("b_inx", request.getParameter("b_inx")); // 문의상품 번호
			
			nextPage = "/index.jsp";
			break; 
			
		case "/replyPro.do" : // 관리자 관점 : 답변달기 버튼 클릭 시 답변 달기 화면 보여주기

			try {
				int replyNum = qnaService.serviceReplyInsertQna(request,response);
				out = response.getWriter();
				out.print("<script>");
				out.print(" alert('"+  replyNum   +" 글 추가 성공!');");
				out.print(" location.href='"+request.getContextPath()+"/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0'");
				out.print("</script>");
				
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		case "/download.bo" : //첨부파일 다운로드
			try {
				qnaService.serviceQnaDownload(request,response);
				
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		case "/searchList.do" : // 검색어 입력 후 결과 보여주기 
			list=qnaService.serviceQnaList(request); // 검색결과 값(배열)
			count = qnaService.serviceQnaTotalRecord(request); // 검색된 행 갯수 
			
			nowPage = request.getParameter("nowPage"); //0 
			nowBlock = request.getParameter("nowBlock"); //0
			
			request.setAttribute("list", list);
			request.setAttribute("count", count);
			request.setAttribute("nowPage", nowPage);
			request.setAttribute("nowBlock", nowBlock);
			request.setAttribute("center","qna/qna.jsp");
			
			nextPage = "/index.jsp";
			break; 
			
		default:
			break;
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}
	
	

}
