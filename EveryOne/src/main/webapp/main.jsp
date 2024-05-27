<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Mustache Enthusiast</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
	<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" rel="stylesheet">
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/5.1.3/css/bootstrap.min.css" rel="stylesheet">
<style>
body {
	font-family: 'Roboto', sans-serif;
}

.content-wrapper {
	display: flex;
	justify-content: space-between;
}

.container {
	width: 45%;
	margin: 20px;
}

.evt-map {
	height: 300px;
	border: solid 1px #eee;
}

.text-content {
	padding: 20px;
	background-color: #f9f9f9;
	border: 1px solid #ddd;
	border-radius: 4px;
	box-shadow: 0 2px 4px rgba(0,0,0,0.1);
	margin: 20px;
	font-size: 16px;
	line-height: 1.6;
}

#gallery_wrap {
	margin: 0 auto;
	width: 640px;
}
</style>
<script>
	/*슬라이드 이미지*/
	$(function() {
		$('.slide_gallery').bxSlider({
			auto : true, // 자동으로 애니메이션 시작
			speed : 500, // 애니메이션 속도
			pause : 3000, // 애니메이션 유지 시간 (1000은 1초)
			mode : 'horizontal', // 슬라이드 모드 ('fade', 'horizontal', 'vertical' 이 있음)
			autoControls : false, // 시작 및 중지버튼 보여짐
			pager : true, // 페이지 표시 보여짐
			captions : true, // 이미지 위에 텍스트를 넣을 수 있음
			slideWidth : 1260, // 크기
			slideMargin : 0,
			autoDelay : 0,
			responsive : true,
			controls : true,
		});
	});
</script>
</head>
<body>
	<div id="body">		
		<div id="gallery_wrap">
				<ul class="slide_gallery">
					<li><img src="<%=contextPath %>/clothimage/header-image-bag.jpg" alt="사진1"></li>
					<li><img src="<%=contextPath %>/clothimage/header-image-bag.jpg" alt="사진2"></li>
					<li><img src="<%=contextPath %>/clothimage/header-image-bag.jpg" alt="사진3"></li>
					<li><img src="<%=contextPath %>/clothimage/header-image-bag.jpg" alt="사진4"></li>
				</ul>
			</div>
		<ul>
			<li>
				<a href="#">
					<img src="<%=contextPath %>/images/the-father.jpg" alt="">
				</a>
			</li>
			<li>
				<a href="#">
					<img src="<%=contextPath %>/images/the-actor.jpg" alt="">
				</a>
			</li>
			<li>
				<a href="#">
					<img src="<%=contextPath %>/images/the-nerd.jpg" alt="">
				</a>
			</li>
		</ul>
	</div>
	
</body>
</html>