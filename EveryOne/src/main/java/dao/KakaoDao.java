package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.ImgVo;
import vo.KakaoUserVo;
import vo.ProductVo;

public class KakaoDao {
	
	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;
	
	
	public KakaoDao() {
	
		try {
			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}// PantsDao()
	
	// 카카오 아이디 있을 시, DB 등록 후 자동 로그인
	public KakaoUserVo kakaoLogin(KakaoUserVo kakaoUserVo) {
		
		String sql = null;
		KakaoUserVo kakaoUserVo1 = null;
		String m_id = String.valueOf(kakaoUserVo.getId());
		
		try {
			con = dataSource.getConnection();
			
			sql = "INSERT INTO tbl_member VALUES(?, null, ?, ?, null, sysdate, null, null, sysdate, null)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			pstmt.setString(2, kakaoUserVo.getNickname());
			pstmt.setString(3, kakaoUserVo.getEmail());
			
			pstmt.executeUpdate();
			
			sql = "select * from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				kakaoUserVo1 = new KakaoUserVo();
				kakaoUserVo1.setId(rs.getLong("m_id"));
				kakaoUserVo1.setNickname(rs.getString("m_name"));
				kakaoUserVo1.setEmail(rs.getString("m_email"));
			}
			
		} catch (Exception e) {
			System.out.println("KakaoDao / kakaoLogin : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return kakaoUserVo1;
	}// kakaoLogin()
	
	
	// id 값이 테이블에 존재하는지 여부 확인
	public int checkId(long id) {
		int resultInt = 0;
		String sql = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "select * from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, String.valueOf(id));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultInt = Integer.parseInt(rs.getString("m_admin"));
			}
			
			
		} catch (Exception e) {
			System.out.println("KakaoDao / checkId : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return resultInt;
	}//checkId()
	
	
	//DB작업관련 객체 메모리들 자원해제 하는 메소드 
	public void ResourceClose() {	
		try {
			if(pstmt != null) {
				pstmt.close();
			}
			if(rs != null) {
				rs.close();
			}
			if(con != null) {
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// ResourceClose()


}	
	