<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<%
	String contextPath = request.getContextPath();
	String m_id = (String)session.getAttribute("m_id");
	int result1 = (Integer)request.getAttribute("result1");
	int result2 = (Integer)request.getAttribute("result2");
	int result3 = (Integer)request.getAttribute("result3");
	int result4 = (Integer)request.getAttribute("result4");
	
	int month1 = (Integer)request.getAttribute("month1");
	int month2 = (Integer)request.getAttribute("month2");
	int month3 = (Integer)request.getAttribute("month3");
	int month4 = (Integer)request.getAttribute("month4");
	int month5 = (Integer)request.getAttribute("month5");
	int month6 = (Integer)request.getAttribute("month6");
	int month7 = (Integer)request.getAttribute("month7");
	int month8 = (Integer)request.getAttribute("month8");
	int month9 = (Integer)request.getAttribute("month9");
	int month10 = (Integer)request.getAttribute("month10");
	int month11 = (Integer)request.getAttribute("month11");
	int month12 = (Integer)request.getAttribute("month12");
%>
<c:set var="cartList" value="${requestScope.list }" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="<%=contextPath %>/css/styles1.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
	   	<style type="text/css">
	   		#bar-chart{
	   			margin-bottom: 50px;
	   		}
	   		#doughnut-chart{
	   			margin-bottom: 50px;
	   		}
	   	</style>
    </head>   
    
	<body class="sb-nav-fixed">
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                        	
                            <div class="sb-sidenav-menu-heading">주문 | 배송 | 장바구니</div>
                            <a class="nav-link" href="<%=contextPath%>/Mypage/cart?center=mypage/cart.jsp&m_id=<%=m_id%>&nowBlock=0&nowPage=0">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                장바구니
                            </a>
                            <a class="nav-link" href="<%=contextPath%>/Mypage/orderDelete?center=mypage/orderDelete.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                결제내역
                            </a>
                            <div class="sb-sidenav-menu-heading">회원정보</div>
                            <a class="nav-link" href="<%=contextPath%>/Mypage/checkpwd?center=mypage/checkpwd.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                회원수정
                            </a>
                            <a class="nav-link" href="<%=contextPath%>/Mypage/quitMember?center=mypage/quitcheck.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                회원탈퇴
                            </a>
                            
                            <div class="collapse" id="collapsePages" aria-labelledby="headingTwo" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav accordion" id="sidenavAccordionPages">
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseAuth" aria-expanded="false" aria-controls="pagesCollapseAuth">
                                        Authentication
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseAuth" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="login.html">Login</a>
                                            <a class="nav-link" href="register.html">Register</a>
                                            <a class="nav-link" href="password.html">Forgot Password</a>
                                        </nav>
                                    </div>
                                    <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#pagesCollapseError" aria-expanded="false" aria-controls="pagesCollapseError">
                                        Error
                                        <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                                    </a>
                                    <div class="collapse" id="pagesCollapseError" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordionPages">
                                        <nav class="sb-sidenav-menu-nested nav">
                                            <a class="nav-link" href="401.html">401 Page</a>
                                            <a class="nav-link" href="404.html">404 Page</a>
                                            <a class="nav-link" href="500.html">500 Page</a>
                                        </nav>
                                    </div>
                                </nav>
                            </div>
                            <div class="sb-sidenav-menu-heading"></div>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Start Bootstrap
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4"><%=m_id %>님 Page</h1>
                        <ol class="breadcrumb mb-4">
                        </ol>
                        <div class="row">
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-primary text-white mb-4">
                                    <div class="card-body">주문 수량</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#" >주문 수량 : <%=result1 %></a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-warning text-white mb-4">
                                    <div class="card-body">장바구니 수량</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#">장바구니 수량 : <%=result2 %></a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-success text-white mb-4">
                                    <div class="card-body">총 결제 금액</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#">총 결제 금액 : <%=result3 %></a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-danger text-white mb-4">
                                    <div class="card-body">찜 체크 횟수</div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="#">찜 체크 횟수 : <%=result4 %></a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- 막대 그래프 차트 -->
                        <canvas id="bar-chart" width="800px" height="200px"></canvas>
                        
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                등록 상품 목록
                            </div>
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                       <tr>
					                       <th>장바구니 번호</th>
					                       <th>상품 번호</th>
					                       <th>상품 이름</th>
					                       <th>상품 이미지</th>
					                       <th>상품 설명</th>
					                       <th>상품 가격</th>
					                   </tr>
                                    </thead>
                                    <tfoot>
                                        <tr>
					                       <th>장바구니 번호</th>
					                       <th>상품 번호</th>
					                       <th>상품 이름</th>
               						       <th>상품 이미지</th>
					                       <th>상품 설명</th>
					                       <th>상품 가격</th>
					                   </tr>
                                    </tfoot>
                                    <tbody>
                                        <c:forEach var="cart" items="${cartList }">    
						                   <tr>
						                       <td> ${cart.c_idx} </td>
						                       <td>	${cart.c_pidx} </td>
						                       <td> ${cart.c_name} </td>
						                       <td><img src="<%=contextPath %>/productimage/${cart.c_pidx}/${cart.c_iname}" style="max-width: 50px; max-height: 50px;"></td>
						                       <td> ${cart.c_pcontent }</td>
						                       <td> ${cart.c_price} </td>
						                   </tr>
						               </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </main>
                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4">
                        <div class="d-flex align-items-center justify-content-between small">
                            <div class="text-muted">Copyright &copy; Your Website 2023</div>
                            <div>
                                <a href="#">Privacy Policy</a>
                                &middot;
                                <a href="#">Terms &amp; Conditions</a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        
        
        <script type="text/javascript">
        var month1 = '<%=month1%>'
        var month2 = '<%=month2%>'
        var month3 = '<%=month3%>'
        var month4 = '<%=month4%>'
        var month5 = '<%=month5%>'
        var month6 = '<%=month6%>'
        var month7 = '<%=month7%>'
        var month8 = '<%=month8%>'
        var month9 = '<%=month9%>'
        var month10 = '<%=month10%>'
        var month11 = '<%=month11%>'
        var month12 = '<%=month12%>'
        
        /* 막대 그래프 차트  */
        new Chart(document.getElementById("bar-chart"), {
            type: 'bar',
            data: {
              labels: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
              datasets: [
                {
                  label: "결제 누적 금액",
                  backgroundColor: ["#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#FF6CFF","#3e95cd", "#8e5ea2","#3cba9f","#e8c3b9","#c45850","#FF6CFF"],
                  data: [month1, month2, month3, month4, month5, month6, month7, month8, month9, month10, month11, month12]

                }
              ]
            },
            options: {
              legend: { display: false },
              title: {
                display: true,
                text: '월별 결제 누적 금액'
              }
            }
        });
        </script>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="<%=contextPath %>/js/scripts.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.min.js" crossorigin="anonymous"></script>
        <script src="<%=contextPath %>/assets/demo/chart-area-demo.js"></script>
        <script src="<%=contextPath %>/assets/demo/chart-bar-demo.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="<%=contextPath %>/js/datatables-simple-demo.js"></script>
    </body>