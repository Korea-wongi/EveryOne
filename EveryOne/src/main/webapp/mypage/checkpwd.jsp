<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");
%>     
<link rel="stylesheet" href="/stu/css/login.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<style type="text/css">
#mypageform {
	width: 500px;
	margin: 0 auto;
	margin-top: 50px;
	text-align: center;
	margin-bottom: 100px
}

.contents {
	font-size : 40px;
}

.pwdchecktable>input {
	width: 100%;
	height: 50px;
	border: 1px solid #e0e0e0;
	margin-bottom: 20px
}

button {
	width: 100%;
	height: 50px;
	display: block;
	border: none;
	margin-top: 10px;
	font-size: 20px;
}
</style>
<body>
		<div class="container">
			<div id="pwdCheck">
				<form action="<%=contextPath%>/Mypage/checkpwd.do" id="pwdcheckfrm"  method="post" >
					<div id="mypageform">
					
						<h3 class="contents">회원정보수정</h3>
						
						<!-- 패스워드확인-->
						<div id="pwdcheck">
							<h5>안전한 개인정보변경을 위해서<br> 
							현재 사용중인  비밀번호를 입력해주세요</h5> <br>
							
							<!-- 비밀번호 박스, 확인 버튼 -->
							<div class="pwdchecktable">
									<input type="password" class="form-control" name="m_pass" id="MEMBER_PASSWD"
										placeholder="비밀번호를 입력해주세요.">
									<input type="hidden" name="m_id" value="<%=m_id%>">
									<button class="defaultBtn loginBtn" type="submit">확인</button>
							</div>
							
						</div>
					</div>
				</form>
			</div>
		</div>
</body>
