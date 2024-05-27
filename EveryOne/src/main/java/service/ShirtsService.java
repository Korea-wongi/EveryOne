package service;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.ShirtsDao;

public class ShirtsService {
	
	private ShirtsDao shirtsDao;
	
	public ShirtsService() {
		shirtsDao = new ShirtsDao();
	}


	public ArrayList serviceshirtsList(HttpServletRequest request) {

		HttpSession session = request.getSession();
		
		return shirtsDao.shirtsListAll();
		
	}

	//낮은 가격순 정렬
	public ArrayList servicePriceAsc(HttpServletRequest request) {
		
		return shirtsDao.priceAsc();
	}

	//높은 가격순 정렬	
	public ArrayList servicePriceDesc(HttpServletRequest request) {
	
		return shirtsDao.priceDesc();
	}


	//상품명 검색
	public ArrayList serviceSerachList(HttpServletRequest request) {
		
		String keyWord = request.getParameter("keyWord");
		request.setAttribute("keyWord", keyWord);
		
		return shirtsDao.searchList(keyWord);
	}
	
	//상세페이지에 보여줄 상품 정보
	public Map<String, String> serviceProductView(HttpServletRequest request) {

		String p_idx = request.getParameter("p_idx");
		
		return shirtsDao.productView(p_idx);
	}
	
	//위시리스트에 있는지 없는지 판단
	public boolean serviceWishValue(String m_id, String p_idx) {
		
		boolean result = shirtsDao.wishValue(m_id, p_idx);
		
		return result;
	}
	
	

}
