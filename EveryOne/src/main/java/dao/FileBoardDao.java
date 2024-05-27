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

import vo.NewsVo;
import vo.MemberVo;

public class FileBoardDao {

	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;
	
	public FileBoardDao() {
		try {

			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			

			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	}
	
	// 공지사항 모든 글 조회
	public ArrayList boardListAll() {
		
		ArrayList list = new ArrayList();
		
		try {
			  con = dataSource.getConnection();
			  
			  pstmt = con.prepareStatement("select * from tbl_news order by n_idx desc");
			  
			  rs = pstmt.executeQuery();
			  
			  while(rs.next()) {				  
					  
				  NewsVo newsVo = new NewsVo();
				  
				  newsVo.setN_idx(rs.getInt("n_idx"));
				  newsVo.setN_title(rs.getString("n_title"));
				  newsVo.setN_content(rs.getString("n_content"));
				  newsVo.setN_date(rs.getDate("n_date"));
				  newsVo.setN_cnt(rs.getInt("n_cnt"));
				  newsVo.setN_sfile(rs.getString("n_sfile"));
				  newsVo.setN_mid(rs.getString("n_mid"));
						  
				  list.add(newsVo);				           
			  }	
		} catch (Exception e) {
			System.out.println("FileBoardDAO / boardListAll 메소드 내부에서 오류 : ");
			e.printStackTrace();
		} finally { 
			ResourceClose();
		}
		return list;
	}
	
	//현재 board테이블에 저장된 총 글의 갯수 조회후 반환 하는 메소드 
	public int getTotalRecord() {
		//조회된 글 갯수 저장할 변수
		int total = 0;		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement("select count(*) as n_cnt from tbl_news");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt("cnt");
			}
		} catch (Exception e) {
			System.out.println("BoardDAO / getTotalRecord 메서드 내부에서 오류 : ");
			e.printStackTrace();
		} finally {
			ResourceClose();//자원해제 
		}
		return total;
	}
	
	
	//새 글 작성시 사용합니다
	//FileBoard테이블에 저장된 최신 글번호 조회 후 반환
	public int getNewArticleNO() {
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select max(n_idx) from tbl_news";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1) + 1); //insert할 글번호 만들어서 반환
			}				
		} catch (Exception e) {
			System.out.println("BoardDAO / getNewArticleNO 메소드 내부에서 오류 :");
			e.printStackTrace();
		} finally {
			ResourceClose();
		}		
		return 0;
	}


	//새글 추가
	public int insertBoard(NewsVo vo) {
		
		int articleNO = getNewArticleNO(); // 글번호 생성
		String sql = null;
		
		try {
			//DB연결
			con = dataSource.getConnection();
			
			sql = "insert into tbl_news (n_idx, n_title, n_content, n_date, "
					+ "n_cnt, n_sfile, n_mid) "
					+ " values (?,?,?,sysdate,?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, articleNO);
			pstmt.setString(2, vo.getN_title());
			pstmt.setString(3, vo.getN_content());
			pstmt.setInt(4, vo.getN_cnt());
			pstmt.setString(5, vo.getN_sfile());
			pstmt.setString(6, vo.getN_mid());
			
			pstmt.executeUpdate();
				
		} catch (Exception e) {
			System.out.println("FileBoardDao / insertBoard 메소드 내부에서 오류:");
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		
		return articleNO;	//새글정보를 insert한후 insert한 글번호 반환 
						    // 글번호를 반환해서 글번호폴더 생성해서 그안에 업로드한파일을 이동시켜 저장
	}
	
	
	//선택한 검색 기준열과 입력한 검색어 단어가 포함된 내용이 있는 글 조회(검토 필요)
	public ArrayList boardSearch(String keyField, String keyWord) {
	
		String sql = null;
		
		ArrayList<NewsVo>  list = new ArrayList<NewsVo>();
				
		if(keyField.equals("subject")) {//제목
			
			sql = "select * from tbl_news "
					+ " where n_title like '%"+ keyWord + "%'"
					+ " order by n_idx asc";				
			
		}else {//글 내용
			
			sql = "select * from tbl_news "
					+ " where n_content like '%"+ keyWord + "%'"
					+ " order by n_idx asc";				
		}
		//-----------------------------------------		
		try {
			  con = dataSource.getConnection(); //DB의 테이블과 연결 
			  
			  pstmt = con.prepareStatement(sql);
			  
			  rs = pstmt.executeQuery();
			  
			  //조회된 ResultSet의 정보를 한 행 단위로 꺼내서
			  //NewsVo객체에 한행씩 저장 후 NewsVo객체들을 ArrayList배열에 하나씩 추가해서 저장
			  while(rs.next()) {				  
				  NewsVo vo = new NewsVo(
				  
					 rs.getInt("n_idx"),
			        	rs.getString("n_title"),
			        	rs.getString("n_content"), 
						rs.getDate("n_date"), 
						rs.getInt("n_cnt"),
						rs.getString("n_sfile"),
						rs.getString("n_mid"));
				  
				  list.add(vo);				           
			  }				
			
		} catch (Exception e) {
			System.out.println("FileBoardDao클래스의 boardSearch메소드 내부에서 SQL실행 오류:" + e);		
		} finally {
			ResourceClose();
		}
		
		return list;//BoardService로 반환
	}

	//글 조회수 증가
	public NewsVo boardRead(String n_idx) {
		
		NewsVo vo = null; 
		String sql = null; 
		
		try {
			 con = dataSource.getConnection(); 
			 sql = "update tbl_news set n_cnt=n_cnt+1  where  n_idx=?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, Integer.parseInt(n_idx));
			 pstmt.executeUpdate();
			 
			 sql = "select * from tbl_news where n_idx=?";
			 pstmt = con.prepareStatement(sql);
			 pstmt.setInt(1, Integer.parseInt(n_idx));
			 rs = pstmt.executeQuery();
			 
			 if(rs.next()) {
				     vo = new NewsVo(
				    		 
							    		 rs.getInt("n_idx"),
								        	rs.getString("n_title"),
								        	rs.getString("n_content"), 
											rs.getDate("n_date"), 
											rs.getInt("n_cnt"),
											rs.getString("n_sfile"),
											rs.getString("n_mid")
											
				    		 			);
			 }	
		} catch (Exception e) {
			System.out.println("FileBoardDAO의 boardRead메소드 내부에서 오류 : " + e);
		} finally {
			ResourceClose();
		}
	
		return vo;
	}

	//글수정 
	public int updateBoard(String idx_, String title_, String content_) {
		int result = 0; //수정 성공시 1저장 또는 실패시 0저장
		try {
			con = dataSource.getConnection();
	
			String sql = "update tbl_news set "
						+ " n_title=?, n_content=? "
						+ " where n_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title_);
			pstmt.setString(2, content_);
			pstmt.setString(3, idx_);

			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("FileBoardDAO의 updateBoard메소드 내부에서 SQL문 실행 오류 : " + e);
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return result;
	}
	
	//글수정 
	public int updateBoard2(String idx_, String title_, String content_, String s_file) {
		int result = 0; //수정 성공시 1저장 또는 실패시 0저장
		try {
			
			con = dataSource.getConnection();
			
			String sql = null;
			
			if(s_file == null) {
			
				sql = "update tbl_news set "
							+ " n_title=?, n_content=? "
							+ " where n_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title_);
			pstmt.setString(2, content_);
			pstmt.setString(3, idx_);
			
			result = pstmt.executeUpdate();
			
			}else {
				
				sql = "update tbl_news set "
						+ " n_title=?, n_content=? n_sfile=? "
						+ " where n_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title_);
			pstmt.setString(2, content_);
			pstmt.setString(3, s_file);
			pstmt.setString(4, idx_);
			
			result = pstmt.executeUpdate();
			
			}
			
		} catch (Exception e) {
			System.out.println("FileBoardDAO의 updateBoard메소드 내부에서 SQL문 실행 오류 : " + e);
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return result;
	}

	//글 삭제 
	public String deleteBoard(String delete_idx) {
		String result = null;		
		try {
			con = dataSource.getConnection();
			
			String sql = "DELETE FROM tbl_news WHERE n_idx=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, delete_idx);
		
			int val = pstmt.executeUpdate();
			
			if(val == 1) { result = "삭제성공";}
			else {result = "삭제실패";}
			
		}catch (Exception e) {
			System.out.println("FileBoardDAO의 deleteBoard메소드 내부에서 오류 :" + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return result; 
	}
	
	//파일만 삭제 (삭제대상검토)
	public String deleteFile(String delete_idx) {
		String result = null;		
		try {
			con = dataSource.getConnection();
			
			String sql = "UPDATE tbl_news set n_sfile=? WHERE n_idx=?";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setNull(1, java.sql.Types.VARCHAR);
			pstmt.setString(2, delete_idx);
		
			int val = pstmt.executeUpdate();
			
			if(val == 1) { result = "삭제성공";}
			else {result = "삭제실패";}
			
		}catch (Exception e) {
			System.out.println("FileBoardDAO의 deleteBoard메소드 내부에서 오류 :" + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return result; 
	}
	
	// 파일 수정 처리
	public int updateBoard(String idx_, String n_sfile) {
		int result = 0; //수정 성공시 1저장 또는 실패시 0저장
		String sql = null;
		try {
			con = dataSource.getConnection();
			System.out.println("다오 = " + idx_);
	
			sql = "update tbl_news set n_sfile=? where n_idx=?";
			 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, n_sfile);
			pstmt.setInt(2, Integer.parseInt(idx_));

			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("FileBoardDAO의 updateBoard메소드 내부에서 SQL문 실행 오류 : " + e);
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return result;
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
	
}







