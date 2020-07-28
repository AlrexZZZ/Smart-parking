<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>小区地理位置与周边</title>
<link rel="stylesheet" type="text/css"
	href="/ump/cbdwccui/styles/mystyle.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />

<script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.0.min.js"></script>
<!--百度地图-->
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=uvO8nATtQ9ALkTq4honbNLXm"></script>

</head>
<body>
	<div class="container">
		<!--container-->

		<div class="detail_map" id="detail_map1">
			<!--百度地图放置区-->
			<!--<img src="images/map_02.png" alt="">-->
		</div>
		<!--百度地图放置区 end-->

		<div class="detail_article">
			${wccItemotherinfo.otherIntro }
		</div>

		<div class="detail_back">
			<input type="button" value="返   回"
				onclick="location.href='javascript:history.go(-1)'">
		</div>
		    <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>

	</div>
	<!--container end-->

	<script type="text/javascript">

// 百度地图API功能
var map = new BMap.Map("detail_map1");   // 创建Map实例
map.centerAndZoom(new BMap.Point(${wccAppartment.itemLng},${wccAppartment.itemLat}), 11);
var local = new BMap.LocalSearch(map, {
  renderOptions:{map: map, autoViewport:true}
});
//加载地图标注
var marker1 = new BMap.Marker(new BMap.Point(${wccAppartment.itemLng},${wccAppartment.itemLat}));
map.addOverlay(marker1);
local.searchNearby(${wccAppartment.itemAddress}, "科技");
</script>


</body>
</html>