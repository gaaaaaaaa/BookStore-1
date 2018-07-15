	function pagebind(info, state){
		$(".page-spliter").empty();
//		$(".turnPage center fr").empty();
		var html = '';
		//前一页
		var prePage = info.currentPage - 1;
		if(prePage <= 0){
			//html += '<a href="javascript:loadData(1, state)"></a>';
		}else{
			html += '<a href="javascript:loadData(' + prePage + ', state)">&lt;</a>';
		}
		
		html += '<a href="javascript:loadData(1, state)">首页</a>';
		
		//当前页
		for(var i = 1; i <= info.totalPage; i++){
			if(i == info.currentPage){
				html += '<span class="current">' + i + '</span>';
			}else{
				html += '<a href="javascript:loadData(' + i + ', state)">' + i + '</a>';
			}
		}
		
		html += '<a href="javascript:loadData(' + info.totalPage + ', state)">尾页</a>';
		
		//下一页
		var nextPage = info.currentPage + 1;
		if(nextPage > info.totalPage){
		//	html += '<a href="javascript:loadData(' + info.totalPage + ', state)"></a>';
		}else{
			html += '<a href="javascript:loadData(' + nextPage + ', state)">&gt;</a>';
		}
		
		$(".page-spliter").append($(html));
//		$(".turnPage center fr").append($(html));


	}
	