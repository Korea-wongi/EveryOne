<%@page import="vo.ProductVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String m_id = request.getParameter("m_id");

	ProductVo vo = (ProductVo)request.getAttribute("vo");
	String p_name = vo.getP_name();
	String p_category = vo.getP_category();
	String p_color = vo.getP_color();
	String p_size = vo.getP_size();
	int p_price = vo.getP_price();
	String p_content = vo.getP_content().replace("/r/n", "<br>");
	int p_codytemp = vo.getP_codytemp(); 

	
	String i_name = request.getParameter("i_name");
	int p_idx = vo.getP_idx();
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록된 상품 수정</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>

<script type="text/javascript">
    function check() {
    	/* 상품 유효성 검사 */
        var productName = document.getElementsByName('p_name')[0].value;
        if (productName.trim() === "") {
            alert("상품 이름을 입력하세요");
            return false;
        }
        
        /* 상품 가격 유효성 검사 */
       	var priceName = document.getElementsByName('p_price')[0].value;
       	if(priceName.trim() === ""){
       		alert("상품 가격을 입력하세요");
       		return false;
       	}
       	
       	/* 상품 설명 유효성 검사 */
       	var contentName = document.getElementsByName('p_content')[0].value;
       	if(contentName.trim() === ""){
       		alert("상품 설명을 입력하세요");
       		return false;
       	}
       	
       	/* 상품 이미지 파일 유효성 검사 */
       	var fileInput = document.getElementsByName('fileName')[0];
        
        if (fileInput.files.length === 0) {
            alert("상품 이미지를 선택하세요");
            return false;
        }
        
    }
</script>
<style>
.abc {
	height: 320px;
	width: 100%;
	object-fit: cover; /* 이미지 비율 유지 */
	border-radius: 5px; /* 이미지 둥글게 처리 */
}
</style>
<body>
	<center>
		<h2>등록된 상품 수정</h2>
		
		<%-- 상품 수정 처리 요청 --%>
		<form action="<%=contextPath%>/Mypage/proUpdate.do?p_idx=<%=p_idx %>" method="post" enctype="multipart/form-data">
		
			<table width="500px" class="table table-bordered border-primary">
				<tr >
					<td rowspan="8" id="imageContainer"> <img class="abc"  alt="" src="<%=contextPath%>/productimage/<%=p_idx %>/<%=i_name%>">  </td>
					<td align="center">상품이름</td>
					<td><input type="text" name="p_name" style="width: 500px" value="<%=p_name%>"> </td>
					
				</tr>
				<tr>
					<td align="center">상품분류</td>
					<td>
						<select name="p_category">
							<option value="top" <%if(p_category.equals("top")) {%>selected<%} %>>top</option>
							<option value="bottom" <%if(p_category.equals("bottom")) {%>selected<%} %>>bottom</option>
							<option value="acc" <%if(p_category.equals("acc")) {%>selected<%} %>>acc</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center">상품색상</td>
					<td>
						<select name="p_color">
							<option value="화이트" <%if(p_color.equals("화이트")) {%>selected<%} %>>화이트</option>
							<option value="블랙" <%if(p_color.equals("블랙")) {%>selected<%} %>>블랙</option>
							<option value="베이지" <%if(p_color.equals("베이지")) {%>selected<%} %>>베이지</option>
							<option value="실버" <%if(p_color.equals("실버")) {%>selected<%} %>>실버</option>
							<option value="블루" <%if(p_color.equals("블루")) {%>selected<%} %>>블루</option>
							<option value="그레이" <%if(p_color.equals("그레이")) {%>selected<%} %>>그레이</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center">사이즈</td>
					<td>
						<select name="p_size">
							<option value="S" <%if(p_size.equals("S")) {%>selected<%} %>>S</option>
							<option value="M" <%if(p_size.equals("M")) {%>selected<%} %>>M</option>
							<option value="L" <%if(p_size.equals("L")) {%>selected<%} %>>L</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td align="center">상품금액</td>
					<td><input type="text" name="p_price" style="width: 500px" value="<%=p_price%>"></td>
				</tr>
				<tr>
					<td align="center">상품설명</td>
					<td>
						<textarea name="p_content" rows="5" cols="100" ><%=p_content %></textarea>
					</td>
				</tr>
				
				<tr>
					<td align="center">최대기온</td>
			    	<td>
			    		<select id="p_codytemp" name="p_codytemp">
							<option value="4" <%if(p_codytemp==4) {%>selected<%} %>>4℃ 이하</option>
							<option value="8" <%if(p_codytemp==8) {%>selected<%} %>>5℃~8℃</option>
							<option value="11" <%if(p_codytemp==11) {%>selected<%} %>>9℃~11℃</option>
							<option value="16" <%if(p_codytemp==16) {%>selected<%} %>>12℃~16℃</option>
							<option value="19" <%if(p_codytemp==19) {%>selected<%} %>>17℃~19℃</option>
							<option value="22" <%if(p_codytemp==22) {%>selected<%} %>>20℃~22℃</option>
							<option value="27" <%if(p_codytemp==27) {%>selected<%} %>>23℃~27℃</option>
							<option value="28" <%if(p_codytemp==28) {%>selected<%} %>>28℃ 이상</option>
						</select>
			    </tr>
				
				 
				<tr>
					<td align="center">상품 이미지</td>
			    	<td><input type="file" id="fileInput" name="fileName"></td>
			   	</tr>
				<tr>
								
					<td colspan="3" align="center">
						<button type="submit" class="btn btn-primary" onclick="return check();">수정하기</button>
					</td>
				</tr>
			</table>
		</form>
		
	</center>


<script>
	document.getElementById('fileInput').addEventListener('change', function(event) {
	    const file = event.target.files[0];
	    const reader = new FileReader();
	
	    reader.onload = function() {
	        const imageData = reader.result;
	        const imageElement = document.createElement('img');
	        imageElement.src = imageData;
	        imageElement.style.width = '100%'; // 이미지의 너비를 100%로 설정
	        imageElement.style.height = '320px'; // 이미지의 높이를 자동으로 조정
	        imageElement.style.object-fit = 'cover'; 

	        // 이전에 추가된 이미지가 있다면 제거
	        const existingImage = document.getElementById('imageContainer').querySelector('img');
	        if (existingImage) {
	            existingImage.remove();
	        }
	        
	        // 새로운 이미지 추가
	        document.getElementById('imageContainer').appendChild(imageElement);
	    };
	
	    reader.readAsDataURL(file);
	});
	
</script>
</body>
</html>