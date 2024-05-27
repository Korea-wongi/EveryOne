<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
<link href="<%=contextPath %>/css/styles1.css" rel="stylesheet" />
<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>


</head>
<body>
<%
//조회된 검색 결과물 
//JSONObject 객체 형태의 문자열 이 저장되어 있음 
//'{.........................}'
String responseStr = (String)request.getAttribute("responseString");


/*
GSon 라이브러리?
	-  Google에서 개발자 자바용 JSON라이브러리 입니다. 
	   JSON데이터를 자바 객체로 변환하거나, 반대로 자바객체를 JSON형식으로 변환하는 기능을 제공합니다.
	-   https://mvnrepository.com/artifact/com.google.code.gson/gson/2.10.1   
		gson-2.10.1.jar 다운로드 하여 복사 해 놓기 			
*/

//Gson 객체 생성
Gson gson = new Gson();

//'{       }' 문자열을   {         } 객체로 변환 해서 맵에 저장후 반환 
Map<String, Object> responseData = gson.fromJson(responseStr, Map.class);

//items 키에 해당하는 값 추출
List<Map<String, String>> items = (List<Map<String, String>>)responseData.get("items");


%>

<script type="text/javascript">
console.log(<%=responseStr %>);
</script>

	<div class="card mb-4">
       <div class="card-header">
           <i class="fas fa-table me-1"></i>
        	검색 결과
       </div>
       <div class="card-body">
           <table id="datatablesSimple">
               <thead>
                   <tr>
                       <th>title</th>
                       <th>image</th>
                       <th>link</th>
                       <th>lowprice</th>
                       <th>mallName</th>
                   </tr>
               </thead>
               <tfoot>
                   <tr>
                       <th>title</th>
                       <th>image</th>
                       <th>link</th>
                       <th>lowprice</th>
                       <th>mallName</th>
                   </tr>
               </tfoot>
               <tbody>
               <%
					//items에 포함된 각 아이템에 대해 반복
					for (Map<String, String> item : items) {
						
					    String title = item.get("title");
					    String image = item.get("image");
					    String link = item.get("link");
					    String lprice = item.get("lprice");
					    String mallName = item.get("mallName");
				%>
					<tr>
						<td width="100px"><%=title %></td>
						<td><img alt="" src="<%=image %>" style="max-width: 50px; max-height: 50px;"></td>
						<td><a href="<%=link %>" rel="noreferrer noopener">바로가기</a></td>
						<td><%=lprice %></td>
						<td><%=mallName %></td>
					</tr>
					<%} %>	
               </tbody>
           </table>
       </div>
   </div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="<%=contextPath %>/js/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
    <script src="<%=contextPath %>/js/datatables-simple-demo.js"></script> 

</body>
</html>