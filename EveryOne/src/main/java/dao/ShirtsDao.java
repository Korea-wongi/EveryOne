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

public class ShirtsDao {

	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;	
	
	public ShirtsDao() {
	
		try {
			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}// ShirtsDao()
	
	public ArrayList shirtsListAll() {
		
		String sql = null;
		ArrayList list = new ArrayList();
					
		try {
			
			con = dataSource.getConnection();
			
			sql = "select p.p_idx, p.p_name, p.p_price, p.p_content, i.i_name "
				+ "from tbl_product p inner join tbl_img i on p_idx  = i_pidx "
				+ "where p.p_category='top'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
	
				Map<String, String> hm = new HashMap<>();
				
				hm.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				hm.put("p_name", rs.getString("p_name"));
				hm.put("p_price", Integer.toString(rs.getInt("p_price")));
				hm.put("p_content", rs.getString("p_content"));
				hm.put("i_name", rs.getString("i_name"));

				list.add(hm);
							
			}
			
		} catch (Exception e) {
			System.out.println("ShirtsDao / shirtsListAll 메서드 오류 = ");
		}finally {
			ResourceClose();
		}		
		return list;
	}//shirtsListAll()
		
	
		//낮은 가격순 정렬
		public ArrayList priceAsc() {
			
			String sql = null;
			ArrayList list = new ArrayList();	
			
			try {				
				con = dataSource.getConnection();
				
				sql = "select p.p_idx, p.p_name, p.p_price, i.i_name "
					+ "from tbl_product p inner join tbl_img i on p_idx  = i_pidx "
					+ "where p.p_category='top' ORDER BY p_price";
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
				System.out.println("ShirtsDao / priceAsc 메서드 오류 = ");
			}finally {
				ResourceClose();
			}

			return list;
	}//priceAsc()
		

		//높은 가격순 정렬
		public ArrayList priceDesc() {
			String sql = null;
			ArrayList list = new ArrayList();	
			
			try {				
				con = dataSource.getConnection();
				
				sql = "select p.p_idx, p.p_name, p.p_price, i.i_name "
					+ "from tbl_product p inner join tbl_img i on p_idx  = i_pidx "
					+ "where p.p_category='top' ORDER BY p_price desc";
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
				System.out.println("ShirtsDao / priceAsc 메서드 오류 = ");
			}finally {
				ResourceClose();
			}

			return list;
		}//priceDesc()
		
		
		//상품명 검색
		public ArrayList searchList(String keyWord) {
			
			String sql = null;
			ArrayList list = new ArrayList();		
			
			try {
				
				con = dataSource.getConnection();
				sql = "SELECT p.p_idx, p.p_name, p.p_price, i.i_name "
					+ "FROM tbl_product p "
					+ "INNER JOIN tbl_img i ON p.p_idx = i.i_pidx "
					+ "WHERE p.p_category = 'top'  AND p.p_name like '%"+ keyWord + "%'";
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
				System.out.println("ShirtsDao / searchList 메서드 오류 = " + e.getMessage());
				e.printStackTrace();
			}finally {
				ResourceClose();
			}
					
			return list;		
		}//searchList();
		
		
	//상세페이지에 보여줄 상품 정보
	public Map<String, String> productView(String p_idx) {

		String sql = null;
		Map<String, String> map = null;
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "select p.p_idx, p.p_name, p.p_price, p.p_size, i.i_name "
				+ "from tbl_product p inner join tbl_img i on p.p_idx  = i.i_pidx "
				+ "where p.p_category='top' and p.p_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(p_idx));
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
	
				map = new HashMap<>();
				
				map.put("p_idx", Integer.toString(rs.getInt("p_idx")));
				map.put("p_name", rs.getString("p_name"));
				map.put("p_price", Integer.toString(rs.getInt("p_price")));
				map.put("p_size", rs.getString("p_size"));
				map.put("i_name", rs.getString("i_name"));					
							
			}
			
		} catch (Exception e) {
			System.out.println("ShirtsDao / productView 메서드 오류 = " + e.toString());
			e.printStackTrace();
		}finally {
			ResourceClose();
		}		
		
		return map;
	}//productView()
	
	//위시리스트에 있는지 없는지 판단 -> ajax에서 판단값
	public boolean wishValue(String m_id, String p_idx) {
		String sql = null;
		boolean result = false;
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "select * from tbl_wish where w_pidx=? and w_mid=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(p_idx));
			pstmt.setString(2, m_id);
			rs = pstmt.executeQuery();

			if(rs.next()) {
				// 위시 리스트에 해당 상품이 존재하는 경우
	            result = true; 				
			}
			
		} catch (Exception e) {
			System.out.println("ShirtsDao / wishValue 메서드 오류 = " + e.getMessage());
			e.printStackTrace();
		}finally {
			ResourceClose();
		}		
		
		return result;
	}//wishValue()
	
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
