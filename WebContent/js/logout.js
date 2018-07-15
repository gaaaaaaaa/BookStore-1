function logout(){
	$.ajax({
		type:"get",
		url:"logout",
		dataType:"json",
		success:function(data){
			if(data.code == 1){
				window.location.href="login.html";
			}else{
				alert(data.msg);
			}
		}
	})
}