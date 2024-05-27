package service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dao.KakaoDao;
import dao.PantsDao;
import vo.KakaoUserVo;
import vo.ProductVo;

public class KakaoService {
	private KakaoDao kakaoDao;
	
	
	public KakaoService() {
		kakaoDao = new KakaoDao();
	}

	// 카카오 로그인 처리
	public KakaoUserVo serviceLogin(KakaoUserVo kakaoUserVo, HttpServletRequest request) {
		KakaoUserVo result = kakaoDao.kakaoLogin(kakaoUserVo);
		
		HttpSession session = request.getSession();
		session.setAttribute("m_id", String.valueOf(result.getId()));
		session.setAttribute("m_admin", "1");
		
		return result;
	}

	// id 값이 테이블에 존재하는지 여부 확인
	public int serviceCheckId(KakaoUserVo kakaoUserVo, HttpServletRequest request) {
		
		int resultInt = kakaoDao.checkId(kakaoUserVo.getId());
		String resultStr = String.valueOf(resultInt);
		
		if(resultInt != 0) {
			HttpSession session = request.getSession();
			session.setAttribute("m_id", String.valueOf(kakaoUserVo.getId()));
			session.setAttribute("m_admin", resultStr);
		}
		
		return resultInt;
	}
	
}
