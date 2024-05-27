package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import vo.ReviewVo;
import vo.MemberVo;

public class ReviewDao {

	private Connection con;	

	private PreparedStatement pstmt;	

	private ResultSet rs;

	private DataSource dataSource;
	
	public ReviewDao() {
	
		try {

			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			

			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}
	
	
	//자원해제
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

		
		//리뷰 모든 글 조회						//상품번호임
		public ArrayList<ReviewVo> boardListAll(String r_pidx) {
			
			ArrayList<ReviewVo> list = new ArrayList<>();
			
			try {
				  con = dataSource.getConnection();
				  
				  pstmt = con.prepareStatement("SELECT * FROM tbl_review WHERE r_pidx=? ORDER BY r_idx DESC");
				  
				  pstmt.setInt(1, Integer.parseInt(r_pidx));
				  
				  rs = pstmt.executeQuery();
				  
				  System.out.println("리뷰다오시작");
				  				  
				  while(rs.next()) {				  

					ReviewVo reviewVo = new ReviewVo();
					  
					  reviewVo.setR_idx(rs.getInt("r_idx"));//글번호
					  reviewVo.setR_title(rs.getString("r_title"));
					  reviewVo.setR_content(rs.getString("r_content"));
					  reviewVo.setR_date(rs.getDate("r_date"));
					  reviewVo.setR_sfile(rs.getString("r_sfile"));
					  reviewVo.setR_mid(rs.getString("r_mid"));
					  reviewVo.setR_pidx(rs.getInt("r_pidx"));
					  
					  list.add(reviewVo);	
					  System.out.println("리뷰다오중간");
				  }	
			} catch (Exception e) {
				System.out.println("ReviewDAO / boardListAll메소드 내부에서 오류 : " + e);
			} finally { 
				ResourceClose();
			}
			
			System.out.println("리뷰다오끝");
			return list;
			
		}
		
		//멤버아이디 반환함
		//용도 : 구매 이력 판별
		public ArrayList<String> mbp(String p_name) {
		    ArrayList<String> midList = new ArrayList<String>(); // o_mid 값을 저장할 리스트

		    try {
		        con = dataSource.getConnection();
		        pstmt = con.prepareStatement("SELECT o.o_mid FROM tbl_buy b JOIN tbl_order o ON b.b_oidx = o.o_idx WHERE b.b_name = ?");
		        pstmt.setString(1, p_name);
		        rs = pstmt.executeQuery();

		        while(rs.next()) {
		            // o_mid 값을 리스트에 추가
		            String o_mid = rs.getString("o_mid");
		            midList.add(o_mid);
		        }

		    } catch (Exception e) {
		        System.out.println("ReviewDAO / mbp 메소드 내부에서 오류 : " + e);
		    } finally { 
		        ResourceClose();
		    }

		    return midList;
		}
		
	
	
	//새 글 작성시 사용합니다
	//FileBoard테이블에 저장된 최신 글번호 조회 후 반환
	public int getRewArticleNO() {
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select max(r_idx) from tbl_review";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1) + 1); //insert할 글번호 만들어서 반환
			}				
		} catch (Exception e) {
			System.out.println("ReviewDAO의 getRewArticleNO메소드 내부에서 오류 :");
			e.printStackTrace();
		} finally {
			ResourceClose();
		}		
		return 0;
	}


	//새글 추가
	public int insertBoard(ReviewVo vo) {
		
		int articleNO = getRewArticleNO(); // 글번호 생성
	//System.out.println(articleNO);
			
	
	String sql = null;
	try {
		//DB연결
		con = dataSource.getConnection();
		
		sql = "insert into tbl_review (r_idx, r_title, r_content, r_date, "
				+ "r_sfile, r_mid, r_pidx) "
				+ " values (?,?,?,sysdate,?,?,?)";
		
		pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, articleNO); //글번호
		pstmt.setString(2, vo.getR_title());
		pstmt.setString(3, vo.getR_content());
		pstmt.setString(4, vo.getR_sfile());
		pstmt.setString(5, vo.getR_mid()); //회원아이디
		pstmt.setInt(6, vo.getR_pidx()); //상품번호
		
		pstmt.executeUpdate();
			
	} catch (Exception e) {
		System.out.println("ReviewDao의 insertBoard메소드 내부에서 오류:" + e);
		e.printStackTrace();
	} finally {
		ResourceClose();
	}
	
	return articleNO;	//새글정보를 insert한후 insert한 글번호 반환 
					    // 글번호를 반환해서 글번호폴더 생성해서 그안에 업로드한파일을 이동시켜 저장
		
	}
	
	

	//선택한 글 조회
	public ReviewVo boardRead(String r_idx) {
		
		ReviewVo vo = null; 
		String sql = null; 
		
		try {
			 con = dataSource.getConnection(); 			 
			 sql = "select * from tbl_review where r_idx=?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, Integer.parseInt(r_idx));
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				     vo = new ReviewVo(
				    		 
							    		 rs.getInt("r_idx"),
								        	rs.getString("r_title"),
								        	rs.getString("r_content"), 
											rs.getDate("r_date"), 
											rs.getString("r_sfile"),
											rs.getString("r_mid"),
											rs.getInt("r_pidx"));
				    		 			
			 }	
		} catch (Exception e) {
			System.out.println("ReviewDAO의 boardRead메소드 내부에서 오류 : " + e);
		} finally {
			ResourceClose();
		}
	
		return vo;
	}

	
	//글 삭제 
	public String deleteBoard(String delete_idx) {
		String result = null;		
		try {
			con = dataSource.getConnection();
			
			String sql = "DELETE FROM tbl_review WHERE r_idx=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_idx);
		
			int val = pstmt.executeUpdate();
			
			if(val == 1) { result = "삭제성공";}
			else {result = "삭제실패";}
			
		}catch (Exception e) {
			System.out.println("ReviewDAO의 deleteBoard메소드 내부에서 오류 :" + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return result; 
	}

	
}







