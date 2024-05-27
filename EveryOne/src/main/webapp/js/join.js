	  	//약관동의 체크박스를 클릭했을때..
	    $("#agree").click(function(){
	    	
    		if( !($("#agree").is(":checked")) ){ 
   	    		$("#agreeInput").text("약관에 동의해 주세요!").css("color","red");
   	    		
   	    	}else{
	    	$("#agreeInput").text("약관동의 완료!").css("color","blue");
   	    	}
	   	});
	   
	    $("#m_id").focusout(function() {
				
	    	if($("#m_id").val().length >= 3 && $("#m_id").val().length < 20 ){
	    		
	    		//입력한 아이디가 DB에 저장되어 있는지 없는지 확인 요청
	    		//Ajax기술을 이용 하여 비동기 방식으로 MemberController로 합니다.
	    		$.ajax({  
	    			url : "/Project44/Member/joinIdCheck.me", //요청할 주소
	    			type : "post",  //전송요청방식 설정! get 또는 post 둘중 하나를 작성
	    			async : true,  //true는 비동기방식 , false는 동기방식 으로 서버페이지 요청!
	    			data : {m_id : $("#m_id").val()}, //서버 페이지로 요청할 변수명 : 값
	    			dataType : "text", //서버페이지로 부터 응답 받을 데이터 종류 설정!
	    							   //종류는 json 또는 xml 또는 text중 하나 설정!
	    			
	    			//전송요청과 응답통신에 성공했을때
	    			//success 속성에 적힌 function(data,textStatus){}이 자동으로 호출된다.
	    			// data매개변수로는 서버페이지가 전달한 응답 데이터가 넘어옵니다.
	    			success : function(data,textStatus){
	    				//서버페이지에서 전송된 아이디 중복? 인지 아닌지 판단하여
	    				//현재 join.jsp화면에 보여주는 처리 구문 작성
	    				if(data=='usable'){ //아이디가 DB에 없으면?
	    					$("#idInput").text("사용할 수 있는 ID 입니다.").css("color","blue");
	    				}else{ //아이디가 DB에 있으면?
	    					$("#idInput").text("이미 사용중인 ID입니다.").css("color","red");
	    				}
	    				
	    			}
	    			
	    		});
	    		
    		}else{
    			$("#idInput").text("한글,특수문자 없이 3~20글자사이로 작성해 주세요!").css("color","red");
    		}
		});
		$("#m_pass").focusout(function(){
			if($("#m_pass").val().length < 4 ){
    			$("#passInput").text("한글,특수문자 없이 4글자 이상으로 작성해 주세요!").css("color","red");
    		}else{
    			$("#passInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
		});
		$("#m_name").focusout(function(){
	    	if($("#m_name").val().length < 2 || $("#m_name").val().length > 6 ){
    			$("#nameInput").text("이름을 제대로 작성하여주세요.").css("color","red");
    		}else{
    			$("#nameInput").text("이름입력완료!").css("color","blue");
    		}
	    });
	    $(".m_gender").click(function() {
	    	$("#genderInput").text("성별체크완료!").css("color","blue");
	    });
	    
	    $("#m_birth").focusout(function(){
			var birthdate = $("#m_birth").val();

			var today = new Date();
		    var selectedDate = new Date(birthdate);
		    if (selectedDate >= today) {
		        $("#birthInput").text("올바른 날짜를 입력해주세요.").css("color", "red");
		    }else{
				$("#birthInput").text("날짜 입력 완료").css("color", "blue");

			};
	    });
	    $(".m_admin").click(function() {
	    	$("#adminInput").text("회원종류 체크완료!").css("color","blue");
	    });
	    $("#m_email").focusout(function() {
			var mail = $("#m_email");
    		var mailValue = mail.val();
    		var mailReg = /^\w{5,12}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,2}$/;
    		var rsEmail = mailReg.test(mailValue);
    		
	    	if(!rsEmail){
    			$("#emailInput").text("이메일 형식이 올바르지 않습니다.").css("color","red");
    			
    		}else{
    			$("#emailInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
	    });
	    
	    $("#m_hp").focusout(function() {
			var p = $("#m_hp");
    		var pValue = p.val();
    		var pReg = RegExp(/^01[0179][0-9]{7,8}$/);
    		var resultP = pReg.test(pValue);
    		if(!resultP){
    			$("#hpInput").text("휴대폰번호 형식이 올바르지 않습니다.").css("color","red");
    		}else{
    			$("#hpInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}	
	    });
	    
	    $("input[name='address1'],input[name='address2'],input[name='address3'],input[name='address4'],input[name='address5']").focusout(function() {
		    		if(	$("input[name='address1']").val()== "" || 
	    				$("input[name='address2']").val()== "" ||
	    				$("input[name='address3']").val()== "" ||
	    				$("input[name='address4']").val()== "" ||
	    				$("input[name='address5']").val()== "" ){
	    			$("#addressInput").text("주소를 모두 작성하여주세요.").css("color","red");
	    		}else{
	    			$("#addressInput").text("올바르게 입력되었습니다.").css("color","blue");
	    		}
		    });
	
	
		function check() {

			
			//약관동의 <input>요소를 선택해서 가져와 
   	    	var checkbox = $("#agree");
   	    	//약관동의 체크했는지 검사
   	    	//선택한 <input type="checkbox">체크박스에 체크가 되어 있지 않으면? 
   	    	//true를 리턴 해서 조건에 만족 합니다. 
   	    	if( !(checkbox.is(":checked")) ){ //== 같은 true값을 반환 한다. if(!$("#agree").prop("checked"))
   	    		$("#agreeInput").text("약관에 동의해 주세요!").css("color","red");
   	    		
   	    		return false;
   	    	}
   	    	//====================================================================================================
   	    		
   	    	var id = $("#m_id");
   	    	var idValue = id.val();
   	    	
   	    	var idReg = RegExp(/^[A-Za-z0-9_\-]{3,20}$/);
   	    	var resultId = idReg.test(idValue);
   	    	
    		if(!resultId){
    			$("#idInput").text("한글,특수문자 없이 3~20글자사이로 작성해 주세요!").css("color","red");
    			id.focus();
    			
    			return false;
    		}
   	    	
    		//====================================================================================================
    		var name = $("#m_name");
   	    	var nameValue = name.val();
   	    	
   	    	var nameReg = RegExp(/^[가-힣]{2,6}$/);
   	    	var resultName = nameReg.test(nameValue);

    		if(!resultName){
    			$("#nameInput").text("이름을 한글로 작성하여주세요.").css("color","red");
    			name.focus();
    			
    			return false;
    		}else{
    			$("#nameInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
    		
    		//====================================================================================================
	    		
   	    	var pass = $("#m_pass");
   	    	var passValue = pass.val();
   	    	
   	    	var passReg = RegExp(/^[A-Za-z0-9_\-]{4,20}$/);
   	    	var resultPass = passReg.test(passValue);

    		if(!resultPass){
    			$("#passInput").text("한글,특수문자 없이 4글자 이상으로 작성해 주세요!").css("color","red");
    			pass.focus();
    			
    			return false;
    		}else{
    			$("#passInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
    		
    		//====================================================================================================
		    var birthdate = $("#m_birth").val();
		    if (!birthdate) {
		        $("#birthInput").text("생년월일을 입력해주세요.").css("color", "red");
		        return false;
		    }
    		
    		//====================================================================================================

    		var address1 = $("#sample4_postcode");
    		var address2 = $("#sample4_roadAddress"); 
    		var address3 = $("#sample4_jibunAddress")
    		var address4 = $("#sample4_detailAddress");
    		var address5 = $("#sample4_extraAddress");
    		var addVal1 = address1.val();
    		var addVal2 = address2.val();
    		var addVal3 = address3.val();
    		var addVal4 = address4.val();
    		var addVal5 = address5.val();
    		if(addVal1 == "" || addVal2 == "" || addVal3 == "" || addVal4 == "" || addVal5 == ""){
    			$("#addressInput").text("주소를 모두 작성하여주세요.").css("color","red");
    			address5.focus();
    			
    			return false;
    		}else{
    			$("#addressInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
    			
    		//====================================================================================================
	
    		var gender = $(".m_gender:checked");
    		var genderValue = gender.val();
    		genderValue = $.trim(genderValue);
    		if (genderValue == ""){
    			
    			$("#genderInput").text("성별을 체크 해주세요.").css("color","red");
    			
    			return false;
    			
    		}
    		//====================================================================================================
			var admin = $(".m_admin:checked");
    		var adminValue = admin.val();
    		adminValue = $.trim(adminValue);
    		if (adminValue == ""){
    			
    			$("#adminInput").text("회원 종류 체크 해주세요.").css("color","red");
    			
    			return false;
    			
    		}
    		//====================================================================================================

			var email = $("#m_email");
    		
    		var emailValue = email.val();
    		
    		var emailReg = /^\w{5,12}@[a-z]{2,10}[\.][a-z]{2,3}[\.]?[a-z]{0,2}$/;
    		
    		var resultEmail = emailReg.test(emailValue);
    		
    		if(!resultEmail){
    			$("#emailInput").text("이메일 형식이 올바르지 않습니다.").css("color","red");
    			
    			email.focus();
    			
    			return false;
    		}else{
    			$("#emailInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}	
    			
			//====================================================================================================
			
			var hp = $("#m_hp");
    		
    		var hpValue = hp.val();
    		
    		var hpReg = RegExp(/^01[0179][0-9]{7,8}$/);
    		
    		var resultHp = hpReg.test(hpValue);
    		
    		if(!resultHp){
    			
    			$("#hpInput").text("휴대폰번호 형식이 올바르지 않습니다.").css("color","red");
    			
    			hp.focus();
    			
    			return false;
    		}else{
    			$("#hpInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}	
    		
        	alert("회원가입이 완료 되었습니다.");
        		
        	$("form").submit(); // 폼 제출
   	    	
			
		}
	
