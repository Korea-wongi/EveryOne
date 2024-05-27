<%@page import="java.math.BigInteger"%>
<%@page import="java.security.SecureRandom"%>
<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();

	String clientId = "클라이언트 아이디"; //애플리케이션 클라이언트 아이디값"
	String redirectURI = URLEncoder.encode("redirect 주소", "UTF-8");     
	SecureRandom random = new SecureRandom();
	String state = new BigInteger(130, random).toString();
	String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
	     + "&client_id=" + clientId
	     + "&redirect_uri=" + redirectURI
	     + "&state=" + state;
	session.setAttribute("state", state);
%>    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Login - SB Admin</title>
        <link href="<%=contextPath %>/css/styles1.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.2.js" charset="utf-8"></script>
         <style>
        .login-frm-btn {
        	width: 200px;
            height: 53px;
        }
    	</style>
    </head>
    <body >
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-5">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Login</h3></div>
                                    <div class="card-body">
                                        <form class="form-signin" action="<%=request.getContextPath()%>/Member/loginPro.do" id="join" method="post">
                                        
                                        	<!-- 아이디 -->
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id="uid" name="m_id" type="text" placeholder="ID" />
                                                <label for="inputId">ID</label>
                                            </div>
                                            
                                            <!-- 비밀번호  -->
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id="upw" name="m_pass" type="password" placeholder="Password" />
                                                <label for="inputPassword">Password</label>
                                            </div>
                                            <div class="form-check mb-3">
                                                <input class="form-check-input" id="inputRememberPassword" type="checkbox" value="" />
                                                <label class="form-check-label" for="inputRememberPassword">Remember Password</label>
                                            </div>
                                            
                                            
                                            <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                            	<!-- 비밀번호 찾기 -->
                                                <a class="small" href="<%=contextPath%>/Member/checkCode?center=member/checkCode.jsp">Forgot Password?</a>
                                                <!-- 로그인 버튼 -->
                                                <input type="submit" id="btnLogin" value="Login" class="btn btn-danger" onclick="return check();">
                                            </div>
                                        </form>
                                    </div>
                                    <div class="card-footer text-center py-3">
                                        <div class="small"><a href="register.html">Need an account? Sign up!</a></div>
                                    </div>
                                </div>
                                <!-- 카카오 로그인 -->
						            <a href="https://kauth.kakao.com/oauth/authorize?client_id=클라이언트아이디&redirect_uri=redirect주소입력&response_type=code">
						   				<img class="login-frm-btn" src="<%=contextPath %>/images/kakaoimage/KakaoTalk_20240422_151321263.png" />
						 			</a>
					 			
					 			 <!-- 네이버 로그인 -->
					 			 <a href="<%=apiURL%>">
					 			 	<img class="login-frm-btn"  id="naverIdLogin_loginButton" src="<%=contextPath %>/images/naverimage/btnG_In.png" />
					 			 </a>
					 			 
                            </div>
                        </div>
                    </div>
                </main>
            </div>
            
            
            <div id="layoutAuthentication_footer">
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2023</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%=contextPath %>/js/scripts.js"></script>
        
        <!-- 아이디, 비밀번호 유효성 검사 -->
        <script type="text/javascript">
        	function check() {
				var uid = document.getElementById('uid').value;
				var upw = document.getElementById('upw').value;
				
				if(uid.trim() == '' || upw.trim() == ''){
					alert("아이디와 비밀번호를 입력하세요");
					return false;
				}
				
			}
        
        </script>
        
        
        
    </body>
</html>
    