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
function getMap(a,b){
	c = $('#c').val();
	d = $('#d').val();	
	if(a && b){
		window.location.href='/ump/pageTLProduct/showProductStroeMap?a='+a+'&b='+b+'&c='+c+'&d='+d;
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
	var areaDis = '${areaDis}';
	if(dis){
		document.getElementById('locationId').innerHTML= areaDis+dis;
	}
	
	getLocation();
}
</script>
<body>
<div>

<input  id="c" type="hidden" />
<input  id="d" type="hidden" />
</div>
<!--弹窗 begin-->
	<div class="zxxy-pop" style="display: none">
		<div class="zxxy-pop-top">
			<p>请选择地区</p>
			<div class="pop-close"><img src="/ump/tailong_ui/img/close.png"/></div>
		</div>
		<div class="zxyy-pop-c">
			<div class="zxyy-pop-c-l">
				<div class="zxyy-area">
					<div class="area-current" data-li="01" onclick="getTThisValue(this);">浦东新区</div>
					<div data-li="02" onclick="getTThisValue1(this);" >徐汇区</div>
					<div data-li="03" onclick="getTThisValue1(this);">黄浦区</div>
					<div data-li="04" onclick="getTThisValue1(this);">卢湾区</div>
					<div data-li="05" onclick="getTThisValue1(this);">静安区</div>
					<div data-li="06" onclick="getTThisValue1(this);">长宁区</div>
					<div data-li="07" onclick="getTThisValue1(this);">闵行区</div>
					<div data-li="08" onclick="getTThisValue1(this);">杨浦区</div>
					<div data-li="09" onclick="getTThisValue1(this);">普陀区</div>
					<div data-li="10" onclick="getTThisValue1(this);">虹口区</div>
					<div data-li="11" onclick="getTThisValue1(this);">宝山区</div>
					<div data-li="12" onclick="getTThisValue1(this);">闸北区</div>
					<div data-li="13" onclick="getTThisValue1(this);">松江区</div>
					<div data-li="14" onclick="getTThisValue1(this);">嘉定区</div>
					<div data-li="15" onclick="getTThisValue1(this);">青浦区</div>
					<div data-li="16" onclick="getTThisValue1(this);">奉贤区</div>
					<div data-li="17" onclick="getTThisValue1(this);">金山区</div>
					<div data-li="18" onclick="getTThisValue1(this);">崇明县</div>
				</div>

			</div>
			<div class="zxyy-pop-c-r">
				<!--浦东新区-->
				<div class="zxyy-area-c" id="area-c01">
					<p onclick="getTThisValue(this);">全部浦东新区</p>
					<p onclick="getTThisValue(this);">陆家嘴</p>
					<p onclick="getTThisValue(this);">八佰伴</p>
					<p onclick="getTThisValue(this);">上南地区</p>
					<p onclick="getTThisValue(this);">世纪公园</p>
					<p onclick="getTThisValue(this);">金桥</p>
					<p onclick="getTThisValue(this);">张江</p>
					<p onclick="getTThisValue(this);">康桥/周浦</p>
					<p onclick="getTThisValue(this);">塘桥</p>
					<p onclick="getTThisValue(this);">川沙</p>
					<p onclick="getTThisValue(this);">金杨地区</p>
					<p onclick="getTThisValue(this);">惠南镇</p>
					<p onclick="getTThisValue(this);">源深体育中心</p>
					<p onclick="getTThisValue(this);">三林地区</p>
					<p onclick="getTThisValue(this);">北蔡</p>
					<p onclick="getTThisValue(this);">外高桥</p>
				</div>
				<!--徐汇区-->
				<div class="zxyy-area-c" style="display:none" id="area-c02">
					<p>全部徐汇区</p>
					<p onclick="getTThisValue(this);">徐家汇</p>
					<p onclick="getTThisValue(this);">音乐学院</p>
					<p onclick="getTThisValue(this);">万体馆</p>
					<p onclick="getTThisValue(this);">漕河泾/田林</p>
					<p onclick="getTThisValue(this);">肇嘉浜路沿线</p>
					<p onclick="getTThisValue(this);">复兴西路/丁香花园</p>
					<p onclick="getTThisValue(this);">衡山路</p>
					<p onclick="getTThisValue(this);">龙华</p>
					<p onclick="getTThisValue(this);">光启城</p>
					<p onclick="getTThisValue(this);">上海南站</p>
				</div>
				<!--黄浦区-->
				<div class="zxyy-area-c" style="display:none" id="area-c03">
					<p>全部黄浦区</p>
					<p onclick="getTThisValue(this);">人民广场</p>
					<p onclick="getTThisValue(this);">南京东路</p>
					<p onclick="getTThisValue(this);">外滩</p>
					<p onclick="getTThisValue(this);">老西门</p>
					<p onclick="getTThisValue(this);">董家渡</p>
					<p onclick="getTThisValue(this);">城隍庙</p>
				</div>
				<!--卢湾区-->
				<div class="zxyy-area-c" style="display:none" id="area-c04">
					<p>全部卢湾区</p>
					<p onclick="getTThisValue(this);">淮海路</p>
					<p onclick="getTThisValue(this);">打浦桥</p>
					<p onclick="getTThisValue(this);">新天地</p>
					<p onclick="getTThisValue(this);">瑞金宾馆区</p>
				</div>
				<!--静安区-->
				<div class="zxyy-area-c" style="display:none" id="area-c05">
					<p>全部静安区</p>
					<p onclick="getTThisValue(this);">静安寺</p>
					<p onclick="getTThisValue(this);">南京西路</p>
					<p onclick="getTThisValue(this);">同乐坊</p>
					<p onclick="getTThisValue(this);">曹家渡</p>
					<p onclick="getTThisValue(this);">长寿路</p>
				</div>
				<!--长宁区-->
				<div class="zxyy-area-c" style="display:none" id="area-c06">
					<p>全部长宁区</p>
					<p onclick="getTThisValue(this);">中山公园</p>
					<p onclick="getTThisValue(this);">虹桥</p>
					<p onclick="getTThisValue(this);">天山</p>
					<p onclick="getTThisValue(this);">上海影城/新华路</p>
					<p onclick="getTThisValue(this);">古北</p>
					<p onclick="getTThisValue(this);">北新泾</p>
					<p onclick="getTThisValue(this);">动物园/虹桥机场</p>
				</div>
				<!--闵行区-->
				<div class="zxyy-area-c" style="display:none" id="area-c07">
					<p>全部闵行区</p>
					<p onclick="getTThisValue(this);">虹桥镇</p>
					<p onclick="getTThisValue(this);">莘庄</p>
					<p onclick="getTThisValue(this);">七宝</p>
					<p onclick="getTThisValue(this);">南方商城</p>
					<p onclick="getTThisValue(this);">龙柏地区</p>
					<p onclick="getTThisValue(this);">虹梅路</p>
					<p onclick="getTThisValue(this);">老闵行</p>
					<p onclick="getTThisValue(this);">万源路/东兰路</p>
					<p onclick="getTThisValue(this);">仲盛商业中心</p>
					<p onclick="getTThisValue(this);">莘庄龙之梦</p>
					<p onclick="getTThisValue(this);">浦江镇</p>
					<p onclick="getTThisValue(this);">春申地区</p>
					<p onclick="getTThisValue(this);">华漕</p>
					<p onclick="getTThisValue(this);">颛桥</p>
					<p onclick="getTThisValue(this);">好爱广场</p>
				</div>
				<!--杨浦区-->
				<div class="zxyy-area-c" style="display:none" id="area-c08">
					<p>全部杨浦区</p>
					<p onclick="getTThisValue(this);">五角场/大学区</p>
					<p onclick="getTThisValue(this);">平凉路</p>
					<p onclick="getTThisValue(this);">控江地区</p>
					<p onclick="getTThisValue(this);">中原地区</p>
					<p onclick="getTThisValue(this);">黄兴公园</p>
					<p onclick="getTThisValue(this);">鞍山新村</p>
				</div>
				<!--普陀区-->
				<div class="zxyy-area-c" style="display:none" id="area-c09">
					<p>全部普陀区</p>
					<p onclick="getTThisValue(this);">长寿路</p>
					<p onclick="getTThisValue(this);">梅川路</p>
					<p onclick="getTThisValue(this);">月星环球港</p>
					<p onclick="getTThisValue(this);">长风公园/华师大</p>
					<p onclick="getTThisValue(this);">中山北路/甘泉地区</p>
					<p onclick="getTThisValue(this);">武宁地区</p>
					<p onclick="getTThisValue(this);">曹杨地区</p>
					<p onclick="getTThisValue(this);">曹家渡</p>
					<p onclick="getTThisValue(this);">桃浦</p>
				</div>
				<!--虹口区-->
				<div class="zxyy-area-c" style="display:none" id="area-c10">
					<p>全部虹口区</p>
					<p onclick="getTThisValue(this);">虹口足球场</p>
					<p onclick="getTThisValue(this);">四川北路</p>
					<p onclick="getTThisValue(this);">临平路/和平公园</p>
					<p onclick="getTThisValue(this);">北外滩</p>
					<p onclick="getTThisValue(this);">海宁路/七浦路</p>
					<p onclick="getTThisValue(this);">凉城路/江湾镇</p>
					<p onclick="getTThisValue(this);">曲阳地区</p>
					<p onclick="getTThisValue(this);">赤峰路</p>
				</div>
				<!--宝山区-->
				<div class="zxyy-area-c" style="display:none" id="area-c11">
					<p>全部宝山区</p>
					<p onclick="getTThisValue(this);">庙行/共康</p>
					<p onclick="getTThisValue(this);">大华地区</p>
					<p onclick="getTThisValue(this);">通河/泗塘</p>
					<p onclick="getTThisValue(this);">宝山城区</p>
					<p onclick="getTThisValue(this);">顾村公园</p>
					<p onclick="getTThisValue(this);">淞滨地区</p>
					<p onclick="getTThisValue(this);">高境</p>
					<p onclick="getTThisValue(this);">淞南</p>
					<p onclick="getTThisValue(this);">美兰湖</p>
					<p onclick="getTThisValue(this);">杨行</p>
					<p onclick="getTThisValue(this);">月浦</p>
				</div>
				<!--闸北区-->
				<div class="zxyy-area-c" style="display:none" id="area-c12">
					<p>全部闸北区</p>
					<p onclick="getTThisValue(this);">大宁地区</p>
					<p onclick="getTThisValue(this);">火车站</p>
					<p onclick="getTThisValue(this);">闸北公园</p>
					<p onclick="getTThisValue(this);">彭浦新村</p>
					<p onclick="getTThisValue(this);">西藏北路/中兴路</p>
					<p onclick="getTThisValue(this);">大悦城</p>
					<p onclick="getTThisValue(this);">北区汽车站</p>
					<p onclick="getTThisValue(this);">市北工业园/汶水路</p>
					<p onclick="getTThisValue(this);">彭浦镇</p>
					<p onclick="getTThisValue(this);">杨行</p>
					<p onclick="getTThisValue(this);">月浦</p>
				</div>
				<!--松江区-->
				<div class="zxyy-area-c" style="display:none" id="area-c13">
					<p>全部松江区</p>
					<p onclick="getTThisValue(this);">松江大学城</p>
					<p onclick="getTThisValue(this);">九亭</p>
					<p onclick="getTThisValue(this);">松江镇</p>
					<p onclick="getTThisValue(this);">佘山</p>
					<p onclick="getTThisValue(this);">松江万达</p>
					<p onclick="getTThisValue(this);">开元地中海</p>
					<p onclick="getTThisValue(this);">泰晤士小镇</p>
					<p onclick="getTThisValue(this);">新桥</p>
					<p onclick="getTThisValue(this);">鹿都国际广场</p>
					<p onclick="getTThisValue(this);">中山中路</p>
					<p onclick="getTThisValue(this);">新松江路</p>
					<p onclick="getTThisValue(this);">江学路</p>
					<p onclick="getTThisValue(this);">泗泾</p>
					<p onclick="getTThisValue(this);">松东路</p>
					<p onclick="getTThisValue(this);">荣乐中路</p>
					<p onclick="getTThisValue(this);">飞航广场</p>
					<p onclick="getTThisValue(this);">人民北路</p>
					<p onclick="getTThisValue(this);">嘉和广场</p>
				</div>
				<!--嘉定区-->
				<div class="zxyy-area-c" style="display:none" id="area-c14">
					<p>全部嘉定区</p>
					<p onclick="getTThisValue(this);">嘉定镇</p>
					<p onclick="getTThisValue(this);">江桥</p>
					<p onclick="getTThisValue(this);">南翔</p>
					<p onclick="getTThisValue(this);">安亭</p>
					<p onclick="getTThisValue(this);">丰庄</p>
					<p onclick="getTThisValue(this);">马陆</p>
					<p onclick="getTThisValue(this);">罗宾森广场</p>
					<p onclick="getTThisValue(this);">嘉定新城</p>
					<p onclick="getTThisValue(this);">博乐广场</p>
					<p onclick="getTThisValue(this);">新源路</p>
					<p onclick="getTThisValue(this);">黄渡</p>
					<p onclick="getTThisValue(this);">外冈</p>
					<p onclick="getTThisValue(this);">戬浜</p>
					<p onclick="getTThisValue(this);">松东路</p>
					<p onclick="getTThisValue(this);">荣乐中路</p>
					<p onclick="getTThisValue(this);">飞航广场</p>
					<p onclick="getTThisValue(this);">人民北路</p>
					<p onclick="getTThisValue(this);">嘉和广场</p>
				</div>
				<!--青浦区-->
				<div class="zxyy-area-c" style="display:none" id="area-c15">
					<p>全部青浦区</p>
					<p onclick="getTThisValue(this);">青浦城区</p>
					<p onclick="getTThisValue(this);">朱家角</p>
					<p onclick="getTThisValue(this);">赵巷</p>
					<p onclick="getTThisValue(this);">徐泾</p>
					<p onclick="getTThisValue(this);">华新</p>
					<p onclick="getTThisValue(this);">重固</p>
				</div>
				<!--奉贤区-->
				<div class="zxyy-area-c" style="display:none" id="area-c16">
					<p onclick="getTThisValue(this);">全部奉贤区</p>
					<p onclick="getTThisValue(this);">南桥</p>
					<p onclick="getTThisValue(this);">海湾旅游区</p>
					<p onclick="getTThisValue(this);">百联南桥购物中心</p>
					<p onclick="getTThisValue(this);">新都汇</p>
					<p onclick="getTThisValue(this);">西渡</p>
					<p onclick="getTThisValue(this);">环城东路</p>
					<p onclick="getTThisValue(this);">易买得</p>
					<p onclick="getTThisValue(this);">南桥新城区</p>
					<p onclick="getTThisValue(this);">环城南路</p>
					<p onclick="getTThisValue(this);">奉城镇</p>
					<p onclick="getTThisValue(this);">金汇镇</p>
				</div>
				<!--金山区-->
				<div class="zxyy-area-c" style="display:none" id="area-c17">
					<p>全部金山区</p>
					<p onclick="getTThisValue(this);">金山卫</p>
					<p onclick="getTThisValue(this);">金山万达</p>
					<p onclick="getTThisValue(this);">百联南桥购物中心</p>
					<p onclick="getTThisValue(this);">枫泾</p>
					<p onclick="getTThisValue(this);">朱泾</p>
					<p onclick="getTThisValue(this);">石化</p>
					<p onclick="getTThisValue(this);">金山嘴</p>
					<p onclick="getTThisValue(this);">蒙山路</p>
					<p onclick="getTThisValue(this);">卫清路</p>
					<p onclick="getTThisValue(this);">卫零路</p>
					<p onclick="getTThisValue(this);">亭林</p>
					<p onclick="getTThisValue(this);">金山新城</p>
					<p onclick="getTThisValue(this);">朱行</p>
				</div>
				<!--崇明县-->
				<div class="zxyy-area-c" style="display:none" id="area-c18">
					<p>全部崇明县</p>
					<p onclick="getTThisValue(this);">崇明县其他</p>
					<p onclick="getTThisValue(this);">长兴镇</p>
					<p onclick="getTThisValue(this);">堡镇</p>
					<p onclick="getTThisValue(this);">新河镇</p>
					<p onclick="getTThisValue(this);">城桥镇</p>
				</div>
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
		$(".zxyy-area-c p").click(function(){
			$(".location-p").show();
			$(".zxxy-pop").hide();
		});


	})
	
	

</script>

	<div class="main wdxx">
		<img src="/ump/tailong_ui/img/wdxx.jpg" alt="" width="100%">
		<div class="title">
			分支行查询
		</div>
		<div id="locationId" class="choose-area">
			选择区域
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
					<p onclick="getMap(${store.storeLngx},${store.storeLaty});"><img src="/ump/tailong_ui/img/addr.png"/><span>查看地图</span></p>
				</div>
			</div>
		</c:forEach>	
		</div>
		
	</div>
</body>
</html>