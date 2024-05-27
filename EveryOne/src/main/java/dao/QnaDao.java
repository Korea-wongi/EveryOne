package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.BuyVo;
import vo.QnaVo;

public class QnaDao {

	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;
	private QnaVo vo;
	private ArrayList list;
	private String sql;
	
	// 기본 생성자
	public QnaDao() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	}
	
	// TBL_QNA 테이블에 등록된 전체 글 조회
	public ArrayList qnaListAll(){  
		list = new ArrayList();
		try {
			  con = dataSource.getConnection();
			  pstmt = con.prepareStatement("select * from TBL_QNA order by Q_GROUP asc");
			  rs = pstmt.executeQuery();
			  while(rs.next()) {				  
				  vo = new QnaVo(rs.getInt("Q_IDX"), 
						  			rs.getString("Q_TITLE"), 
						  			rs.getString("Q_CONTENT"), 
						  			rs.getInt("Q_GROUP"), 
						  			rs.getInt("Q_LEVEL"), 
						  			rs.getDate("Q_DATE"), 
						  			rs.getInt("Q_CNT"), 
						  			rs.getString("Q_SFILE"), 
						  			rs.getString("Q_MID"), 
						  			rs.getInt("Q_BIDX"));
				  list.add(vo);				           
			  }	
		} catch (Exception e) {
			System.out.println("QnaDAO의 qnaListAll메소드 내부에서 오류 : " + e);
		} finally { 
			ResourceClose();
		}
		return list;
	}
		
		
	// 배송문의,상품문의,취소문의,결제문의 페이지 요청, q_title 문의유형 뿌려주기
	public ArrayList qnaShipListAll(String q_title) {
		list = new ArrayList();
		try {
			  con = dataSource.getConnection();
			  pstmt = con.prepareStatement("select * from TBL_QNA where q_title=? order by Q_GROUP asc");
			  pstmt.setString(1, q_title);
			  rs = pstmt.executeQuery();
			  while(rs.next()) {				  
				  vo = new QnaVo(rs.getInt("Q_IDX"), 
						  			rs.getString("Q_TITLE"), 
						  			rs.getString("Q_CONTENT"), 
						  			rs.getInt("Q_GROUP"), 
						  			rs.getInt("Q_LEVEL"), 
						  			rs.getDate("Q_DATE"), 
						  			rs.getInt("Q_CNT"), 
						  			rs.getString("Q_SFILE"), 
						  			rs.getString("Q_MID"), 
						  			rs.getInt("Q_BIDX"));
				  list.add(vo);				           
			  }	
		} catch (Exception e) {
			System.out.println("QnaDAO의 qnaShipListAll메소드 내부에서 오류 : " + e);
		} finally { 
			ResourceClose();
		}
		return list;
	}

	
	// TBL_QNA 테이블에 등록된 전체 글 갯수 조회
	public int getTotalRecord() { 
		int total = 0;		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement("select count(*) as Q_CNT from TBL_QNA");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				total = rs.getInt("Q_CNT");
			}
		} catch (Exception e) {
			System.out.println("QnaDAO의 getTotalRecord메소드 내부에서 오류 : " + e);
		} finally {
			ResourceClose(); 
		}
		return total;
	}
	
	
	// TBL_QNA 테이블에 등록된 배송문의,상품문의,취소문의,결제문의 글 갯수 조회
	public int getShipRecord(String q_title) {
		int total = 0;		
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement("select count(*) as Q_CNT from TBL_QNA where q_title=?");
			pstmt.setString(1, q_title);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				total = rs.getInt("Q_CNT");
			}
			
		} catch (Exception e) {
			System.out.println("QnaDAO의 getShipRecord메소드 내부에서 오류 : " + e);
		} finally {
			ResourceClose(); 
		}
		return total;
	}
	
	// 글 제목 클릭 시 작성자 비번 일치 확인 
	public QnaVo passCheck(String q_idx, String qnapass,String m_admin) {
	    try {
	        con = dataSource.getConnection();
	        String m_pass = null; // 비밀번호 값 저장할 변수 선언
	        
	        sql="select m_admin from tbl_member where m_admin=? AND ROWNUM <= 1"; //관리자 or 회원 판단 
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, m_admin);
	        rs = pstmt.executeQuery();
	        rs.next();
	        String admin= rs.getString("m_admin");
	        System.out.println("admin의 값은? " + admin);
	        
	        sql = "select q_level from tbl_qna where q_idx=? AND ROWNUM <= 1"; // 주글 or 답변글인지 판단
	        pstmt = con.prepareStatement(sql);
	        pstmt.setInt(1, Integer.parseInt(q_idx));
	        rs = pstmt.executeQuery();
	        rs.next();
	        int level = rs.getInt("Q_LEVEL");
	        System.out.println("level의 값은? " + level);

	        if (level == 0) { // 주글일 경우
	            sql = "select m_pass from tbl_member where m_id=(select q_mid from tbl_qna where q_idx=?)";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, Integer.parseInt(q_idx));
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                System.out.println("주글 작성자 비번은? " + rs.getString("m_pass"));
	                m_pass = rs.getString("m_pass");
	            }
	        } else if (level > 0) { //답변글일 경우 
	            sql = "select m_pass from tbl_member where m_id=(select q_mid from tbl_qna where Q_GROUP<(select q_group from tbl_qna where q_idx=?) and q_level=0 AND ROWNUM <= 1)";
	            pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, Integer.parseInt(q_idx));
	            rs = pstmt.executeQuery();
	            if (rs.next()) {
	                System.out.println("답변글 작성자 비번은? " + rs.getString("m_pass"));
	                m_pass = rs.getString("m_pass");
	            }
	        }

	        if (qnapass != null && qnapass.equals(m_pass) || admin.equals("2")) {
	            pstmt = con.prepareStatement("update TBL_QNA set Q_CNT=Q_CNT+1 where Q_IDX=?");
	            pstmt.setInt(1, Integer.parseInt(q_idx));
	            pstmt.executeUpdate();

	            pstmt = con.prepareStatement("select * from TBL_QNA where Q_IDX=?");
	            pstmt.setInt(1, Integer.parseInt(q_idx));
	            rs = pstmt.executeQuery();

	            while (rs.next()) {
	                vo = new QnaVo(rs.getInt("Q_IDX"),
	                        rs.getString("Q_TITLE"),
	                        rs.getString("Q_CONTENT"),
	                        rs.getInt("Q_GROUP"),
	                        rs.getInt("Q_LEVEL"),
	                        rs.getDate("Q_DATE"),
	                        rs.getInt("Q_CNT"),
	                        rs.getString("Q_SFILE"),
	                        rs.getString("Q_MID"),
	                        rs.getInt("Q_BIDX"));
	            }
	        } else {      	
	        		vo = null;
	        }
	    } catch (Exception e) {
	        System.out.println("QnaDAO의 passCheck메소드 내부에서 오류 : " + e);
	    } finally {
	        ResourceClose();
	    }
	    return vo;
	}


	// 고객 관점 : 글 수정 후 업데이트 요청 기능
		public int updateQna(String update_title, String update_content, String update_idx) {
			int result=0;
			try {
				con=dataSource.getConnection();
				pstmt=con.prepareStatement("update TBL_QNA set Q_TITLE=?, Q_CONTENT=? where Q_IDX=?");
				pstmt.setString(1, update_title);
				pstmt.setString(2, update_content);
				pstmt.setString(3, update_idx);
				result=pstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("QnaDAO의 updateQna()메소드 내부 SQL문 오류 : "+e);
				e.printStackTrace(); //예외메세지
			}finally {
				ResourceClose();
			}
			return result;
		}

	// 문의사항 새 글 등록 화면 보여주기
			public ArrayList wirteNewQna(String loginId) {
				BuyVo buyVo=null;
				list=new ArrayList();
				
				try {
					con=dataSource.getConnection();
					pstmt=con.prepareStatement("select * from tbl_buy where tbl_buy.b_oidx IN"
							+ "(select tbl_order.O_IDX from TBL_ORDER where tbl_order.O_MID=?) order by tbl_buy.b_idx desc");
					pstmt.setString(1, loginId);
					rs=pstmt.executeQuery();
					while (rs.next()) {
						buyVo=new BuyVo(rs.getInt("B_IDX"), 
										rs.getString("B_OIDX"),
										rs.getString("B_NAME"),
										rs.getInt("B_PRICE"));
						list.add(buyVo);
					}
				} catch (Exception e) {
					System.out.println("QnaDAO의 wirteNewQna()메소드 내부 SQL문 오류 : "+e);
				}finally {
					ResourceClose();
				}
				return list;
			}
			
			
	
	// 자원해제
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


	public int insertQna(QnaVo insertVo) {
		int articleNO = getNewArticleNO();
		try {
			con = dataSource.getConnection();
			// 주글의 b_group(pos)열의 값을 1증가
			pstmt = con.prepareStatement("update TBL_QNA set Q_GROUP = Q_GROUP + 1");
			pstmt.executeUpdate();
			
			
			// 주글 TBL_QNA테이블에 insert
			pstmt = con.prepareStatement("insert into TBL_QNA "
					+ "(Q_IDX,Q_TITLE,Q_CONTENT,Q_GROUP,Q_LEVEL,Q_DATE,Q_CNT,Q_SFILE,Q_MID,Q_BIDX) "
			  + "values(  ?  ,   ?   ,    ?    ,   0  ,   0  , sysdate,  0  ,   ?   ,  ?  ,  ?)");
			pstmt.setInt(1, articleNO);
			pstmt.setString(2, insertVo.getQ_title());
			pstmt.setString(3, insertVo.getQ_content());
			pstmt.setString(4, insertVo.getQ_sfile());
			pstmt.setString(5, insertVo.getQ_mid());
			pstmt.setInt(6, insertVo.getQ_bidx());

			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("QnaDAO의 insertQna메소드 내부에서 오류 : " + e);
			
		}finally {
			ResourceClose();
		}
		
		return articleNO;
	}

	// TBL_QNA 저장된 최신 글번호 조회 후 반환 
	private int getNewArticleNO() {
		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement("select max(Q_IDX) from TBL_QNA");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1) + 1);
			}				
		} catch (Exception e) {
			System.out.println("getNewArticleNO메소드 내부에서 오류 :" + e);
			e.printStackTrace();
		} finally {
			ResourceClose();
		}		
		return 0;
	}

	public String deleteQna(String delete_idx) {
		String delResult = null;
		
		try {
			con=dataSource.getConnection();
			pstmt=con.prepareStatement("delete from tbl_qna where q_idx=?");
			pstmt.setString(1,delete_idx);
			int val=pstmt.executeUpdate();
			
			if(val==1) {
				delResult="삭제성공";
			}else {
				delResult="삭제실패";
			}
		} catch (Exception e) {
			System.out.println("QnaDAO의 deleteQna()메소드 내부 SQL문 오류 : "+e);
		}finally {
			ResourceClose();
		}
		return delResult;
	}

	public int updateQnaFile(String q_idx, String upfile) {
		int result = 0; //수정 성공시 1저장 또는 실패시 0저장

		try {
			con = dataSource.getConnection();
			pstmt = con.prepareStatement("update tbl_qna set Q_SFILE=? where q_idx=?");
			pstmt.setString(1, upfile);
			pstmt.setInt(2, Integer.parseInt(q_idx));
			
			result = pstmt.executeUpdate();
					
		} catch (Exception e) {
			System.out.println("QnaDAO의 updateQnaFile메소드 내부에서 SQL문 실행 오류 : " + e);
			e.printStackTrace();
		} finally {
			ResourceClose();
		}
		return result;
	}


	public int replyInsertQna(String re_title,String re_mid,String re_b_inx,String re_sfile,String re_content,String super_q_idx){
			
		int articleNO = getNewArticleNO();//추가할 답변글의 글번호 얻기 
		try {
			con = dataSource.getConnection();
						
			//1. 부모글(주글)의 글번호를 이용해 q_group열의 값과 , q_level열의 값을 조회 
			sql = "select q_group, q_level from tbl_qna where q_idx=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, Integer.parseInt(super_q_idx)); 
					rs = pstmt.executeQuery();
					rs.next();
					String q_group = rs.getString("q_group");//주글의 그룹번호
					String q_level = rs.getString("q_level");//주글의 들여쓰기정도값 
						

			//주글(부모글)의 q_group열의 값보다 큰 값을 가지는 주글 q_group을 1증가한 값으로 수정
			sql = "update tbl_qna set q_group = q_group + 1 where q_group > ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, q_group);
					pstmt.executeUpdate();

			//답변글 insert 주글(부모글)의 q_group,q_level열의 값에 1더한 값으로 insert
			sql="insert into TBL_QNA (Q_IDX,Q_TITLE,Q_CONTENT,Q_GROUP,Q_LEVEL,Q_DATE,Q_CNT,Q_SFILE,Q_MID,Q_BIDX) "
					        + "values(  ?  ,   ?   ,    ?    ,   ?  ,   ?  , sysdate,  0  ,   ?   ,  ?  ,  ?   )";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, articleNO);
					pstmt.setString(2,re_title);
					pstmt.setString(3,re_content);
					pstmt.setInt(4,Integer.parseInt(q_group) + 1);
					pstmt.setInt(5, Integer.parseInt(q_level) + 1);
					pstmt.setString(6,re_sfile);
					pstmt.setString(7,re_mid);
					pstmt.setInt(8,Integer.parseInt(re_b_inx));
					pstmt.executeUpdate();
				} catch (Exception e) {
					System.out.println("QnaDAO의 replyInsertQna메소드 내부에서 SQL문 실행 오류:" + e);
				} finally {
					ResourceClose();
				}
				return articleNO; //추가한 답변글의 글번호 반환
			}

	// 검색어 입력 후 결과 보여주기 -> 검색결과 값(배열)
	public ArrayList qnaList(String keyField, String keyWord) {
		list = new ArrayList();
		if(!keyWord.equals("")) { //검색어 입력O
			if(keyField.equals("name")) {//검색 기준열 작성자(Q_MID)
				sql = "select * from tbl_qna "
					+ " where q_mid like '%"+ keyWord + "%'"
					+ " order by q_group asc";				
			}else if(keyField.equals("tilte")) {//검색 기준열 작성자(Q_TITLE)
				sql = "select * from tbl_qna "
						+ " where Q_TITLE like '%"+ keyWord + "%'"
						+ " order by q_group asc";					
			}else {//검색 기준열 작성자(Q_CONTENT)
				sql = "select * from tbl_qna "
						+ " where Q_CONTENT like '%"+ keyWord + "%'"
						+ " order by q_group asc";					
			}
		}else { //검색어 입력X
			sql = "select * from tbl_qna order by q_group asc"; //모든 글 조회
		} // if-else문 종료 
		
		try {
			  con = dataSource.getConnection();
			  pstmt = con.prepareStatement(sql);
			  rs = pstmt.executeQuery();
			  while(rs.next()) {				  
				  vo =new QnaVo(rs.getInt("Q_IDX"), 
				  			rs.getString("Q_TITLE"), 
				  			rs.getString("Q_CONTENT"), 
				  			rs.getInt("Q_GROUP"), 
				  			rs.getInt("Q_LEVEL"), 
				  			rs.getDate("Q_DATE"), 
				  			rs.getInt("Q_CNT"), 
				  			rs.getString("Q_SFILE"), 
				  			rs.getString("Q_MID"), 
				  			rs.getInt("Q_BIDX"));
				  list.add(vo);				           
			  }				
		} catch (Exception e) {
			System.out.println("QnaDAO클래스의 qnaList메소드 내부에서 SQL실행 오류:" + e);		
		} finally {
			ResourceClose();
		}
		return list;//BoardService로 반환
	}

	// 검색어 입력 후 결과 보여주기 -> 검색된 행 갯수
	public int getTotalRecord(String keyField, String keyWord) {
		int qnaSearchRecord=0;
		if(!keyWord.equals("")) { //검색어 입력O
			if(keyField.equals("name")) {//검색 기준열 작성자(Q_MID)
				sql = "select count(*) as cnt from tbl_qna "
					+ " where q_mid like '%"+ keyWord + "%'"
					+ " order by q_group asc";				
			}else if(keyField.equals("tilte")) {//검색 기준열 작성자(Q_TITLE)
				sql = "select count(*) as cnt from tbl_qna "
						+ " where Q_TITLE like '%"+ keyWord + "%'"
						+ " order by q_group asc";					
			}else {//검색 기준열 작성자(Q_CONTENT)
				sql = "select count(*) as cnt from tbl_qna "
						+ " where Q_CONTENT like '%"+ keyWord + "%'"
						+ " order by q_group asc";					
			}
		}else { //검색어 입력X
			sql = "select count(*) as cnt from tbl_qna order by q_group asc"; //모든 글 조회
		} // if-else문 종료
		try {
			  con = dataSource.getConnection();
			  pstmt = con.prepareStatement(sql);
			  rs = pstmt.executeQuery();
			  while(rs.next()) {				  
				  qnaSearchRecord= rs.getInt("cnt");			           
			  }				
		} catch (Exception e) {
			System.out.println("QnaDAO클래스의 getTotalRecord메소드 내부에서 SQL실행 오류:" + e);		
		} finally {
			ResourceClose();
		}
		return qnaSearchRecord;//BoardService로 반환
	}
	


		
}
