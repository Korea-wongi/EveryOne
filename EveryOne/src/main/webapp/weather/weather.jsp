<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.sql.Date"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- JQuery 라이브러리 불러오는 코드 -->
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

//현재 위치의 날씨 정보
function weather(latitude, longitude) {
    let API_KEY = '날씨 api 키';

    $.ajax({
        type: "get",
        dataType: "JSON",
        async: true,
        url: "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + API_KEY + "&lang=kr&units=metric",
        success: function(data) {
            console.log(data);

            let temp = data.main.temp; // 기온
            $("#temp").text(temp); // 기온
            $("#feel_temp").text(data.main.feels_like); // 체감온도
            $("#weather_desc").text(data.weather[0].description); // 날씨상태

            let icon = data.weather[0].icon;
            let iconURL = '<img src="http://openweathermap.org/img/wn/' + icon + '.png"/>'; // 날씨상태 아이콘이미지
			$("#icon").html(iconURL);
        }
    });
}

// 사용자의 현재 위치를 가져오는 함수
function getLocation() { // 브라우저의 Geolocation API 사용
    if (navigator.geolocation) { // 위치 정보 가져오는데 성공
        navigator.geolocation.getCurrentPosition(function(position) { // position: 사용자의 위치를 나타내는 객체
            weather(position.coords.latitude, position.coords.longitude); // 위치 정보 확보 성공 시 실행되는 콜백 함수
        });
    } else {
        alert("Geolocation is not supported by this browser.");
    }
}

// 페이지가 로드되면 사용자의 현재 위치를 가져와서 날씨 정보를 불러옴
$(document).ready(function() {
    getLocation();
});


//코디 추천 버튼 클릭 시 실행될 함수
function onSubmitForm() {
    // 현재 기온 값 가져오기
    var currentTemp = $("#temp").text();
    console.log(currentTemp);
    location.href="<%=request.getContextPath()%>/temp/codyset.do?P_CODYTEMP="+currentTemp;
}

</script>

<style type="text/css">
    #weather-container {
        width: 140px;
        height: 80px;
        background-color: #f0f0f0;
        padding: 10px;
        border-radius: 10px; /* Rounded corners */
        text-align: center; /* Center align contents */
        margin-bottom: 20px; /* Add space to the bottom */
        font-size: 10px;
    }

    #weather-table {
        width: 140px;
        border-collapse: collapse;
        margin-bottom: 20px; /* Add space to the bottom */
    }

    #weather-table td {
        padding: 5px;
        border: 1px solid #ccc;
        text-align: center;
    }

    #weather-button {
        display: block;
        margin-left: auto;
        margin-right: auto;
        margin-top: 80px;
        font-size: 10px;
        font-weight: bold;
    }
</style>


</head>
<body>
<h6><%=new Date(System.currentTimeMillis())%> 날씨정보</h6>
<div id="weather-container">
    <table id="weather-table" border="1px">
        <tr>
            <td id="icon" colspan="2" align="center"></td>
        </tr>
        <tr>
            <td>기온</td>
            <td id="temp"></td>
        </tr>
        <tr>
            <td>체감온도</td>
            <td id="feel_temp"></td>
        </tr>
        <tr>
            <td>날씨상태</td>
            <td id="weather_desc"></td>
        </tr>
    </table>
</div>

<!-- Button for additional action -->
<button id="weather-button" type="button" onclick="onSubmitForm()">오늘 날씨 코디 추천 GOGO!!</button>

</body>
</html>
