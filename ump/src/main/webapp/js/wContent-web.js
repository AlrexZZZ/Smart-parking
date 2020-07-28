$(document).ready(function(){
	  $("#commContent").focus(function(){
	 	 $("#commContent").val("");
	  });
	  $("#commContent").blur(function(){
		  if($("#commContent").val()=="请输入内容！"
			  || $.trim($("#commContent").val())==""){
			  $("#commContent").val("请输入内容！");
			  return;
		  }
	  });
	  $("#comment").click(function(){
		 	 $("#return").show();
	  });
	  $("#nav_input01").click(function(){
		  $("#commContent").val("请输入内容！");	 
		  $("#return").hide();
	  });
	  $("#nav_input02").click(function(){
		  var content= $("#commContent").val();
		  var contentId= $("#contentId").val();
		  var friendId=$("#friendid").val();
		  if($("#commContent").val()!="请输入内容！" && $.trim($("#commContent").val())!=""){
		  $.ajax({url:"/ump/wxController/saveWccComment",type:'POST',async:true,
			  data:{"content":content,"contentId":contentId,"friendId":friendId},timeout: 20000,
			  success: function(data){
				  if(data == "0"){
					  $("#return").hide();
					  $("#commContent").val("请输入内容！");	
					  var pl=$("#pl").html();
					  var counts=$("#commCount").html();
					  var count=parseInt(counts);
					  var imgSer=$("#imgSer").val();
					  var headImg=$("#headImg").val();
					  var nickName=$("#friendName").val()+":";
					  var html ="<div class='huifu_list' id='dis${data.id}'>"+
					  			"<div class='huifu_left'><img src="+headImg+" width=\"33px\" height=\"33px\"></div>"+
					  			"<div class=\"huifu_right\"><p><span style=\"padding-right: 5px;\">"+nickName+"</span>"+content+"</p>"+
					  			"<time>"+formatData(new Date())+"</time>"+
					  			"</div></div>";
					  $("#pl").html(html+pl);
					  $("#commCount").html(count+1);	
				  }
			  },
			  error: function(){
				  alert("操作失败!");  
			  }
			 });
		  }
		  else{
			  	$("#commContent").val("请输入内容！");
			  }
	  });
	  
	  $("#praise").click(function(){
		  var friendId=$("#friendid").val();
		  var contentId= $("#contentId").val();
		  var praNum =  $("#praiseNum").val();
		  $.ajax({
				  url:"/ump/wxController/pointPraise",type:'POST',async:true,
				  data:{"contentId":contentId,"friendId":friendId},timeout: 20000,
				  success: function(data){
					 var praImg ="/ump/images/praise2.png";
					 $("#praStr").attr("src",praImg);
					 $("#praiseNum").html(data);
				  },
				  error: function(){
					  alert("操作失败!");  
				  }
			 });
	  })
});

function formatData(time) {
	var date = new Date(time);
	var str = date.Format("yyyy-MM-dd ");
	return str;
}
Date.prototype.Format = function(fmt) { //author: meizz   
	var o = {
		"M+" : this.getMonth() + 1, //月份   
		"d+" : this.getDate(), //日   
		"h+" : this.getHours(), //小时   
		"m+" : this.getMinutes(), //分   
		"s+" : this.getSeconds(), //秒   
		"q+" : Math.floor((this.getMonth() + 3) / 3), //季度   
		"S" : this.getMilliseconds()
	//毫秒   
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1,
					(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
							.substr(("" + o[k]).length)));
	return fmt;
}
 
  function goodPoint(pk){
	  if("on"==$("#competenceId").val()){
		  $.ajax({url:"./home.do?method=addPraiseCount",type:'POST',async:true,
			  data:{"pk":pk},timeout: 20000,
			  error: function(){
			  alert("操作失败!");  
			  },
			  success: function(data){
				  $("#praiseCount"+pk).html(data);
			  }
			 });
		}else{
	        alert("您没有同意授权，无法点赞");		
		}
  }
 
  function disMore(pk,more){
	  $(".huifu_list").css("display","block");
	  $("#"+more).css("display","none");
  }
  
  function loadInfo(count){
	  var num = parseInt(count);
	  var pageCount = 5;
	  var v = 0;
	  for(i=1;i<=num;i++){
		  var status = $("#main"+i).css("display");
		  if(status=='none' && v<pageCount){
			  $("#main"+i).css("display","block");
			 v++;
		  }
	  }
	  if(v==0){$("#loadText").html("已显示全部")}
  }
