package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

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
import service.MypageService;
import vo.CartVo;
import vo.MemberVo;
import vo.ProductVo;

@WebServlet("/Mypage/*")
public class MypageController extends HttpServlet {
	
	private MemberService memberService;
	private MemberVo memberVo;
	private MypageService mypageService;
	private CartVo cartVo;
	private ProductVo productVo;

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		memberService = new MemberService();
		memberVo = new MemberVo();
		mypageService = new MypageService();
		cartVo = new CartVo();
		productVo = new ProductVo();

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
		boolean resultBoolean = false;
		int resultInt = 0;
		ArrayList list = null;
		HttpSession session = null;
		String m_id = null;
		String result = null;
		int nowPage = 0;
		int nowBlock = 0;
		
		int month1 = 0;
		int month2 = 0;
		int month3 = 0;
		int month4 = 0;
		int month5 = 0;
		int month6 = 0;
		int month7 = 0;
		int month8 = 0;
		int month9 = 0;
		int month10 = 0;
		int month11 = 0;
		int month12 = 0;

		
		String action = request.getPathInfo();
		
		switch (action) {
		
		case "/mypage2": // 관리자 mypage 페이지 요청
			
			m_id = request.getParameter("m_id");
			list = mypageService.serviceProductListAll(m_id);
			center = request.getParameter("center");
			
			// 월별 판매 금액
			month1 = mypageService.serviceTotalSales(m_id, "01");
			month2 = mypageService.serviceTotalSales(m_id, "02");
			month3 = mypageService.serviceTotalSales(m_id, "03");
			month4 = mypageService.serviceTotalSales(m_id, "04");
			month5 = mypageService.serviceTotalSales(m_id, "05");
			month6 = mypageService.serviceTotalSales(m_id, "06");
			month7 = mypageService.serviceTotalSales(m_id, "07");
			month8 = mypageService.serviceTotalSales(m_id, "08");
			month9 = mypageService.serviceTotalSales(m_id, "09");
			month10 = mypageService.serviceTotalSales(m_id, "10");
			month11 = mypageService.serviceTotalSales(m_id, "11");
			month12 = mypageService.serviceTotalSales(m_id, "12");
			
			// 해당 관리자 매출총액
			int totalSales = month1+month2+month3+month4+month5+month6+month7
							+month8+month9+month10+month11+month12;
			// 해당 관리자 등록 상품 수량
			int regiProCount = mypageService.serviceProCount(m_id);
			
			request.setAttribute("list", list);
			request.setAttribute("center", center);
			request.setAttribute("month1", month1);
			request.setAttribute("month2", month2);
			request.setAttribute("month3", month3);
			request.setAttribute("month4", month4);
			request.setAttribute("month5", month5);
			request.setAttribute("month6", month6);
			request.setAttribute("month7", month7);
			request.setAttribute("month8", month8);
			request.setAttribute("month9", month9);
			request.setAttribute("month10", month10);
			request.setAttribute("month11", month11);
			request.setAttribute("month12", month12);
			request.setAttribute("totalSales", totalSales);
			request.setAttribute("regiProCount", regiProCount);
			
			nextPage = "/index.jsp";
			break;
			
		case "/mypage1": // 회원 mypage 페이지 요청, 장바구니 리스트
			m_id = request.getParameter("m_id");
			list = mypageService.serviceMypage1List(request);
			center = request.getParameter("center");
			
			int resultInt1 = mypageService.serviceCheckOrder(request);	// 주문한 수량 리턴
			int resultInt2 = mypageService.serviceCheckCart(request); // 장바구니 수량 리턴
			int resultInt3 = mypageService.serviceTotalPrice(request); // 총 주문금액 리턴
			int resultInt4 = mypageService.serviceCheckWish(request); // 총 찜 횟수 리턴
			
			// 월별 결제 금액
			month1 = mypageService.serviceMonth(m_id, "01");
			month2 = mypageService.serviceMonth(m_id, "02");
			month3 = mypageService.serviceMonth(m_id, "03");
			month4 = mypageService.serviceMonth(m_id, "04");
			month5 = mypageService.serviceMonth(m_id, "05");
			month6 = mypageService.serviceMonth(m_id, "06");
			month7 = mypageService.serviceMonth(m_id, "07");
			month8 = mypageService.serviceMonth(m_id, "08");
			month9 = mypageService.serviceMonth(m_id, "09");
			month10 = mypageService.serviceMonth(m_id, "10");
			month11 = mypageService.serviceMonth(m_id, "11");
			month12 = mypageService.serviceMonth(m_id, "12");
			
			
			request.setAttribute("month1", month1);
			request.setAttribute("month2", month2);
			request.setAttribute("month3", month3);
			request.setAttribute("month4", month4);
			request.setAttribute("month5", month5);
			request.setAttribute("month6", month6);
			request.setAttribute("month7", month7);
			request.setAttribute("month8", month8);
			request.setAttribute("month9", month9);
			request.setAttribute("month10", month10);
			request.setAttribute("month11", month11);
			request.setAttribute("month12", month12);
			request.setAttribute("result1", resultInt1);
			request.setAttribute("result2", resultInt2);
			request.setAttribute("result3", resultInt3);
			request.setAttribute("result4", resultInt4);
			request.setAttribute("list", list);
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
		case "/memupdate": // 회원수정 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
		case "/checkpwd": // 정보수정 패스워드체크 페이지 요청	
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
		case "/checkpwd.do":  // 비밀번호 체크 후 회원 수정 페이지 요청
			
			out = response.getWriter();
			
			// 비밀번호 유효성 체크
			resultBoolean = memberService.servicePassCheck(request);
			// 회원 수정 페이지 뿌려줄 vo 가져오기
			memberVo = memberService.serviceMemberOne(request.getParameter("m_id"));
			center = "/mypage/memupdate.jsp";
			
			if(resultBoolean) { // 비밀번호가 일치하면
				request.setAttribute("center", center);
				request.setAttribute("memberVo", memberVo);
				
			}else { // 비밀번호가 불일치하면
				out.write(" <script> ");
				out.write(" alert('"+ "비밀번호가 틀렸습니다." +"'); ");
				out.write(" history.back(); ");
				out.write(" </script> ");
				return;
			}
			
			nextPage = "/index.jsp";
			break;
			
		case "/memupdate.do": // 회원정보 수정 후 mypage 요청
			out = response.getWriter();
			resultInt = memberService.serviceMemUpdate(request);
			
			if(resultInt == 1) {
				center = request.getParameter("center");
				request.setAttribute("center", center);
			
			}else {
				out.write(" <script> ");
				out.write(" alert('"+ "회원수정 불가합니다." +"'); ");
				out.write(" history.back(); ");
				out.write(" </script> ");
				return;
			}
			
			nextPage = "/Mypage/mypage1";
			break;
		
		case "/proUpInfo": // 상품 등록 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;	
			
		case "/updateView":	// 상품 수정 버튼 눌러서 updateView.jsp 페이지 요청
			
			m_id = request.getParameter("m_id");
			// 등록된 모든 상품 조회 기능
			list = mypageService.serviceProductListAll(m_id);
			
			request.setAttribute("list", list);
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/quitMember": // 회원탈퇴(관리자포함) 버튼 눌러서 quitcheck.jsp 페이지 요청
			
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
		case "/quitChckPwd.do": // 비밀번호 체크 후 회원 정보 페이지 요청
			
			out = response.getWriter();
			
			// 비밀번호 유효성 체크
			resultBoolean = memberService.servicePassCheck(request);
			// 회원 정보 페이지 뿌려줄 vo 가져오기
			memberVo = memberService.serviceMemberOne(request.getParameter("m_id"));
			center = "/mypage/quitconfirm.jsp";
			
			if(resultBoolean) { // 비밀번호가 일치하면
				request.setAttribute("center", center);
				request.setAttribute("memberVo", memberVo);
				
			}else { // 비밀번호가 불일치하면
				out.write(" <script> ");
				out.write(" alert('"+ "비밀번호가 틀렸습니다." +"'); ");
				out.write(" history.back(); ");
				out.write(" </script> ");
				return;
			}
			
			nextPage = "/index.jsp";
			break;
			
		case "/proUpdate":	// 선택한 상품 정보 수정하는 proupdate 페이지 요청
			
			String p_idx = request.getParameter("p_idx");
			String i_name = request.getParameter("i_name");
			m_id = request.getParameter("m_id");
			
			productVo = mypageService.serviceProductRead(p_idx);
			
			request.setAttribute("vo", productVo);
			request.setAttribute("p_idx", p_idx);
			request.setAttribute("i_name", i_name);
			request.setAttribute("m_id", m_id);
			center = request.getParameter("center");
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
		
		case "/productRegister.do": // 상품 등록 처리

			int num; //글번호 폴더
			m_id = request.getParameter("m_id");
			try {
			 	num = mypageService.serviceProRegister(request, response,m_id);	
			 	out = response.getWriter();
				
				out.print(" <script> ");
				out.print(" alert('" + num + "상품등록 되었습니다.'); ");
				out.print(" location.href='"+request.getContextPath()+"/Mypage/mypage2?center=mypage/mypage2.jsp&m_id="+m_id+"'; ");
				out.print(" </script> ");
				
				return;	
			 	
				}catch(Exception e) {
					e.printStackTrace();
				}
			break;
			
		case "/cart": // 회원 장바구니 페이지 요청, 장바구니 보유 여부 확인
			resultInt = mypageService.serviceCheckCart(request);
			m_id = request.getParameter("m_id");
			out = response.getWriter();
			
			if(resultInt == 0) {
				out.print(" <script> ");
				out.print(" alert('장바구니를 추가하고 오세요~!.'); ");
				out.print(" location.href='"+request.getContextPath()+"/Mypage/mypage1?center=mypage/mypage1.jsp&m_id="+m_id+"' ; ");
				out.print(" </script> ");
				
				return;
			}
			
			list = mypageService.serviceCartList(request);
			center = "mypage/cart.jsp";
			memberVo = memberService.serviceMemberOne(request.getParameter("m_id"));
			
			request.setAttribute("count", list.size());
			request.setAttribute("memberVo", memberVo);
			request.setAttribute("list", list);
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/cartDelete.do": // 회원 장바구니 삭제 요청
			
			resultInt = mypageService.serviceCartDelete(request);
			m_id = request.getParameter("m_id");
			out = response.getWriter();
			
			if(resultInt == 1) {
				out.print(" <script> ");
				out.print(" alert('장바구니가 삭제되었습니다.'); ");
				out.print(" location.href='"+request.getContextPath()+"/Mypage/cart?m_id="+m_id+"' ; ");
				out.print(" </script> ");
				return;
			}
			
			break;
			
		case "/orderDelete": // 결제 내역 페이지 요청
			
			list = mypageService.serviceOrderList(request);
			center = request.getParameter("center");
			
			request.setAttribute("list", list);
			request.setAttribute("center", center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/orderDelete.do": // 주문 취소 처리 요청
			
			resultInt = mypageService.serviceOrderDelete(request);
			
			session = request.getSession();
			m_id = (String)session.getAttribute("m_id");
			out = response.getWriter();
			
			if(resultInt == 2) {
				out.write(" <script> ");
				out.write(" location.href='"+request.getContextPath()+"/Mypage/orderDelete?center=mypage/orderDelete.jsp'; ");
				out.write(" </script> ");
				return;
			}else {
				out.write(" <script> ");
				out.write(" alert('삭제실패') ");
				out.write(" history.back(); ");
				out.write(" </script> ");
				return;
			}
			
		case "/proDelete.do":  // 한 개의 상품 삭제 후 mypage2요청
			
			try {
				result = mypageService.serviceProDelete(request);
				out = response.getWriter();
				
				out.write(result);
				
				return;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "/proUpdate.do":  // 상품 정보 수정 후 mypage2요청
			
			int updateNo; // 수정된 글번호 폴더
			
			try {
				updateNo = mypageService.serviceProUpdate(request, response);
				
				out = response.getWriter();
				out.print(" <script> ");
				out.print(" alert('" + updateNo + "번 상품수정 되었습니다.'); ");
				out.print(" location.href='"+request.getContextPath()+"/Mypage/mypage2?center=mypage/mypage2.jsp'; ");
				out.print(" </script> ");
				
				return;	
			 	
				}catch(Exception e) {
					e.printStackTrace();
				}
			
			break;
			
		case "/quitMember.do": // 탈퇴 후 main페이지 요청
			out = response.getWriter();
			
			result = memberService.serviceMemDelete(request);
			
			out.write(result);
			
			return;
		
		case "/payment.do": // 결제 처리 요청
			center = request.getParameter("center");
			mypageService.servicePayment(request);
			
			request.setAttribute("center",center);
			
			nextPage = "/index.jsp";
			break;
			
		case "/cartInsert.do" : // 장바구니 추가		
			
			out = response.getWriter();
	         
	         //장바구니에 동일 상품 추가X
	         Boolean result4 = mypageService.serviceCartCount(request);
	         if(result4) {
	            out.print("<script>");
	            out.print("window.alert('이미 카트에 존재합니다.');");
	            out.print("history.back(-1);");   
	            out.print("</script>");
	            
	            return;

	         }else{
	          //상세페이지 구매상품 카트추가	 
	         int result5 = mypageService.serviceCartInsert(request);
	         	         
	         if(result5 == 1) { 
	              out.print("<script>");
	              out.print("window.alert('카트 추가 완료!');");
	              out.print("history.back(-1);");   
	              out.print("</script>");
	         }
			return;
			}
			
		default:
			break;
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
		
	}
	
	

}
