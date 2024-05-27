package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import service.NaverService;
import vo.NaverUserVo;

@WebServlet("/naver/api")
public class NaverController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	protected void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code = request.getParameter("code");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");
		
		NaverService naverService = new NaverService();
		String nextPage = null;
		
		
		String apiURL;
	    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	    apiURL += "client_id=" + "클라이언트 아이디";
	    apiURL += "&client_secret=" + "시크릿 키";
	    apiURL += "&redirect_uri=" + "redirect 주소";
	    apiURL += "&code=" + code;
	    
	    try {
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("GET");
	        int responseCode = con.getResponseCode();
	        BufferedReader br;
	        if(responseCode==200) { // 정상 호출
	          br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        } else {  // 에러 발생
	          br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        }
	        String inputLine;
	        StringBuffer res = new StringBuffer();
	        while ((inputLine = br.readLine()) != null) {
	          res.append(inputLine);
	        }
	        br.close();
	        if(responseCode==200) {
	        	System.out.println(" / 네이버컨트롤러의 access_token이 담긴 JSON데이터 = " + res.toString());
	        }
	        
	        Gson gson = new Gson();
	        
	        //액세스 토큰정보가 포함된 문자열을 json데이터로 변환
	        JsonObject jo = gson.fromJson(res.toString(), JsonObject.class);
	        
	        //액세스 토큰을 얻어옴
	        String accessToken = jo.get("access_token").getAsString();
	        
	        String token = accessToken; // 네이버 로그인 접근 토큰;
		    String header = "Bearer " + token; // Bearer 다음에 공백 추가
		
		
		    apiURL = "https://openapi.naver.com/v1/nid/me";
		
		
		    Map<String, String> requestHeaders = new HashMap<>();
		    requestHeaders.put("Authorization", header);
		    requestHeaders.put("Accept-Charset", "UTF-8");
		    //이름 값을 한글로 받아오기 위해서 설정해주어야 함
		    
		    //회원 프로필 정보(id, gender 등)가 저장되어 있는 json데이터 형식의 문자열
		    String responseBody = naverService.get(apiURL,requestHeaders);
		    
		
		    request.setAttribute("JSONDATA", responseBody);
		
	        
	        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
	        
	        //JSON데이터의 "response" 객체 내부의 데이터 추출
	        JsonObject responseData = jsonObject.getAsJsonObject("response");
	        
	        //필요한 데이터 추출
	        String id = responseData.get("id").getAsString();
	        
	        String gender = null;	        
	        if(responseData.get("gender").getAsString().equals("F")) {
	        	gender = "woman";
	        }else if(responseData.get("gender").getAsString().equals("M")) {
	        	gender = "man";
	        }
	                
	        String email = responseData.get("email").getAsString();
	        String mobile = responseData.get("mobile").getAsString().replace("-", "");
	        String name = responseData.get("name").getAsString();
	        
	        NaverUserVo naverUserVo = new NaverUserVo(id, gender, email, name, mobile);

			// id 값이 테이블에 존재하는지 여부 확인(존재시 1, 비존재시 0)
			NaverUserVo searchNvo = naverService.serviceCheckId(naverUserVo.getId(), request);
			
			String m_id = searchNvo.getId();
			String m_admin = searchNvo.getAdmin();
			
			//id가 테이블에 없는 경우(회원가입 되지 않은 경우 naverJoin페이지로 이동한다.
			if(searchNvo.getId() == null) {
				
				request.setAttribute("center", "member/naverJoin.jsp");
				request.setAttribute("NaverUserVo", naverUserVo);		
				
				//id가 조회되는 경우 그냥 로그인 시킨다.			
			}else if(searchNvo.getId() != null){
				
				naverService.NaverLogin(m_id, m_admin, request);
			}
		
			nextPage = "/index.jsp";
		    
	      } catch (Exception e) {
	        System.out.println(e);
	      }
	    
	    RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
	    
		}

			

}
