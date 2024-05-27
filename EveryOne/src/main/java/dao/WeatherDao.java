package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.ImgVo;
import vo.ProductVo;
import vo.QnaVo;

public class WeatherDao {
	
	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;
	
	
	public WeatherDao() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	}
	
	
	public List cody(int cody_temp) {
		String sql=null;
		List list = new ArrayList();
		
		try {
			  con = dataSource.getConnection();
			  sql="SELECT * FROM"
			  		+ " (SELECT tbl_product.P_CATEGORY,tbl_product.p_idx,tbl_product.p_name,tbl_product.p_price,tbl_img.i_name,"
			  		+ "    ROW_NUMBER() OVER (PARTITION BY tbl_product.P_CATEGORY ORDER BY tbl_product.p_idx DESC) AS row_num"
			  		+ "      FROM tbl_product"
			  		+ " INNER JOIN tbl_img ON tbl_product.p_idx = tbl_img.i_pidx"
			  		+ "     WHERE tbl_product.p_codytemp = ?"
			  		+ "     AND tbl_product.P_CATEGORY IN ('top', 'bottom', 'acc')"
			  		+ "	)"
			  		+ "WHERE row_num <= 3";
			  pstmt = con.prepareStatement(sql);
			  pstmt.setInt(1, cody_temp);
			  rs = pstmt.executeQuery();
			  while(rs.next()) {
				  	
					  ImgVo imgVo=new ImgVo();
				  	  imgVo.setI_name(rs.getString("I_NAME")); //상품 이미지명
				  	  
				  	  ProductVo productVo=new ProductVo();
				  	
				  		productVo.setP_category(rs.getString("P_CATEGORY"));
				  		productVo.setP_idx(rs.getInt("P_IDX"));
				  		productVo.setP_name(rs.getString("P_NAME"));
				  		productVo.setP_price(rs.getInt("P_PRICE"));
				  		productVo.setImgVo(imgVo);
				  	  
				  	  System.out.println("dao : "+productVo.getP_idx());
				  	  
				  	  list.add(productVo);
				  	  
			  }	
		} catch (Exception e) {
			System.out.println("WeatherDAO의 cody메소드 내부에서 오류 : " + e);
		} finally { 
			ResourceClose();
		}
		return list;
	}


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
	}




}	
	