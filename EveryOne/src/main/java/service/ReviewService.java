package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.apache.tomcat.util.http.fileupload.MultipartStream;

import dao.ReviewDao;
import dao.MemberDao;
import vo.ReviewVo;
import vo.MemberVo;

public class ReviewService {
	
	
	private ReviewDao ReviewDao;
	private MemberDao MemberDao;
	String path = "C:\\TeamProject1\\Project44\\src\\main\\webapp\\reviewimage";

	
	//생성자 
	public ReviewService() {
		ReviewDao = new ReviewDao();
		MemberDao = new MemberDao();
	}

	//모든 리뷰 리스트 조회
	public ArrayList<ReviewVo> serviceBoardListAll(String r_pidx) {
		
		return ReviewDao.boardListAll(r_pidx);
	}
	
	//구매여부조회
	public boolean buyProduct(String p_name, String m_id) {
		
		ArrayList<String> midList = ReviewDao.mbp(p_name);
        
        // midList에서 아이디 값을 찾기
        boolean foundId = false; // 'Sally' 값을 찾았는지 여부를 나타내는 변수
        
        for(String mid : midList) {
            if(mid == null) {
            	break;        
            }else if(mid.equals(m_id)) {
            	foundId = true;
            	break;
            }
        }
        
        return foundId;
	}

	//글작성
	public int serviceInsertBoard(HttpServletRequest request, 
									  HttpServletResponse response) throws Exception {
			
			//파일업로드 후 업로드한 파일의 <input> name속성에 대한 value에 설정된 값
			// 쓴 새글 정보들을 담고 있는 해쉬맵을 반환 받는다.
			Map<String, String> articleMap = upload(request,response);
			
			//작성한 글정보(업로드할 파일정보 포함)를 HashMap에서 꺼내오기 
			String r_title = articleMap.get("r_title");
			String r_content = articleMap.get("r_content");
										//Reviewwrite.jsp의 아이디가 r_mid로 저장되어있기 때문에 r_mid여야합니다
			String r_mid = articleMap.get("r_mid");//멤버아이디
			int r_idx = Integer.parseInt(articleMap.get("p_idx"));//상품번호
			
			String r_sfile = articleMap.get("r_sfile");

			
			
			ReviewVo vo = new ReviewVo();
						vo.setR_title(r_title);
						vo.setR_content(r_content);
						vo.setR_mid(r_mid);
						vo.setR_pidx(r_idx);
						vo.setR_sfile(r_sfile);
						
			
			int articelNO = ReviewDao.insertBoard(vo);
						
			if(r_sfile != null && r_sfile.length() != 0) {//글을 추가할떄 업로드한 파일이 있으면
				//temp폴더에 이미 업로드된 파일에 접근해서 글번호로 폴더로 이동시키기 위해 경로 저장 
				File srcFile = new File(path + "\\temp\\" + r_sfile);
				
				//글번호 폴더 생성 하기 위해 경로 File객체에 저장
				File destDir = new File(path + "\\"+ articelNO );
				
				//DB에 추가한 글에 대한 글번호를 가져와서 글번호폴더 생성 
				destDir.mkdirs();
				
				//temp폴더에 이미 업로드된 파일을 글번호번호폴더로 이동시키자
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
					
			} 
			
			return articelNO;
		}
	
	//---------------------파일을 톰캣서버의 하드디스크공간("C:\\file_repo")에 업로드하는 기능의 메소드 
	private Map<String, String>  upload(HttpServletRequest request, 
			  							HttpServletResponse response) throws Exception {
		
		//가변길이 메모리 생성 이유 : 업로드한 파일의 정보를 저장해서 제공하면 테이블에 insert할수 있게 하기위해 
		Map<String, String> aritcelMap = new HashMap<String, String>();
		
		//요청한 파라미터 한글처리
		request.setCharacterEncoding("UTF-8");
		
		String encoding = "utf-8";
		
		//업로드할 파일 경로와 연결된 File객체 메모리 생성
		File currentDirPath = new File(path);
		
		//업로드할 파일 데이터를 임시로 저장할 객체 메모리 생성
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//파일 업로드시 사용할 임시메모리 최대 크기를 1메가 바이트로 설정
		factory.setSizeThreshold(1024 * 1024 * 1);
		//임시 메모리에 파일업로드시 ~~ 지정한 1메가 바이트 크기를 넘을 경우 업로드될 폴더경로를 설정
		factory.setRepository(currentDirPath);
		
		//참고.
		//DiskFileItemFactory클래스는 업로드할 파일의 크기가 지정한 임시메모리의 크기를 넘기전까지는
		//업로드한 파일 데이터를 임시메모리에 저장하고  지정한 크기를 넘길 경우  업로드할 file_repo폴더로 업로드해서 저장시키는 역할을 함.
		
		//파일을 업로드할 임시 메모리 객체의 주소를 생성자쪾으로 전달해 저장한  파일업로드를 실제 처리할 객체 생성
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		try {
			/*
				uploadForm.jsp 파일업로드 요청하는 디자인 페이지에서  첨부한 파일 2개와  입력한 파라미터3개의 정보들
				request객체에서 꺼내와서  각각의 DiskFileItem객체들에 저장한 후 
				각각의 DiskFileItem객체들을  ArrayList배열에 추가 하게 됩니다. 그후 ~ ArrayList배열 자체를 반환 해 줍니다.
			*/
			List items = upload.parseRequest(request);
			
			//ArrayList배열에 저장된 DiskFileItem객체(요청한 아이템하나)의 갯수만큼 반복
			for(int i=0;   i<items.size();   i++) {
				
				//ArrayList배열에서 DiskFileItem객체를 얻는다
				FileItem  fileItem = (FileItem)items.get(i);
				
				//얻은 DiskFileItem객체의 정보가 첨부한 파일 요청이 아닐 경우
				if( fileItem.isFormField()) {
					
					
					aritcelMap.put(fileItem.getFieldName(), fileItem.getString(encoding));

				}else {//얻은 DiskFileItem객체의 정보가  첨부한 파일일 경우
					
					System.out.println("<input type='file'>의 name속성명 : " +  fileItem.getFieldName() );
					
					//업로드시 첨부한 파일의 크기가 0보다 크다면?
					if(fileItem.getSize() > 0 ) {
						
						//업로드할 파일명을 얻어  파일명의 뒤에서부터 \\문자열이 들어 있는지 index위치를 알려주는데..
						//없으면  -1을 반환함.
						int idx = fileItem.getName().lastIndexOf("\\");//파일명의 뒤에서 부터 \\문자열이 들어 있는지 검색해서
																	   //만약 있으면 \의 index위치를 int로 반환,없으면 -1을 반환
						
						
						if(idx == -1) {
							idx = fileItem.getName().lastIndexOf("/"); //-1얻기
						}
						
						//업로드할 파일명 얻기
						String r_sfile = fileItem.getName().substring(idx + 1);
						//업로드할 파일경로  + 파일명 을 주소로 만들어서 접근할 File객체 생성
						File uploadFile = new File(currentDirPath + "\\temp\\"  + r_sfile);
											//    C:\\file_repo  + "\\temp\\" + 업로드할파일명
						
						//업로드 하기 전에 업로드할 파일을 선택했던 <input type="file">의 name속성값을 key로 얻고
						//<input type="file">에 업로드시 첨부한 업로드할 파일명을 value로 하여 HashMap에 저장 
						aritcelMap.put(fileItem.getFieldName(), r_sfile);
						
						//실제 파일업로드
						fileItem.write(uploadFile);
			
					} // 가장 안쪽 if
				
				}//안쪽 if else중에서 else
		
			}//for
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return aritcelMap; //upload메소드를 호출한 장소로 HashMap을 반환
	}
	
	
	//글 제목을 클릭 시 해당리뷰 조회
	public ReviewVo serviceBoardRead(HttpServletRequest request) {
		
		String r_idx = request.getParameter("r_idx");
		
		
		// 조회수 증가, ReviewVo 객체 가져오기
		return ReviewDao.boardRead(r_idx);
	}
	
	//DB의 테이블에 저장된 글삭제 시~ 삭제하는 글에 처부된 첨부폴더(글번호 폴더)도 같이 삭제 
	public String serviceDeleteBoard(HttpServletRequest request) throws IOException {
		
		String delete_idx = request.getParameter("r_idx");//요청한 글번호 얻기 
		//삭제할 글번호 폴더에 접근하기 위해 File객체 생성
		File deleteDir = new File(path +"\\" + delete_idx);
		//						  "C:\\file_repo\\2"
		
		//1.글번호에 해당하는 글 정보 DB의 테이블에서 삭제함! 삭제에 성공하면 "삭제성공"문자열 반환받고
		//  실패하면? "삭제실패" 문자열 반환 받자
		String result = ReviewDao.deleteBoard(delete_idx);
		
		//2.DB의 테이블에 글이 삭제 되면?
		if(result.equals("삭제성공")) {
			 
			if( deleteDir.exists() ) {
				
				FileUtils.deleteDirectory(deleteDir);   //글번호 폴더 삭제
			}
		}
		
		return result;//글삭제 + 글번호 삭제에 성공또는 실패시  "삭제성공" 또는 "삭제실패" 반환 
	}

	

}//ReviewService 끝
 



