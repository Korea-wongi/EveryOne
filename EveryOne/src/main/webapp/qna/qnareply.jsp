<%@page import="vo.QnaVo"%>

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% 
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	//주글(부모글)의 글번호,문의상품
	String q_idx = (String)request.getAttribute("q_idx");
	String b_inx = (String)request.getAttribute("b_inx");
	//작성자 아이디
	String q_mid = (String)session.getAttribute("m_id");
	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>답변글 작성 화면</title>
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

<p id="t" align="center" style="background-color: aqua;">답변글 작성 화면</p>

<form onsubmit="return check();"  id="frm" action="<%=contextPath%>/Qna/replyPro.do" method="post" enctype="multipart/form-data">

<%--답변글(자식글)을 DB의 테이블에 insert하기위해 주글(부모글)의 글번호를 같이 전달 --%>
<input type="hidden" name="super_q_idx" value="<%=q_idx%>">

<div class="container">
  <div class="line"><img src="<%=contextPath%>/images/qnaImages/line_870.gif" width="870" height="4"></div>
  <div class="content">
    <div class="form">
      <div class="row">
        <label for="content">제목: </label>
        <input type="text" name="title" class="input-field" value="답변" readonly>
      </div>
      <div class="row">
        <div class="label">작성자 ID:</div>
        <input type="text" name="writer_id" class="input-field" value="<%=q_mid%>" readonly>
      </div>
    </div>
    <div class="form">
      <div class="row">
        <label for="buyItems">문의상품:</label>
        <input type="text" name="b_inx" id="b_inx" value="<%=b_inx%>" readonly>
      </div>
    <div class="form">
      <div class="row">
        <label for="fileName">첨부파일 선택:</label>
        <input type="file" name="fileName" id="fileName" class="input-field">
      </div>
    </div>
    <div class="form">
      <div class="row">
        <label for="content">내용:</label>
        <textarea name="content" id="content" rows="15" cols="100" class="input-field"></textarea>
      </div>
    </div>
  </div>
  <div class="buttons">
    <button type="submit" class="btn btn-outline-primary">글 등록</button>
    <!-- 목록보기 -->
   <a href="" id="list"><button class="btn btn-outline-primary">목록</button></a>
  </div>
</div>
</form>

<script type="text/javascript">
	//목록보기 
	document.getElementById("list").addEventListener("click", function(event) {
	    event.preventDefault(); // 기본 동작 방지
	    location.href = "<%= contextPath %>/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0";
	  });

	// 폼이 제출되기 전에 내용이 입력되었는지 확인
	  function check() {
	    var content = document.getElementById("content").value.trim();
	    if (content === "") {
	      alert("답변을 작성하세요");
	      return false; // 폼 제출을 막음
	    }
	    return true; // 폼 제출을 허용
	  }
</script>
</body>
</html>