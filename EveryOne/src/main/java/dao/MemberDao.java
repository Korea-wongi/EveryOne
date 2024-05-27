package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.websocket.Session;

import vo.KakaoUserVo;
import vo.MemberVo;

public class MemberDao {

	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;
	
	
	public MemberDao() {
	
		try {
			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}// MemberDao()
	
	
	// 회원가입 처리 요청
	public void insertMember(MemberVo memberVo) {
		String sql = null;
		try {
			con = dataSource.getConnection();
			SimpleDateFormat birth = new SimpleDateFormat("yyyy-MM-dd");
			
			
			
			sql = "INSERT INTO tbl_member VALUES"
					+ "(?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getM_id());
			pstmt.setString(2, memberVo.getM_pass());
			pstmt.setString(3, memberVo.getM_name());
			pstmt.setString(4, memberVo.getM_email());
			pstmt.setString(5, memberVo.getM_hp());
			pstmt.setDate(6, memberVo.getM_birth());
			pstmt.setString(7, memberVo.getM_gender());
			pstmt.setString(8, memberVo.getM_address1());
			pstmt.setString(9, memberVo.getM_admin());
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("MemberDao / insertMember 메소드 오류 : " + e);
		} finally {
			ResourceClose();
		}
	}// insertMember()
	
	
	// 아이디 중복성 체크
	public boolean overLappedId(String m_id) {
		boolean result = false;
		String sql = null;
		try {
			con = dataSource.getConnection();
			System.out.println("다오 " + m_id );
			
			// 아이디 존재 여부 
			sql = "select decode(count(*), 1, 'true', 'false') as result from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			rs.next();
			if(rs.getString("result").equals("true")) {
				return true;
			}
			
			
		} catch (Exception e) {
			System.out.println("MemberDao / overLappedId 메소드 오류 : " + e);
		} finally {
			ResourceClose();
		}
		return result;
	}// overLappedId()
	
	
	//로그인처리 요청
		public int userLogin(String m_id, String m_pass) {
			
			int check = -1;
			try {
				con = dataSource.getConnection();
				
				String sql = "select * from tbl_member where m_id=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,  m_id);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {			
					
					if(m_pass.equals(rs.getString("m_pass"))) {
						
						check = 1; //아이디 OK, 비밀번호 OK
				
					}else {//아이디 OK, 비밀번호 NO
						check = 0;
					}
				}else {
					check = -1; //아이디 NO
				}
			} catch (Exception e) {
				System.out.println("MemberDao /  userLogin메소드에서 오류 : " + e);
			} finally {
				ResourceClose();
			}
			return check;
		}//userLogin()
	
	
	// 비밀번호 체크 후 회원 수정 페이지 요청
	public Boolean passCheck(String m_id, String m_pass) {
		boolean result = false;
		String sql = null;
		try {
			con = dataSource.getConnection();
			
			sql = "select m_pass from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 비밀번호가 일치하면  true
				if(rs.getString("m_pass").equals(m_pass)) {
					return true;
				}else {
					return false;
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("MemberDao / passCheck 메소드 오류 : " + e);
		} finally {
			ResourceClose();
		}
		return result;
	}// passCheck()
	
	
	// 회원 수정 페이지 뿌려줄 vo 가져오기
	public MemberVo MemberOne(String m_id) {
		MemberVo memberVo = null;
		String sql = null;
		try {
			con = dataSource.getConnection();
			
			sql = "select * from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberVo = new MemberVo();
				
				memberVo.setM_id(rs.getString("m_id"));
				memberVo.setM_pass(rs.getString("m_pass"));
				memberVo.setM_name(rs.getString("m_name"));
				memberVo.setM_email(rs.getString("m_email"));
				memberVo.setM_hp(rs.getString("m_hp"));
				memberVo.setM_birth(rs.getDate("m_birth"));
				memberVo.setM_gender(rs.getString("m_gender"));
				memberVo.setM_address1(rs.getString("m_address1"));
				memberVo.setM_regdate(rs.getDate("m_regdate"));
				memberVo.setM_admin(rs.getString("m_admin"));
			}
			
		} catch (Exception e) {
			System.out.println("MemberDao / MemberOne 메소드 오류 : " + e);
		} finally {
			ResourceClose();
		}
		
		return memberVo;
		
	}// MemberOne()	
	
	// 회원정보 수정 후 mypage 요청
	public int memUpdate(MemberVo memberVo) {
		String sql = null;
		int result = 0;
		try {
			con = dataSource.getConnection();
			
			sql = "update tbl_member set m_pass=?, m_name=?, m_email=?, m_hp=?, m_address1=? where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getM_pass());
			pstmt.setString(2, memberVo.getM_name());
			pstmt.setString(3, memberVo.getM_email());
			pstmt.setString(4, memberVo.getM_hp());
			pstmt.setString(5, memberVo.getM_address1());
			pstmt.setString(6, memberVo.getM_id());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("MemberDao / memUpdate 메소드 오류 : " + e);
		} finally {
			ResourceClose();
		}
		
		return result;
	}// memUpdate()

	
	

	public MemberVo selectMember(String m_id, String m_email) {
		
		MemberVo memberVo = null;
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select m_id, m_email from tbl_member where m_id=? and m_email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			pstmt.setString(2, m_email);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				memberVo = new MemberVo();
				memberVo.setM_id(m_id);
				memberVo.setM_email(m_email);
			}
	
		} catch (Exception e) {
			System.out.println("MemberDao / selectMember 메소드 오류 : " + e);
		}finally {
			ResourceClose();
		}
		return memberVo;
	}
	
	// 탈퇴 후 main페이지 요청. 탈퇴 하기
	public String memDelete(String m_id) {
		
		String sql = null;
		String result = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "delete from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			
			int val = pstmt.executeUpdate();
			
			
			if(val == 1) {
				result = "탈퇴성공";
			}else {
				result = "탈퇴실패";
			}
			
		} catch (Exception e) {
			System.out.println("회원 한명 삭제 처리 중 SQL문 오류 : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
		
	}// memDelete()


	//비밀번호 수정
	public int updatePw(String m_id, String newPw) {
		
		int updateResult = 0;
		
		try {
			con = dataSource.getConnection();
			String sql = "update tbl_member "
		               + "set m_pass=? "
		               + "where m_id=?";
	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, newPw);
	        pstmt.setString(2, m_id);
	        
	        updateResult = pstmt.executeUpdate();
	        
	    } catch (Exception e) {
	        System.out.println("MemberDao / updatePw 메소드 오류 : " + e);
	    } finally {
	        ResourceClose();
	    }

	
		return updateResult;
	}//updatePw()
	
	// 카카오 아이디 있을 시, DB 등록 후 자동 로그인
	public KakaoUserVo kakaoLogin(String m_id, String m_name, String m_email) {
		
		String sql = null;
		KakaoUserVo kakaoUserVo = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "INSERT INTO tbl_member VALUES(?, null, ?, ?, null, sysdate, null, null, sysdate, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			pstmt.setString(2, m_name);
			pstmt.setString(3, m_email);
			pstmt.setString(4, "1");
			
			System.out.println("다오  = " + m_id);
			
			
			pstmt.executeUpdate();
			
			sql = "select * from tbl_member where m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				kakaoUserVo = new KakaoUserVo();
				kakaoUserVo.setId(rs.getLong("m_id"));
				kakaoUserVo.setNickname(rs.getString("m_name"));
				kakaoUserVo.setEmail(rs.getString("m_email"));
			}
			
		} catch (Exception e) {
			System.out.println("MemberDao / kakaoLogin : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return kakaoUserVo;
	}
	
	
	// 카카오 로그인 추가 정보 처리
	public void KakaoJoin(MemberVo memberVo) {
		String sql = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "update tbl_member set m_pass=?, m_hp=?, m_birth=?, m_gender=?, m_address1=?, m_admin=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getM_pass());
			pstmt.setString(2, memberVo.getM_hp());
			pstmt.setDate(3, memberVo.getM_birth());
			pstmt.setString(4, memberVo.getM_gender());
			pstmt.setString(5, memberVo.getM_address1());
			pstmt.setString(6, memberVo.getM_admin());
			
			pstmt.executeQuery();
			
		} catch (Exception e) {
			System.out.println("MemberDao / KakaoJoin : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
	}// KakaoJoin()
	
	//네이버 로그인 후 최종가입
	public void NaverJoin(MemberVo memberVo) {
		String sql = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "INSERT INTO tbl_member VALUES (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVo.getM_id());
			pstmt.setString(2, memberVo.getM_pass());
			pstmt.setString(3, memberVo.getM_name());
			pstmt.setString(4, memberVo.getM_email());
			pstmt.setString(5, memberVo.getM_hp());
			pstmt.setDate(6, memberVo.getM_birth());
			pstmt.setString(7, memberVo.getM_gender());
			pstmt.setString(8, memberVo.getM_address1());
			pstmt.setString(9, memberVo.getM_admin());
			
			
			pstmt.executeQuery();
			
		} catch (Exception e) {
			System.out.println("MemberDao / NaverJoin : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
	}// NaverJoin()

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
