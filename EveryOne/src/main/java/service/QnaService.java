package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;


import dao.QnaDao;
import vo.BuyVo;
import vo.QnaVo;

public class QnaService {
	
	// 인스턴스 변수
	QnaDao qnaDao;
	String path = "C:\\TeamProject1\\Project44\\src\\main\\webapp\\qnaUpload";

	
	// 기본 생성자
	public QnaService() {
		qnaDao=new QnaDao();
	}
	
	// TBL_QNA 테이블에 등록된 전체 글 조회
	public ArrayList serviceQnaListAll() { 
		return qnaDao.qnaListAll();
	}
	
	// 배송문의,상품문의,취소문의,결제문의 페이지 요청, q_title 문의유형 뿌려주기
	public ArrayList serviceShipListAll(String q_title) {
		return qnaDao.qnaShipListAll(q_title);
	}
	
	// TBL_QNA 테이블에 등록된 전체 글 갯수 조회
	public int getTotalRecord() { 
		return qnaDao.getTotalRecord();
	}
	
	// TBL_QNA 테이블에 등록된 배송문의,상품문의,취소문의,결제문의 글 갯수 조회
	public int getShipRecord(String q_title) {
		return qnaDao.getShipRecord(q_title);
	}
	

//	// 글 제목 클릭 시 해당 TBL_QNA 테이블에서 글 정보 조회
//	public QnaVo serviceQnaRead(HttpServletRequest request) {
//		
//		String q_idx=request.getParameter("q_idx");
//		return qnaDao.qnaRead(q_idx);
//	}

	// 글 제목 클릭 시 작성자 비번 일치 확인
	public QnaVo servicePassCheck(HttpServletRequest request) {
		
		String q_idx=request.getParameter("q_idx");
		String qnapass=request.getParameter("qnapass");
		String m_admin=request.getParameter("m_admin");

		return qnaDao.passCheck(q_idx,qnapass,m_admin);
	}

	// 고객 관점 : 글 수정 후 업데이트 요청 기능
	public int serviceUpdateQna(HttpServletRequest request) {
		String update_idx=request.getParameter("q_idx"); //글 번호
		String update_title=request.getParameter("title"); //글 제목
		String update_content=request.getParameter("content"); //글 내용
		
		return qnaDao.updateQna(update_title, update_content, update_idx);
	}

	public int serviceUpdateQnaFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> articleMap = upload(request,response);
		
		String q_idx = articleMap.get("upq_inx");//글 번호
		String upfile = articleMap.get("upfile"); //글을 작성할떄 업로드하기 위해 첨부한 파일
		
		int updateResult= qnaDao.updateQnaFile(q_idx,upfile);	
		
		if(upfile != null && upfile.length() != 0) {
	         File upsrcFile = new File(path +"\\temp\\" + upfile); //새로 업데이트 될 파일경로
	         File destDir = new File(path +"\\"+ q_idx); // 기존 폴더
	         FileUtils.deleteDirectory(destDir); // 기존 파일 경로 삭제
	         File destDir2 = new File(path +"\\"+ q_idx); // 기존 폴더 번호로 재생성
	         
	         destDir2.mkdir();
	         FileUtils.moveFileToDirectory(upsrcFile, destDir2, true);
	      }
		return updateResult;
	}
	
	// 문의사항 글 삭제 요청
	public String serviceDeleteQna(HttpServletRequest request) throws Exception {
		
		String delete_idx=request.getParameter("q_idx");
		
		File deleteDir=new File(path+"\\"+delete_idx);
		String delResult=qnaDao.deleteQna(delete_idx);
		
		if(delResult.equals("삭제성공")) {
			if(deleteDir.exists()) {
				FileUtils.deleteDirectory(deleteDir); //글 번호 폴더 삭제
			}
		}
		return delResult;
	}
	
	// 문의사항 새 글 등록 화면 보여주기
	public ArrayList serviceWriteQnaView(String loginId) {
		
		return qnaDao.wirteNewQna(loginId);
	}

	// 문의사항 새 글 작성 완료 후 등록요청
	public int serviceInsertQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Map<String, String> articleMap = upload(request,response);

		String title = articleMap.get("title");//작성한글제목
		String id = articleMap.get("writer_id");//글 작성자 아이디
		String buyItems = articleMap.get("buyItems");//문의상품(구매상품)
		String sfile = articleMap.get("fileName"); //글을 작성할떄 업로드하기 위해 첨부한 파일명
		String content = articleMap.get("content");//작성한글내용
		
		QnaVo insertVo=new QnaVo();
				insertVo.setQ_title(title);
				insertVo.setQ_mid(id);
				insertVo.setQ_bidx(Integer.parseInt(buyItems));
				insertVo.setQ_sfile(sfile);
				insertVo.setQ_content(content);
		
		int articelNO = qnaDao.insertQna(insertVo);				

		if(sfile != null && sfile.length() != 0) {
			File srcFile = new File(path +"\\temp\\" + sfile); 
			File destDir = new File(path +"\\"+ articelNO); 
			destDir.mkdirs();
			FileUtils.moveFileToDirectory(srcFile, destDir, true);
		}
		return articelNO;
	}

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> aritcelMap = new HashMap<String, String>();
		request.setCharacterEncoding("UTF-8");
		String encoding = "utf-8";
		
		
		File currentDirPath = new File(path);  // 설정된 파일 경로 파일에 주입
		DiskFileItemFactory factory = new DiskFileItemFactory(); 
		factory.setSizeThreshold(1024 * 1024 * 1);
		factory.setRepository(currentDirPath); // 파일팩토리에 파일을 주입
		
		ServletFileUpload upload = new ServletFileUpload(factory); 
		try {
			List items = upload.parseRequest(request);
			for(int i=0;   i<items.size();   i++) {
				FileItem  fileItem = (FileItem)items.get(i);
					if(fileItem.isFormField()) {
						aritcelMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
					}else {
						System.out.println("<input type='file'>의 name속성명 : " +  fileItem.getFieldName() );
							if(fileItem.getSize() > 0) {
								int idx = fileItem.getName().lastIndexOf("\\");
								System.out.println("file \\ 유무 확인 : "+idx);
								if(idx == -1) {
									idx = fileItem.getName().lastIndexOf("/");
								}
								String fileName = fileItem.getName().substring(idx + 1);
								File uploadFile = new File(currentDirPath + "\\temp\\"  + fileName);
								aritcelMap.put(fileItem.getFieldName(), fileName);
								fileItem.write(uploadFile);
							}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return aritcelMap;
	}

	public int serviceReplyInsertQna(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		Map<String, String>  articleMap = upload(request, response); //첨부파일 업로드 + 저장된 정보 반환 
		
		String re_title = articleMap.get("title"); // 답변 제목(고정)
		String re_mid = articleMap.get("writer_id"); // 답변자 아이디
		String re_b_inx=articleMap.get("b_inx"); //부모글 문의상품
		String re_sfile = articleMap.get("fileName"); // 답변글 첨부파일 
		String re_content = articleMap.get("content");// 답변글 
		String super_q_idx = articleMap.get("super_q_idx");//부모글 번호 
		
		// tbl_qna에 답변글 저장 + 저장된 글 번호 반환 
		int articleNO = qnaDao.replyInsertQna(re_title,re_mid,re_b_inx,re_sfile,re_content,super_q_idx);
		
		// 첨부파일O -> 생성된 글 번호로 폴더 만들어서 파일 저장 
		if(re_sfile != null && re_sfile.length() != 0) {
			File srcFile = new File(path +"/temp/" + re_sfile); 
			File destDir = new File(path +"/"+ articleNO); 
			destDir.mkdirs();
			FileUtils.moveFileToDirectory(srcFile, destDir, true);
		}
		return articleNO;
		
	}

	//첨부파일 다운로드
	public void serviceQnaDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String idx = request.getParameter("path"); //다운로드할 파일이 저장된 글번호폴더명 
		String name = request.getParameter("fileName"); //실제 다운로드할 파일명 
		
		
		String filePath = path+ "\\" + idx; //다운로드할 파일이 저장되어 있는 글번호 폴더 경로
		File qnaDownFile = new File(filePath + "\\" + name); //다운로드할 파일
		
		OutputStream qnaOutStream = response.getOutputStream(); // 웹브라우저로 보낼 출력스트림
		FileInputStream qnaInputStream = new FileInputStream(qnaDownFile); // 파일을 읽어들일 입력스트림
		
		response.setHeader("Cache-Control", "no-cache"); // 브라우저에서 응답받은 결과 캐시 스토리지에 저장 x
		response.addHeader("Cache-Control", "no-store");
		
		// 다운로드 시 '다른이름 저장' 대화상자 생성 + 인코딩 방식 설정  
		response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(name,"utf-8")+"\";");
		
		//입출력 작업
		byte[] buffer = new byte[1024*8];
			while(true) {
					int cnt = qnaInputStream.read(buffer); // 8바이트 단위로 읽고 바이트 수 반환, 읽어들일 바이트 없으면 -1 반환 
					if(cnt == -1) {//파일에서 더이상 읽어들일 바이트가 없으면
					break;}
			qnaOutStream.write(buffer, 0, cnt);		
				}
			//자원해제 
			qnaInputStream.close();  qnaOutStream.close();
		}

	// 검색어 입력 후 결과 보여주기 -> 검색결과 값(배열)
	public ArrayList serviceQnaList(HttpServletRequest request) {
		String keyField=request.getParameter("keyField");
		String keyWord=request.getParameter("keyWord");
		return qnaDao.qnaList(keyField,keyWord);
	}

	// 검색어 입력 후 결과 보여주기 -> 검색된 행 갯수
	public int serviceQnaTotalRecord(HttpServletRequest request) {
		String keyField=request.getParameter("keyField");
		String keyWord=request.getParameter("keyWord");
		return qnaDao.getTotalRecord(keyField,keyWord);
	}

}
		
