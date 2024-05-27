function toggleWishlistBtn(m_id, p_idx) {
	console.log(typeof m_id);
	console.log(m_id);
	if(m_id == null || m_id.length == 0){//미로그인 
		if (confirm('로그인이 필요한 서비스입니다. 로그인 하시겠습니까?')){
			location.href = 'http://localhost:8081/Project44/Member/login?center=member/login.jsp';
		}
	}else{//로그인 했을떄
	
		if ($('#Wishlist').val() == 'true') {
			// 위시리스트에 추가되어 있으면 삭제
			$.ajax({
				type: 'post',
				url: "/Project44/Wish/delete.do",
				dataType : "text",
				data : {m_id : m_id ,
						p_idx : p_idx},
				success: function() {
				
					// 버튼만 변경
					/*$('#Wishlist').attr('value', false);*/
					$('#wishlistBtn').attr('class', 'far fa-heart fa-2xl');
					document.getElementById("Wishlist").value = false;
				},
				error: function() {
					alert('위시리스트 제거 실패');
				}
			});		
			
		}else if($('#Wishlist').val() == 'false'){
			$.ajax({
				type: 'get',
				url: '/Project44/Wish/addWishList.do',
				dataType : "text",
				data : {m_id : m_id ,
						p_idx : p_idx},
				success: function() {
						// 버튼만 변경
						/*$('#Wishlist').attr('value', true);*/
						$('#wishlistBtn').attr('class', 'fas fa-heart fa-2xl');
						document.getElementById("Wishlist").value = true;
					
				},
				error: function() {
					alert('위시리스트 추가 실패');
				}
			});		
		}
	} // if-else

}// toggleWishlistBtn()