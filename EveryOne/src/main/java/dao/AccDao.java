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
import vo.ProductVo;

public class AccDao {

	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;	
	
	public AccDao() {
	
		try {
			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}// AccDao()
	
	
	// 중간 네비게이션 acc 페이지 요청, acc 상품 리스트 리턴
	public ArrayList accListAll() {
		String sql = null;
		ArrayList list = new ArrayList();
					
		try {
			
			con = dataSource.getConnection();
			
			sql = "select p.p_idx, p.p_name, p.p_price, i.i_name "
				+ "from tbl_product p inner join tbl_img i on p_idx  = i_pidx "
				+ "where p.p_category='acc'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	
				Map<String, String> hm = new HashMap<>();
				
				hm.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				hm.put("p_name", rs.getString("p_name"));
				hm.put("p_price", Integer.toString(rs.getInt("p_price")));
				hm.put("i_name", rs.getString("i_name"));

				list.add(hm);
							
			}
			
		} catch (Exception e) {
			System.out.println("AccDao / accListAll 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}		
		return list;
	}// accListAll()
	
	
	//상품명 검색
	public ArrayList searchList(String keyWord) {
		String sql = null;
		ArrayList list = new ArrayList();		
		
		try {
			
			con = dataSource.getConnection();
			sql = "SELECT p.p_idx, p.p_name, p.p_price, i.i_name "
				+ "FROM tbl_product p "
				+ "INNER JOIN tbl_img i ON p.p_idx = i.i_pidx "
				+ "WHERE p.p_category = 'acc'  AND p.p_name like '%"+ keyWord + "%'";
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				Map<String, String> hm = new HashMap<>();
				
				hm.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				hm.put("p_name", rs.getString("p_name"));
				hm.put("p_price", Integer.toString(rs.getInt("p_price")));
				hm.put("i_name", rs.getString("i_name"));

				list.add(hm);
		
			}	
		} catch (Exception e) {
			System.out.println("AccDao / searchList 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
				
		return list;		
	}//searchList();
	
	
	// 낮은 가격순 정렬
	public ArrayList priceAsc() {
		
		String sql = null;
		ArrayList list = new ArrayList();	
		
		try {				
			con = dataSource.getConnection();
			
			sql = "select p.p_idx, p.p_name, p.p_price, i.i_name "
				+ "from tbl_product p inner join tbl_img i on p_idx  = i_pidx "
				+ "where p.p_category='acc' ORDER BY p_price";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	
				Map<String, String> hm = new HashMap<>();
				
				hm.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				hm.put("p_name", rs.getString("p_name"));
				hm.put("p_price", Integer.toString(rs.getInt("p_price")));
				hm.put("i_name", rs.getString("i_name"));

				list.add(hm);
			}
		} catch (Exception e) {
			System.out.println("AccDao / priceAsc 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}

		return list;
	}//priceAsc()
	
	
	// 높은 가격순 정렬
	public ArrayList priceDesc() {
		String sql = null;
		ArrayList list = new ArrayList();	
		
		try {				
			con = dataSource.getConnection();
			
			sql = "select p.p_idx, p.p_name, p.p_price, i.i_name "
				+ "from tbl_product p inner join tbl_img i on p_idx  = i_pidx "
				+ "where p.p_category='acc' ORDER BY p_price desc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	
				Map<String, String> hm = new HashMap<>();
				
				hm.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				hm.put("p_name", rs.getString("p_name"));
				hm.put("p_price", Integer.toString(rs.getInt("p_price")));
				hm.put("i_name", rs.getString("i_name"));

				list.add(hm);
			}
		} catch (Exception e) {
			System.out.println("AccDao / priceDesc 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}

		return list;
	}//priceDesc()
	
	
	
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