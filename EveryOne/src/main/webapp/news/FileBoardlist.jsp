<%@page import="vo.NewsVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
	//한글처리
	request.setCharacterEncoding("UTF-8");
	//컨텍스트주소 얻기
	String contextPath = request.getContextPath();
	
	String name = "관리자";
	
	String m_admin=(String)session.getAttribute("m_admin");
	
%>

<HTML>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<script>
	// 아래의 <select> option 에서 option하나를 선택하고 검색어를 입력해 
	//  검색 찾기를 눌렀을때 호출 되는 함수로 
	//  유효성 검사후 모두 입력하면 <form>의 전송이벤트를 실행시키는 함수 
	function fnSearch(){
		//입력한 검색어 값 얻기 
		let word = document.getElementById("word").value;
		
		//검색어를 입력하지 않았다면
		if(word == null || word == ""){
			alert("검색어를 입력하세요");
			//검색어를 입력할수 있는 <input>을 선택해서 포커스 강제로 설정
			document.getElementById("word").focus();
			//아래의 <form>태그로 false를 전달해서 onsubmit이벤트 차단
			return false;
		
		}else{//검색어를 입력 했다면
			
			//<form>을 선택해서 action속성에 적힌 서블릿으로 요청!(전송이벤트 실행!)
			//<form action="/board/searchlist.bo">
			//				1단계     2단계
			document.search.submit();
		}
	}
	
	
	//글제목 하나를 클릭했을때 글번호를 매개변수로 받아서 <form>로 
	//글번호에 해당되는 글의 정보를 DB로부터 조회 요청을 FileBoardController로 합니다.
	function  fnRead(val){		
		
		document.frmRead.action = "<%=contextPath%>/FileBoard/read.do";
		document.frmRead.n_idx.value = val;
		document.frmRead.submit();	
	}
</script>
<BODY>


<%
/*주제 : 페이징 처리 변수 선언후 계산 */
//[1]
	int totalRecord = 0;//조회된 총 글 갯수 저장될 변수 ---- [2] 가서 살펴보기  ok
	int numPerPage = 5; //페이지번호 하나당 보여질 글목록의 갯수 저장될 변수    ok
	int pagePerBlock = 3; // 하나의 블럭당 묶여질 페이지번호 갯수 저장될 변수  ok
						  //  1   2    3   <-  한블럭으로 묶음   3개 이겠죠?  
								  
    int totalPage = 0; //전체 총글의 갯수에 대한 총 페이지번호 갯수  저장될 변수 --  [4] 가서 살펴보기 ok
    int totalBlock = 0;//전체 총 페이지번호 갯수에 대한 총 블럭 갯수 저장될 변수 --  [5] 가서 살펴보기 ok 
    int nowPage = 0; //현재 사용자에게 보여지고 있는 페이지가 위치한 번호 저장(클릭한 페이지번호) -- [7] ok 
    int nowBlock = 0; //현재 사용자에게 보여지고 있는 페이지 번호가 속한 블럭 위치번호 저장 -- [8] ok
    int beginPerPage = 0; //각페이지마다 보여지는 시작 글번호(맨위의 글번호)저장 -- [9] ok
//------------------------[1] 끝   
    

	//조회된 글목록 얻기 
	//FileBoardController에서 재요청해서 전달한 request에 담긴 ArrayList배열 꺼내오기 	
	ArrayList list = (ArrayList)request.getAttribute("list");
  
	//[2] 조회된 총 글 갯수 얻기 
	totalRecord= (Integer)request.getAttribute("count");

	//[7]페이지 번호를 클릭했다면 클릭한 페이지 번호를 얻어 nowPage변수에 저장 
	if( request.getAttribute("nowPage") != null ){
		nowPage =  Integer.parseInt( request.getAttribute("nowPage").toString() );
	}
	//[8]클릭한 페이지번호가 속한 블럭 위치 번호 구하기 
	if( request.getAttribute("nowBlock") != null){
		nowBlock = Integer.parseInt( request.getAttribute("nowBlock").toString() );
	}	
	//[9] 각 페이지 번호에 보여지는 글목록의 가장위의 글에 대한 글번호 구하기 
	beginPerPage = nowPage *numPerPage;
	
	totalPage =  (int)Math.ceil( (double)totalRecord / numPerPage );	
	
	
	totalBlock =  (int)Math.ceil( (double)totalPage / pagePerBlock) ;
	

%>

<center><br>

<!-- 글제목 하나를 클릭했을때 BoardController로  글정보 조회 요청하는 <form> -->
<form name="frmRead">
	<input type="hidden" name="n_idx">  <%--글번호 --%>
	<input type="hidden" name="nowPage" value="<%=nowPage%>">
	<input type="hidden" name="nowBlock" value="<%=nowBlock%>">
</form>



<h2>공지사항 게시판</h2>

<table border=0 width=100%>
<tr>
	<td>Total :  Articles(
		<font color=red> <%=nowPage+1%> / <%=totalPage%> Pages </font>)
	</td>
</tr>
</table>

<%-- fnSearch()함수의 리턴값이 false이면  action속성에 적힌 컨트롤러 요청을 하지 않습니다. --%>


<!-- 네비게이션 Start -->
<nav class="navbar navbar-expand-lg navbar-light"  style="background-color: #e3f2fd;">
  
  	<!-- 무시 하세요  -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
      	<li class="nav-item"></li>
      	<li class="nav-item"></li>
      	<li class="nav-item"></li>
      	<li class="nav-item"></li>
      </ul>
      
      
      <!-- 찾기,search 폼 -->
      <form class="d-flex" action="<%=contextPath%>/FileBoard/searchList.do"
      		name="search"
      		method="post"
      		onsubmit="return fnSearch();"
      		>
      
      	<!-- select 조건  -->
      	<select class="form-select" name="keyField" size="1" aria-label="Default select example">
		  <option value="subject">제목</option>
		  <option value="content">내용</option>
		</select>
		
		
		<input type="hidden" name="nowPage" value= "0">
		<input type="hidden" name="nowBlock" value= "0">
		
		
		&nbsp;&nbsp;
        <input class="form-control me-2" name="keyWord" type="text" placeholder="Search" aria-label="Search" id="word">
        <button class="btn btn-outline-success" type="submit" value="찾기">
        Search
        </button>
        
      </form>
    </div>
  </div>
</nav>
<!-- 네비게이션 End -->



<table align=center width=80% border=0 cellspacing=0 cellpadding=3 class="table table-success table-striped">
  <div class="container">
        <div class="row justify-content-center">
            <div class="col-10">
               <table align="center" width="80%" border="0" cellspacing="0" cellpadding="3" class="table table-success table-striped" id="apple">
                    <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">이름</th>
                            <th scope="col">날짜</th>
                            <th scope="col">조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(list.isEmpty()){ %>
                            <tr align="center">
                                <td colspan="5">등록된 글이 없습니다.</td>
                            </tr>
                        <% } else {
                            for(int cnt=beginPerPage; cnt<(beginPerPage+numPerPage); cnt++) {
                                if(cnt == totalRecord) {
                                    break;
                                }
                                NewsVo vo = (NewsVo)list.get(cnt);
                                
                        %>
                            <tr>
                                <td><%=vo.getN_idx()%></td>
                                <td>								
                                    <a href="javascript:fnRead('<%=vo.getN_idx()%>')"><%=vo.getN_title()%></a>
                                </td>
                                <td>관리자</td>
                                <td><%=vo.getN_date()%></td>
                                <td><%=vo.getN_cnt()%></td>
                            </tr>
<%			
		}	 
	 }
%>				
		</tbody>
                </table>
            </div>
        </div>
    </div>
	</td>
</tr>
<tr>
	<td><BR><BR></td>
</tr>
<tr>
	<td align="left">Go to Page 
	<%
		if(totalRecord != 0){// 조회한 글갯수가 0이 아니라면?
			
			if(nowBlock > 0){// 조회한 글이 존재하면 페이지번호 또한 존재하며 
							//  페이지 번호가 존재하면 블럭 위치도 존재 하기 떄문에 0 보다 크다면?
	%>			
				<a href="<%=contextPath%>/FileBoard/list.do?nowBlock=<%=nowBlock-1%>&nowPage=<%=((nowBlock-1)*pagePerBlock)%>">
					◀ 이전 <%=pagePerBlock%>  개 
				</a>
	<%			
			}//if
			
			// 페이지 번호들 반복해서 출력하기 
			for(int i=0;  i<pagePerBlock;  i++){
				
	%>			
				&nbsp;&nbsp;
				<a href="<%=contextPath%>/FileBoard/list.do?nowBlock=<%=nowBlock%>&nowPage=<%=(nowBlock * pagePerBlock) + i%>">
					<%=(nowBlock * pagePerBlock) + 1 + i %>
					<% if((nowBlock * pagePerBlock) + 1 +i == totalPage) break; %>
				</a>
	<%			
			}//for 반복
			
			if(totalBlock > nowBlock + 1){
	%>			
				<a href="<%=contextPath%>/FileBoard/list.do?nowBlock=<%=nowBlock+1%>&nowPage=<%=(nowBlock+1)*pagePerBlock%>">
					▶ 다음 <%=pagePerBlock%>개 
			    </a>
	<%			
			}//안쪾 if		
		}//바깥쪽 if
	%>
	
	</td>
	</tr>
	</table>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<div align="right">
	
	
	<button id="newContent" 
			onclick="location.href='<%=contextPath%>/FileBoard/write.do?nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>'"
			style="visibility: hidden;"
			class="btn btn-secondary">
			글쓰기
	</button>
	<%
		//관리자 판별
		if(	m_admin == null){
		}else if(m_admin.equals("2")){			
	%>
		<script>
			$("#newContent").css("visibility","visible");
		</script>	
	<%
		}
	%>
	
	</div>	
	
<BR>

	<a href="<%=contextPath%>/FileBoard/list.do?nowBlock=0&nowPage=0">[처음으로]</a>

</center>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</BODY>
</HTML>