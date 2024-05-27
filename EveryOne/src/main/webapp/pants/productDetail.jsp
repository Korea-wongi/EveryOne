<%@page import="vo.ProductVo"%>
<%@page import="vo.MemberVo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String contextPath = request.getContextPath();

	/* pants.jsp 에서 받은 상품 정보, 회원 id */
	String m_id = (String)request.getAttribute("m_id");
	String p_idx = (String)request.getAttribute("p_idx");
	String p_name = (String)request.getAttribute("p_name");
	String p_price = (String)request.getAttribute("p_price");
	String p_size = (String)request.getAttribute("p_size");
	String p_content = (String)request.getAttribute("p_content");
	String i_name = (String)request.getAttribute("i_name");
	boolean result = (Boolean)request.getAttribute("result");
	
	//관리자 판별 및 페이지컨텍스트등록
	String session_m_id=(String)session.getAttribute("m_id");
	String m_admin=(String)session.getAttribute("m_admin");
	pageContext.setAttribute("m_admin", m_admin);
	
	//true인 경우 구매함, false인 경우 구매안함
	boolean buyId = (boolean)request.getAttribute("buyId"); 
%>
<c:set var="memberVo" value="${requestScope.memberVo}" />
<c:set var="productVo" value="${requestScope.productVo}" />

<c:if test="${not empty memberVo}">
    <c:set var="m_address1" value="${memberVo.m_address1}" />
    <c:set var="space" value="${fn:indexOf(m_address1, ' ')}" />
    <c:choose>
        <c:when test="${space ne -1}">
            <c:set var="postcode" value="${fn:substring(m_address1, 0, space)}" />
            <c:set var="m_address" value="${fn:substring(m_address1, space+1, fn:length(m_address1))}" />
        </c:when>
        <c:otherwise>
            <c:set var="postcode" value="${m_address1}" />
            <c:set var="m_address" value="" />
        </c:otherwise>
    </c:choose>
    <c:set var="m_address" value="${fn:trim(m_address)}" />
</c:if>

<!DOCTYPE html>
<html lang="zxx">
<head>
	<meta charset="UTF-8">
	<title>상품 상세 정보</title>
	<link rel="stylesheet" href="<%=contextPath %>/css/productDetail.css?after2">
	<link rel="stylesheet" href="<%=contextPath %>/css/productDetail2.css">
	<script type="text/javascript" src="<%=contextPath %>/js/productDetail.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>

	<!-- iamport.payment.js -->
	<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
	
</head>

	<body>
		<!-- Product Shop Section Begin -->
		<section class="product-shop spad page-details">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<div class="row">
						
							<div class="col-lg-6" id="image">
								<div class="product-pic-zoom" >
									<ul class="slideImage">
										<li><img src="<%=contextPath %>/productimage/<%=p_idx %>/<%=i_name%>"></li>
										<li><img src="<%=contextPath %>/productimage/<%=p_idx %>/<%=i_name%>"></li>
									</ul>	
								</div>
							</div>
							
							<div class="col-lg-6" id="b">
								<div class="product-details">
									<div class="pd-title">
										<input type="hidden" value="" id="prod_num">
										<h2 style="margin-bottom: 50px"><%=p_name %></h2>
									</div>
									
									
									<div class="pd-tags">
	 				 					<dl class="list">
											<dt class="title">가격</dt>
											<dd class="description "><span class="finalPrice"> <%=p_price %></span> 원</dd>
										</dl>
										<hr>
										<dl class="list">
											<dt class="title">배송구분</dt>
											<dd class="description">택배배송</dd>
										</dl>
										<hr>
										<dl class="list">
											<dt class="title">원산지</dt>
											<dd class="description">국내산</dd>
										</dl>
										<hr>
										
										<div class="top-info-size clearfix">
											<dt class="title">SIZE</dt>
		 			 							<select class="select-form" id="sizeOption"> 
													<option>사이즈 S</option>
													<option>사이즈 M</option>
													<option>사이즈 L</option>																					
												</select> <span class="select-icon"> </span> 
											<hr>
											
											<dl class="list">
												<div class="quantity">
													<dt class="title">구매수량</dt>
													<div class="pro-qty">
														<button type="button" class="minus" id="minus" onclick="del()">-</button>
														<input type="hidden" id="sell_price" style="width:30px;" value="<%=p_price %>" >
														<input type="text" id="amounts" style="width:20px; border: none; outline: none;" value="1" readonly="readonly">
														<button type="button" class="plus" id="plus" onclick="add()">+</button>
														<input type="hidden" name="sum" size="11" readonly>
													</div>
													<br>
												</div>
											</dl>
											<dl class="list">
												<dt class="title">배송 요청사항</dt>
												<dd class="description"> <textarea id="area_request" class="form-control" aria-label="With textarea" name="input_request"></textarea></dd>
											</dl>
										</div>
										
										
										<div class="pd-desc">
											<h5 class="priceText">
												총 상품금액 :
												<span id="sum"><fmt:formatNumber value="<%=p_price %>" pattern="#,###" /></span>
												원
											</h5>
										</div>

									</div>
									
									
									<div class="quantity justify-content-end" id="cartBuy" style="display: flex;">
                                    <input type="hidden" id="Wishlist" value="<%=result%>">
                        <%
                        			if(m_id.equals("null") || m_id.trim() == "" || m_id == null){ // 아이디 없으면 로그인 화면 요청
                        %>				
                       					<button id="icon_heart_alt" style=" border: none; outline: none; background-color: white;"
									        onclick="toggleWishlistBtn(null, <%=p_idx%>);">
									        <i class="far fa-heart fa-2xl"></i>
									    </button>
                        
                        <% 				
                        			}else{	// 로그인 상태일 때
                        				if(result == false){ // 찜 목록이 없으면
                        %>
										  <button class="icon_heart_alt" style=" border: none; outline: none; background-color: white;"
									        onclick="toggleWishlistBtn('<%=m_id %>', <%=p_idx%>);" >
									        <i class="far fa-heart fa-2xl" id="wishlistBtn"></i>
									     </button>                      
                        <% 				
                        				}else{ // 찜 목록이 있으면 
                        %>
                        				<button class="icon_heart"  style=" border: none; outline: none; background-color: white;"
									        onclick="toggleWishlistBtn('<%=m_id %>', <%= p_idx %>);">
									        <i class="fas fa-heart fa-2xl" id="wishlistBtn"></i>
								        </button>	
                        
                        <% 					
                        				}
                        			}
                        %>
                                    
                                    
                        <%
                        			if(p_idx.equals("0")){
                        %>
                        				<button class="primary-btn pd-cart soldout" id="cartBtn" disabled>상품 준비 중입니다.</button>
                        <% 
                        			}else{
                        				if(m_id.equals("null") || m_id.trim() == "" || m_id == null){
                        %>
                                          <button class="primary-btn pd-cart" id="cartBtn" onclick="return askLogin();">장바구니 담기</button>
                        <% 					
                        				}else{
                        %>
                        				<form action="<%=contextPath%>/Mypage/cartInsert.do">
									       <input type="hidden" name="m_id" value="<%=m_id%>">
									       <input type="hidden" name="p_idx" value="<%=p_idx%>">
									       <input type="hidden" name="c_amount" value="1" id="c_amount">
									       <input type="hidden" name="p_price" value="<%=p_price%>">
									       <input type="hidden" name="p_name" value="<%=p_name%>">										       
										   <button class="primary-btn pd-cart" id="cartBtn" 
										   		   type="submit" >장바구니 담기</button>
										 </form>
                        <% 					
                        				}
                        			}
                        %>            
                        				 <!-- 결제 정보 컨트롤러 전송, 결제 정보  -->       
									    <form action="<%=contextPath %>/Mypage/payment.do?center=mypage/mypage1.jsp" name="frm">
									    	<input type="hidden" id="merchant_uid" name="merchant_uid">
									   		<input type="hidden" id="p_name" name="p_name" value="<%=p_name%>">
									   		<input type="hidden" id="amount" name="amount" >
											<input type="hidden" id="buyer_email" name="buyer_email" value="${memberVo.m_email }">
									  		<input type="hidden" id="buyer_name" name="buyer_name" value="${memberVo.m_name }">
											<input type="hidden" id="buyer_tel" name="buyer_tel" value="${memberVo.m_hp }">
											<input type="hidden" id="buyer_addr" name="buyer_addr" value="${m_address }">
											<input type="hidden" id="buyer_postcode" name="buyer_postcode" value="${m_postcode }">
											<input type="hidden" id="m_id" name="m_id" value="<%=m_id%>">
											<input type="hidden" id="c_pmid" name="c_pmid" value="${productVo.p_mid }">
											<input type="hidden" id="hidden_input_request" name="input_request">
										</form>	
                        			
                        				<button class="primary-btn pd-buy" id="buyBtn" onclick="requestPay()">바로구매</button>
								</div>
								</div>
							</div>
						</div>
						<br> <br>
					</div>
				</div>
			</div>
		</section>
		
		<!-- 상품 메뉴바 -->
		<div class="row detailNav">
			<div class="col-12 d-flex justify-content-center">
				<ul class="link p-0" style="height: 100px;">
					<span class="select"> <a href="#clothesDetail">Detail
							View</a>
					</span>
					<span>&nbsp;&nbsp;/&nbsp;&nbsp;</span>
					<span class="seller_detail"> <a href="#brand">Brand info</a>
					</span>
					<span>&nbsp;&nbsp;/&nbsp;&nbsp;</span>
					<span class="clothes_review"> <a href="#review">Review</a>
					</span>
				</ul>
			</div>
		</div>
<!-- 기업설명 -->		
		<div class="container-explain d-flex flex-column justify-content-center"
			 style="padding: 10%;">
			<div class="row contentExplain">
				<div class="col-md-12 d-flex justify-content-center">
					<img style="width: 70%" src="<%=contextPath%>/clothimage/detailAll.jpg">
				</div>
				<div class="col-md-12 d-flex justify-content-center">
					<p style="font-size: 15px;">고객님께 늘 감동을 선사하는<br>
												OO샵입니다.<br>
												저희 쇼핑몰은 색다른 디자인과 스타일로 늘 일상속 새로움을 추구합니다
					</p>
				</div>
			</div>
		</div>
		
		<!-- 상품설명 -->
		<div
			class="container-explain d-flex flex-column justify-content-center">
			<div class="row contentExplain">
				<div class="col-md-12 d-flex justify-content-center">
					<p style="font-size: 30px; font-weight: bold;"><%=p_name%></p>
				</div>
				<div class="col-md-12 d-flex justify-content-center">
					<p style="font-size: 15px;">상품 설명</p>
				</div>
				<div class="col-md-12 d-flex justify-content-center">
					<p style="font-size: 20px; max-width: 700px;"><%=p_content%></p>
				</div>
			</div>
		</div>
		
		<!-- 상품사진 -->
		<div class="row clothesDetail" id="clothesDetail">
				<div class="col-md-12 p-2 d-flex justify-content-center">
					<img src="<%=contextPath%>/productimage/<%=p_idx %>/<%=i_name%>" style="width: 40%;">
				</div>
				<div class="col-md-12 p-2 d-flex justify-content-center">
					<img src="<%=contextPath%>/clothimage/상세컷.PNG" style="width: 40%; padding-top: 10%;">
				</div>				
		</div>
		
		<!-- 상품 정보 -->
		<div class="brand" id="brand">
			<div class="row brand_title d-flex justify-content-center">
				<div class="col-md-12 p-0">
					<p>
						<b style="font-size: 20px; font-weight: bold;">상품 추가정보</b>
					</p>
				</div>
			</div>
			<div class="row brand_detail1">
				<div class="col-md-2 brand_info">
					<p>색상</p>
				</div>
				<div class="col-md-4 info_detail">
					<p>사진참조</p>
				</div>
				<div class="col-md-2 brand_info">
					<p>제조사</p>
				</div>
				<div class="col-md-4 info_detail">
					<p>한국</p>
				</div>
			</div>
			<div class="row brand_detail2">
				<div class="col-md-2 brand_info">
					<p>제조국</p>
				</div>
				<div class="col-md-4 info_detail">
					<p>한국</p>
				</div>
				<div class="col-md-2 brand_info">
					<p>세탁방법</p>
				</div>
				<div class="col-md-4 info_detail">
					<p>드라이클리닝 추천</p>
				</div>
			</div>
			<div class="row brand_detail3">
				<div class="col-md-2 brand_info">
					<p>수입여부</p>
				</div>
				<div class="col-md-4 info_detail">
					<p>N</p>
				</div>
				<div class="col-md-2 brand_info">
					<p>제조년월</p>
				</div>
				<div class="col-md-4 info_detail">
					<p>2022년 5월 이후</p>
				</div>
			</div>
					<p style="font-size: 11px;">색상은 모니터의 해상도,밝기 휴대전화 기종에 따라 약간 차이가 있을 수 있습니다</p>							
		</div>
		
		<%-- 배송,환불 안내 이미지 --%>	
		<img  src="<%=contextPath%>/clothimage/Design2.jpg">	

		<!-- 브랜드 설명 -->
		<div class="detail" id="detail">
			<div class="row seller">
				<div class="col-md-12 m-auto">
					<div
						style="font-size: 20px; font-weight: bold; padding: 14px 0 20px 0;">판매자정보</div>
				</div>
			</div>
			<div class="row detail_info1">
				<div class="col-md-4 detail_title">
					<div>
						<p>상호 / 대표자</p>
					</div>
				</div>
				<div class="col-md-8 seller-detail">
					<div>
						<p>주식회사 HYPE / 이호준</p>
					</div>
				</div>
			</div>
			<div class="row detail_info2">
				<div class="col-md-4 detail_title">
					<p>브랜드</p>
				</div>
				<div class="col-md-8 seller-detail">
					<p>HYPE</p>
				</div>
			</div>
			<div class="row detail_info3">
				<div class="col-md-4 detail_title">
					<p>사업자번호</p>
				</div>
				<div class="col-md-8 seller-detail">
					<p>1111111111</p>
				</div>
			</div>
			<div class="row detail_info4">
				<div class="col-md-4 detail_title">
					<p>통신판매업신고</p>
				</div>
				<div class="col-md-8 seller-detail">
					<p>제2022-서울당산</p>
				</div>
			</div>
			<div class="row detail_info5">
				<div class="col-md-4 detail_title">
					<p>연락처</p>
				</div>
				<div class="col-md-8 seller-detail">
					<p>02-777-7777</p>
				</div>
			</div>
			<div class="row detail_info6">
				<div class="col-md-4 detail_title">
					<p>E-mail</p>
				</div>
				<div class="col-md-8 seller-detail">
					<p>HYPE@hype.co.kr</p>
				</div>
			</div>
			<div class="row detail_info7">
				<div class="col-md-4 detail_title">
					<p>영업소재지</p>
				</div>
				<div class="col-md-8 seller-detail">
					<p>서울 영등포구 선유동2로 57 이레빌딩딩</p>
				</div>
			</div>
			<div class="row detail_info8">
				<div class="col-md-12">
					<ul style="font-size: 0.3rem; opacity: 0.6; margin-top: 1rem;">
						<li>본 상품 정보의 내용은 공정거래위원회 ‘상품정보제공고시’ 에 따라 판매자가 직접 등록한 것으로 해당
							정보에 대한 책임은 판매자에게 있습니다.</li>
					</ul>

				</div>
			</div>
		</div>
		
		<!-- 리뷰 영역 -->
		<div class="review" id="review">
			<div class="row reviewTableHead d-flex justify-content-start">
				<div class="reviewHead col-md-6 pb-4 ps-4">
					<span style="font-weight:bold; font-size:25px;">상품 리뷰</span>
				</div>
				<c:choose>
					<c:when test="${reviewList.size() == 0}">
						<div class="reviewContent col-md-12 d-flex justify-content-center">
							<table class="table">
								<thead>
									<tr>
										<th scope="row">ID</th>
										<th scope="row">날짜</th>
										<th scope="row">제목</th>
										<th scope="row">내용</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th scope="row" style="text-align:end;">등록된 리뷰가 없습니다.</th>
										<td></td>
										<td></td>
										<td></td>
									</tr>

								</tbody>
							</table>
						</div>
					</c:when>
					<c:otherwise>
						<div class="reviewContent col-md-12 d-flex justify-content-center">
							<table class="table">		
								<tbody>
									<!-- 모달 오픈 티알-->
									<tr>
										<th scope="row">ID</th>
										<th scope="row">날짜</th>
										<th scope="row">제목</th>
										<th scope="row">내용</th>
									</tr>
									<c:forEach items="${reviewList}" var="reviewList">
										<tr id="reviewOpen" onclick="javascript:shopReview('${reviewList.getR_idx()}');"  data-bs-toggle="modal" data-bs-target="#reviewModal" >
																<!-- 아이디 -->
											<td>${reviewList.getR_mid()}</th>
											<td>${reviewList.getR_date()}</td>
											<td>${reviewList.getR_title()}</td>
											<td>${reviewList.getR_content()}</td>													
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:otherwise>
				</c:choose>
			</div>


			<!-- 리뷰 조회 모달 수정본 -->
			<div class="modal fade" id="reviewModal" tabindex="-1" aria-labelledby="reviewModalLabel" aria-hidden="true">
				    <div class="modal-dialog modal-lg">
				        <div class="modal-content">
				            <div class="modal-header">
				                <h5 class="modal-title" id="reviewModalLabel">리뷰 조회</h5>
				                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="reviewClose"></button>
				            </div>
				            <div class="modal-body" style="display: flex;">
				                <!-- 이미지 -->
				                <div id="reviewImgDiv" style="max-width: 50%;"></div>
				                <!-- 입력 요소들 -->
				                <div class="form-wrapper" style="flex: 1;">
				                    <form>
				                        <input class="form-control mb-3" type="text" name="r_mid" id="openr_mid" value="" disabled>
				                        <input class="form-control mb-3" type="text" name="r_title" id="openr_title" value="" disabled>
				                        <textarea class="form-control" id="openr_content" name="r_content" style="height: 300px; resize: none" disabled></textarea>
				                    </form>    
				                        <div class="modal-footer">
				                            <button type="submit" id="reviewDelete" class="btn btn-secondary" data-bs-dismiss="modal" style="visibility:hidden"  onclick="javascript:deletePro(review_num);">삭제하기</button>
				                        </div>
				                    
				                </div>
				            </div>
				        </div>
				    </div>
				</div>

			<div class="row m-2">
			<!-- 리뷰 작성 모달 -->
			<c:choose>
			<c:when test="${buyId}">
				<div class="btnReview col-md-12 d-flex justify-content-end mb-4">
					<button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal" id="write" >작성하기</button>
				</div>

				<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
					aria-hidden="true">
					<div class="modal-dialog" style="max-width: 100%; width: 600px; display: table;">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">리뷰 작성</h5>
								<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form id="reviewForm" action="<%=contextPath%>/Review/write.do" method="post" enctype="multipart/form-data" >
																						<!-- 상품번호 -->
									<input type="hidden" name="p_idx" id="p_idx" value="<%=p_idx%>">
																											<!-- 멤버 아이디 disabled속성 추가하면 값 전달이 안됨 readonly로 -->
									<input class="form-control" type="text" name="r_mid" id="r_mid" value="<%=m_id %>" readonly="readonly">
									<input class="form-control" type="text" name="r_title" id="r_title" placeholder="제목을 입력해주세요." >
									<br>
									<div class="mb-3">
										<!-- 파일 업로드 -->
										<input type="file" name="r_sfile" size="70"/>
										<textarea class="form-control" placeholder="내용을 입력해주세요." id="r_content" name="r_content"
											style="height: 300px; resize: none"></textarea>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
										<button type="submit" class="btn btn-primary" id="btnReview">작성완료</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					</div>
					</c:when>
				</c:choose>
			</div>
		</div>

		<!-- 맨위로 올라가기 -->
		<div class="top" style="position: fixed; bottom: 30px; right: 3%;">
			<a href="#header"><i class="fa-solid fa-circle-arrow-up fa-3x"></i></a>
		</div>
		
		<!-- Footer-->
		<footer class="footer bg-light mt-5">
			<div class="container">
				<div class="row">
					<div class="col-lg-6 h-100 text-center text-lg-start my-auto">
						<ul class="list-inline mb-2">
							<li class="list-inline-item"><a href="#">COMPANY</a></li>
							<li class="list-inline-item">⋅</li>
							<li class="list-inline-item"><a href="<%=contextPath %>/KakaoMap.jsp">매장찾기</a></li>
							<li class="list-inline-item">⋅</li>
							<li class="list-inline-item"><a href="#">고객센터</a></li>
							<li class="list-inline-item">⋅</li>
							<li class="list-inline-item">
								<p style="color: red; font-weight: bold;">개인정보처리방침</p>
							</li>

						</ul>
						<p class="text-muted small mb-4 mb-lg-0">하잇프랜드(주) 대표 : 이호준 | 개인정보관리책임자 : 김영완 | 사업자등록번호 : 22-02-22</p>
						<p class="text-muted small mb-4 mb-lg-0">주소 : 부산광역시 부산진구 신천대로50번길 79</p>
						<p class="text-muted small mb-4 mb-lg-0">&copy; Your Website 2022. All Rights Reserved.</p>
					</div>
					<div class="col-lg-6 h-100 text-center text-lg-end my-auto">
						<ul class="list-inline mb-0">
							<li class="list-inline-item me-4">
								<a href="https://ko-kr.facebook.com/" target="_blank"><i class="bi-facebook fs-3"></i></a>
							</li>
							<li class="list-inline-item me-4">
								<a href="https://twitter.com/?lang=ko" target="_blank"><i class="bi-twitter fs-3"></i></a>
							</li>
							<li class="list-inline-item">
								<a href="https://www.instagram.com/" target="_blank"><i class="bi-instagram fs-3"></i></a>
							</li>
						</ul>
					</div>
				</div>
			</div>
		</footer>
	<%-- 상세페이지 끝! --%>
		
	<script type="text/javascript">	
	
	<%-- 메인 이미지 슬라이드 --%>
	$(function() {
		$('.slideImage').bxSlider({
			auto : true, // 자동으로 애니메이션 시작
			speed : 500, // 애니메이션 속도
			pause : 3000, // 애니메이션 유지 시간 (1000은 1초)
			mode : 'horizontal', // 슬라이드 모드 ('fade', 'horizontal', 'vertical' 이 있음)
			autoControls : false, // 시작 및 중지버튼 보여짐
			pager : true, // 페이지 표시 보여짐
			captions : true, // 이미지 위에 텍스트를 넣을 수 있음
			slideWidth : 500, // 크기
			slideMargin : 0,
			autoDelay : 0,
			responsive : true,
			controls : true
		});
	});
		
		<%-- - , + 클릭시 총금액 계산 --%>
		var hm = 1;
		function add() {
			hm = document.getElementById("amounts");
			sell_price = document.getElementById("sell_price");
			hm.value++;
			var sum_ = parseInt(hm.value) * sell_price.value;
			document.getElementById("sum").innerHTML = sum_.toLocaleString('ko-KR');
		}
		
		function del() {
			hm = document.getElementById("amounts");
			sell_price = document.getElementById("sell_price");

			if(hm.value>1){
				hm.value--;
				
				var sum_ = parseInt(hm.value) * sell_price.value;
				document.getElementById("sum").innerHTML = sum_.toLocaleString('ko-KR');
			}
		}
		
				
		<%-- 비로그인시 장바구니 버튼클릭 할 경우 로그인 창으로 --%>
		
		function askLogin() {
			var m_id = '<%=m_id%>';
			
			if(m_id==null || m_id.trim() == "" || m_id == 'null'){
				alert("로그인이 필요한 서비스입니다. 로그인 하시겠습니까?");
				location.href = '<%=contextPath%>/Member/login?center=member/login.jsp';
				return false;	
			}else {//검색어를 입력 했다면
	
				document.search.submit();
			}	
		}
		
		function updateHiddenInput() {
	        var userInput = document.getElementById("area_request").value;
	        document.getElementById("hidden_input_request").value = userInput;
	    }

	    // 사용자가 textarea에 입력할 때마다 호출되는 함수
	    document.getElementById("area_request").addEventListener("input", updateHiddenInput);
	    
	  	//바로구매 결제 처리
	    var p_name = document.getElementById("p_name").value;  
	    var amount = parseInt(document.getElementById("amount").value); 
	    var buyer_email = document.getElementById("buyer_email").value; 
	    var buyer_name  = document.getElementById("buyer_name").value; 
	    var buyer_tel = document.getElementById("buyer_tel").value; 
	    var buyer_addr = document.getElementById("buyer_addr").value; 
	    var buyer_postcode = document.getElementById("buyer_postcode").value; 
	    var c_pmid = document.getElementById("c_pmid").value;
	    
	    var IMP = window.IMP; 
	    IMP.init("발급키");
	    var today = new Date();   
	    var hours = today.getHours(); // 시
	    var minutes = today.getMinutes();  // 분
	    var seconds = today.getSeconds();  // 초
	    var milliseconds = today.getMilliseconds();
	    var makeMerchantUid = hours +  minutes + seconds + milliseconds;
	    
	    function requestPay() {
	    	var sell_price = parseInt(document.getElementById("sell_price").value);
	        var amount = parseInt(document.getElementById("amounts").value);
	        var totalPrice = sell_price * amount;
	        document.getElementById("amount").value = totalPrice;
	        
	        let m_id = "<%= m_id %>";
	        if(m_id == 'null'){
	        	alert("로그인이 필요한 서비스 입니다.");
	        	return false;
	        }
	        
	    	
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
	    
	    <%-- 리뷰 조회 기능 --%>
		function shopReview(r_idx){
		
			$.ajax({
				type : "post",
				async : true,
				url : "<%=contextPath%>/Review/read.do",
				data : {r_idx : r_idx},
				dataType : "json",
				success : function(data){
					
					
					if(data=="조회실패"){
						window.alert('조회 실패');
						$("#reviewClose").trigger("click");
					
					}else{
						
						 $("#reviewImgDiv").empty();
						
					 // JSON 데이터에서 각 속성의 값을 추출하여 변수에 저장
				        var r_title = data.R_title;
				        var r_mid = data.R_mid;
				        var r_content = data.R_content;
				        var r_sfile = data.R_sfile;
				        var r_date = data.R_date;
				        review_num = r_idx;
				        
				        // 추출한 값들을 각각의 HTML 요소에 할당
				        $("#openr_title").val(r_title);
				        $("#openr_mid").val(r_mid);
				        $("#openr_content").val(r_content);
				        $("#openr_date").text(r_date);
				        $("#openr_idxx").val(r_idx);
				        
				        
				        if(r_sfile != null){
				        
				        $(".form-wrapper").css("padding-left", "20px");	
				        $("#reviewImgDiv").append("<img src='" + '<%=contextPath %>/reviewimage/' + r_idx + '/' + r_sfile + "'>");

				        }else{
				        	$(".form-wrapper").css("padding-left", ""); 
				        }
				        
				        if("<%=session_m_id%>" === r_mid || "<%=m_admin%>"==="2"){
				        	$("#reviewDelete").css("visibility","visible");
				        }else{
				        	$("#reviewDelete").css("visibility","hidden");
				        }
				        
					}
					
					
				},
				error : function(){
					alert("비동기 통신 장애");
				}
			});
		}
		
	      <%-- 리뷰 삭제 기능 --%>
	      function deletePro(r_idx){
	         var result = window.confirm("정말로 글을 삭제하시겠습니까?");
	         
	         if(result == true){//확인 버튼 클릭
	            
	            //비동기방식으로 글삭제 요청!
	            $.ajax({
	               type : "post",
	               async : true,
	               url : "<%=contextPath%>/Review/delete.do",
	               data : {r_idx : r_idx},
	               dataType : "text",
	               success : function(data){
	                  
	                  if(data=="삭제성공"){

	                     window.alert('삭제 성공');
	                     location.href = "<%=contextPath%>/Shirts/list.do";

	                  }else{//"삭제실패"
	                     window.alert('삭제 실패');
	                     location.href = "<%=contextPath%>/Shirts/list.do";
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
		
		
		<!-- Product Shop Section End -->
		
		<!-- Js Plugins -->
		<%-- 아이콘, 폰트 선언문 --%>
		<script src="https://kit.fontawesome.com/b01b7411b2.js" crossorigin="anonymous"></script>
	</body>
</html>
