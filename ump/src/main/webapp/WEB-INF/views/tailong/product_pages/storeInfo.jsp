<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
	<meta content="yes" name="apple-mobile-web-app-capable" />
	<meta content="black" name="apple-mobile-web-app-status-bar-style" />
	<meta content="telephone=no" name="format-detection" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>网点信息</title>
	<link rel="stylesheet" href="/ump/tailong_ui/css/index.css">
</head>
<script type="text/javascript">
var area='浦东新区',district,c,d;
var urlStr ;
function queryStore(){
	
	
//	window.location.href='/ump/pageTLProduct/showProductStroe?area='+district;
	$("#searchForm").submit();
}

function getLocation()
{
if (navigator.geolocation)
  {
  var obj = navigator.geolocation.getCurrentPosition(showPosition);
  return obj;
  }
else{x.innerHTML="Geolocation is not supported by this browser.";}
}
function showPosition(position)
{

	$('#c').val(position.coords.longitude);
	$('#d').val(position.coords.latitude);
	
}
function getMap(a,b,name){
	c = $('#c').val();
	d = $('#d').val();	
	if(a && b){
	
		window.location.href='/ump/pageTLProduct/showProductStroeMap?a='+a+'&b='+b+'&c='+c+'&d='+d+'&store='+encodeURIComponent(name);
	}
	
	
}

function getTThisValue1(e){
	area = e.innerHTML;
	$("#areaDisId").val(area);
}
function getTThisValue(e){
	district = e.innerHTML;
	$("#districtId").val(district);
	document.getElementById('locationId').innerHTML= area+e.innerHTML;
}
window.onload = function(){
	var dis = '${area}';
	//var areaDis = '${areaDis}';
	if(dis){
		document.getElementById('areaDisDivId').innerHTML= dis;
	}
	
	getLocation();
}

function hideDis(e){
	if(e.innerHTML == '全部区'){
		$("#districtId").val("");
		document.getElementById('areaDisDivId').innerHTML= '全部区';
	}else{
		$("#districtId").val(e.innerHTML);
		document.getElementById('areaDisDivId').innerHTML= e.innerHTML;
	}
	//$(".wd-area-pop").hide();

	
	$('#disDivId').hide();

	}
</script>
<body>
<div>

<input  id="c" type="hidden" />
<input  id="d" type="hidden" />
</div>
<!--弹窗 begin-->
<div  id="disDivId" class="wd-area-pop" style="display:none">
	<div class="wd-area">
       <div class="wd-area-h"><span><img src="/ump/tailong_ui/img/pop-close.png"/></span>请选择地区</div>

			<div  class="zxyy-area02">
			    <div onclick="hideDis(this);">全部区</div>
				<div onclick="hideDis(this);">浦东新区</div>
				<div onclick="hideDis(this);">徐汇区</div>
				<div onclick="hideDis(this);">黄浦区</div>
				<div onclick="hideDis(this);">卢湾区</div>
				<div onclick="hideDis(this);">静安区</div>
				<div onclick="hideDis(this);">长宁区</div>
				<div onclick="hideDis(this);">闵行区</div>
				<div onclick="hideDis(this);">杨浦区</div>
				<div onclick="hideDis(this);">普陀区</div>
				<div onclick="hideDis(this);">虹口区</div>
				<div onclick="hideDis(this);">宝山区</div>
				<div onclick="hideDis(this);">闸北区</div>
				<div onclick="hideDis(this);">松江区</div>
				<div onclick="hideDis(this);">嘉定区</div>
				<div onclick="hideDis(this);">青浦区</div>
				<div onclick="hideDis(this);">奉贤区</div>
				<div onclick="hideDis(this);">金山区</div>
				<div onclick="hideDis(this);">崇明县</div>
			</div>
    </div>
</div>
<!--弹窗 end-->

<script src="/ump/tailong_ui/js/jquery-1.10.2.min.js" type="text/javascript"></script>
<script>

$(document).ready(function(){
	//弹窗效果
	$(".choose-area").click(function(){
		$(".zxxy-pop").show();
	});
	$(".pop-close").click(function(){
		$(".zxxy-pop").hide();
	});


	//切换效果
	$(".zxyy-area-c").hide();
	$("#area-c"+$(".zxyy-area div.area-current").attr("data-li")).show();
	$(".zxyy-area div").on('click', function(event) {
		if (!$(this).hasClass('area-current')) {
			$(".zxyy-area div").removeClass('area-current');
			$(this).addClass('area-current');
			$(".zxyy-area-c").hide();
			$("#area-c"+$(".zxyy-area div.area-current").attr("data-li")).show();
		}
	});

	//地区选择
	$(".choose-area").click(function(){
		var e=window.event || event;
		if(e.stopPropagation){
			e.stopPropagation();
		}else{
			e.cancelBubble = true;
		}
		$(".wd-area-pop").show();



		$(".wd-area").click(function(event){
			var e=window.event || event;
			if(e.stopPropagation){
				e.stopPropagation();
			}else{
				e.cancelBubble = true;
			}

		});

		//点击空白处弹框消失
		document.onclick = function(){
			$(".wd-area-pop").hide();
		};

	});

	$(".wd-area-h span").click(function(){
		$(".wd-area-pop").hide();
	});
})
</script>

	<div class="main wdxx">
		<img src="/ump/tailong_ui/img/wdxx.jpg" alt="" width="100%">
		<div class="title">
			分支行查询
		</div>
		<div id="locationId" class="choose-area">
			<div id="areaDisDivId">选择区域</div>
			<p class="location-p" style="display: none"></p><span class="ico"><img src="/ump/tailong_ui/img/arrow.png" alt=""></span>
		</div>
		<div style="display: none;">
		<form id="searchForm" action="" method="post">
		<input type="text" value="${area}" name="area" id="districtId" /> 
		<input type="text" value="${areaDis}" name="areaDis" id="areaDisId" />
		</form>
		</div>
		<div class="search" onclick="queryStore();">查询</div>

		<!--查询列表-->
		<div class="wdxx-list">
		<c:forEach var="store" varStatus="varStatus" items="${storeList}">
			<div class="wdxx-list-c">
                <div class="wdxx-h">${store.storeName}</div>
				<div class="wdxx-p"><span>地址:</span>${store.storeAddres}</div>
				<div class="wdxx-p"><span>电话:</span>${store.storePhone}</div>
				<div class="wdxx-p"><span>营业时间:</span>${store.storeText}</div>
				<div class="wdxx-btm">
					<p><a href="tel:${store.storePhone}"><img src="/ump/tailong_ui/img/tel.png"/><span>拨打电话</span></a></p>
					<p onclick="getMap(${store.storeLngx},${store.storeLaty},'${store.storeName}');"><img src="/ump/tailong_ui/img/addr.png"/><span>查看地图</span></p>
				</div>
			</div>
		</c:forEach>	
		</div>
		
	</div>
</body>
</html>