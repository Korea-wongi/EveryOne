<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="vo.QnaVo"%>
 
<%
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	
	//조회한 글정보 얻기
	QnaVo vo = (QnaVo)request.getAttribute("qnaVo");
	int q_idx=vo.getQ_idx(); //조회한 글 번호
	String q_mid = vo.getQ_mid();//조회한 글을 작성한 사람
	int b_inx = vo.getQ_bidx(); //조회한 글을 작성한 사람 구매번호
	String title = vo.getQ_title();//조회한 글제목
	String content = vo.getQ_content().replace("/r/n", "<br>");//조회한 글 내용
	String sfile = vo.getQ_sfile();//업로드한 실제파일명 (조회후 받아온 값)
	int level=vo.getQ_level(); //주글 or 답변글 판단 
	
	String nowPage = (String)request.getAttribute("nowPage");
	String nowBlock = (String)request.getAttribute("nowBlock");
	
	String id = (String)session.getAttribute("m_id"); // 아이디 값 
	String m_admin=(String)session.getAttribute("m_admin"); // 관리자 or 회원 로그인 유무 확인
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 수정 화면</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<style type="text/css">
.custom-table-wrapper {
    margin: 20px auto;
    width: 80%;
}

.custom-table-wrapper table {
    width: 100%;
}

.custom-table-wrapper td {
    padding: 10px;
}
</style>
</head>
<body>
<%	if(id == null || m_admin == null){//로그인 하지 않았을경우 %>		
	<script>	
	alert("로그인을 하셔야 합니다.");
	history.back();
	</script>
<%}
else {%>
<div class="custom-table-wrapper">
	
    <div style="width: 80%; margin: 0 auto;">
        
        <div style="text-align: center; width: 95%; margin: 0 auto;">
            <div style="height: 20px;"></div>
            <div style="height: 327px; overflow: auto;">
                <div style="width: 95%; height: 373px; border: 1px solid #000; overflow: auto;">
                    <div style="display: flex;">
                        <div style="width: 13%; background-color: #e4e4e4; text-align: center;">
                            <div>작 성 자</div>
                        </div>
                        <div style="width: 34%; background-color: #f5f5f5; text-align: left;">
                            &nbsp;&nbsp; <input type="text" name="writer" id="writer" value="<%=q_mid%>" disabled>
                        </div>
                        <div style="width: 13%; background-color: #e4e4e4; text-align: center;">
                            <div>구매번호</div>
                        </div>
                        <div style="width: 40%; background-color: #f5f5f5; text-align: left;">
                            &nbsp;&nbsp; <input type="b_inx" name="b_inx" id="b_inx" value="<%=b_inx%>" disabled>
                        </div>
                    </div>
                    <div style="display: flex;">
                        <div style="width: 13%; background-color: #e4e4e4; text-align: center;">
                            <div>제 목</div>
                        </div>
                        <div style="width: 87%; background-color: #f5f5f5; text-align: left;">
                            &nbsp;&nbsp;
                            <select name="title" id="title">
                                <option value="배송문의" <%if(title.equals("배송문의")){%> selected<%} %>>배송문의</option>
                                <option value="상품문의" <%if(title.equals("상품문의")){%> selected<%} %>>상품문의</option>
                                <option value="취소문의" <%if(title.equals("취소문의")){%> selected<%} %>>취소문의</option>
                                <option value="결제문의" <%if(title.equals("결제문의")){%> selected<%} %>>결제문의</option>
                            </select>
                        </div>
                    </div>
                    <div style="display: flex;">
                        <div style="width: 13%; background-color: #e4e4e4; text-align: center;">
                            <div>첨부파일:</div>
                        </div>
                        <div style="width: 87%; background-color: #f5f5f5; text-align: left;">
                            <%-- 다운로드할 폴더번호 경로와 다운로드 할 파일명 전달 --%>
                            <a href="<%=contextPath%>/Qna/download.bo?path=<%=q_idx%>&fileName=<%=sfile%>">&nbsp;&nbsp;<%=sfile%></a>
                            <form enctype="multipart/form-data" action="<%=contextPath%>/Qna/updateQnaFile.do" method="POST">
                            	<input type="text" name="upq_inx" value="<%=q_idx%>" style="visibility:hidden">
                            	<input type="file" name="upfile" id="upfile">
                            	<input type="submit" value="파일 수정하기" id="upfileConfirm">
                            </form>
                        </div>
                    </div>
                    <div style="display: flex;">
                        <div style="width: 13%; background-color: #e4e4e4; text-align: center;">
                            <div>내 용</div>
                        </div>
                        <div style="width: 87%; background-color: #f5f5f5; text-align: left;">
                            &nbsp; <textarea rows="20" cols="100" name="content" id="content"><%=content%></textarea>
                        </div>
                    </div>
                </div>
            </div>
            
            
            <div style="height: 19px;"></div>
            <div style="display: flex;">
                <div style="width: 48%; text-align: right;">
                    <button type="button" id="update" class="btn btn-outline-warning" style=<%if(m_admin.equals("2")|| (m_admin.equals("1") && level>0)){%>"visibility:hidden"<%}%>>수정하기</button>&nbsp;&nbsp;
                    <button type="button" id="delete" class="btn btn-outline-danger" style=<%if((m_admin.equals("2")&& level==0)|| (m_admin.equals("1") && level>0)){%>"visibility:hidden"<%}%>>삭제하기</button>&nbsp;&nbsp;
                    <button type="button" id="reply" class="btn btn-outline-secondary" style=<%if(m_admin.equals("1")){%>"visibility:hidden"<%}%>>답변달기</button>&nbsp;&nbsp;
                </div>
                <div style="width: 10%; text-align: center;">
                    <button id="list" onclick="location.href='<%=contextPath%>/Qna/list.do?center=qna/qna.jsp&nowBlock=<%=nowBlock%>&nowPage=<%=nowPage%>'" class="btn btn-outline-primary">목록</button>
                </div>
                <div style="width: 42%;"></div>
            </div>
            <div style="height: 19px;"></div>
        </div>
    </div>
</div>
<br>
<!-- <div id="container"></div> -->
<%}%>	

<%--답변을 눌렀을때 답변을 작성할 수 있는 화면 요청 --%>
<form action="<%=contextPath%>/Qna/reply.do" id="replyForm">
	<input type="hidden" name="q_idx" value="<%=q_idx%>" id="q_idx">
	<input type="hidden" name="b_inx" value="<%=b_inx%>" id="b_idx">
	<input type="hidden" name="id" value="<%=id%>"> 
</form>

<script>
$("#reply").on("click",function(){ // 관리자 관점 : 답변달기 버튼 클릭 시 답변 달기 화면 보여주기 	
		$("#replyForm").submit();
	});

// 글 삭제 요청
$("#delete").on("click",function() {
	var result = window.confirm("정말로 글을 삭제하시겠습니까?");
	var q_idx = $("#q_idx").val();
	
	if(result==true){
		$.ajax({
			type : "post",
			async : true,
			url : "<%=contextPath%>/Qna/deleteQna.do",
			data : {q_idx : q_idx},
			dataType : "text",
			success : function(data){
				if(data=="삭제성공"){
					alert("삭제 성공");
					location.href = "<%=contextPath %>/Qna/list.do?center=qna/qna.jsp&nowBlock=0&nowPage=0"
				}else{
					alert("삭제 실패");
					document.getElementById("title").disabled = false;
					}	
				},
			error : function(){alert("비동기 통신 장애");}	
				});
			}
	}); 		

// 글 수정 요청 
$("#update").on("click", function() {
    var title = $("#title").val();
    var content = $("#content").val();
    var q_idx = $("#q_idx").val();

    $.ajax({
        type: "post",
        async: true,
        url: "<%=contextPath%>/Qna/updateQna.do",
        data: {
            title: title,
            content: content,
            q_idx: q_idx
        },
        dataType: "text",
        success: function(data) {
            if (data == "수정성공") {
                alert("수정 완료하였습니다.");
            } else { // "수정실패"
                alert("수정 실패하였습니다.");
            }
        },
        error: function() {
            alert("비동기 통신 장애");
        }
    });
});
</script>
</body>
</html>