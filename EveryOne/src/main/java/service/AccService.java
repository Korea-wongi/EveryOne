package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.AccDao;
import dao.PantsDao;
import vo.ProductVo;

public class AccService {
	
	private AccDao accDao;
	
	public AccService() {
		accDao = new AccDao();
	}
	
	// acc 상품 리스트 전체 리턴
	public ArrayList serviceAccList(HttpServletRequest request) {
		return accDao.accListAll();
	}

	// 상품명 검색
	public ArrayList serviceSerachList(HttpServletRequest request) {
		String keyWord = request.getParameter("keyWord");
		request.setAttribute("keyWord", keyWord);
		
		return accDao.searchList(keyWord);
	}

	// 낮은 가격순 정렬
	public ArrayList servicePriceAsc(HttpServletRequest request) {
		
		return accDao.priceAsc();
	}
	
	// 높은 가격순 정렬
	public ArrayList servicePriceDesc(HttpServletRequest request) {
		
		return accDao.priceDesc();
	}
	
}
