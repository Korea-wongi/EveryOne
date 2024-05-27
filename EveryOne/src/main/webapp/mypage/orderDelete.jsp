<%@page import="vo.CartVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
    
<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");
%>

<c:set var="orderList" value="${requestScope.list }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
	<link href="<%=contextPath %>/css/styles1.css" rel="stylesheet" />
	<script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<script type="text/javascript">
	//삭제 클릭 이벤트 등록
	function orderDelete(o_idx){
		var result = window.confirm("정말로 주문을 취소하시겠습니까?");
		
		if(result == true){
			location.href = "<%=contextPath%>/Mypage/orderDelete.do?o_idx="+o_idx;
		}
		
	}
</script>
<body>
<div class="card mb-4">
       <div class="card-header">
           <i class="fas fa-table me-1"></i>
        	주문 목록
       </div>
       <div class="card-body">
           <table id="datatablesSimple">
               <thead>
                   <tr>
                       <th>주문 번호</th>
                       <th>결제 금액</th>
                       <th>주문자명</th>
                       <th>배송 주소</th>
                       <th>휴대폰 번호</th>
                       <th>요청 사항</th>
                       <th>주문 취소</th>
                   </tr>
               </thead>
               <tfoot>
                   <tr>
                       <th>주문 번호</th>
                       <th>결제 금액</th>
                       <th>주문자명</th>
                       <th>배송 주소</th>
                       <th>휴대폰 번호</th>
                       <th>요청 사항</th>
                       <th>주문 취소</th>
                   </tr>
               </tfoot>
               <tbody>
           	   <c:forEach var="order" items="${orderList }">    
                   <tr>
                       <td> ${order.o_idx} </td>
                       <td> ${order.o_price} </td>
                       <td>	${order.o_name} </td>
                       <td> ${order.o_address1} </td>
                       <td> ${order.o_hp} </td>
                       <td> ${order.o_request} </td>
   					   <td> <button  class="btn btn-danger" onclick="orderDelete('${order.o_idx}');">삭제하기 </button> </td>
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