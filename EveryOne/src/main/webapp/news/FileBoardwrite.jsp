
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<% 
	request.setCharacterEncoding("UTF-8");
	String contextPath = request.getContextPath();
	String nowPage = request.getParameter("nowPage");
	String nowBlock = request.getParameter("nowBlock");
	String n_name = "관리자";
%>
 
<%
	String m_id = (String)session.getAttribute("m_id");
	if(m_id == null){//로그인 하지 않았을경우
%>		
	<script>	
		alert("로그인 하고 글을 작성하세요!"); 
		history.back(); 
 	</script>
<% 	}%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="stylesheet" type="text/css" href="/MVCBoard/style.css"/>
<title>Insert title here</title>

<!-- SmartEditor를 사용하기 위해서 다음 js파일을 추가 (경로 확인) -->
<script type="text/javascript" src="<%=contextPath%>/SE2/js/HuskyEZCreator.js" charset="utf-8"></script>
<!-- jQuery를 사용하기위해 jQuery라이브러리 추가 -->
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>

	<script type="text/javascript">
			var oEditors = [];
			
			$(function(){
			      nhn.husky.EZCreator.createInIFrame({
			          oAppRef: oEditors,
			          elPlaceHolder: "ir1", //textarea에서 지정한 id와 일치해야 합니다. 
			          //SmartEditor2Skin.html 파일이 존재하는 경로
			          sSkinURI: "/Test/SE2/SmartEditor2Skin.html",  
			          htParams : {
			              // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			              bUseToolbar : true,             
			              // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			              bUseVerticalResizer : true,     
			              // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			              bUseModeChanger : true,         
			              fOnBeforeUnload : function(){
			                   
			              }
			          }, 
			          fOnAppLoad : function(){
			              //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
			              oEditors.getById["ir1"].exec("PASTE_HTML", ["기존 DB에 저장된 내용을 에디터에 적용할 문구"]);
			          },
			          fCreator: "createSEditor2"
			      });
			      
			      //저장버튼 클릭시 form 전송
			      $("#save").click(function(){
			          oEditors.getById["ir1"].exec("UPDATE_CONTENTS_FIELD", []);
			          $("#frm").submit();
			      });    
			});
</script>


</head>
<body>
<form id="frm" action="<%=contextPath%>/FileBoard/writePro.do" method="post" enctype="multipart/form-data">
<table width="90%" border="0" cellspacing="0" cellpadding="0">
  <tr height="40"> 
    <td width="41%" style="text-align: left"> &nbsp;&nbsp;&nbsp; 
    	<img src="<%=contextPath%>/images/newsimages/board02.gif" width="150" height="30">
    </td>
    <td width="57%">&nbsp;</td>
    <td width="2%">&nbsp;</td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center"><img src="<%=contextPath%>/news/images/line_870.gif" width="870" height="4"></div></td>
  </tr>
  <tr> 
    <td colspan="3"><div align="center"> 
        <table width="95%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="20" colspan="3"></td>
          </tr>
          <tr> 
            <td colspan="3" valign="top">
            <div align="center"> 
                <table width="100%" height="373" border="0" cellpadding="0" cellspacing="1" class="border1">
                  <tr> 
                    <td width="13%" height="29" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">작 성 자</div>
                    </td>
                    <td width="34%" bgcolor="#f5f5f5" style="text-align: left">
                    															<!-- 자바 표현식으로 value에 name변수 들어있었음 -->
                    	<input type="text" name="writer" size="20" class="text2" value="관리자" readonly />
                    </td>
                    <td width="13%" height="29" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">아 이 디</div>
                    </td>
                       
                    <td width="34%" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="text" name="m_id" size="20" class="text2" value="<%=m_id%>" readonly/>
                    </td>
                   </tr>
                   
                   <!-- 메일주소 지움 -->
                             
                  <tr> 
                    <td height="31" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">제&nbsp;&nbsp;&nbsp;목</div>
                    </td>
                    <td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="text" name="n_title" size="70" id="n_title"/>
                    	<span id="n_titleInput"></span>
                    </td>
                  </tr>
                  <tr> 
                    <td height="31" bgcolor="#e4e4e4" class="text2">
                    	<div align="center">첨부파일 선택</div>
                    </td>
                    <td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
                    	<input type="file" name="n_sfile" size="70"/>
                    </td>
                  </tr>
                  <tr> 
                    <td bgcolor="#e4e4e4" class="text2">
                    	<div align="center">내 &nbsp;&nbsp; 용</div>
                    </td>
                    <td colspan="3" bgcolor="#f5f5f5" style="text-align: left">
                    	<textarea name="n_content" rows="15" cols="100" id="ir1"></textarea>
                    	<span id="n_contentInput"></span>
                    	
                    </td>
                  </tr>
                </table>
              </div>
              </td>
          </tr>
          <tr> 
            <td colspan="3">&nbsp;</td>
          </tr>
          <tr> 
          
            <td width="48%">
            <!-- 등록 버튼 -->
            <div align="right">
            	<button type="submit" class="btn btn-outline-success" onclick="return check();">글 등록</button>
            </div>
            </td>
            
            <td width="10%">
            <!-- 목록보기 -->
            <!-- 클릭 이벤트 등록 -->
            <div align="center">
            	<a href="" id="list">
            		<button class="btn btn-outline-primary">목록</button>
            	</a>
            </div>
            </td>
            <td width="42%" id="resultInsert"></td>
          </tr>
          
        </table>
      </div></td>
  </tr>
</table>
</form>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<%-- 글 등록 유효성 검사 체크 --%>
	<script src="<%=contextPath %>/js/join3.js"></script>
	
	<script type="text/javascript">
		
		$("#list").click(function(event) {
			event.preventDefault();
			//board테이블에 저장된 글을 조회 하는 요청!
			location.href = "<%=contextPath%>/FileBoard/list.do?nowPage=<%=nowPage%>&nowBlock=<%=nowBlock%>";
			
		});
		
		
	</script>
</body>
</html>