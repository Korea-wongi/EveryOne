<%@page import="vo.CartVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
    
<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");
%>

<c:set var="productList" value="${requestScope.list }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
	<link href="<%=contextPath %>/css/styles1.css" rel="stylesheet" />
	<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
	
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	
</head>

<script type="text/javascript">

	function productUpdate(p_idx, i_name) {
		 var baseUrl = '<%=contextPath%>/Mypage/proUpdate';
	     var params = '?center=mypage/proupdate.jsp&p_idx=' + encodeURIComponent(p_idx) 
	    		 	  + '&i_name=' + encodeURIComponent(i_name) 
	    		 	  + '&m_id=' + encodeURIComponent('<%=m_id%>');
	     window.location.href = encodeURI(baseUrl + params);	
	}
	
	// 삭제 클릭 이벤트 등록
    function productDelete(p_idx){
	var result = window.confirm("정말로 상품을 삭제하시겠습니까?");
	
	if(result == true){//확인 버튼 클릭
		
		//비동기방식으로 글삭제 요청!
		$.ajax({
			type : "post",
			async : true,
			url : "<%=contextPath%>/Mypage/proDelete.do",
			data : {p_idx : p_idx},
			dataType : "text",
			success : function(data){
				
				if(data=="삭제성공"){
					window.alert("삭제 성공!");
						window.location.reload();
					
				}else{//"삭제실패"
					window.alert("삭제실패!");
					window.history.back()
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

</script>


<body>
<div class="card mb-4">
       <div class="card-header">
           <i class="fas fa-table me-1"></i>
        	상품 목록
       </div>
       <div class="card-body">
           <table id="datatablesSimple">
               <thead>
                   <tr>
                       <th>상품 번호</th>
                       <th>상품명</th>
                       <th>상품이미지</th>
                       <th>상품 색상</th>
                       <th>상품 사이즈</th>
                       <th>상품 카테고리</th>
                       <th>상품 가격</th>
                       <th>최대기온</th>
                       <th>수정</th>
                       <th>삭제</th>
                   </tr>
               </thead>
               <tfoot>
                   <tr>
                       <th>상품 번호</th>
                       <th>상품명</th>
                       <th>상품이미지</th>
                       <th>상품 색상</th>
                       <th>상품 사이즈</th>
                       <th>상품 카테고리</th>
                       <th>상품 가격</th>
                       <th>최대기온</th>
                       <th>수정</th>
                       <th>삭제</th>
                   </tr>
               </tfoot>
               <tbody>
           	   <c:forEach var="product" items="${productList }">    
                   <tr>
                       <td> ${product.p_idx} </td>
                       <td>	${product.p_name} </td>
                       <td><img alt="" src="<%=contextPath%>/productimage/${product.p_idx}/${product.imgVo.i_name}" style="max-width: 50px; max-height: 50px;"></td>
                       <td> ${product.p_color} </td>
                       <td> ${product.p_size} </td>
                       <td> ${product.p_category} </td>
                       <td> ${product.p_price} </td>
                       <td> ${product.p_codytemp} </td>
   					   <td> <button class="btn btn-danger" onclick="productUpdate('${product.p_idx}', '${product.imgVo.i_name}');">수정하기 </button> </td>
   					   <td> <button class="btn btn-danger" onclick="javascript:productDelete('${product.p_idx}');">삭제하기 </button> </td>
                   </tr>
               </c:forEach>
               </tbody>
           </table>
       </div>
   </div>


	  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
      <script src="<%=contextPath %>/js/scripts.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
      <script src="<%=contextPath %>/assets/demo/chart-area-demo.js"></script>
      <script src="<%=contextPath %>/assets/demo/chart-bar-demo.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
      <script src="<%=contextPath %>/js/datatables-simple-demo.js"></script>
</body>
</html>