<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.QnaVo"%>

<%
	String contextPath = request.getContextPath();
%>
<!doctype html>
<!-- Website Template by freewebsitetemplates.com -->
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>문의사항</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
	
<script>
	// 검색어 입력 시 유효성 검사 후 <form>태그 전송이벤트 발생
	function fnSearch(){
		let word = document.getElementById("word").value; 
		if(word == null || word == ""){
			alert("검색어를 입력하세요");
			document.getElementById("word").focus();
			return false; // onsubmit이벤트 차단	
		}else{
			document.getElementById("word").submit(); // 전송이벤트 실행(/searchlist.do)
		}
	}
	
	// 글 제목 클릭 시 글 번호를 이용하여 글 정보 조회
	function fnRead(val){
		document.frmRead.action="<%=contextPath%>/Qna/read.do";
		document.frmRead.q_idx.value=val;
		document.frmRead.submit();
	}
</script>
	
</head>
<body>
<%
//주제: 페이징 처리 변수 선언 
int totalRecord=0; //조회된 총 글 갯수
int numPerPage=5; //페이지 번호 하나당 보여질 글목록의 갯수
int pagePerBlock=3; //한 블럭당 묶여질 페이지 번호 갯수

int totalPage=0; //총 페이지번호 갯수
int totalBlock=0; //총 블럭 갯수
int nowPage=0; //현재 사용자 화면에 보여지고 있는 페이지 번호 
int nowBlock=0; //현재 사용자 화면에 보여지고 있는 페이지 번호가 속한 블럭 위치값
int beginPerPage=0; //각 페이지마다 보여지는 시작 글번호(맨 위의 글번호)저장


	//조회된 글목록 	
	ArrayList list = (ArrayList)request.getAttribute("list");
  
	// 총 글 갯수
	totalRecord= (Integer)request.getAttribute("count");
	
	// 클릭한 페이지 번호
	if(request.getAttribute("nowPage")!=null){
		nowPage=Integer.parseInt(request.getAttribute("nowPage").toString()); 
	}
	// 클릭한 페이지 블럭
	if(request.getAttribute("nowBlock")!=null){
		nowBlock=Integer.parseInt(request.getAttribute("nowBlock").toString()); 
	}

	// 각 페이지 맨 위 번호
	beginPerPage=nowPage*numPerPage;

	// 총 페이지 갯수
	totalPage=(int)Math.ceil((double)totalRecord/numPerPage);
	
	// 총 페이지 블럭
	totalBlock=(int)Math.ceil((double)totalPage/pagePerBlock);
	
%>
<center><br>

<h2>문의사항</h2>

<table border=0 width=100%>
<tr>
	<td>Total :  Articles (<font color=red>  <%=nowPage+1%> / <%=totalPage%> Pages </font>)</td>
</tr>
</table>

<!-- 네비게이션 Start -->
<nav class="navbar navbar-expand-lg navbar-light"  style="background-color: #e3f2fd;">
  
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      	
      	<!-- 배송문의 -->
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="<%=contextPath%>/Qna/ship?center=qna/ship.jsp&nowPage=0&nowBlock=0&q_title=배송문의">배송문의</a>
        </li>
        
        <!-- 상품문의 -->
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="<%=contextPath%>/Qna/product?center=qna/ship.jsp&nowPage=0&nowBlock=0&q_title=상품문의">상품문의</a>
        </li>
        
        <!-- 취소문의 -->
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="<%=contextPath%>/Qna/cancel?center=qna/ship.jsp&nowPage=0&nowBlock=0&q_title=취소문의">취소문의</a>
        </li>
        
        <!-- 결제문의 -->
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="<%=contextPath%>/Qna/pay?center=qna/ship.jsp&nowPage=0&nowBlock=0&q_title=결제문의">결제문의</a>
        </li>
      </ul>

	  <!-- 검색기능 -->      
      <form class="d-flex" action="<%=contextPath%>/Qna/searchList.do"
      		name="search"
      		method="post"
      		onsubmit="return fnSearch();">
      
      	<select class="form-select" name="keyField" size="1" aria-label="Default select example">
		  <option value="name">작성자</option>
		  <option value="tilte">제목</option>
		  <option value="content">내용</option>
		</select>
		
		<input type="hidden" name="nowPage" value= "0">
		<input type="hidden" name="nowBlock" value= "0">
		
		&nbsp;&nbsp;
        <input class="form-control me-2" name="keyWord" type="text" placeholder="Search" aria-label="Search" id="word">
        <button class="btn btn-outline-success" type="submit" value="찾기" >Search</button> 
      </form> 
    </div>
  </div>
</nav>
<!-- 네비게이션 End -->


<table align=center width="100%" border=0 cellspacing=0 cellpadding=3 >
	<tr>
		<td align=center colspan=2>
			<table border="0px" width="100%" cellpadding=2 cellspacing=0 class="table table-success table-striped">
				<tr align=center  height=120% >
					<td align="left"> 번호 </td>
					<td align="left"> 구매번호 </td>
					<td align="left"> 제목 </td>
					<td align="left"> 내용 </td>
					<td align="left"> 작성자 </td>
					<td align="left"> 작성일 </td>
					<td align="left"> 조회수 </td>
				</tr>
			<%
	 				if(list.isEmpty()){  // Qna 게시판에 글이 없을 경우
			%>		 
						<tr align="center">
							<td colspan="7">등록된 글이 없습니다.</td>
						</tr>
				<%		 
					 }else{	// Qna 게시판에 글이 있을 경우
						 
						for(int i=beginPerPage;  i<(beginPerPage+numPerPage); i++){
							
							if(i==totalRecord){break;}
							
							QnaVo vo  = (QnaVo)list.get(i);
								int level=vo.getQ_level(); //들여쓰기 정도 레벨 값 (주글 or 답변글)
				%>			
						<tr>
							<td align="left"><%=vo.getQ_idx()%></td>
							<td><%=vo.getQ_bidx()%></td>
							<td>
				
					<%		int width=0; 
							if(level>0){width=level*10;	%>
								<img src="<%=contextPath%>/images/qnaimages/level.gif" width="<%=width%>">
								<img src="<%=contextPath%>/images/qnaimages/re.gif">
										<%}%>
								<!-- 글 제목 하나를 클릭했을 때 글번호를 이용해 글하나 조회하여 보여주기 -->
								<a href="javascript:fnRead('<%=vo.getQ_idx()%>')"><%=vo.getQ_title()%></a>
							</td>
							<td><%=vo.getQ_content()%></td>
							<td><%=vo.getQ_mid()%></td>
							<td><%=vo.getQ_date()%></td>
							<td><%=vo.getQ_cnt()%></td>
						</tr>
						<%}	 
	 					}%>				
			</table>
		</td>
	</tr>
	<tr>
		<td><BR><BR></td>
	</tr>
	<tr>
		<td align="left">Go to Page 
			<% 
				if(totalRecord!=0){//조회한 글 갯수가 0이 아니라면?
					if(nowBlock>0){//페이지 블럭이 0 보다 크다면?
						%>
						<a href="<%=contextPath%>/Qna/list.do?center=qna/qna.jsp&nowBlock=<%=nowBlock-1%>&nowPage=<%=((nowBlock-1)*pagePerBlock)%>">
						◀ 이전 <%=pagePerBlock%> 개
						</a>
						<%
							}
					// 페이지 번호 출력 
					for(int i=0; i<pagePerBlock; i++)
					{
						%>
						&nbsp;&nbsp;
						<a href="<%=contextPath%>/Qna/list.do?center=qna/qna.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=(nowBlock*pagePerBlock)+i%>">
						<%=(nowBlock*pagePerBlock)+1+i%>
						<% if((nowBlock*pagePerBlock)+1+i==totalPage){break;}%>
						</a>
						<%
							}//for반복문 종료
					if(totalBlock>nowBlock+1){
						%>
						<a href="<%=contextPath%>/Qna/list.do?center=qna/qna.jsp&nowBlock=<%=nowBlock+1%>&nowPage=<%=(nowBlock+1)*pagePerBlock%>"> <%-- 페이지의 시작또한 0부터 시작 --%>
						▶ 다음 <%=pagePerBlock%> 개
						</a>
						<%
						}
					}%>
		</td>
	
		<td align=right>
		<%
			//로그인 유무로 글쓰기 버튼 visible 설정 
			String id = (String)session.getAttribute("m_id");
			if(id == null){//미로그인(글쓰기 이미지 버튼 감춤)
		%>		
			<input type="image"
				   id="newContent"
				   src="<%=contextPath%>/images/qnaimages/write.gif"
				   onclick="location.href='<%=contextPath%>/Qna/write.bo'"
				   style="visibility: hidden;">	
		<%		
			}else{//로그인(글쓰기 이미지 버튼 노출)
		%>		
			<button id="newContent" 
					onclick="location.href='<%=contextPath%>/Qna/write.do?nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>'"
					class="btn btn-secondary">
					글쓰기
			</button>
		<%		
			}
		%>
		</td>
	</tr>
</table>

<BR>
	<a href="<%= contextPath %>/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0">[처음으로]</a>
	
<!-- 글 제목 클릭 시 보내는 정보 -->
<form name="frmRead">
	<input type="hidden" name="q_idx"> 
	<input type="hidden" name="nowPage"  value="<%=nowPage%>">
	<input type="hidden" name="nowBlock" value="<%=nowBlock%>">
</form>
	
</center>	
</body>
</html>
