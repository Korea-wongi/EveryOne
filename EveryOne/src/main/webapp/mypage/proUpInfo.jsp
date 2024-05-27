<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");

	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 등록</title>
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

<body>
	<center>
		<h2>상품 등록</h2>
		
		<%-- 상품등록 처리 요청 --%>
		<form action="<%=contextPath%>/Mypage/productRegister.do?m_id=<%=m_id %>" method="post" enctype="multipart/form-data">
			
			<table width="500px" class="table table-bordered border-primary">
				<tr >
					<td rowspan="8" id="imageContainer"></td>
					<td align="center">상품이름</td>
					<td><input type="text" name="p_name" style="width: 500px"> </td>
					
				</tr>
				<tr>
					<td align="center">상품분류</td>
					<td>
						<select name="p_category">
							<option value="top">top</option>
							<option value="bottom">bottom</option>
							<option value="acc">acc</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center">상품색상</td>
					<td>
						<select name="p_color">
							<option value="화이트">화이트</option>
							<option value="블랙">블랙</option>
							<option value="베이지">베이지</option>
							<option value="실버">실버</option>
							<option value="블루">블루</option>
							<option value="그레이">그레이</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="center">사이즈</td>
					<td>
						<select name="p_size">
							<option value="S">S</option>
							<option value="M">M</option>
							<option value="L">L</option>
						</select>
					</td>
				</tr>
				
				<tr>
					<td align="center">상품금액</td>
					<td><input type="text" name="p_price" style="width: 500px"></td>
				</tr>
				<tr>
					<td align="center">상품설명</td>
					<td>
						<textarea name="p_content" rows="5" cols="100"></textarea>
					</td>
				</tr>
				
				<tr>
					<td align="center">최대기온</td>
			    	<td>
			    		<select id="p_codytemp" name="p_codytemp">
							<option value="4">4℃ 이하</option>
							<option value="8">5℃~8℃</option>
							<option value="11">9℃~11℃</option>
							<option value="16">12℃~16℃</option>
							<option value="19">17℃~19℃</option>
							<option value="22">20℃~22℃</option>
							<option value="27">23℃~27℃</option>
							<option value="28">28℃ 이상</option>
						</select>
			    </tr>
				
				<tr>
					<td align="center">상품 이미지</td>
			    	<td><input type="file" id="fileInput" name="fileName"></td>
			   </tr>
				<tr>
								
					<td colspan="3" align="center">
						<button type="submit" class="btn btn-primary" onclick="return check();">등록하기</button>
					</td>
				</tr>
			</table>
		</form>
		
	</center>


<!-- 선택된 파일 비동기로 이미지 뿌리기  -->	
<script>
	document.getElementById('fileInput').addEventListener('change', function(event) {
	  const file = event.target.files[0];
	  const reader = new FileReader();
	
	  reader.onload = function() {
	    const imageData = reader.result;
	    const imageElement = document.createElement('img');
	    imageElement.src = imageData;
	    imageElement.style.width = '489px'; // 이미지의 너비를 100%로 설정
	    imageElement.style.height = '384px'; // 이미지의 높이를 자동으로 조정
	    document.getElementById('imageContainer').innerHTML = '';
	    document.getElementById('imageContainer').appendChild(imageElement);
	  };
	
	  reader.readAsDataURL(file);
	});
</script>
</body>
</html>