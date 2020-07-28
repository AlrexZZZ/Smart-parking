/**
 * 加载全网搜索标签
 */

function loadVocWebSearchLabel(){
   var allWebSearchLabel = getObjByKey("vocWebSearchUlId");
   if(allWebSearchLabel && allWebSearchLabel.labels){
	 $("#vocWebSearchUlId").html("");
	 $("#vocWebSearchUlId").html(allWebSearchLabel.labels);  
   }
  
   var data = {};
   data.currentUrlId = 'currentUrlId';
   data.currentUrl = window.location.href;
   saveMyObject('currentUrlId',data);
}

/**
 * 全网搜索页签显示与不显示控制
 */
function vocWebSearchLabelControl(){
	if($("li[name='vocTabName']").length == 0){ //没有打开的标签,全网页签关闭
		hideWebSearch();
	}else{
	  if(isWebSearchOn()){
	    addWebSearchOnStyle();
	  }else{
		addWebSearchOffStyle();
	  }
    }
	
}