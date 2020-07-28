<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>故障报修</title>
    
    
    <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
	<script src="${kind}" type="text/javascript"><!-- required for FF3 and Opera --></script>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>

<script type="text/javascript">
window.onload = function (){
if($('#itmePk').val() != ""){
	
	document.getElementById('itmePk').style.color = 'black';	
	
}else{
	document.getElementById('itmePk').style.color = '#C0C0C0';
}	
	
	
}



function getInfo(e,text){
	var value = e.value;
	var platId=$("#platId").val();
	  var str="/ump/pageRepair/typeCreate?type="+value+"&platId="+platId;

		window.location.href = str;
		
}

function changeColor(e){

	if(e.value !=  "")
	{
	e.style.color = 'black';	
	}else{
	e.style.color = '#C0C0C0';	
	}
	
}
</script>
<body>

      <form action="/ump/pageRepair/create?" method="post" enctype="multipart/form-data" id="signupForm">
    <div class="container"><!--container-->
    <input type="hidden" value="${platId }" name="platId" id="platId"/>
     <div class="float_head"><!--float_head-->
       <div class="float_head_title" id="float_head_title1">报修服务</div>        
     </div> <!--float_head end--> 
              
    <div class="newpro_content">
     <div class="new_img"><img src="${wcc.imgPath }" alt=""></div><!--new_img-->  
     
 <div class="bd_fromecontant"><!--表单-->
   <div class="search_down_bd"><!--search_down-->
          <select name="itmePk"  onchange="javascript:getInfo(this,this.options[this.selectedIndex].text);" id="itmePk">
            <option style="color: black;" value="">请选择所在的小区(必填)</option>  
            <c:forEach items="${list }" var="br">
            <c:if test="${id==br.id}">
            <option style="color: black;" selected="selected" value="${br.id }">${br.itemName }</option>  
            </c:if>
             <c:if test="${id!=br.id}">
            <option style="color: black;"  value="${br.id}">${br.itemName }</option>  
            </c:if>
            </c:forEach>
          </select>  
      </div> <!--search_down end-->
      
      <div class="search_down_bd"><!--search_down-->
          <select name="malfunctionType" onchange="changeColor(this);" id="malfunctionType">
            <option  value="">请选择报修项目(必填)</option>  
            <c:forEach items="${listMal }" var="br">
            <option value="${br.id }"  >${br.name }</option>
            </c:forEach>
          </select>  
      </div> <!--search_down end-->
      
      <input type="text" name="name" style="color: black;"  id="name" placeholder="在此输入姓名(必填)" class="input_style input_name">
      <input type="text" name="phone" style="color: black;"  id="phone" placeholder="在此输入联系电话(必填)" class="input_style input_phone">
      <input type="text" name="address"  style="color: black;" id="address" placeholder="在此输入地址，如：1棟201室(必填)" class="input_style input_address" id="home_num">
       <div class="rep_img">
        <div class="repimg_input"><input type="text"    class="repimg_input_in" id="re_path_in"></div>        
        <div class="repimg_button">
        <div class="repimg_input_buabout" >上传图片</div>
        <input type="file" name="malPic" readonly="readonly" style="color: black;"  class="repimg_input_bu" accept="image/*" onchange="showPath(this)">
        </div>          
       </div>
       
       <textarea style="color: black;" placeholder="请输入详情" name="remark" id="remark" class="re_detail"></textarea> 

    
       <input type="button" value="确  定"  class="input_submit" onclick="showdiv();" >      
     </div><!--表单 end-->
    
    </div>
    
    </div><!--container end-->
    
<!--弹出框-->
<div class="info_div" id="re_div">
 <div class="info_div_tit">信息提示</div>
 <div class="info_div_con"><!--info_div_con-->
  <div id="xianshi">业主您好：<br>您的故障已报修成功</div>
  
  <div>
  <input type="submit" value="确 定" class="yz_submit" onclick="hideendiv();return false;">
 </div>
 </div><!--info_div_con end-->   
</div> 

<div class="blackcover" id="re_blackcover"></div>   
<!--弹出框 end--> 
    
<script type="text/javascript">

	
function showPath(h){//显示选中图片的路径

  var img_Path=$(h).val();   
  $('#re_path_in').val(img_Path);
  
};

//弹出框
	var re_div_ID="re_div";//弹出层ID
	var re_blackcoverID="re_blackcover"//黑色层ID

function showdiv(){
	var itmePk=$("#itmePk").val();	
	if(itmePk==null || itmePk==""){
		$('#xianshi').html("业主您好：<br>请选择所在小区");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}	
		
	var	malfunctionType=$("#malfunctionType").val();
	if(malfunctionType==null || malfunctionType==""){
		$('#xianshi').html("业主您好：<br>请选择报修项目");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}
	
		
		
	var name=$("#name").val();
	if(name==null || name==""){
		$('#xianshi').html("业主您好：<br>请输入名称");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}
	var phone=$("#phone").val();
	if(phone==null || phone==""){
		$('#xianshi').html("业主您好：<br>请输入电话");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}
	var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
	if(!reg.test(phone)){
		$('#xianshi').html("业主您好：<br>手机格式错误");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}
	var address=$("#address").val();
	if(address==null || address==""){
		$('#xianshi').html("业主您好：<br>请输入地址");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}
	var re_path_in=$("#re_path_in").val();
	if(re_path_in==null || re_path_in==""){
		$('#xianshi').html("业主您好：<br>请上传图片");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}
	var remark=$("#remark").val();
	if(remark==null || remark==""){
		$('#xianshi').html("业主您好：<br>请输入详情");
		 var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
		return false;
	}

	if(itmePk!=null && itmePk!="" && malfunctionType!=null && malfunctionType!="" && name!=null && name!="" && phone!=null && phone!="" && address!=null && address!="" && re_path_in!=null && re_path_in!="" && remark!=null && remark!=""){
		$('#xianshi').html("业主您好：<br>您的故障已报修成功");
		var leftval=(parseInt($(window).width())-parseInt($("#"+re_div_ID).width()))/2;
		 $(window).scrollTop(0);
	     $("#"+re_div_ID).css("left",leftval).show();
	     $("#"+re_blackcoverID).show();   
	     return true;
	}
	
 
 
	
};
    
function hideendiv(){
	var remark=$("#remark").val();
	var re_path_in=$("#re_path_in").val();
	var address=$("#address").val();
	var itmePk=$("#itmePk").val();	
	var	malfunctionType=$("#malfunctionType").val();
	var name=$("#name").val();
	var phone=$("#phone").val();
 	$("#"+re_div_ID).hide();
 	$("#"+re_blackcoverID).hide();
 	var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
	if(itmePk!=null && itmePk!="" && malfunctionType!=null && malfunctionType!="" && name!=null && name!="" && phone!=null && phone!="" && address!=null && address!="" && re_path_in!=null && re_path_in!="" && remark!=null && remark!="" && reg.test(phone)){
	     $("#signupForm").submit();
	     return true;
	}
	
	

};
</script>
</form>
</body>
</html>