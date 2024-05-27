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

import vo.WishVo;

public class WishDao {
	
	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;	
	
	public WishDao() {
	
		try {
			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}// LikeDao()
	
	
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
	

	//좋아요 전체 조회
	public ArrayList wishAll() {
		
		String sql = null;
		ArrayList list = new ArrayList();
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "select p.p_idx, w_pidx, m.m_id from tbl_wish w "
				+ "inner join tbl_product p on p_idx = w_pidx "
				+ "inner join tbl_member m  on m_id = w_mid";
		    
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	
				Map<String, String> hm = new HashMap<>();
				
				hm.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				hm.put("l_idx", Integer.toString(rs.getInt("l_idx")));
				hm.put("m_id", rs.getString("m_id"));

				list.add(hm);
			
			
			}
		} catch (Exception e) {
			System.out.println("LikeDao() / likeAll 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return list;
	}//likeAll()

	
	//이미 좋아요를 누른 경우 기존 좋아요 삭제
	public int deleteWish(String m_id, String p_idx) {
		
		String sql = null;
		int result = 0;
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "delete from tbl_wish where w_pidx=? and w_mid=?";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, p_idx);
			pstmt.setString(2, m_id);
		
			result = pstmt.executeUpdate();		
			
		} catch (Exception e) {
			System.out.println("WishDao() / deleteWish 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
	}//deleteLike()

	
	//좋아요 누르면 디비추가
	public int addWishList(String m_id, String p_idx) {
		
		String sql = null;
		int result = 0;
		int w_idx = 0;
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "select * from tbl_wish where w_mid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			System.out.println(rs.next());
			if(rs.next()) {
				result = 1;
			}
			
			if(result==0) { 
				
				
			sql =  "INSERT INTO TBL_WISH VALUES (TBL_WISH_W_IDX.NEXTVAL , ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(p_idx));
			pstmt.setString(2, m_id);
			
		
			result = pstmt.executeUpdate();		
			}
			
			
		} catch (Exception e) {
			System.out.println("WishDao() / addWishList 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
	}//addWishList()



}//class LikeDao
