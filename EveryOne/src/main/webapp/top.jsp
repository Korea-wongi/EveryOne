<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<%
	String contextPath = request.getContextPath();
	String m_id =(String) session.getAttribute("m_id");
	String m_admin = (String)session.getAttribute("m_admin");
%>    



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Mustache Enthusiast</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	<style type="text/css">
		#navbar{
			display: flex;
		}
		#nav{
			margin-left: auto;
		}
		#left-content {
			flex-grow: 1;
			padding-right: 20px;
			float: left; /* Align the left-content div to the left */
		}
	</style>
	
	<script type="text/javascript">
		function search(){
			var keyword = document.getElementById('keyword').value; // 검색어 가져오기
		    var contextPath = '<%=contextPath%>'; // contextPath 변수 사용

		    // 검색어가 비어 있는지 확인
		    if (keyword.trim() === '') {
		        alert('검색어를 입력하세요.');
		        return; // 검색어가 비어 있으면 함수 종료
		    }

		    // 페이지 전환
		    var url = contextPath + '/naverSearch.do?center=naverSearchView.jsp&keyword=' + encodeURIComponent(keyword);
		    window.location.href = url;
		}
		// 엔터 키를 눌렀을 때 실행할 함수
	    function handleKeyPress(event) {
	        if (event.keyCode === 13) {
	            search(); 
	        }
	    }

	    // 페이지가 로드될 때 실행할 초기화 함수
	    function init() {
	        var inputElement = document.getElementById('keyword'); // 입력란 가져오기
	        inputElement.addEventListener('keypress', handleKeyPress); // keypress 이벤트에 핸들러 등록
	    }

	    // 페이지 로드 시 초기화 함수 호출
	    window.onload = init;
	</script>
</head>
<body>
		<div id="left-content"><jsp:include page="/weather/weather.jsp" /></div>
	
		<!-- 최상단 네비게이션 -->
		<div id="navbar">
		  <table id="nav">
		    <tr>
		     <!-- 로그인 했을 시 mypage 가능
		      	   admin 1 회원 페이지 요청
		      	   admin 2 관리자 페이지 요청 -->
<% 
				if(m_id != null){
%>
		      	<!-- 로그인 완료 시 보이는 버튼 -->
			      <td align="right">
			        <a href="<%= contextPath %>/Member/logout.do">
			       		<button class="btn btn-warning">로그아웃</button>
			        </a>
			      </td>
<%					
					if(m_admin.equals("2")){ /* 로그인 완료, 관리자 MyPage */ 
%>
			      <td>
			       <a href="<%= contextPath %>/Mypage/mypage2?center=mypage/mypage2.jsp&m_id=<%=m_id%>">
			       		<button class="btn btn-dark">MyPage</button>
			       </a>
			      </td>
<%
					}else if(m_admin.equals("1")){ /* 로그인 완료, 회원 Mypage */
%>
				  <td>
			       <a href="<%= contextPath %>/Mypage/mypage1?center=mypage/mypage1.jsp&m_id=<%=m_id%>">
			       		<button class="btn btn-dark">MyPage</button>
			       </a>
			      </td>
<%
					}
%>
				<tr>
				    <td colspan="2" align="right">
				            <div class="input-group"> 
				                <input class="form-control me-2" 
				                       type="search"
				                       id="keyword" 
				                       name="keyword" 
				                       placeholder="네이버쇼핑검색" aria-label="Search">
				                <button class="btn btn-warning" onclick="search()">쇼핑검색</button>
				            </div>
				    </td>	
				</tr>  

<%
				}else{  
%>		      
		     <!-- 미로그인 시 보이는 버튼 -->
		      	<td align="right"> 
			      	<a href="<%= contextPath %>/Member/login?center=member/login.jsp">
			      		<button class="btn btn-warning">로그인</button>
			      	</a> 
			      </td>
			      <td>
			        <a href="<%= contextPath %>/Member/memjoin?center=member/memjoin.jsp">
			       		<button class="btn btn-warning">회원가입</button>
			        </a>
			      </td>
			     
			     <tr>
				    <td colspan="2" align="right">
				            <div class="input-group"> 
				                <input class="form-control me-2" 
				                       type="search"
				                       id="keyword" 
				                       name="keyword" 
				                       placeholder="네이버쇼핑검색" aria-label="Search">
				                <button class="btn btn-warning" onclick="search()">쇼핑검색</button>
				            </div>
				    </td>	
				</tr> 
<%
				}
%>
		    </tr>
		  </table>
		</div>
		
	
	<div id="header">
		
		<!-- 메인로고 -->
		<a href="<%=contextPath %>/Shop/logo?center=main.jsp" class="logo">
			<img src="<%=contextPath %>/images/toplogo/KakaoTalk_20240422_144909962.jpg" alt="">
		</a>
		
		<!-- 중간 네비게이션  -->
		<ul id="navigation">
			<li class="selected">
				<a href="<%= contextPath %>/Shop/home?center=main.jsp">home</a> 
			</li>
			<li>
				<a href="<%= contextPath %>/Shirts/list.do">Shirts</a>
			</li>
			<li>
				<a href="<%= contextPath %>/Pants/list.do">Pants</a>
			</li>
			<li>
				<a href="<%= contextPath %>/Acc/list.do">Acc</a>
			</li>
			<li>
				<a href="<%= contextPath %>/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0">Qna</a>
			</li>
			<li>
				<a href="<%= contextPath %>/FileBoard/list.do?center=news/FileBoardlist.jsp&nowBlock=0&nowPage=0">News</a>
			</li>
		</ul>
	</div>
</body>
</html>