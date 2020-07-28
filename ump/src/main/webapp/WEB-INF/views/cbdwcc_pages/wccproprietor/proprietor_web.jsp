<jsp:directive.page contentType="text/html;charset=UTF-8" />
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>业主信息</title>
  <link rel="stylesheet" type="text/css" href="../cbdwccui/styles/mystyle.css">
   <script type="text/javascript" src="./../cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<body>
    <div class="container"><!--container-->
    
     <div class="float_head" onclick="show_downlist()" id="float_head1"><!--float_head-->
       <div class="float_head_title" id="float_head_title1">业主信息</div>        
     </div> <!--float_head end--> 
              
    <div class="newpro_content">
     <div class="new_img"><img src="${wcc.imgPath }" alt=""></div><!--new_img-->  
     
     <div class="bd_fromecontant" id="bd_fromecontant"><!--表单-->
      <form action="">
        <div class="search_down_bd"><!--search_down-->
          <select  id="itempk" name="itempk" onchange="che(this);">
       <c:forEach items="${apartment }" var="data" varStatus="status">
       <c:if test="${data.id==proprietor.wccappartment.id }">
          <option value="${data.id}" selected="selected">${data.itemName}</option>
       </c:if>
       <c:if test="${data.id!=proprietor.wccappartment.id }">
              <option value="${data.id}">${data.itemName}</option>
       </c:if>
        </c:forEach>
      
          </select>  
      </div> <!--search_down end-->
      
      <input type="text" style="color: black;" placeholder="在此输入姓名" class="input_style input_name" name="name" id="nameid" value="${proprietor.name }" onblur="che(this);">
      <input type="text" style="color: black;" placeholder="在此输入联系电话" class="input_style input_phone" name="phone" id="phoneid" value="${proprietor.phone }" onblur="che(this);">
      <!-- <input type="text" placeholder="在此输入地址，如：1棟201室" class="input_style input_address" name="doorplate" id="doorplateid" value="${proprietor.doorplate }"> -->
    <div class="search_down_bd">
      <select id="doorplateid" onchange="changeColor(this);" name="doorplate">
      	<option value="${proprietor.doorplate }">${proprietor.doorplate }</option>
      </select>  
      </div>
      <c:if test="${isensured==0 }">
      <input type="button" value="登  记" onclick="bindprob();" class="input_submit" id="dengji" > 
      <input type="button" value="修改信息" onclick="updateprob();" class="input_submit" style="display: none;"  id="change_button">
      </c:if>
       <c:if test="${isensured==1 }">
      <input type="button" value="修改信息" onclick="updateprob();" class="input_submit"  id="change_button">
      </c:if>
      <input type="hidden" value="" id="tishiyu" name="tis"/> 
      </form>     
     </div><!--表单 end-->
    
    </div>
   		    <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>
    </div><!--container end-->
<!--弹出框-->
<div class="info_div" id="info_div_yz">
 <div class="info_div_tit">信息提示</div>
 <div class="info_div_con"><!--info_div_con-->
  <div id="msgid"></div>  
  <div>
  <input type="button" value="确 定" onclick="hid_div(this)" class="yz_submit">
 </div>
 
 </div><!--info_div_con end--> 
 
</div> 

<div class="blackcover" id="blackcover1"></div>   
<!--弹出框 end--> 
    
<script type="text/javascript">
function changeColor(e){

	if(e.value !=  "")
	{
	e.style.color = 'black';	
	}else{
	e.style.color = '#C0C0C0';	
	}
	
}


  function che(e){
	  
		if(e.value !=  "")
		{
		e.style.color = 'black';	
		}else{
		e.style.color = '#C0C0C0';	
		}
	 
	  var friendId="${friendId}";
		var name=$("#nameid").val();;
		var phone=$("#phoneid").val();
		var itempk=$("#itempk").val();
		var param={};
		param.friendId=friendId;
		param.name=name;
		param.phone=phone;
		param.itempk=itempk;
	  var url = "/ump/pageProprietor/chaxun";
		$.ajax({
			url : url,
			async : false,
			dataType : "text",
			type : "POST",
			data :param,
			error : function(msg) {
			},
			success : function(data) {
					// console.log(data);
					var da=eval(data);
					if(da!=null){
					  for(var i = 0; i< da.length; i++){  
	                        //str += "<tr><td><input type='checkbox' id='fieldName' name='fieldName'/>"+fieldList[i].fieldName+"<td></tr>";  
	                        //str += "<option value='"+fieldList[i].id+"'>"+fieldList[i].fieldName+"</option>";  
	                        $("<option value='"+da[i].doorplate+"'>"+da[i].doorplate+"</option>").appendTo("#doorplateid");  
	                    } 
					}
			}
		});
  }
  
  
 var info_div_id="info_div_yz";//弹出层的ID
 var blackcover_id="blackcover1";//弹出黑色层的ID
 var formstyle_id="bd_fromecontant";//form 层的ID 
 var comm_id="itempk";//选择小区下拉框ID
 var home_id="doorplateid";//填写住址文本框ID
 var show_comm_id="comm_name";//弹出框显示小区ID
 var show_home_id="comm_homenum";//弹出框显示填写住址ID
 var dengji_id="dengji";//登记按钮ID
 var chage_button_id="change_button";//修改信息按钮ID
   
 
 function showdiv(msgtext){//点击确定中央显示弹出框、把表单中小区名、详细地址显示在弹出框
  var leftval=(parseInt($(window).width())-parseInt($("#"+info_div_id).width()))/2;
  $("#msgid").html(msgtext);
  $("#"+info_div_id).css("left",leftval).show();
  $("#"+blackcover_id).show();   
 };

  function hid_div(e){//隐藏弹出框、把所有表单元素变为不可
	 	$("#"+info_div_id).hide();
	  	$("#"+blackcover_id).hide();
		   	var friendId="${friendId}";
			var name=$("#nameid").val();;
			var phone=$("#phoneid").val();
			var itempk=$("#itempk").val();
			var doorplate=$("#doorplateid").val();
			var dengji=$("#dengji").val();
			var change_button=$("#change_button").val();
		if(dengji!=null && dengji!="" && dengji=="登  记"){
			var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
			if(friendId==null||friendId==undefined||friendId=="")
			{
			$("#"+dengji_id).show();
			 $("#"+chage_button_id).hide();
			return;
			}
			if(name==null||name==undefined||name=="")
				{
				$("#"+dengji_id).show();
				 $("#"+chage_button_id).hide();
				return;
				}
			if(phone==null||phone==undefined||phone=="")
				{
				$("#"+dengji_id).show();
				 $("#"+chage_button_id).hide();
				return;
				}
		    if(!reg.test(phone))
		       {
		  	 	 $("#phoneid").val('');
		  			$("#"+dengji_id).show();
		  			 $("#"+chage_button_id).hide();
		  		return;
		       }
			if(itempk==null||itempk==undefined||itempk=="")
			{
				$("#"+dengji_id).show();
				 $("#"+chage_button_id).hide();
				return;
			}
			if(doorplate==null||doorplate==undefined||doorplate=="")
			{
				$("#"+dengji_id).show();
				  $("#"+chage_button_id).hide();
				return;
			}
		}
		else{
			var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
			if(friendId==null||friendId==undefined||friendId=="")
			{
			$("#"+dengji_id).hide();
			 $("#"+chage_button_id).show();
			return;
			}
			if(name==null||name==undefined||name=="")
				{
				$("#"+dengji_id).hide();
				 $("#"+chage_button_id).show();
				return;
				}
			if(phone==null||phone==undefined||phone=="")
				{
				$("#"+dengji_id).hide();
				 $("#"+chage_button_id).show();
				return;
				}
		    if(!reg.test(phone))
		       {
		  	 	 $("#phoneid").val('');
		  	 	$("#"+dengji_id).hide();
				 $("#"+chage_button_id).show();
		  		return;
		       }
			if(itempk==null||itempk==undefined||itempk=="")
			{
				$("#"+dengji_id).hide();
				 $("#"+chage_button_id).show();
				return;
			}
			if(doorplate==null||doorplate==undefined||doorplate=="")
			{
				$("#"+dengji_id).hide();
				 $("#"+chage_button_id).show();
				return;
			}
		}
		if( $("#tishiyu").val()!=null &&  $("#tishiyu").val()!=""){
			$("#"+dengji_id).show();
			  $("#"+chage_button_id).hide();
		 		return;
		}
		 $("#"+dengji_id).hide();
		  $("#"+chage_button_id).show();
	 // WeixinJSBridge.call('closeWindow');
  };
function bindprob()
{
	var friendId="${friendId}";
	var name=$("#nameid").val();;
	var phone=$("#phoneid").val();
	var itempk=$("#itempk").val();
	var doorplate=$("#doorplateid").val();
	var param={};
	param.friendId=friendId;
	param.name=name;
	param.phone=phone;
	param.itempk=itempk;
	param.doorplate=doorplate;
	var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
	if(friendId==null||friendId==undefined||friendId=="")
	{
	showdiv("粉丝PK为空，不能进行绑定");
	return;
	}
	if(name==null||name==undefined||name=="")
		{
		showdiv("名称不能为空");
		return;
		}
	if(phone==null||phone==undefined||phone=="")
		{
		showdiv("电话号码不能为空");
		return;
		}
    if(!reg.test(phone))
       {
    	 showdiv("手机号码格式不正确");
  	 	 $("#phoneid").val('');
  		 return;
       }
	if(itempk==null||itempk==undefined||itempk=="")
	{
		showdiv("项目信息不能为空");
		return ;
	}
	if(doorplate==null||doorplate==undefined||doorplate=="")
	{
		showdiv("门牌号不能为空");
		return;
	}
	var url = "/ump/pageProprietor/bindprop";
	$.ajax({
		url : url,
		async : false,
		dataType : "text",
		type : "POST",
		data :param,
		error : function(msg) {
		},
		success : function(data) {
			if(data=='绑定失败,没有该业主信息'){
				 $("#"+dengji_id).hide();
				 $("#tishiyu").val(data);
			}
			if(data=='绑定失败,该信息已被认证'){
				 $("#"+dengji_id).hide();
				 $("#tishiyu").val(data);
			}
			
			showdiv(data);	
		}
	});
	
}
function updateprob()
{
	var friendId="${friendId}";
	var name=$("#nameid").val();;
	var phone=$("#phoneid").val();
	var itempk=$("#itempk").val();
	var doorplate=$("#doorplateid").val();
	var param={};
	//var reg=/^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9]|186)[0-9]{8}$/;
	var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
	param.friendId=friendId;
	param.name=name;
	param.phone=phone;
	param.itempk=itempk;
	param.doorplate=doorplate;
	if(friendId==null||friendId==undefined||friendId=="")
	{
		showdiv("粉丝PK为空，不能进行绑定修改");
		 $("#"+dengji_id).hide();
		  $("#"+chage_button_id).show();
		return;
	}
	if(name==null||name==undefined||name=="")
		{
		showdiv("名称不能为空");
		 $("#"+dengji_id).hide();
		  $("#"+chage_button_id).show();
		return;
		}
	if(phone==null||phone==undefined||phone=="")
		{
		showdiv("电话号码不能为空");
		 $("#"+dengji_id).hide();
		  $("#"+chage_button_id).show();
		return;
		}
	  if(!reg.test(phone))
      {
   		    showdiv("手机号码格式不正确");
   			 $("#"+dengji_id).hide();
   		 	 $("#"+chage_button_id).show();
 	 		$("#phoneid").val('');
 	 	return;
      }
	if(itempk==null||itempk==undefined||itempk=="")
	{
	showdiv("项目信息不能为空");
	 $("#"+dengji_id).hide();	
	  $("#"+chage_button_id).show();
	return ;
	}
	if(doorplate==null||doorplate==undefined||doorplate=="")
	{
		showdiv("门牌号不能为空");
		 $("#"+dengji_id).hide();
		  $("#"+chage_button_id).show();
		return;
	}
	var url = "/ump/pageProprietor/updateprop";
	$.ajax({
		url : url,
		async : false,
		dataType : "text",
		type : "POST",
		data :param,
		error : function(msg) {
		},
		success : function(data) {
			showdiv(data);
			
		}
	});
	
}
</script>
</body>
</html>



