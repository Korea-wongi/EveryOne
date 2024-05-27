		// 비밀번호 포커스 아웃 시 글자 수 등 유효성 체크
		$("#m_pass").focusout(function(){
			if($("#m_pass").val().length < 4 ){
    			$("#passInput").text("한글,특수문자 없이 4글자 이상으로 작성해 주세요!").css("color","red");
    		}else{
    			$("#passInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
		});
		
		// 이름 포커스 아웃 시 글자 수 등 유효성 체크
		$("#m_name").focusout(function(){
	    	if($("#m_name").val().length < 2 || $("#m_name").val().length > 6 ){
    			$("#nameInput").text("이름을 제대로 작성하여주세요.").css("color","red");
    		}else{
    			$("#nameInput").text("이름입력완료!").css("color","blue");
    		}
	    });
	    
	    // 이메일 포커스 아웃 시 이메일 형식 유효성 체크
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
	    
	    // 휴대폰 번호 포커스 아웃 시 번호 양식 유효성 체크
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
	    
	    // 배송주소 포커스 아웃 시 빈칸 여부 유효성 체크
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
	
		
		// 회원 수정 버튼 클릭 시 호출 함수
		function check() {
			
    		//====================================================================================================
    		
    		// 이름 형식 유효성 체크
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
	    	
	    	// 비밀번호 형식 유효성 체크	
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

			// 주소 빈칸 여부 유효성 체크
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
	
			// 이메일 형식 유효성 체크
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
			
			// 휴대폰 번호 형식 유효성 체크
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
    		
        	alert("회원수정이 완료 되었습니다.");
        		
        	$("form").submit(); // 폼 제출
   	    	
			
		}
	
