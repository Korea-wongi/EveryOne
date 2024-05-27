package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.AccService;
import service.MemberService;
import service.MypageService;
import service.ReviewService;
import service.ShirtsService;
import vo.MemberVo;
import vo.ProductVo;
import vo.ReviewVo;

@WebServlet("/Acc/*")
public class AccController extends HttpServlet {
	
	private AccService accService;
	private ShirtsService shirtsService;
	private MemberService memberService;
	private MemberVo memberVo;
	private ProductVo productVo;
	private MypageService mypageService;
	
    public AccController() {
    	accService = new AccService();
		shirtsService = new ShirtsService();
		memberService = new MemberService();
    	memberVo = new MemberVo();
		productVo = new ProductVo();
		mypageService = new MypageService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=utf-8");
		String nextPage = null;
		String center = null;
		ArrayList list = null;
		int count = 0;
		String nowPage = null;
		String nowBlock = null;
		
		String action = request.getPathInfo();
		
		switch(action) {
		case "/list.do": // 중간 네비게이션 acc 페이지 요청
			
			// acc 상품 리스트 전체 리턴
			list = accService.serviceAccList(request);
			
			center = "/acc/acc.jsp";
			nowPage = request.getParameter("nowPage");
			nowBlock = request.getParameter("nowBlock");

			//조회한 글의 갯수 
			count = list.size();
			
			request.setAttribute("list", list);
			request.setAttribute("center", center);
			request.setAttribute("count", count);//조회된 글갯수 
			//조회후 보여질 글목록의 페이지번호위치값 
			request.setAttribute("nowPage", nowPage);
			//조회후 보여질 글목록의 페이지번호가 속한 블럭위치값 
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/searchList.do": // 상품명 검색
			
			list = accService.serviceSerachList(request);
			center = "acc/acc.jsp";
			
			nowPage = request.getParameter("nowPage");
			nowBlock = request.getParameter("nowBlock");
			
			count = list.size();
			
			request.setAttribute("list", list);
			request.setAttribute("center", center);	
			request.setAttribute("count", count);//조회된 글갯수 
			
			//조회후 보여질 글목록의 페이지번호위치값 
			request.setAttribute("nowPage", nowPage);
			//조회후 보여질 글목록의 페이지번호가 속한 블럭위치값 
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/priceAsc.do": // 낮은 가격순 정렬
			
			list = accService.servicePriceAsc(request);
			
			center = "acc/acc.jsp";
			nowPage = request.getParameter("nowPage");
			nowBlock = request.getParameter("nowBlock");
			
			count = list.size();
			
			request.setAttribute("list", list);
			request.setAttribute("center", center);	
			request.setAttribute("count", count);//조회된 글갯수 
			
			//조회후 보여질 글목록의 페이지번호위치값 
			request.setAttribute("nowPage", nowPage);
			//조회후 보여질 글목록의 페이지번호가 속한 블럭위치값 
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
		
		case "/pricDesc.do": //높은 가격순 정렬	
			
			list = accService.servicePriceDesc(request);
			
			center = "acc/acc.jsp";
			nowPage = request.getParameter("nowPage");
			nowBlock = request.getParameter("nowBlock");
			
			count = list.size();
			
			request.setAttribute("list", list);
			request.setAttribute("center", center);	
			request.setAttribute("count", count);//조회된 글갯수 
			
			//조회후 보여질 글목록의 페이지번호위치값 
			request.setAttribute("nowPage", nowPage);
			//조회후 보여질 글목록의 페이지번호가 속한 블럭위치값 
			request.setAttribute("nowBlock", nowBlock);
			
			nextPage = "/index.jsp";
			break;
			
		case "/productDetail.do" : //상세페이지에 보여줄 상품 정보
			
			HttpSession session = request.getSession();
			
			String m_id = request.getParameter("m_id");
			String p_idx = request.getParameter("p_idx");
			
			boolean result = shirtsService.serviceWishValue(m_id,p_idx); //위시리스트에 있는지 없는지 판단
			
			center = "acc/productDetail.jsp";
	
			request.setAttribute("center", center);	
			
			//shirts.jsp 에서 input hidden으로 가져온 애들
			request.setAttribute("p_idx", request.getParameter("p_idx"));
			request.setAttribute("p_name", request.getParameter("p_name"));
			request.setAttribute("p_price", request.getParameter("p_price"));
			request.setAttribute("p_size", request.getParameter("p_size"));
			request.setAttribute("i_name", request.getParameter("i_name"));
			request.setAttribute("m_id", request.getParameter("m_id"));
			request.setAttribute("result", result);
			
			
			memberVo = memberService.serviceMemberOne(m_id);
			productVo = mypageService.serviceProductRead(p_idx);
			
			request.setAttribute("memberVo", memberVo);
			request.setAttribute("productVo", productVo);
			
			//--------------상세 페이지 리뷰 가져오는 부분----------------
			//서비스객체생성
			ReviewService reviewService = new ReviewService();
			
			//r_pidx
			ArrayList<ReviewVo> reviewList = reviewService.serviceBoardListAll(p_idx);				
			request.setAttribute("reviewList", reviewList); //조회된 글목록 			

			//--------------구매 여부 판단--------------------------
			//상품이름
			boolean buyId = reviewService.buyProduct(request.getParameter("p_name"), m_id);
			request.setAttribute("buyId", buyId);
				
			nextPage = "/index.jsp";
			break;
			
			
		default:
			break;
		
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
	
	}

}
