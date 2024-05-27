package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.PantsDao;
import vo.ProductVo;

public class PantsService {
	
	private PantsDao pantsDao;
	
	public PantsService() {
		pantsDao = new PantsDao();
	}

	
	public ArrayList servicePantsList(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		return pantsDao.pantsListAll();
	}

	
	//낮은 가격순 정렬
	public ArrayList servicePriceAsc(HttpServletRequest request) {
		
		return pantsDao.priceAsc();
	}

	
	//높은 가격순 정렬	
	public ArrayList servicePriceDesc(HttpServletRequest request) {
		
		return pantsDao.priceDesc();
	}

	
	//상품명 검색
	public ArrayList serviceSerachList(HttpServletRequest request) {
		
		String keyWord = request.getParameter("keyWord");
		request.setAttribute("keyWord", keyWord);
		
		return pantsDao.searchList(keyWord);
	}


	//위시리스트에 있는지 없는지 판단
	public boolean serviceWishValue(String m_id, String p_idx) {
		
		boolean result = pantsDao.wishValue(m_id, p_idx);
		
		return result;
	}
	
}
