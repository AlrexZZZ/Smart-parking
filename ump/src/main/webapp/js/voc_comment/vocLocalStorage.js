/**
 * loadStorage.js
 */

//id 是否存在：   true 不存在 ,false 存在
function exitsObj(commentId){
	var id = 'commentId_' + commentId;
	var jsonStr = getObjByKey(id);
	
   if(jsonStr){
	 return false;
   }else{
	   var jsonStr = null;
	 for(var i=0;i<localStorage.length;i++){
	   jsonStr = JSON.parse(localStorage.getItem(localStorage.key(i)));
	   if(jsonStr.commentId){
		 if(jsonStr.batchCommentId){
		   for(var j=0;j < jsonStr.batchCommentId.length;j++){
			  if(jsonStr.batchCommentId[j] == commentId){
				  return false;
			  }
			
		   }
		 }
	   }
	 }
   }
	
	return true;
}


// 存储数据
function saveObject(commentId) {
	var id = 'commentId_' + commentId;
	var datas = {};
	datas.commentId = id;
	datas.commentContent = '';
	datas.replyContent = '';
	datas.saveDate = new Date().getTime();
	if (!datas.commentId) {
		alert("内容不能为空！");
		return;
	}
	var dataStr = JSON.stringify(datas);
	localStorage.setItem(datas.commentId, dataStr);
}

//保存批量回复的id
function saveBatchObject(commentId,batchCommentId) {
	var id = 'commentId_' + commentId;
	var datas = {};
	datas.commentId = id;
	datas.commentContent = '';
	datas.replyContent = '';
	datas.saveDate = new Date().getTime();
	datas.batchCommentId = batchCommentId;
	if (!datas.commentId) {
		alert("内容不能为空！");
		return;
	}
	var dataStr = JSON.stringify(datas);
	localStorage.setItem(datas.commentId, dataStr);
}

// 保存对象
function saveObj(datas) {
	var dataStr = JSON.stringify(datas);
	localStorage.setItem(datas.commentId, dataStr);
}

//直接保存json格式数据
function saveJsonObj(jsonStr){
 localStorage.setItem(jsonStr.commentId, jsonStr);
}

// 根据key获取缓存的内容
function getObjByKey(key) {
	if (null != localStorage) {
		return JSON.parse(localStorage.getItem(key));
	} else {
		alert("localStorage 为空");
		return null;
	}

}

// 根据key删除对象
function removeObjByKey(key) {
	if (null != localStorage) {
		localStorage.removeItem(key);
	}
}

// 删除所有对象
function removeAllObj() {
	if (null != localStorage) {
		localStorage.clear();
	}
}

/**
 * 是否所有的label标签删除完毕
 * @returns {Boolean} true 删除完毕 false 未删完
 */
function allLabelRemoved() {
	var jsonStr = getObjByKey('vocWebSearchUlId');
  if(jsonStr && jsonStr.labels){
      if(/loadTable/.test(jsonStr.labels)){
    	 return false;
      }
	}

  return true;
}

/**
* 保存所有的标签
*/	
function saveAllLabel(labelId,datas){
 if(datas){
   var dataStr = JSON.stringify(datas);
   localStorage.setItem(labelId, dataStr);	
 }
}

/**
 * 保存地址
 * @param objId
 * @param datas
 */
function saveMyObject(objId,datas){
	 if(datas){
	   var dataStr = JSON.stringify(datas);
	   localStorage.setItem(objId, dataStr);	
	 }
}	