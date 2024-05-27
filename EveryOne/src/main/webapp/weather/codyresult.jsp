<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="vo.ProductVo"%>
<%@page import="vo.ImgVo"%>
<% 
    String m_id = (String) request.getAttribute("m_id");
    String context = request.getContextPath();
    List codylist = (List) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result Page</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<style type="text/css">
    .flex-container {
        display: flex;
        width: 60%;
        flex-wrap: wrap;
    }

    .flex-item {
        flex: 0 0 33.33%; /* 3 items per row */
        box-sizing: border-box;
        padding: 5px;
        width: 33.33%; /* 3 items per row */
    }

    .flex-item img {
        width: 100%;
        height: 100%;
        object-fit: cover; /* Maintain aspect ratio */
    }
</style>

<body>

<!-- 기온에 따른 코디 리스트 -->
<div class="flex-container">
<% for (int i = codylist.size() - 1; i >= 0; i--) {
    ProductVo item = (ProductVo)codylist.get(i);
%>
    <div class="flex-item">
        <a href="#" onclick="detailView(event, '<%= item.getP_category()%>', '<%= item.getP_idx()%>', '<%= item.getP_name()%>', '<%= item.getP_price()%>', '<%= item.getImgVo().getI_name() %>');">
            <img src="<%= context %>/productimage/<%= item.getP_idx() %>/<%= item.getImgVo().getI_name() %>">
        </a>
    </div>
<% } %>
</div>

<script type="text/javascript">
function detailView(event, category, idx, name, price, imageName) {
    var context = '<%= context %>';
    var m_id = '<%= m_id %>';

    if (category == 'top') { // index 0 ~ 2
        window.location.href = context + '/Shirts/productDetail.do?p_idx=' + idx + '&p_name=' + name + '&p_price=' + price + '&p_size=null&i_name=' + imageName + '&m_id=' + m_id;
    } else if (category == 'bottom') { // index 3 ~ 5
        window.location.href = context + '/Pants/productDetail.do?p_idx=' + idx + '&p_name=' + name + '&p_price=' + price + '&p_size=null&i_name=' + imageName + '&m_id=' + m_id;
    } else if (category == 'acc') { // index 6 ~ 8
        window.location.href = context + '/Acc/productDetail.do?p_idx=' + idx + '&p_name=' + name + '&p_price=' + price + '&p_size=null&i_name=' + imageName + '&m_id=' + m_id;
    }

    event.preventDefault();
}
</script>

</body>
</html>

