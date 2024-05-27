<%@page import="java.util.List"%>
<%@page import="vo.MemberVo"%>
<%@page import="vo.CartVo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");
	MemberVo memberVo = (MemberVo)request.getAttribute("memberVo");
	ArrayList<CartVo> cartList= (ArrayList)request.getAttribute("list");
	
	String m_address1 = memberVo.getM_address1();
	
	int space = m_address1.indexOf(' '); // 우편번호, 주소 분리
	
	String m_email = memberVo.getM_email();
	String m_name = memberVo.getM_name();
	String m_hp = memberVo.getM_hp();
	String postcode = m_address1.substring(0, space); // 우편번호
	String m_address = m_address1.substring(space+1).trim(); // 주소
	String p_name = null;
	String c_pmid = null;
	int totalPrice = 0;
	
    

%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
 	<!-- iamport.payment.js -->
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
	
</head>

<script type="text/javascript">

	function cartDelete(cart_idx) {
		var result = window.confirm("장바구니를 삭제하시겠습니까?");
		var m_id = '<%=m_id%>';
		
		if(result == true){
			location.href = '<%=contextPath%>/Mypage/cartDelete.do?cart_idx='+cart_idx+'&m_id='+m_id;	
		}
		
	}
</script>


<body>

	<div class="card mb-4">
       <div class="card-header">
           <i class="fas fa-table me-1"></i>
          장바구니 목록
       </div>
       <div class="card-body">
           <table class="table">
               <thead>
                   <tr>
                       <th>장바구니 번호</th>
                       <th>상품 번호</th>
                       <th>판매자 명</th>
                       <th>상품 이름</th>
                       <th>선택 수량</th>
                       <th>상품 가격</th>
                       <th>장바구니 취소</th>
                   </tr>
               </thead>
               <tbody>
			<%
			    for(int i=0; i<cartList.size(); i++){
					CartVo cartVo = (CartVo)cartList.get(i);
					// 상품 이름 누적
					p_name += " " + cartVo.getC_name();
					c_pmid += " " + cartVo.getC_pmid();
					totalPrice += cartVo.getC_price();

			%>
				<tr >
					<td width="500px"> <%=cartVo.getC_idx() %> </td>
                	<td> <%=cartVo.getC_pidx() %> </td>
                	<td id="c_pmid_<%=i%>"> <%=cartVo.getC_pmid() %> </td>
                	<td id="c_pname_<%=i%>"> <%=cartVo.getC_name() %> </td>
                	<td>
					    <select name="c_amount" id="c_amount_<%=i%>" class="form-select" aria-label="Default select example">
					        <option value="1" selected="selected">1</option>
					        <option value="2">2</option>
					        <option value="3">3</option>
					        <option value="4">4</option>
					        <option value="5">5</option>
					        <option value="6">6</option>
					    </select>
					</td>
					
				<input id="i_price_<%=i %>" type="hidden" value="<%= cartVo.getC_price() %>">
			    <td id="c_price_<%=i%>">
			     	<%= cartVo.getC_price() %> 
			     </td>
			     
			    <td> <button class="btn btn-danger" onclick="cartDelete('<%=cartVo.getC_idx() %>');">삭제하기</button> </td>
			    </tr>
			    
<%
			    }
%>
			
		   <!-- 상품 이름 null 값 뺀 코드  -->
<%
		   		int abc = p_name.indexOf(' ');
		   		p_name = p_name.substring(abc + 1);
		   		c_pmid = c_pmid.substring(abc + 1);
%>
				<tr>
				 <!-- 결제 정보 컨트롤러 전송, 결제 정보  -->       
			    <form action="<%=contextPath %>/Mypage/payment.do?center=mypage/mypage1.jsp" name="frm">
			    	<input type="hidden" id="merchant_uid" name="merchant_uid">
			   		<input type="hidden" id="p_name" name="p_name" value="<%=p_name%>">
			   		<input type="hidden" id="amount" name="amount" value="<%=totalPrice %>">
					<input type="hidden" id="buyer_email" name="buyer_email" value="<%=m_email%>">
			  		<input type="hidden" id="buyer_name" name="buyer_name" value="<%=m_name%>">
					<input type="hidden" id="buyer_tel" name="buyer_tel" value="<%=m_hp%>">
					<input type="hidden" id="buyer_addr" name="buyer_addr" value="<%=m_address%>">
					<input type="hidden" id="buyer_postcode" name="buyer_postcode" value="<%=postcode%>">
					<input type="hidden" id="m_id" name="m_id" value="<%=m_id%>">
					<input type="hidden" id="c_pmid" name="c_pmid" value="<%=c_pmid%>">
					<div class="input-group">
					  <span class="input-group-text">배송 요청사항</span>
					  <textarea id="area_request" class="form-control" aria-label="With textarea" name="input_request"></textarea>
					</div>
				</form>
				</tr>
               </tbody>
           </table>
		</div>
   </div>
	
   <!-- 결제버튼 -->
   <img src="<%=contextPath%>/images/kakaoimage/payment_icon_yellow_medium.png" 
   		style="cursor: pointer;"
   		onclick="requestPay()">
   
   
	<script>
    var totalPrice = <%= totalPrice %>;
    var p_name = document.getElementById("p_name").value;  
    var amount = parseInt(document.getElementById("amount").value); 
    var buyer_email = document.getElementById("buyer_email").value; 
    var buyer_name  = document.getElementById("buyer_name").value; 
    var buyer_tel = document.getElementById("buyer_tel").value; 
    var buyer_addr = document.getElementById("buyer_addr").value; 
    var buyer_postcode = document.getElementById("buyer_postcode").value; 
    var c_pmid = document.getElementById("c_pmid").value; 

    var IMP = window.IMP; 
    IMP.init("발급 키");
    var today = new Date();   
    var hours = today.getHours(); // 시
    var minutes = today.getMinutes();  // 분
    var seconds = today.getSeconds();  // 초
    var milliseconds = today.getMilliseconds();
    var makeMerchantUid = hours +  minutes + seconds + milliseconds;
    
    
    function requestPay() {
        IMP.request_pay({
            pg : 'kakaopay',
            pay_method : 'kakaopay',
            merchant_uid: "IMP"+makeMerchantUid, 
            name : p_name, 
            amount : totalPrice,
            buyer_email : buyer_email,
            buyer_name : buyer_name,
            buyer_tel : buyer_tel,
            buyer_addr : buyer_addr,
            buyer_postcode : buyer_postcode,
            display: {
                card_quota: [3]  // 할부개월 3개월까지 활성화
            }
        	}, 
        	
        	function (rsp) { // callback
            if (rsp.success) {
            	
            	document.getElementById("merchant_uid").value = rsp.merchant_uid
            	document.getElementById("p_name").value = rsp.name;
            	document.getElementById("amount").value = rsp.paid_amount;
            	document.getElementById("buyer_email").value = rsp.buyer_email;
            	document.getElementById("buyer_name").value = rsp.buyer_name;
            	document.getElementById("buyer_tel").value = rsp.buyer_tel;
            	document.getElementById("buyer_addr").value = rsp.buyer_addr;
            	document.getElementById("buyer_postcode").value = rsp.buyer_postcode;
            	
             	document.frm.submit();
            } else {
            }
        });
    };
    
		// 선택 수량 변경시(상품 가격 * 수량 동적으로 변경),(누적 가격 동적으로 변경)
	    $('.form-select').change(function() {
			// 수량 값 가져오기	    	
	        var selectedAmount = $(this).val();
			// 반복문 i 값 가져오기
	        var index = $(this).attr('id').split('_')[2];
			// i의 가격 가져오기
	        var price = $('#c_price_'+index).text().trim();
			// 초기 가격으로 초기화
	        var defaultValue = document.getElementById("i_price_"+index).defaultValue;
	        document.getElementById('c_price_' + index).innerHTML = defaultValue;
	        
	        // 초기 가격 * 수량
	        updatedPrice = selectedAmount * defaultValue;
	        
	        // 가격 변경
	        document.getElementById('c_price_' + index).innerHTML = updatedPrice;
	        totalPrice += (updatedPrice - price);
	        document.getElementById("totalPrice").innerHTML = totalPrice;
	        
	    });
	</script>

</body>
</html>
