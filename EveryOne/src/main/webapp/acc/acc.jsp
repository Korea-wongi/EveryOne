<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.ArrayList"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<%
	String contextPath = request.getContextPath();
	String m_id = (String) session.getAttribute("m_id");

%>


<!doctype html>
<!-- Website Template by freewebsitetemplates.com -->
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gallery - Mustache Enthusiast</title>

<style type="text/css">
.serv_list {
	padding-top: 70px;
	padding-bottom: 70px;
}

.serv_list .container {
	max-width: 1000px;
	margin: auto;
}

.serv_list .item_list {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-between;
	gap: 20px; /* 요소 간 간격 조절 */
}

.serv_list .card {
	border: 1px solid #eee;
	border-radius: 5px;
	width: calc(33.33% - 20px); /* 한 줄에 3개씩 배치하기 위해 너비 조절 */
	padding: 5px;
	margin-bottom: 70px;
}

.serv_list .card img {
	height: 320px;
	width: 100%;
	object-fit: cover; /* 이미지 비율 유지 */
	border-radius: 5px; /* 이미지 둥글게 처리 */
}

.serv_list .card .text {
	padding: 10px;
}

.serv_list .card .text h5 {
	font-size: 18px;
}

.serv_list .card .text p {
	font-size: 14px;
	opacity: .6;
}

.serv_list .card .text button {
	background: black;
	color: white;
	border: 2px solid white;
	border-radius: 5px;
	padding: 5px 10px;
	font-size: 14px;
	transition: .5s ease;
}

.serv_list .card .text button:hover {
	transition: .5s ease;
	border: 2px solid black;
	background: transparent;
	color: black;
}

@media ( max-width : 768px) {
	.serv_list .card {
		width: calc(50% - 20px); /* 화면이 작아지면 2개씩 배치 */
	}
}

@media ( max-width : 480px) {
	.serv_list .card {
		width: 100%; /* 화면이 더 작아지면 한 개씩 배치 */
	}
}
</style>
<script type="text/javascript">
	function fnSearch() {
		//입력한 검색어 값 얻기 
		let word = document.getElementById("word").value;

		//검색어를 입력하지 않았다면
		if (word == null || word == "") {
			alert("검색어를 입력하세요");
			//검색어를 입력할수 있는 <input>을 선택해서 포커스 강제로 설정
			document.getElementById("word").focus();
			//아래의 <form>태그로 false를 전달해서 onsubmit이벤트 차단
			return false;

		} else {//검색어를 입력 했다면

			//<form>을 선택해서 action속성에 적힌 서블릿으로 요청!(전송이벤트 실행!)
			//<form action="/board/searchlist.bo">
			//				1단계     2단계
			document.search.submit();
		}
	}
</script>
</head>
<body>

	<%
		//[1] 페이징 처리
		int totalRecord = 0;//조회된 총 글 갯수 저장될 변수 ---- [2] 가서 살펴보기  ok
		int numPerPage = 9; //페이지번호 하나당 보여질 글목록의 갯수 저장될 변수    ok
		int pagePerBlock = 3; // 하나의 블럭당 묶여질 페이지번호 갯수 저장될 변수  ok  

		int totalPage = 0; //전체 총글의 갯수에 대한 총 페이지번호 갯수  저장될 변수 --  [4] 가서 살펴보기 ok
		int totalBlock = 0;//전체 총 페이지번호 갯수에 대한 총 블럭 갯수 저장될 변수 --  [5] 가서 살펴보기 ok 
		int nowPage = 0; //현재 사용자에게 보여지고 있는 페이지가 위치한 번호 저장(클릭한 페이지번호) -- [7] ok 
		int nowBlock = 0; //현재 사용자에게 보여지고 있는 페이지 번호가 속한 블럭 위치번호 저장 -- [8] ok
		int beginPerPage = 0; //각페이지마다 보여지는 시작 글번호(맨위의 글번호)저장 -- [9] ok
		//------------------------[1] 끝   

		//조회된 글목록 얻기 
		//Controller에서 재요청해서 전달한 request에 담긴 ArrayList배열 꺼내오기 	
		ArrayList list = (ArrayList) request.getAttribute("list");

		//[2] 조회된 총 글 갯수 얻기 
		totalRecord = (Integer) request.getAttribute("count");

		//[7]페이지 번호를 클릭했다면 클릭한 페이지 번호를 얻어 nowPage변수에 저장 
		if (request.getAttribute("nowPage") != null) {
			nowPage = Integer.parseInt(request.getAttribute("nowPage").toString());
		}
		//[8]클릭한 페이지번호가 속한 블럭 위치 번호 구하기 
		if (request.getAttribute("nowBlock") != null) {
			nowBlock = Integer.parseInt(request.getAttribute("nowBlock").toString());
		}
		//[9] 각 페이지 번호에 보여지는 글목록의 가장위의 글에 대한 글번호 구하기 
		beginPerPage = nowPage * numPerPage;

		totalPage = (int) Math.ceil((double) totalRecord / numPerPage);

		totalBlock = (int) Math.ceil((double) totalPage / pagePerBlock);
	%>
	<%-- fnSearch()함수의 리턴값이 false이면  action속성에 적힌 컨트롤러 요청을 하지 않습니다. --%>
	<form action="<%=contextPath%>/Acc/searchList.do" name="search"
		method="post" onsubmit="return fnSearch();"
		style= "float: right; padding-right: 100px;">

		<input type="text" size="16" name="keyWord" id="word"> 
		<input type="image" value="찾기" id="btn_submit"  src="<%=contextPath %>/images/icon/돋보기.png" height="22px" >
	</form>

	<div class="price_list">
		<p>
			<a href="<%=contextPath%>/Acc/priceAsc.do">낮은가격</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="<%=contextPath%>/Acc/pricDesc.do">높은가격</a>
		</p>
	</div>
	
	<section class="serv_list">
		<div class="container">
			<div class="item_list">
				<%
					for (int i = beginPerPage; i < (beginPerPage + numPerPage); i++) {

						if (i == totalRecord) {
							break;
						}
				%>
				<div class="card">
					<div class="img">
						<img
							src="<%=contextPath%>/productimage/<%=((HashMap)list.get(i)).get("p_idx")%>/<%=((HashMap) list.get(i)).get("i_name")%>">
					</div>
					<div class="text" id="price">
						<h5><%=((HashMap) list.get(i)).get("p_name")%></h5>
						<p><%=((HashMap) list.get(i)).get("p_price")%></p>
						
						<form action="<%=contextPath%>/Acc/productDetail.do">
							<input type="hidden" name="p_idx" value="<%=((HashMap) list.get(i)).get("p_idx") %>">	
							<input type="hidden" name="p_name" value="<%=((HashMap) list.get(i)).get("p_name") %>">	
							<input type="hidden" name="p_price" value="<%=((HashMap) list.get(i)).get("p_price") %>">	
							<input type="hidden" name="p_size" value="<%=((HashMap) list.get(i)).get("p_size") %>">
							<input type="hidden" name="i_name" value="<%=((HashMap) list.get(i)).get("i_name") %>">									
							<input type="hidden" name="m_id" value="<%=m_id%>">									
							<button type="submit">사러가기</button>
						</form>
					</div>
				</div>
				<%}%>
			</div>
		</div>
	</section>


	<%
		if (totalRecord != 0) {//조회한 글 갯수가 0이 아니라면?
			if (nowBlock > 0) {//조회한 글이 존재하면 페이지번호 또한 존재하며 페이지 번호가 존재하면 블럭 위치도 존재하기 때문에 0보다 크다면?
	%>
	<a
		href="<%=contextPath%>/Acc/list.do?center=acc.jsp&nowBlock=<%=nowBlock - 1%>&nowPage=<%=((nowBlock - 1) * pagePerBlock)%>">
		◀ 이전 <%=pagePerBlock%> 개
	</a>
	<%
		} //if
				//페이지 번호들 반복해서 출력하기
			for (int i = 0; i < pagePerBlock; i++) {
	%>
	&nbsp;&nbsp;
	<a
		href="<%=contextPath%>/Acc/list.do?center=acc.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=(nowBlock * pagePerBlock) + i%>">
		<%=(nowBlock * pagePerBlock) + 1 + i%> <%
 	if ((nowBlock * pagePerBlock) + 1 + i == totalPage) {
 				break;
 			}
 %>
	</a>
	<%
		} //for반복문 종료
			if (totalBlock > nowBlock + 1) {
	%>
	<a
		href="<%=contextPath%>/Acc/list.do?center=acc.jsp&nowBlock=<%=nowBlock + 1%>&nowPage=<%=(nowBlock + 1) * pagePerBlock%>">
		<%-- 페이지의 시작또한 0부터 시작 --%> ▶ 다음 <%=pagePerBlock%> 개
	</a>
	<%
		}
		}
	%>



</body>
</html>