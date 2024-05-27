		
		// 비밀번호 유효성 검사
		$("#m_pass").focusout(function(){
			if($("#m_pass").val().length < 4 ){
    			$("#passInput").text("한글,특수문자 없이 4글자 이상으로 작성해 주세요!").css("color","red");
    		}else{
    			$("#passInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
		});
		
	    $(".m_gender").click(function() {
	    	$("#genderInput").text("성별체크완료!").css("color","blue");
	    });
	    
	    // 생년월일 유효성 검사
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
	    
	    // 회원 종류 유효성 검사
	    $(".m_admin").click(function() {
	    	$("#adminInput").text("회원종류 체크완료!").css("color","blue");
	    });
	    
	    // 휴대폰 유효성 검사
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
	    
	    // 주소 유효성 검사
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
   	    	
    		//====================================================================================================
	    	
    		// 비밀번호 유효성 검사
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
    		
    		// 생년월일 유효성 검사
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
    		
    		// 회원 종류 유효성 검사
			var admin = $(".m_admin:checked");
    		var adminValue = admin.val();
    		adminValue = $.trim(adminValue);
    		if (adminValue == ""){
    			
    			$("#adminInput").text("회원 종류 체크 해주세요.").css("color","red");
    			
    			return false;
    			
    		}
    			
			//====================================================================================================
			
    		// 휴대폰 유효성 검사
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
	
