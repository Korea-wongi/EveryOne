<%@page import="vo.NewsVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

 
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	String n_name = "관리자";
	
	//조회한 글정보 얻기
	NewsVo vo = (NewsVo)request.getAttribute("vo");
	String n_title = vo.getN_title();//조회한 글제목
	String n_content = vo.getN_content().replace("/r/n", "<br>");//조회한 글 내용
	String n_sfile = vo.getN_sfile();//업로드한 실제파일명 (조회후 받아온 값)
	
	String n_idx = (String)request.getAttribute("n_idx");
	
	String nowPage = (String)request.getAttribute("nowPage");
	String nowBlock = (String)request.getAttribute("nowBlock");
	
	String m_admin=(String)session.getAttribute("m_admin");
	
	%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<title>글 수정 화면</title>
</head>
<body>
	

	<table width="80%" border="0" cellspacing="0" cellpadding="0">
		<tr height="40">
			<td width="41%" style="text-align: left">&nbsp;&nbsp;&nbsp; 
				<img src="<%=contextPath%>/images/newsimages/board02.gif" width="150" height="30">
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<div align="center">
					<img src="<%=contextPath%>/news/images/line_870.gif" width="870" height="4">
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<div align="center">
					<table width="95%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20" colspan="3"></td>
						</tr>
						<tr>
							<td height="327" colspan="3" valign="top">
								<div align="center">
									<table width="95%" height="373" border="0" cellpadding="0" cellspacing="1" class="border1">
										<tr>
											<td width="13%" height="29" bgcolor="#e4e4e4" class="text2">
												<div align="center">작 성 자</div>
											</td>
											<td width="34%" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; <input type="text" name="writer" id="writer" value="<%=n_name%>" disabled >
											</td>
											
											<!-- 이메일 지움 -->
											
										</tr>
										<tr>
											<td height="31" bgcolor="#e4e4e4" class="text2">
												<div align="center">제&nbsp;&nbsp;&nbsp; &nbsp; 목</div>
											</td>
											<td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
												&nbsp;&nbsp; <input type="text" name="n_title" id="n_title" value="<%=n_title%>" disabled>
												<span id="n_titleInput"></span>
											</td>
										</tr>
										<tr>
											<td height="31" bgcolor="#e4e4e4" class="text2">
												<div align="center">다운로드:</div>
											</td>
											<td colspan="2" bgcolor="#f5f5f5" style="text-align: left">
												<%-- 다운로드할 폴더번호 경로와 다운로드 할 파일명 전달 --%>
												&nbsp;&nbsp;<a id="fileDown" href="<%=contextPath%>/FileBoard/Download.do?path=<%=n_idx%>&n_sfile=<%=n_sfile%>"><%=n_sfile%>
												</a>
												<%-- 파일 수정 --%>
												<form id="fileTable"action="<%=contextPath%>/FileBoard/fileUpdate.do" method="POST" enctype="multipart/form-data" style="visibility:hidden">
												&nbsp;&nbsp;<input type="file" name="n_sfile" id="n_sfile" size="70" />
												<input type="hidden" name="n_idx" id="n_idx" value="<%=n_idx%>" />
												<input type="submit" value="파일 수정">
											</form>	
											</td>
											
											<!-- 다운로드 수 지움 -->
											
										</tr>
										<tr>
											<td height="245" bgcolor="#e4e4e4" class="text2">
												<div align="center">내 &nbsp;&nbsp; 용</div>
											</td>
											<td colspan="3" bgcolor="#f5f5f5" style="text-align: left; vertical-align: top;">
												&nbsp; <textarea rows="20" cols="100" name="n_content" id="n_content" disabled><%=n_content%></textarea>
											<span id="n_contentInput"></span>
											</td>
										</tr>
										
										<!-- 패스워드 지움 -->
										
									</table>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3">&nbsp;</td>
						</tr>
						<tr>
							<td style="width: 48%">
								<div align="right" id="menuButton" >
								<%--수정완료 --%>	
									<button id="update" class="btn btn-outline-warning" style="visibility: hidden;">수정완료</button>
								<%--수정하기 --%>	
									<button id="change" class="btn btn-outline-warning" style="visibility: hidden;">수정하기</button>
								<%--삭제하기 --%>	
									<button id="delete" class="btn btn-outline-danger"  onclick="javascript:deletePro('<%=n_idx%>');" style="visibility:hidden">삭제하기</button>&nbsp;&nbsp;
								</div>
							</td>
							<td width="10%">
								<div align="center">
									<%-- 목록 --%>
									<button id="list"
											class="btn btn-outline-primary" 
											onclick="location.href='<%=contextPath%>/FileBoard/list.do?nowBlock=<%=nowBlock%>&nowPage=<%=nowPage%>'">
											목록
									</button>
								</div>
							</td>
							<td width="42%"></td>
						</tr>
						<tr>
							<td colspan="3" style="height: 19px">&nbsp;</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	
		<%-- 글 수정 유효성 검사 체크 --%>
	<script src="<%=contextPath %>/js/join4.js"></script>
	
	<%
		//로그인하지 않았을 경우
  		if(m_admin == null){
  			
	  }else if(m_admin.equals("2")){//admin값이 2인 경우 관리자
	  %>
	<script>
		alert("<%=m_admin%>확인. 관리자로 로그인하셨습니다. 수정에 주의하세요.");
	
			//관리자로 로그인 한 경우 수정, 삭제버튼 보이도록 함
			$("#change").css("visibility","visible");
			$("#delete").css("visibility","visible");

	</script>
<%}	%>
	
	<%//글에 등록된 파일이 없을 경우
  		if(n_sfile == null){
  	%>		
  		<script>	
  			//다운로드 링크 제거
  			$("#fileDown").remove();
  		</script>
  	<%
  		}
  	%>
  	
  	
	<script>
	function deletePro(n_idx){
		var result = window.confirm("정말로 글을 삭제하시겠습니까?");
		
		if(result == true){//확인 버튼 클릭
			
			//비동기방식으로 글삭제 요청!
			$.ajax({
				type : "post",
				async : true,
				url : "<%=contextPath%>/FileBoard/deleteBoard.do",
				data : {n_idx : n_idx},
				dataType : "text",
				success : function(data){
					
					if(data=="삭제성공"){
<%-- 							location.href = "<%=contextPath%>/Fileboard/read.bo?nowBlock=0&nowPage=0"; --%>
						window.alert('삭제 성공');
						//목록 이미지를 강제로 클릭 이벤트 발생시키는 부분!
						$("#list").trigger("click");
					}else{//"삭제실패"
						window.alert('삭제 실패');
						  history.go(-1);
					}
					
					
				},
				error : function(){
					alert("비동기 통신 장애");
				}
			});
			
		}else{//취소 버튼을 눌렀을때
			return false;
		}
	}
	
	
		//수정 이미지 버튼을 선택해서 가져와서 클릭 이벤트 등록!
		$("#change").click(function(){
			
						document.getElementById("n_title").disabled = false;
						document.getElementById("n_content").disabled = false;
						$("#update").css("visibility","visible");
						//수정완료 버튼 보이게
						
						$("#change").remove();
						//$("#change").css("visibility","hidden");
						//수정하기 버튼 사라지게
						
						$("#fileTable").css("visibility","visible");
						//파일 수정 보이게
						
		});
		
		//수정 이미지 버튼을 선택해서 가져와서 클릭 이벤트 등록!
		$("#update").click(function() {
			
			var isValid = check();
			
			if (isValid != false) {
			
			var n_title = $("#n_title").val(); //수정시 입력한 글 제목 얻기
			var n_content = $("#n_content").val(); //수정시 입력한 글 내용 얻기
			var n_idx = <%=n_idx%>; //수정시 사용할 글번호 얻기
			
			$.ajax({
				type : "post",
				async : true,
				url : "<%=contextPath%>/FileBoard/updateBoard.do",
				data : {
					n_title : n_title,
					n_content : n_content,
					n_idx : n_idx
				},
				dataType : "text",
				success : function(data){
					
					if(data=="수정성공"){
						window.alert('수정 성공');
						location.href = "<%=contextPath%>/FileBoard/read.do?nowBlock=0&nowPage=0&n_idx=<%=n_idx%>";
						
					}else{//"수정실패"
						window.alert('수정 실패');
						history.go(-1);
					
					}
					
				},
				error : function(){
					alert("비동기 통신 장애");
				}
				
				
			});
			
		}
			
		});

	</script>
	

	
</body>
</html>