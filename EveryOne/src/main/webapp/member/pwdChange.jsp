<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>    
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Change Password</title>
        <link href="<%=contextPath %>/css/styles1.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body >
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-5">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Change Password</h3></div>
                                    <div class="card-body">
                                        <form class="form-signin" action="<%=contextPath%>/Member/memberChangePw.do" id="join">
                                        <input type="hidden" name="m_id" value="${m_id}">
                                        
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id=newAuthenCode name="newAuthenCode" type="text" placeholder="인증번호를 입력하세요" />
                                                <label for="inputId">인증번호를 입력하세요.</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <c:set var="abc" value="${requestScope.m_id}"></c:set>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id="newPw" name="newPw" type="password" placeholder="새 비밀번호를 입력하세요" />
                                                <label for="inputPassword">새 비밀번호를 입력하세요.</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" id="checkPw" name="checkPw" type="password" placeholder="새 비밀번호를 한번 더 입력하세요" />
                                                <label for="inputPassword">새 비밀번호를 한번 더 입력하세요.</label>
                                            </div>
                                            <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                <input type="submit" id="btn" value="change" class="btn btn-danger" onclick="return check();">
                                            </div>
                                        </form>
                                    </div>
                                </div>
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
				var code = document.getElementById('newAuthenCode').value;
				var newPw = document.getElementById('newPw').value;
				var checkPw = document.getElementById('checkPw').value;
				
				if(newPw.trim() == '' || checkPw.trim() == '' || code.trim() == '' ){
					alert("모든 입력란을 작성하세요.");
					return false;
				}
				if(newPw !== checkPw){
					alert("비밀번호가 일치하지 않습니다.");
					return false;
				}
				
			}
        
        </script>
    </body>
</html>
    