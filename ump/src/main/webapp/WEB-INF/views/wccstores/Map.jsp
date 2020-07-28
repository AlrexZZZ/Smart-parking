<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<style type="text/css">
body,html,#container {
	width: 100%;
	height: 100%;
	min-height: 400px;
	max-width: 640px;
	margin: 0px auto;
	clear: both;
	overflow: hidden;
	position: relative;
	display: block;
	overflow: auto;
	margin-bottom: 0px;
}

#allmap {
	width: 100%;
	height: 65%;
}

.tu{
	float:left;
	height:200px;
	width:8%;
	clear:left;
	text-align:center;
}

.tu img{
	margin-top:10px;
	}

.wenzi{
	float:left;
	height:200px;
	width:80%;
	clear:right;
}

.wenzi span{
	font-family:"微软雅黑";
	color:#39F;
	font-size:1.2em;
}

.wenzi p{
	font-family:"微软雅黑";
	color:#000;
	font-size:1em;
}
.dw{ width:100%;max-width:640px; min-width:320px; margin:0px auto; clear:both; }

</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uvO8nATtQ9ALkTq4honbNLXm"></script>
<script type="text/javascript">
function mapInit() {
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(${wccStore.storeLngx},${wccStore.storeLaty});
	map.centerAndZoom(point,15);
	map.enableScrollWheelZoom();
	//加载地图标注
	var marker1 = new BMap.Marker(new BMap.Point(${wccStore.storeLngx},${wccStore.storeLaty}));
	// 将标注添加到地图中
	map.addOverlay(marker1);
}
</script>

<title></title>
</head>
<body onload="mapInit();">
<div id="container">
	<div id="allmap"></div>
<div class="dw">
	<c:if test="${wccStore!=null }">
		<div class="wenzi" style="padding-top: 10px;">
		 <span><c:out value="${wccStore.storeName}"/></span>
			<p style="line-height: 20px;"> 地址：<c:out value="${wccStore.storeAddres}"></c:out><br>
                                                电话：<c:out value="${wccStore.storePhone}"></c:out><br>
             <c:if test="${ ! empty wccStore.storeUrl}">                           
                                                    网址：<c:out value="${wccStore.storeUrl}"></c:out><br>
              </c:if>
              <c:if test="${ ! empty wccStore.storeText}">                      
                                                 备注：<c:out value="${wccStore.storeText}"></c:out>
              </c:if>                          
            </p>
         </div>
	</c:if>
  </div>
</div>
</body>
</html>
