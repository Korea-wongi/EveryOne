package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.WishDao;
import vo.WishVo;

public class WishService {
	
	private WishDao wishDao;
	
	public WishService() {
		wishDao = new WishDao();
	}

	//좋아요 전체 조회
	public ArrayList serviceWishAll(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		
		return wishDao.wishAll();
	}

	//이미 좋아요를 누른 경우 기존 좋아요 삭제
	public int serviceDeleteWish(HttpServletRequest request) {
		
		String m_id = request.getParameter("m_id");
		String p_idx = request.getParameter("p_idx");
		
		int result = wishDao.deleteWish(m_id, p_idx);
		
		return result;
	}

	//좋아요 누르면 디비추가
	public int serviceAddWishList(HttpServletRequest request) {
		
		String m_id = request.getParameter("m_id");
		String p_idx = request.getParameter("p_idx");
		
		int result_ = wishDao.addWishList(m_id,p_idx);
		
		return result_;
	}
	


}
