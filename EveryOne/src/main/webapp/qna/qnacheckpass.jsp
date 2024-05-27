<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% 
	String contextPath=request.getContextPath();
	String q_idx=request.getParameter("q_idx");
	String nowBlock=request.getParameter("nowBlock");
	String nowPage=request.getParameter("nowPage");
	
	String m_admin=(String)session.getAttribute("m_admin");
	String m_id=(String)session.getAttribute("m_id");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀글 보기</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<style>
    /* CSS to position the buttons */
    .password {
        display: flex;
        align-items: center;
        justify-content: center; /* Center items horizontally */
        margin-bottom: 20px; /* Add margin below the password field */
    }
    .password label {
        margin-right: 10px; /* Adjust the margin between label and input */
    }
    .password button {
        margin-left: 10px; /* Adjust the margin between input and button */
    }
    .button-group {
        display: flex;
        justify-content: center; /* Center items horizontally */
    }
</style>

</head>
<body>
    <form action="<%=contextPath%>/Qna/password.do" onsubmit="return check();">
        <input type="hidden" name="q_idx" value="<%=q_idx%>"> <!-- 글 번호 -->
        <input type="hidden" name="nowPage" value="<%=nowPage%>"> 
        <input type="hidden" name="nowBlock" value="<%=nowBlock%>">
        <input type="hidden" name="m_admin" value="<%=m_admin%>">
        
        <legend>비밀글보기</legend>
        <p class="info">이 글은 비밀글입니다. <strong class="txtEm">비밀번호를 입력하여 주세요.</strong><br>관리자는 확인버튼만 누르시면 됩니다.</p>
        <div class="password">
            <label for="secure_password">비밀번호</label>
            <input id="qnapass" name="qnapass" type="password">
            <button type="submit" id="passConfirm" class="btn btn-outline-secondary">확인</button>
        </div>
        
        <div class="button-group"> <!-- Container for buttons -->
            <input type="button" id="list" value="목록" onclick="location.href='<%=contextPath%>/Qna/list.do?center=qna/qna.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=nowPage%>'" class="btn btn-outline-primary">
        </div>
    </form>
</body>
</html>
