package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.websocket.Session;

import vo.BuyVo;
import vo.CartVo;
import vo.ImgVo;
import vo.MemberVo;
import vo.OrderVo;
import vo.PayVo;
import vo.ProductVo;

public class MypageDao {

	private Connection con;	
	private PreparedStatement pstmt;	
	private ResultSet rs;
	private DataSource dataSource;
	String path = "C:\\work_jsp\\Project44\\WebContent\\productimage";
	
	
	public MypageDao() {
	
		try {
			Context ctx = new InitialContext();
			
			Context envContext = (Context) ctx.lookup("java:/comp/env");
			
			dataSource = (DataSource) envContext.lookup("jdbc/oracle1");
			
		} catch (Exception e) {
			System.out.println("DataSouce커넥션풀 객체 얻기 실패  : " + e);
		}
	
	}// MemberDao()
	
	
	
	
	//이미지폴더명(번호) 반환
	public int getNewArticleNo() {
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select max(i_pidx) from tbl_img";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return (rs.getInt(1) + 1);
			}
			
		}catch(Exception e) {
			System.out.println("getNewArticleNo메소드 내부에서 오류 :");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return 0;
	}// getNewArticleNo()

	// 상품 등록 처리
	// 주의. product 시퀀스 사용 x
	public int insertProductRegister(Map<String, String> articleMap, String m_id) {
		
		int articleNo = getNewArticleNo();
		
		String p_name =  articleMap.get("p_name");
		String p_category = articleMap.get("p_category");
		String p_color = articleMap.get("p_color");
		String p_size = articleMap.get("p_size");
		int p_price = Integer.parseInt(articleMap.get("p_price"));
		String p_content = articleMap.get("p_content");
		int p_codytemp = Integer.parseInt(articleMap.get("p_codytemp"));

		
		String sql = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "insert into tbl_product(p_idx,"
										+ "p_mid,"
									    + "p_category,"
									    + "p_name,"
									    + "p_price,"
									    + "p_color,"
									    + "p_size,"
									    + "p_content,"
									    + "p_codytemp) "
									    + "values(?,"
									    + "?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			
			pstmt.setInt(1, articleNo);
			pstmt.setString(2, m_id);
			pstmt.setString(3, p_category);
			pstmt.setString(4, p_name);
			pstmt.setInt(5, p_price);
			pstmt.setString(6, p_color);
			pstmt.setString(7, p_size);
			pstmt.setString(8, p_content);
			pstmt.setInt(9, p_codytemp);
			
			pstmt.executeUpdate();
			
			
			String fileName = articleMap.get("fileName");
			String i_path = path+ "\\" +articleNo;
			
			sql = "insert into tbl_img (i_pidx, i_name, i_path) "
							  + "values(?,?,?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, articleNo);
			pstmt.setString(2, fileName);
			pstmt.setString(3, i_path);
				
		    pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("상품 등록 테이블 & 상품 이미지 테이블 처리 중 오류 :" + e);
		}finally {
			ResourceClose();
		}
		

		return articleNo;
		
	}// insertProductRegister()
	
	
	// 회원 장바구니 페이지 요청
	// 장바구니 추가하기 > sql insert 구문 참조..
	public ArrayList cartListAll(String m_id) {
		CartVo cartVo = null;
		String sql = null;
		ArrayList list = new ArrayList();
 		try {
			con = dataSource.getConnection();
			
			// 회원 아이디로 장바구니 리스트 조회
			sql = "select * "
					+ " from tbl_product p inner join tbl_cart c "
					+ " on p.p_idx = c.c_pidx "
					+ " where c.c_mid=? "
					+ " order by c_idx ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cartVo = new CartVo();
				
				cartVo.setC_idx(rs.getInt("c_idx"));
				cartVo.setC_pidx(rs.getInt("c_pidx"));
				cartVo.setC_mid(rs.getString("c_mid"));
				cartVo.setC_name(rs.getString("c_name"));
				cartVo.setC_amount(rs.getInt("c_amount"));
				cartVo.setC_price(rs.getInt("c_price"));
				cartVo.setC_pmid(rs.getString("p_mid"));
				
				list.add(cartVo);
			}
			
		}catch(Exception e) {
			System.out.println("MypageDao / cartListAll 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
 		
 		
		return list;
	}// cartListAll()
	
	// 회원 장바구니 삭제
	public int cartDelete(String cart_idx) {
		String sql = null;
		int resultInt = 0;
 		try {
			con = dataSource.getConnection();
			
			sql = "delete from tbl_cart where c_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cart_idx);
			
			resultInt = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("MypageDao / cartDelete 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
 		return resultInt;
	}// cartDelete()
	
	
	// 주문 취소 페이지 요청
	public ArrayList orderListAll(String m_id) {
		String sql = null;
		OrderVo orderVo = null;
		ArrayList list = new ArrayList();
		
 		try {
			con = dataSource.getConnection();
			
			sql = "select o.o_idx, b.b_price, o.o_mid, o.o_name, o.o_address1, o.o_hp, o.o_request" 
					+ " from tbl_order o INNER join tbl_buy b "
					+ " on o.o_idx = b.b_oidx "
					+ " where o.o_mid =? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				orderVo = new OrderVo();
				
				orderVo.setO_idx(rs.getString("o_idx"));
				orderVo.setO_mid(rs.getString("O_MID"));
				orderVo.setO_name(rs.getString("O_name"));
				orderVo.setO_address1(rs.getString("O_address1"));
				orderVo.setO_hp(rs.getString("O_hp"));
				orderVo.setO_request(rs.getString("O_request"));
				orderVo.setO_price(rs.getInt("b_price"));
				
				list.add(orderVo);
			}
			
		}catch(Exception e) {
			System.out.println("MypageDao / orderListAll 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return list;
	}//orderDelete()
	
	// 주문 취소 처리 요청
	public int orderDelete(String o_idx) {
		String sql = null;
		int resultInt = 0;
 		try {
			con = dataSource.getConnection();
			
			sql = "delete from tbl_order where o_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, o_idx);
			resultInt = pstmt.executeUpdate();
			
			sql = "delete from tbl_pay where p_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, o_idx);
			resultInt += pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("MypageDao / orderDelete 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
 		return resultInt;
	}//orderDelete()
	
	
	// 회원 mypage 페이지 요청, 장바구니 리스트
	public ArrayList Mypage1List(String m_id) {
		CartVo cartVo = null;
		String sql = null;
		ArrayList list = new ArrayList();
		
 		try {
			con = dataSource.getConnection();
			
			// 회원 아이디로 장바구니 리스트 조회
			
			sql = " SELECT c.c_idx, c.c_pidx, c.c_name, p.p_content, c.c_price, i.i_name "
				+ " FROM tbl_product p "
				+ "	INNER JOIN tbl_cart c ON p.p_idx = c.c_pidx "
				+ "	LEFT JOIN tbl_img i ON p.p_idx = i.i_pidx "
				+ "	WHERE c.c_mid=?" ;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cartVo = new CartVo();
				
				cartVo.setC_idx(rs.getInt("c_idx"));
				cartVo.setC_pidx(rs.getInt("c_pidx"));
				cartVo.setC_mid(m_id);
				cartVo.setC_name(rs.getString("c_name"));
				cartVo.setC_price(rs.getInt("c_price"));
				cartVo.setC_pcontent(rs.getString("p_content"));
				cartVo.setC_iname(rs.getString("i_name"));
				
				list.add(cartVo);
			}
			
			
			
		}catch(Exception e) {
			System.out.println("MypageDao / Mypage1List 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return list;
	}//Mypage1List()
	
	
	// 등록된 모든 상품 조회 기능
	public ArrayList ProductListAll(String m_id) {

		ArrayList list = new ArrayList();
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select p_idx, p_category, p_name, p_price, p_color, p_size, p_content, p_codytemp, i_name "
					     + " from tbl_product p inner join tbl_img i "
					     + " on p_idx = i_pidx "
					     + " where p.p_mid =? ";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductVo productVo = new ProductVo();
				
				productVo.setP_idx(rs.getInt("p_idx"));
				productVo.setP_mid(m_id);
				productVo.setP_category(rs.getString("p_category"));
				productVo.setP_name(rs.getString("p_name"));
				productVo.setP_price(rs.getInt("p_price"));
				productVo.setP_color(rs.getString("p_color"));
				productVo.setP_size(rs.getString("p_size"));
				productVo.setP_content(rs.getString("p_content"));
				productVo.setP_codytemp(rs.getInt("p_codytemp"));
				
				ImgVo imgVo = new ImgVo();
				imgVo.setI_name(rs.getString("i_name"));
				
				productVo.setImgVo(imgVo);
				
				
				list.add(productVo);
			}
			
		} catch (Exception e) {
			System.out.println("mypage 하단에 전체 상품목록 보여주기 sql처리중 오류 : " + e);
		}
		
		return list;
	}// ProductListAll()
	
	
	// 선택한 상품 정보 수정하는 proupdate 페이지 요청
	public ProductVo productRead(String p_idx) {

		ProductVo productVo = null;
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select * from tbl_product where p_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(p_idx));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productVo = new ProductVo();
				
				productVo.setP_idx(rs.getInt("p_idx"));
				productVo.setP_category(rs.getString("p_category"));
				productVo.setP_name(rs.getString("p_name"));
				productVo.setP_price(rs.getInt("p_price"));
				productVo.setP_color(rs.getString("p_color"));
				productVo.setP_size(rs.getString("p_size"));
				productVo.setP_content(rs.getString("p_content"));
				productVo.setP_codytemp(rs.getInt("p_codytemp"));
				productVo.setP_mid(rs.getString("p_mid"));
			}
			
		} catch (Exception e) {
			System.out.println("상품등록 수정위해 선택된 상품정보 조회하는 sql 오류 :" + e);
		}finally {
			ResourceClose();
		}
		
		
		return productVo;
		}// productRead()
	
	// 상품 정보 수정 후 mypage요청 (선택한 상품 정보 조회)
	public ProductVo getProduct(int articleNo) {

		ProductVo productVo = null;
		
		try {
			con = dataSource.getConnection();
			
			String sql = "select p_idx, p_category, p_name, p_price, p_color, p_size, p_content, i_name "
					   + "from tbl_product p NATURAL JOIN tbl_img i "
					   + "where p.p_idx = ? and i.i_pidx = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, articleNo);
			pstmt.setInt(2, articleNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				productVo = new ProductVo(rs.getInt("p_idx"), 
									 	  rs.getString("p_category"),
									 	  rs.getString("p_name"),
									 	  rs.getInt("p_price"),
									 	  rs.getString("p_color"),
									 	  rs.getString("p_size"),
									 	  rs.getString("p_content")
										 ); 
				
				ImgVo imgVo = new ImgVo();
				imgVo.setI_name(rs.getString("i_name"));
				
				productVo.setImgVo(imgVo);
			}
			
		} catch (Exception e) {
			System.out.println("상품 수정 위한 기존 정보 조회하는 sql 오류 : " + e);
		}
		
		return productVo;
	}// getProduct()
	
	
	// 상품 정보 수정 후 mypage요청 (수정된 상품 정보 update)
	public int updateProduct(Map<String, String> articleMap, int articleNo) {

		String p_name =  articleMap.get("p_name");
		String p_category = articleMap.get("p_category");
		String p_color = articleMap.get("p_color");
		String p_size = articleMap.get("p_size");
		int p_price = Integer.parseInt(articleMap.get("p_price"));
		String p_content = articleMap.get("p_content");
		String p_codytemp = articleMap.get("p_codytemp");
		

		String sql = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "update tbl_product set p_category=?, p_name=?, p_price=?, p_color=?, p_size=?, p_content=?, p_codytemp=? WHERE p_idx=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, p_category);
	        pstmt.setString(2, p_name);
	        pstmt.setInt(3, p_price);
	        pstmt.setString(4, p_color);
	        pstmt.setString(5, p_size);
	        pstmt.setString(6, p_content);
	        pstmt.setString(7, p_codytemp);
	        pstmt.setInt(8, articleNo);
			
			pstmt.executeUpdate();
			
			
			String fileName = articleMap.get("fileName");

			String i_path = path +"\\"+articleNo+"\\"+fileName;
			
			sql = "update tbl_img set i_name=?, i_path=? WHERE i_pidx=?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, fileName);
	        pstmt.setString(2, i_path);
	        pstmt.setInt(3, articleNo);
				
		    pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("상품 정보 수정 sql 중 오류 :" + e);
		}finally {
			ResourceClose();
		}
		

		return articleNo;
		
	}// updateProduct()
	
	// 한 개의 상품 삭제 후 mypage2요청
	public String deleteProduct(String delete_idx) {

		String result = null;
		
		try {
			con = dataSource.getConnection();
			
			String sql = null;
			
			sql = "delete from tbl_product where p_idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(delete_idx));
			
			int val1 = pstmt.executeUpdate();
			
			sql = "delete from tbl_img where i_pidx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(delete_idx));
			
			pstmt.executeUpdate();
			System.out.println(val1);
			
			
			if(val1 == 1) {
				result = "삭제성공";
			}else {
				result = "삭제실패";
			}
			
		} catch (Exception e) {
			System.out.println("한 개의 상품 삭제 처리 SQL문 실행 오류 : " + e);
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
		
	}// deleteProduct()
	
	
	// 결제 처리 요청
	public void payment(OrderVo orderVo, PayVo payVo, BuyVo buyVo) {
		String sql = null;
		
		try {
			con = dataSource.getConnection();
			
			sql = "insert into tbl_order values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderVo.getO_idx());
			pstmt.setString(2, orderVo.getO_mid());
			pstmt.setString(3, orderVo.getO_name());
			pstmt.setString(4, orderVo.getO_address1());
			pstmt.setString(5, orderVo.getO_hp());
			pstmt.setString(6, orderVo.getO_request());
			pstmt.executeUpdate();
			
			sql = "insert into tbl_pay values(?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, payVo.getP_idx());
			pstmt.setString(2, payVo.getP_mid());
			pstmt.setInt(3, payVo.getP_price());
			pstmt.executeUpdate();
			
			sql = "insert into tbl_buy values(TBL_BUY_B_IDX.nextval,?,?,?,?,sysdate)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, buyVo.getB_oidx());
			pstmt.setString(2, buyVo.getB_name());
			pstmt.setInt(3, buyVo.getB_price());
			pstmt.setString(4, buyVo.getB_pmid());
			pstmt.executeUpdate();
			
			sql = "delete from tbl_cart where c_mid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, orderVo.getO_mid());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("MypageDao / payment 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
	}// payment()
	
	
	
	// 장바구니 보유 여부 확인
	public int checkCart(String m_id) {
		String sql = null;
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			sql = "select count(*) count from tbl_cart where c_mid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("count");
			}
			
		} catch (Exception e) {
			System.out.println("MypageDao / checkCart 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
	}
	
	// 주문한 수량 리턴
	public int checkOrder(String m_id) {
		String sql = null;
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			sql = "select count(*) count from tbl_order where o_mid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("count");
			}
			
		} catch (Exception e) {
			System.out.println("MypageDao / checkOrder 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
	}// checkOrder()
	
	
	// 총 주문금액 리턴
	public int TotalPrice(String m_id) {
		String sql = null;
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			sql = "select sum(p_price) as total from tbl_pay where p_mid=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("total");
			}
			
		} catch (Exception e) {
			System.out.println("MypageDao / checkOrder 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
	}// TotalPrice()
	
	
	// 월별 결제 금액
	public int month(String m_id, String month) {
		String sql = null;
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			sql = "SELECT sum(p_price) FROM tbl_pay WHERE TO_CHAR(p_date, 'MM') = ? and p_mid=? ";;
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, month);
			pstmt.setString(2, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("sum(p_price)");
			}
			
		} catch (Exception e) {
			System.out.println("MypageDao / month 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		return result;
	}// month()
	
	
	//장바구니에 동일 상품 추가X
	public Boolean cartCount(String c_mid, int c_pidx) {
		String sql = null;
		boolean result = false;
		
 		try {
			con = dataSource.getConnection();
			
			// 회원 아이디로 장바구니 리스트 조회
			sql = "SELECT COUNT(*) AS count FROM tbl_cart WHERE c_mid=? and c_pidx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, c_mid);
			pstmt.setInt(2, c_pidx);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
	            int count = rs.getInt("count");
	            if (count > 0) {
	            	result = true;
	            }
	        }				
		}catch(Exception e) {
			System.out.println("MypageDao / cartCount 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return result;
	}//cartCount()
	
	
	//상세페이지 구매상품 카트추가
	public int cartInsert(CartVo cartVo) {
		String sql = null;
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			sql = "insert into tbl_cart values "
				+ "(TBL_CART_C_IDX.NEXTVAL, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, cartVo.getC_pidx());
			pstmt.setString(2, cartVo.getC_mid());
			pstmt.setString(3, cartVo.getC_name());
			pstmt.setInt(4, cartVo.getC_amount());
			pstmt.setInt(5, cartVo.getC_price());
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("상세페이지 구매상품 카트추가 SQL문 실행 오류 : " + e);
			e.printStackTrace();
			e.toString();
		}finally {
			ResourceClose();
		}
		return result;
	}//cartInsert()
	
	// 총 찜 횟수 리턴
	public int checkWish(String m_id) {
		String sql = null;
		int result = 0;
		
		try {
			con = dataSource.getConnection();
			
			sql = "select count(*) from tbl_wish where w_mid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt("count(*)");
			}
			
		} catch (Exception e) {
			System.out.println("MypageDao / checkWish 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return result;
	}// checkWish()
	
	
	// 해당 관리자 등록 상품 수량
	public int getProCount(String m_id) {
		int regiProCount = 0;
		String sql = null;
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "select count(*) as regipro_count "
				+ "from tbl_product "
				+ "where p_mid = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				regiProCount = rs.getInt("regipro_count");
			}
			
		} catch (Exception e) {
			System.out.println("MypageDao / getProCount 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		return regiProCount;
	}// getProCount()
	
	
	// 해당 관리자 월별 매출 금액
	public int getTotalSales(String m_id, String month) {
		
		int totalSales = 0;
		String sql = null;
		
		try {
			
			con = dataSource.getConnection();
			
			sql = "select sum(b_price) as total_sales "
				+ "from tbl_buy "
				+ "where b_pmid like '%' || ? || '%' "
				+ "and  TO_CHAR(b_date, 'MM') = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, m_id);
			pstmt.setString(2, month);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalSales = rs.getInt("total_sales");
			}
			
			
			
		} catch (Exception e) {
			System.out.println("MypageDao / getTotalSales 메서드 오류 = ");
			e.printStackTrace();
		}finally {
			ResourceClose();
		}
		
		
		return totalSales;
	}// getTotalSales()

	
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
