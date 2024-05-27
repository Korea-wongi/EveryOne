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

import dao.FileBoardDao;
import dao.MemberDao;
import vo.NewsVo;
import vo.MemberVo;

public class FileBoardService {
	
	
	private FileBoardDao FileBoardDao;
	private MemberDao MemberDao;
	String path = "C:\\TeamProject1\\Project44\\src\\main\\webapp\\newsimage";

	
	//생성자 
	public FileBoardService() {
		FileBoardDao = new FileBoardDao();
		MemberDao = new MemberDao();
	}

	// 공지사항 모든 글 조회
	public ArrayList serviceBoardListAll() {
		
		return FileBoardDao.boardListAll();//DAO의 메소드 호출해서 모든글 조회후 List배열에 담아 반환
	}
	
	//선택한 option별 키워드 글검색 
	public ArrayList serviceBoardList(HttpServletRequest request) {
		
		//ArrayList를 BoardController로 리턴 
		return FileBoardDao.boardSearch(request.getParameter("keyField"), 
									  request.getParameter("keyWord"));
	}
	
	//"2단계 요청한 주소 :  /writePro.bo " 를 받았을때 
	public int serviceInsertBoard(HttpServletRequest request, 
									  HttpServletResponse response) throws Exception {
			
			//파일업로드 후 업로드한 파일의 <input> name속성에 대한 value에 설정된 값
			// 쓴 새글 정보들을 담고 있는 해쉬맵을 반환 받는다.
			Map<String, String> articleMap = upload(request,response);
			
			//작성한 글정보(업로드할 파일정보 포함)를 HashMap에서 꺼내오기 
			String n_title = articleMap.get("n_title");
			String n_content = articleMap.get("n_content");
										//FileBoardwrite.jsp의 아이디가 m_id로 저장되어있기 때문에 m_id여야합니다
			String n_mid = articleMap.get("m_id");
			String n_sfile = articleMap.get("n_sfile"); 
			
			
			NewsVo vo = new NewsVo();
						vo.setN_title(n_title);
						vo.setN_content(n_content);
						vo.setN_mid(n_mid);
						vo.setN_sfile(n_sfile);
						
			
			int articelNO = FileBoardDao.insertBoard(vo);
						
			if(n_sfile != null && n_sfile.length() != 0) {//글을 추가할떄 업로드한 파일이 있으면
				//temp폴더에 이미 업로드된 파일에 접근해서 글번호로 폴더로 이동시키기 위해 경로 저장 
				File srcFile = new File(path + "\\temp\\" + n_sfile);
				
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
						String n_sfile = fileItem.getName().substring(idx + 1);
						//업로드할 파일경로  + 파일명 을 주소로 만들어서 접근할 File객체 생성
						File uploadFile = new File(currentDirPath + "\\temp\\"  + n_sfile);
											//    C:\\file_repo  + "\\temp\\" + 업로드할파일명
						
						//업로드 하기 전에 업로드할 파일을 선택했던 <input type="file">의 name속성값을 key로 얻고
						//<input type="file">에 업로드시 첨부한 업로드할 파일명을 value로 하여 HashMap에 저장 
						aritcelMap.put(fileItem.getFieldName(), n_sfile);
						
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
	
	
	// 공지사항 글 제목을 클릭 시 글 조회
	public NewsVo serviceBoardRead(HttpServletRequest request) {
		
		//FileBoardList.jsp페이지에서 전달하여 요청한 3개의 값 중 글번호를 이용해 
		//글 정보를 조회하기 위해 요청한 글번호 얻자
		String n_idx = request.getParameter("n_idx");
		
		
		// 조회수 증가, NewsVo 객체 가져오기
		return FileBoardDao.boardRead(n_idx);
	}
	

	
	// "/Download.do"
	//	파일 다운로드 요청이 들어 왔을때 
	//  파일 다운로드 로직과  다운로드한 파일의 다운로드수를 FileBoard테이블에 DOWNCOUNT열에 1증가하여 UPDATE
	public void serviceDownload(HttpServletRequest request, 
								HttpServletResponse response) throws IOException {
		
		
		
		//파일 다운로드 로직 구현-----
			//다운로드할  글번호 폴더 경로와 다운로드할 파일명 얻기 
			String idx = request.getParameter("path"); //다운로드할 파일이 저장된 글번호폴더명 
			String name = request.getParameter("n_sfile"); //실제 다운로드할 파일명 
			
			//다운로드할 파일이 저장되어 있는 글번호 폴더 경로를 문자열로 만들어서 변수에 저장
			String filePath = path +"\\" + idx;
			
			File f = new File(filePath + "\\" + name);
							//"C:\\file_repo\\2\\1.png" 
			
			//다운로드할 파일의 정보를 읽어들여 웹브라우저로 바이트단위로 내보낼 출력스트림 통로 얻기 
			OutputStream outputStream = response.getOutputStream();
			
			
			//다운로드할 파일과 연결된 파일의 정보를 바이트단위로 읽어들일 입력스트림 통로 얻기 
			FileInputStream fileInputStream = new FileInputStream(f);
			
			//HTTP 1.1버전에서 지원하는 헤더로 no-cache로 설정하면 브라우저는 응답받은 결과를
			//캐시 스토리지에 저장하지 않습니다.
			//또한 뒤로가기 등을 통해서 페이지 이동하는 경우 페이지를 캐싱할수 있으므로 no-stroe 값또한
			//추가해 주어야 합니다. 
			response.setHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "no-store");
			
			//웹브라우저에서 다운로드할 파일명 클릭시
			//1.Content-Disposition속성에 attachment; 값을 지정하여 
			//  다운로드시 무조건 "파일 다운로드 다른이름으로 저장?" 대화상자가 뜨도록 하는 헤더 속성의 설정 
			//2. 다운로드할 파일명 깨져 내려 받아 지지 않도록 하기 위해 Content-Disposition속성에 다운로할 파일명 인코딩 후 설정 
			response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(name,"utf-8")+"\";");//			response.setHeader("Content-Disposition","attachment; n_sfile=" + URLEncoder.encode(name, "UTF-8") + ";");
																	//ㅇㅇ
			//입출력 작업
			//파일 전체 내용을 배열크기 단위로 읽어서 웹브라우저로 내보내기(다운로드 시키기)
			byte[] buffer = new byte[1024*8];			
			while(true) {
											 			//다운로드할 파일의 전체 데이터 중에서 
				int cnt = fileInputStream.read(buffer);//한번 8바이트만큼 읽어들여 위 buffer배열에 저장
											 			//그리고 읽어들인 바이트수 반환
											 			//만약 더이상 읽어 들일 바이트가 없으면 -1 반환
				if(cnt == -1) {//파일에서 더이상 읽어들일 바이트가 없으면
					break; //while반복문 벗어나기 
				}
				//요청한 클라이언트의 웹브라우저와 연결된 출력 스트림 통로를 이용해 
				//한번 읽어들인 8바이트의 데이터를 반복해서 내보냅니다.  다 ~~ 내보내면 파일전체가 다운로드 되는 것입니다
				outputStream.write(buffer, 0, cnt);		
			}			
			fileInputStream.close();  outputStream.close();
				
	}



	//DB의 테이블에 저장된 글삭제 시~ 삭제하는 글에 처부된 첨부폴더(글번호 폴더)도 같이 삭제 
	public String serviceDeleteBoard(HttpServletRequest request) throws IOException {
		
		String delete_idx = request.getParameter("n_idx");//요청한 글번호 얻기 
		
		//삭제할 글번호 폴더에 접근하기 위해 File객체 생성
		File deleteDir = new File(path +"\\" + delete_idx);
		//						  "C:\\file_repo\\2"
		
		//1.글번호에 해당하는 글 정보 DB의 테이블에서 삭제함! 삭제에 성공하면 "삭제성공"문자열 반환받고
		//  실패하면? "삭제실패" 문자열 반환 받자
		String result = FileBoardDao.deleteBoard(delete_idx);
		
		//2.DB의 테이블에 글이 삭제 되면?
		if(result.equals("삭제성공")) {
			 
			if( deleteDir.exists() ) {
				
				FileUtils.deleteDirectory(deleteDir);   //글번호 폴더 삭제
			}
		}
		
		return result;//글삭제 + 글번호 삭제에 성공또는 실패시  "삭제성공" 또는 "삭제실패" 반환 
	}

	//업로드 된 파일만 삭제(파일삭제) (삭제대상검토)
	public String fileDelete(HttpServletRequest request) throws IOException {
		
		String delete_idx = request.getParameter("n_idx");//요청한 글번호 얻기 
		
		//삭제할 글번호 폴더에 접근하기 위해 File객체 생성
		File deleteDir = new File(path + "\\" + delete_idx);
		
		//1.글번호에 해당하는 글 정보 DB의 테이블에서 삭제함! 삭제에 성공하면 "삭제성공"문자열 반환받고
		//  실패하면? "삭제실패" 문자열 반환 받자
		String result = FileBoardDao.deleteFile(delete_idx);
		
		//2.DB의 테이블에 글이 삭제 되면?
		if(result.equals("삭제성공")) {
			 
			if( deleteDir.exists() ) {
				
				FileUtils.deleteDirectory(deleteDir);   //글번호 폴더 삭제
			}
		}
		
		return result;//글삭제 + 글번호 삭제에 성공또는 실패시  "삭제성공" 또는 "삭제실패" 반환 
	}
	
	// "/updateBoard.do" 글 수정 요청 주소를 받았을떄 
	public int serviceUpdateBoard2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//수정시 입력한 정보 얻기 
		String idx_ = request.getParameter("n_idx");
		String title_ = request.getParameter("n_title");
		String content_ = request.getParameter("n_content");
	//	String n_sfile = request.getParameter("n_sfile");
	
		//파일업로드 후 업로드한 파일의 <input> name속성에 대한 value에 설정된 값
		// 쓴 새글 정보들을 담고 있는 해쉬맵을 반환 받는다.
		Map<String, String> articleMap = upload(request, response);
		
		String n_sfile = articleMap.get("n_sfile"); 
		
		
		
		int result = FileBoardDao.updateBoard2(idx_, title_, content_, n_sfile);
		
		if(n_sfile != null && n_sfile.length() != 0) {//글을 추가할떄 업로드한 파일이 있으면
			
		
			//삭제할 글번호 폴더에 접근하기 위해 File객체 생성
			File deleteDir = new File(path + "\\" + idx_);					
			
			FileUtils.deleteDirectory(deleteDir);
			
			//temp폴더에 이미 업로드된 파일에 접근해서 글번호로 폴더로 이동시키기 위해 경로 저장 
			File srcFile = new File(path +"\\" + n_sfile);
			
			//글번호 폴더 생성 하기 위해 경로 File객체에 저장
			File destDir = new File(path +"\\"+ idx_ );
			
			//DB에 추가한 글에 대한 글번호를 가져와서 글번호폴더 생성 
			destDir.mkdirs();
			
			//temp폴더에 이미 업로드된 파일을 글번호번호폴더로 이동시키자
			FileUtils.moveFileToDirectory(srcFile, destDir, true);
				
		} 
		
		//수정에 성공하면 1을 반환 실패하면 0을 반환 
		return result;
		
	}
	
	// "/updateBoard.do" 글 수정 요청 주소를 받았을떄 
		public int serviceUpdateBoard(HttpServletRequest request) {
			//수정시 입력한 정보 얻기 
			String idx_ = request.getParameter("n_idx");
			String title_ = request.getParameter("n_title");
			String content_ = request.getParameter("n_content");
		
			//수정에 성공하면 1을 반환 실패하면 0을 반환 
			return FileBoardDao.updateBoard(idx_, title_, content_);
		}
	
		// 파일 수정 처리
		public int serviceUpdatFileeBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
			int result = 0;

			Map<String, String> articleMap = upload(request,response);
			String n_sfile = articleMap.get("n_sfile");
			String n_idx = articleMap.get("n_idx");
			
			
			FileBoardDao.updateBoard(n_idx, n_sfile);
			
			result = Integer.parseInt(n_idx);
			
			if(n_sfile != null && n_sfile.length() != 0) {//글을 추가할떄 업로드한 파일이 있으면
				
				//temp폴더에 이미 업로드된 파일에 접근해서 글번호로 폴더로 이동시키기 위해 경로 저장 
				File srcFile = new File(path+"\\temp\\" + n_sfile);
				
				//글번호 폴더 생성 하기 위해 경로 File객체에 저장
				File destDir = new File(path+"\\"+ Integer.parseInt(n_idx) );
				FileUtils.deleteDirectory(destDir);
				
				File destDir2 = new File(path+"\\"+ Integer.parseInt(n_idx) );
				
				//DB에 추가한 글에 대한 글번호를 가져와서 글번호폴더 생성 
				
				destDir2.mkdirs();
				
				//temp폴더에 이미 업로드된 파일을 글번호번호폴더로 이동시키자
				FileUtils.moveFileToDirectory(srcFile, destDir2, true);
				
			}
			return result;
		}
}//FileBoardService 부장 
 



