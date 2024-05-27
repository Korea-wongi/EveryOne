package service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import dao.MemberDao;
import dao.MypageDao;
import vo.BuyVo;
import vo.CartVo;
import vo.ImgVo;
import vo.MemberVo;
import vo.OrderVo;
import vo.PayVo;
import vo.ProductVo;

public class MypageService {
	
	private MemberDao memberDao;
	private MypageDao mypageDao;
	private OrderVo orderVo;
	private PayVo payVo;
	private BuyVo buyVo;
	String path = "C:\\work_jsp\\Project44\\WebContent\\productimage";
	
	public MypageService() {
		memberDao = new MemberDao();
		mypageDao = new MypageDao();
		orderVo = new OrderVo();
		payVo = new PayVo();
		buyVo = new BuyVo();
	}
	
	// 상품 등록 처리
	public int serviceProRegister(HttpServletRequest request, HttpServletResponse response, String m_id) throws Exception {
		
		Map<String, String> articleMap = upload(request, response);
		String sfile = articleMap.get("fileName"); //글을 작성할 때 업로드하기 위해 첨부한 파일명
		
		int articleNo  = mypageDao.insertProductRegister(articleMap, m_id);
		

		
		if(sfile != null && sfile.length() != 0) {
			
			File srcFile = new File(path+"\\temp\\" + sfile);
			File destDir = new File(path +"\\" + articleNo);
			
			destDir.mkdirs();
			
			FileUtils.moveFileToDirectory(srcFile, destDir, true);
			
		}
		
		return articleNo;
		
	}// serviceProRegister()
	
	
	// 파일 업로드 메소드
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> articleMap = new HashMap<String, String>();
		
		String encoding = "UTF-8";
		
		File currentDirPath = new File(path);
		
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		factory.setSizeThreshold(1024 * 1024 * 1);
		
		factory.setRepository(currentDirPath);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			List items = upload.parseRequest(request);
			
			for(int i=0; i<items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				
				if(fileItem.isFormField()) {
				 
				 articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				 
				}else {
					
				 
				 if(fileItem.getSize() > 0) {
					 
					 int idx = fileItem.getName().lastIndexOf("\\");
					 
					 if(idx == -1) {
						 
						 idx = fileItem.getName().lastIndexOf("/");
					 }
					 
					 String fileName = fileItem.getName().substring(idx + 1);
					 
					 File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
					 
					 articleMap.put(fileItem.getFieldName(), fileName);
					 
					 fileItem.write(uploadFile);
					 
				 }
				 
				 
				}// if else중 esle
			
			}// for
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return articleMap;
		
	}// upload()

	
	// 회원 장바구니 페이지 요청
	// 장바구니 추가하기 > sql insert 구문 참조..
	public ArrayList<CartVo> serviceCartList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id = (String)session.getAttribute("m_id");
		
		
		return mypageDao.cartListAll(m_id);
	}
	
	// 회원 장바구니 삭제
	public int serviceCartDelete(HttpServletRequest request) {
		String cart_idx = request.getParameter("cart_idx");
		return mypageDao.cartDelete(cart_idx);
	}
	
	// 주문 취소 페이지 요청
	public ArrayList serviceOrderList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id = (String)session.getAttribute("m_id");
		
		return mypageDao.orderListAll(m_id);
	}
	
	// 주문 취소 처리 요청
	public int serviceOrderDelete(HttpServletRequest request) {
		String o_idx = request.getParameter("o_idx");
		return mypageDao.orderDelete(o_idx);
	}
	
	// 회원 mypage 페이지 요청, 장바구니 리스트
	public ArrayList serviceMypage1List(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String m_id = (String)session.getAttribute("m_id");
		
		
		return mypageDao.Mypage1List(m_id);
	}

	// 등록된 모든 상품 조회 기능
	public ArrayList serviceProductListAll(String m_id) {

		return mypageDao.ProductListAll(m_id);
	}

	 
	// 선택한 상품 정보 수정하는 proupdate 페이지 요청
	public ProductVo serviceProductRead(String p_idx) {

		return mypageDao.productRead(p_idx);
	}// serviceProductRead()

	// 상품 정보 수정 후 mypage요청
		public int serviceProUpdate(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			int articleNo = Integer.parseInt(request.getParameter("p_idx"));
			
			ProductVo productVo = mypageDao.getProduct(articleNo); // 기존 상품 정보 (이미지 명 까지) 가져오기
			
			
			// 기존 파일 삭제
			String existingFileName = productVo.getImgVo().getI_name();
			String existingFilePath = path + "\\" + articleNo + "\\" + existingFileName;
			File existingFile = new File(existingFilePath);		
			
			if (existingFile.exists()) {
			    if (existingFile.delete()) {
			        System.out.println("기존 이미지 파일 삭제 성공: " + existingFilePath);
			    } else {
			        System.out.println("기존 이미지 파일 삭제 실패: " + existingFilePath);
			    }
			} else {
			    System.out.println("기존 이미지 파일이 존재하지 않습니다: " + existingFilePath);
			}
			
			// 새로운 파일 업로드
			
			Map<String, String> articleMap = upload(request, response);
			
			String sfile = articleMap.get("fileName"); // 새로운 파일명
			
			File srcFile = new File(path + "\\temp\\" + sfile);
		    File destDir = new File(path + "\\" + articleNo);
		    
		    try {
		        FileUtils.moveFileToDirectory(srcFile, destDir, true);
		    } catch (IOException e) {
		    }

			int updateNo = mypageDao.updateProduct(articleMap, articleNo);
	        	
			return updateNo;
		}// serviceProUpdate()

		// 한 개의 상품 삭제 후 mypage2요청
		public String serviceProDelete(HttpServletRequest request) throws IOException {

			String delete_idx = request.getParameter("p_idx");
			
			File deleteDir = new File(path + "\\" + delete_idx);
			
			String result = mypageDao.deleteProduct(delete_idx);
			
			
			if(result.equals("삭제성공")) {	
				if(deleteDir.exists()) {
					FileUtils.deleteDirectory(deleteDir);
				}
					System.out.println("글번호 폴더가 존재 하지 않습니다 : " + deleteDir);
			}
			
			return result;
			
		}// serviceProDelete()

		// 결제 처리 요청
		public void servicePayment(HttpServletRequest request) {
			
			orderVo.setO_idx((request.getParameter("merchant_uid")));
			orderVo.setO_mid(request.getParameter("m_id"));
			orderVo.setO_name(request.getParameter("buyer_name"));
			orderVo.setO_address1(request.getParameter("buyer_postcode") 
								  +" "
								  +request.getParameter("buyer_addr"));
			orderVo.setO_hp(request.getParameter("buyer_tel"));
			orderVo.setO_request(request.getParameter("input_request"));
			
			payVo.setP_idx(request.getParameter("merchant_uid"));
			payVo.setP_mid(request.getParameter("m_id"));
			payVo.setP_price(Integer.parseInt(request.getParameter("amount")));
			
			buyVo.setB_oidx(request.getParameter("merchant_uid"));
			buyVo.setB_name(request.getParameter("p_name"));
			buyVo.setB_price(Integer.parseInt(request.getParameter("amount")));
			buyVo.setB_pmid(request.getParameter("c_pmid"));
			
			 mypageDao.payment(orderVo, payVo, buyVo);
		}
		
		// 장바구니 보유 여부 확인
		public int serviceCheckCart(HttpServletRequest request) {
			
			return mypageDao.checkCart(request.getParameter("m_id"));
		}
		
		// 주문한 수량 리턴
		public int serviceCheckOrder(HttpServletRequest request) {
			return mypageDao.checkOrder(request.getParameter("m_id"));
		}
		
		// 총 주문금액 리턴
		public int serviceTotalPrice(HttpServletRequest request) {
			return mypageDao.TotalPrice(request.getParameter("m_id"));
		}
		
		// 월별 결제 금액
		public int serviceMonth(String m_id, String month) {
			return mypageDao.month(m_id, month);
		}

		
		//장바구니에 동일 상품 추가X
		public Boolean serviceCartCount(HttpServletRequest request) {
			
			String c_mid = request.getParameter("m_id");
			int c_pidx = Integer.parseInt(request.getParameter("p_idx")) ;
					
			return mypageDao.cartCount(c_mid,c_pidx);		
		}

		//상세페이지 구매상품 카트추가
		public int serviceCartInsert(HttpServletRequest request)  {
			HttpSession session = request.getSession();
			CartVo cartVo = new CartVo();
			
		 	cartVo.setC_pidx(Integer.parseInt(request.getParameter("p_idx")));
			cartVo.setC_mid(request.getParameter("m_id"));
			cartVo.setC_name(request.getParameter("p_name"));
			cartVo.setC_amount(Integer.parseInt(request.getParameter("c_amount")));
			cartVo.setC_price(Integer.parseInt(request.getParameter("p_price")));
			
			int result = mypageDao.cartInsert(cartVo);
			
			return result;
			
		}//serviceCartInsert()

		// 찜 횟수 리턴
		public int serviceCheckWish(HttpServletRequest request) {
			
			return mypageDao.checkWish(request.getParameter("m_id"));
		}

		// 해당 관리자 등록 상품 수량
		public int serviceProCount(String m_id) {
			return mypageDao.getProCount(m_id);
		}

		// 해당 관리자 월별 매출 금액
		public int serviceTotalSales(String m_id, String month) {
			return mypageDao.getTotalSales(m_id, month);
		}// serviceTotalSales()
	
}


