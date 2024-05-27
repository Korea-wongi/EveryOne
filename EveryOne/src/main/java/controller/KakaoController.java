package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.FileBoardService;
import service.KakaoService;
import service.MemberService;
import vo.KakaoUserVo;
import vo.NewsVo;
import vo.TokenVo;

@WebServlet("/kakao/api")
public class KakaoController extends HttpServlet {
	private KakaoService kakaoService;
	private KakaoUserVo KakaoUserVo;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		kakaoService = new KakaoService();
		KakaoUserVo = new KakaoUserVo();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = request.getParameter("code");

		String contextPath = request.getContextPath();
		String nextPage = null;

		try {
			/* 얻어온 code로 토큰 발급 POST 요청 */
			URL url = new URL("https://kauth.kakao.com/oauth/token");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.setDoOutput(true); // 서버한테 전달할게 있는지 없는지

			String parameter = "grant_type=authorization_code" + "&client_id=클라이언트 id"
					+ "&redirect_uri=redirect 주소 작성" + "&code=" + code;

			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
				bw.write(parameter.toString());
			} catch (IOException e) {
				throw e;
			} finally {
				if (bw != null)
					bw.flush();
			}

			int resultInt = conn.getResponseCode();

			BufferedReader br = null;
			String line = "", result = "";
			try {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					result += line;
				}
			} catch (IOException e) {
				throw e;
			} finally {
				if (br != null)
					br.close();
			}


			// JSON 데이터 객체로 변환 후 access 토큰 get
			Gson gson = new Gson();
			TokenVo tokenDTO = gson.fromJson(result, TokenVo.class);
			String access_token = tokenDTO.getAccess_token();

			// access_token 으로 다시 요청
			url = new URL("https://kapi.kakao.com/v2/user/me");
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

			line = "";
			result = "";
			try {
				br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					result += line;
				}
			} catch (IOException e) {
				throw e;
			} finally {
				if (br != null)
					br.close();
			}

			// Json 데이터 Vo 저장 후 반환
			String json = "{\"id\":id값,\"nickname\":이름,\"email\":이메일}";
			KakaoUserVo = gson.fromJson(json, KakaoUserVo.class);
			
			// id 값이 테이블에 존재하는지 여부 확인
			int resultInt1 = kakaoService.serviceCheckId(KakaoUserVo, request);
			
			if(resultInt1 == 0) {
				KakaoUserVo KakaoUserVo1 = kakaoService.serviceLogin(KakaoUserVo, request);
				request.setAttribute("center", "member/kakaoJoin.jsp");
				request.setAttribute("kakaoUserVo", KakaoUserVo1);
			}
			
			nextPage = "/index.jsp";

			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);		
		dispatch.forward(request, response);
	}

}
