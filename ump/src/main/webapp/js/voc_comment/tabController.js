/**
 * 回复标签控制
 * tabController.js
 */


//显示回复页签
function showVocTabBar(commentId){
 $("#onevocMenuName").css("display","block");
  createLabel(commentId);
  showWebSearch();
}

/**
 * 创建页签
 * @param commentId
 */
function createLabel(commentId){
	var liId = 'commentId_'+commentId;
	 var li_id = "\'"+liId+"\'";
	 $("#vocWebSearchUlId").append('<li name="vocTabName" onmouseout="recoverLastStyle('+commentId+')" onmouseover="addOnHoverStyle('+commentId+')" style="cursor: pointer;" id='+li_id+'><span id="layerOne_'+commentId+'"><span id="layerTwo_'+commentId+'" onclick="loadTable('+li_id+')">回复评论</span><i onclick="closeTable('+li_id+')"><img src="/ump/images/vocImg/x.png"/></i></span></li>');
	 saveWebSearchLabel();
}

/**
 *鼠标放在页签上状态的样式
 */
function addOnHoverStyle(id){
	saveCurrentStyle(id);
	if($("#layerOne_"+id).attr("style")){
	  $("#layerOne_"+id).css({"background-color":"#dfe9f8","border":"1px solid #bbb"});
	}
	if($("#layerTwo_"+id).attr("style")){
	   $("#layerTwo_"+id).css({"background-color":"#dfe9f8"});	
	}
	
}

/**
 * 保存当前页签的样式
 * @param id
 */
function saveCurrentStyle(id){
 var datas = {};
 datas.tabStyleId = "tabStyleId";
 datas.commentId = id;
 datas.layerOne = $("#layerOne_"+id).attr("style");
 datas.layerTwo = $("#layerTwo_"+id).attr("style");
 var jsonStr = JSON.stringify(datas);
 
 saveMyObject("tabStyleId",jsonStr);
 
}

/**
 * 恢复当前页签的样式
 */
function recoverLastStyle(id){
 var str = getObjByKey("tabStyleId");
 var jsonStr = JSON.parse(str);
  if(jsonStr){
	if(jsonStr.commentId == id){
	  if(jsonStr.layerOne){
		  if($("#layerOne_"+id).attr("style")){
			 $("#layerOne_"+id).attr("style",jsonStr.layerOne);
		  }
	  }
	  if(jsonStr.layerTwo){
		  if($("#layerTwo_"+id).attr("style")){
			 $("#layerTwo_"+id).attr("style",jsonStr.layerTwo);	  
		  }
	  }
     
	}
  }
  
}

/**
 * 页签不选中状态时的样式
 * @param id
 */
function addOffStyle(id){
	$("#layerOne_"+id).css({"background-color":"#aad9ee","border":"1px solid #bbb"});
	$("#layerTwo_"+id).css({"background-color":"#aad9ee","color":"#111"});
}

/**
 *全网搜索页签页面样式变暗
 */
function addWebSearchOffStyle(){
  $("#websearchNamelabelId").css({"background-color":"#aad9ee","color":"#111"});
  if(!$("#websearchNamelabelId").attr("style")){
	$("#websearchNamelabelId").style="background-color:#aad9ee;color:#111";
  }
}

/**
 * 全网搜索页签显示
 */
function addWebSearchOnStyle(){
  $("#websearchNamelabelId").removeAttr("style");
}

/**
 * true 全网搜索显示  false 不显示
 * @returns {Boolean}
 */
function isWebSearchOn(){
  var flag = true;
  $("li[name='vocTabName']").find("span span").each(function(){
	 if($(this).attr("style") == undefined | $(this).attr("style") == ""){
	   flag = false;
	   return flag;
	 }
  });
  
  return flag;
}

/**
 * 页签选中状态的样式
 * @param id
 */
function tabChooseStateStyle(id){
  $("#layerOne_"+id).removeAttr("style");
  $("#layerTwo_"+id).removeAttr("style");
}

/**
 * 页面标签是否超过限制
 * @returns {Boolean}
 */
function isLabelLimit(){
  var flag = false;
  if($("li[name='vocTabName']").length < 7){
	flag = true;
  }
  return flag;
}




/**
 * 关闭标签
 */
function closeTable(tabId){ 
	//TODO 关闭时，判断当前页面是否在全网搜索上，如果不是，直接关闭标签
	  var jsonStr = getObjByKey('currentUrlId'); 
	 if(jsonStr && !/displayId=46/.test(jsonStr.currentUrl)){ //当前页面不在全网搜索上
		//alert("tab------->>> "+$("#"+tabId).html());
		removeObjByKey(tabId);
		$("#"+tabId).remove();
		saveWebSearchLabel();
	 }else{//如果关闭的是当前显示的页签,显示关闭页签的前一个页签，否则就是直接关闭
		var isOpenedTab = isOpenTab(tabId.split("_")[1]);
		var vocTabNames = $("#vocWebSearchUlId li[name='vocTabName']");
		removeObjByKey(tabId);
		$("#"+tabId).remove();
		saveWebSearchLabel();
		if(allLabelRemoved()){ //都删除完了，显示全网搜索
		  turnToWebSearch();
		}else{ //否则显示相邻的上一个(或者下一个)
			if(isOpenedTab){
				$.each(vocTabNames,function(index,vocTabName){
					if(vocTabName.id == tabId ){
						if(index > 0){
						   if((index-1) >= 0){
							loadTable(vocTabNames[index-1].id);
						   }else{
							 loadTable(vocTabNames[index+1].id);
						   }	
						}else{
							loadTable(vocTabNames[0].id);
						}
					}
				  });	
			}
		}
	}
	 
	//所有的页签是否关闭，如果关闭，全网搜索页签也关闭
	 hideWebSearch();	 
}

/**
 * 关闭全网搜索
 */
function hideWebSearch(){
 /* if(!/displayId=46/.test(window.location.href)){
	$("#voclocation").hide();
  }*/
 if($("li[name='vocTabName']").length == 0){
	$("#voclocation").hide();	 
 }
 
}

/**
 * 显示标签
 */
function showWebSearch(){
  $("#voclocation").show();	
  addWebSearchOffStyle();
}

/**
 * 判断当前页签（tabId）是否是打开的页签 true ：当前页签（tabId）是打卡的页签，false ：tabId页签不是打开的
 * @param tabId
 */
function isOpenTab(tabId){
	var layerOneSpan = $("#layerOne_"+tabId);
	if(layerOneSpan.attr("style")){
	 return false;
	}
	return true;
}

/**
 * 返回到全网搜索
 * 
 */
function turnToWebSearch(){
  if(isCurrentPageWebSearchPage()){
	  $("#showWebSearchId").css("display","block");
	  $("#showReplyPage").css("display","none");
	  queryComment($("#page").val());
  }else{
	  window.location.href = "/ump/voccomments?page=1&size=10&displayId=46"; 
  }
  //其它回复标签样式暗掉
  removeAllTabStyle();
  addWebSearchOnStyle();
  saveWebSearchLabel();
}

/**
 * true 当前页面是全网搜索，false 不是全网搜索
 * @returns {Boolean}
 */
function isCurrentPageWebSearchPage(){
  var jsonStr = getObjByKey('currentUrlId');
  if(jsonStr){
    if(/displayId=46/.test(jsonStr.currentUrl)){
	  return true;
	}
   }else{
	   return false;
   }
  
}


/**
 * 根据id加载页签数据
 * @param tabId
 */
function loadTable(tabId){
  var jsonStr = getObjByKey(tabId);
   if(!/displayId=46/.test(window.location.href)){ //点击标签时，当前页面不在全网搜索里
	 window.location.href = "/ump/voccomments?page=1&size=10&displayId=46";
	 var data = {};
	 data.id = "goBack2Label";
	 data.commentId = tabId;
	 saveMyObject('goBack2Label',data);
   }

  if(jsonStr){
    //alert("loadTable jsonStr "+jsonStr);
    getReplyTaskById(jsonStr);
  }
  
  showCurrentTab(tabId);
  addWebSearchOffStyle();
  saveWebSearchLabel();
  
}

/**
 * 比较评论id是否在打开的页签里
 * @param commentIds
 * @returns
 */
function getShownTabId(commentIds){
   var commentId;
  for(key in commentIds){
	  commentId = commentIds[key]; 
    for(var i=0;i<localStorage.length;i++){
 	   jsonStr = JSON.parse(localStorage.getItem(localStorage.key(i)));
 	   if(jsonStr.commentId){
 		 if(jsonStr.batchCommentId){ //比对批量
 		    for(var j=0;j < jsonStr.batchCommentId.length;j++){
 			  if(jsonStr.batchCommentId[j] == commentId){
 				return jsonStr.commentId;
 			  }
 			
 		   }
 		 }else{ //比对单条
 			 if(jsonStr.commentId == ('commentId_'+commentId)){
 				return jsonStr.commentId; 
 			 }
 		 }
 	   }
 	 }
  }
  
}

/**
 * 显示当前标签,其它页面显示默认样式
 * 
 */
function showCurrentTab(tabId){
	var liArr = $("li[name=vocTabName]");
	var commentId;
	for(var index=0;index < liArr.length;index++){
		commentId = $(liArr[index]).attr("id");
	    if(commentId){
	     addOffStyle(commentId.split("_")[1]);
	    }
	}
	
	tabChooseStateStyle(tabId.split("_")[1]);
}

/**
 * 移除掉所有标签的样式
 */
function removeAllTabStyle(){
	var liArr = $("li[name=vocTabName]");
	var commentId;
	for(var index=0;index < liArr.length;index++){
		commentId = $(liArr[index]).attr("id");
	    if(commentId){
	     addOffStyle(commentId.split("_")[1]);
	    }
	}
	
}

/**
 * 加载回复内容
 * @param jsonStr
 */
function loadReplyContentPage(commentId){
	var jsonStr = getObjByKey("commentId_"+commentId);
	if(jsonStr){
	  $("#replyContentId").val(jsonStr.replyContent);
	  if($("#replyContentId").val()){
	    $("#numbertips")[0].innerText = 500-$("#replyContentId").val().length;
	   }
	}
	
	$("#showReplyPage").css("display","block");
	$("#showWebSearchId").css("display","none");
}

/**
 * 从后台取带回复的任务
 * @param commentIds
 */
function getReplyTaskById(jsonStr){
	var commentIds;
	var tabKey =  jsonStr.commentId.split("_")[1];
	 if(jsonStr.batchCommentId){//批量回复
		 commentIds = jsonStr.batchCommentId.join();
	 }else{//单个回复
		 commentIds =  jsonStr.commentId.split("_")[1];
	 }
	
	 var param = {
	   commentIds : commentIds
	  };
	 
	 $.ajax({
			url : "/ump/voccomments/getRelyPage",
			type : "POST",
			data : param,
			error : function(msg) {
				alert("请求错误! ");
			},
			success : function(text) {
				$("#showReplyResult").html(text);
				loadReplyContentPage(tabKey);
			}
		 });
}

/**
 * 封装voc全网搜索回复标签(当前显示的页签保存下来)
 */	 
function saveWebSearchLabel(){
	var allLabels = $("#vocWebSearchUlId").html();
	var webSearchLabel = {};
	webSearchLabel.labelId = "vocWebSearchUlId";
	webSearchLabel.labels = allLabels;
	saveAllLabel('vocWebSearchUlId',webSearchLabel);
}	

/**
 * 找出当前评论id，找出已经打开的批量回复页签id
 * @param commentId
 * @returns {Boolean}
 */
function getBatchReplyTabId(commentId){
	  var jsonStr = null;
	 for(var i=0;i<localStorage.length;i++){
	   jsonStr = JSON.parse(localStorage.getItem(localStorage.key(i)));
	   if(jsonStr.commentId){
		 if(jsonStr.batchCommentId){
		   for(var j=0;j < jsonStr.batchCommentId.length;j++){
			  if(jsonStr.batchCommentId[j] == commentId){
				 return jsonStr.commentId;
			  }
			
		   }
		 }
	   }
	 }

}
	