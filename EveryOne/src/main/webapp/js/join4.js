		// 제목 포커스 아웃 시 유효성 체크
		$("#n_title").focusout(function(){
			if($("#n_title").val().length == 0 ){
    			$("#n_titleInput").text("제목을 작성해 주세요!").css("color","red");
    		}else{
    			$("#n_titleInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
		});
		
	    // 내용 포커스 아웃 시 유효성 체크
	    $("#n_content").focusout(function(){
			if($("#n_content").val().length == 0 || $("#n_content").val() == ""){
				$("#n_contentInput").text("내용을 작성해 주세요!").css("color","red");
			}else{
				$("#n_contentInput").text("올바르게 입력되었습니다.").css("color","blue");
			}
		});
	    
		
		// 글 등록 버튼 클릭 시 호출 함수
		function check() {
			    		
    		// 이름 형식 유효성 체크
    		var title = $("#n_title");
   	    	var titleValue = title.val();

    		if(titleValue == "" || titleValue.length == 0){
    			$("#n_titleInput").text("제목은 필수로 작성해야합니다.").css("color","red");
    			
    			return false;
    		}else{
    			$("#n_titleInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
    		
    		//====================================================================================================
	    	
	    	// 내용 형식 유효성 체크
    		var content = $("#n_content");
   	    	var contentValue = content.val();

    		if(contentValue == "" || contentValue.length == 0){
    			$("#n_contentInput").text("내용은 필수로 작성해야합니다.").css("color","red");
    			
    			return false;
    		}else{
    			$("#n_contentInput").text("올바르게 입력되었습니다.").css("color","blue");
    		}
    		
    		//====================================================================================================

   	    	
			
		}
	
