<%@page import="java.util.ArrayList"%>
<%@page import="vo.BuyVo"%>
<%@page import="vo.QnaVo"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String contextPath = request.getContextPath();
	String nowPage = request.getParameter("nowPage");
	String nowBlock = request.getParameter("nowBlock");
	String q_mid=(String)session.getAttribute("m_id");
%>

<c:set var="buyList" value="${requestScope.buyList}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!-- <link rel="stylesheet" type="text/css" href="/MVCBoard/style.css"/> -->
<title>문의사항 글 작성 화면</title>

<style type="text/css">
.container {
  width: 90%;
  margin: 0 auto;
  text-align: center; /* Align internal content to center */
}

.line {
  text-align: center;
  margin-bottom: 20px;
}

.content {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: left; /* Reset text alignment */
}

.form {
  margin-bottom: 20px;
}

.row {
  margin-bottom: 15px;
}

.label {
  display: block;
  font-weight: bold;
  margin-bottom: 5px;
}

.input-field {
  width: calc(100% - 120px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-sizing: border-box;
  font-size: 14px;
}

.buttons {
  text-align: right;
}

.submit-button {
  padding: 12px 24px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #45a049;
}

.submit-button:active {
  background-color: #3e8e41;
}

</style>

</head>
<body>
<script  src="https://code.jquery.com/jquery-latest.min.js"></script>
<form id="frm" action="<%=contextPath%>/Qna/writePro.do" method="post" enctype="multipart/form-data" onsubmit="return check();" >

<div class="container">
  <div class="line"><img src="<%=contextPath%>/images/qnaImages/line_870.gif" width="870" height="4"></div>
  
  <div class="content">
  
    <div class="form">
      <div class="row">
        <div class="label">제목:</div>
        <select name="title" id="title" class="input-field">
          <option value="배송문의">배송문의</option>
          <option value="상품문의">상품문의</option>
          <option value="취소문의">취소문의</option>
          <option value="결제문의">결제문의</option>
        </select>
      </div>
      <div class="row">
        <div class="label">작성자 ID:</div>
        <input type="text" name="writer_id" class="input-field" value="<%=q_mid%>" readonly>
      </div>
    </div>
    
    <div class="form">
      <div class="row">
        <div class="label">문의상품:</div>
		  <c:choose>
		    <c:when test="${empty buyList}">
		      <script>
		      	alert("구매상품에 한해 문의사항 작성이 가능합니다.");
		        history.back();
		      </script>
		    </c:when>
		    <c:otherwise>
		    	<select name="buyItems" id="buyItems" class="input-field">
		      		<c:forEach var="buyItem" items="${buyList}">
		        		<option value="${buyItem.b_idx}">${buyItem.b_idx}</option>
		      		</c:forEach>
		    </c:otherwise>
		  </c:choose>
      </div>
      <div class="row">
        <div class="label">첨부파일 선택:</div>
        <input type="file" name="fileName" id="fileName" class="input-field">
      </div>
    </div>
    
    
    <div class="form">
      <div class="row">
        <div class="label">내용:</div>
        <textarea name="content" id="content" rows="15" cols="100" class="input-field"></textarea>
        <div id="contentInput"></div>
      </div>
    </div>
  </div>
  
  <div class="buttons">
    <button type="submit" class="btn btn-outline-primary" >글 등록</button>
    <a href="" id="list"><button class="btn btn-outline-primary">목록</button></a>
    <div id="resultInsert"></div>
  </div>
</div>

</form>



<script type="text/javascript">
	$("#list").click(function(event) {
		event.preventDefault();
		location.href = "<%= contextPath %>/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0"
	});
	
	function check(){
		var content = $("#content").val();
		
		if(!content){
			$("#contentInput").text("내용을 입력해주세요").css("color","red");
			
			return false;
		}
		return true;
	};
	
</script>

</body>
</html>