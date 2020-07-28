<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
		#golist {display: none;}
		@media (max-device-width: 780px){#golist{display: block !important;}}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=iBprAAMUokAEOI81LGvjqev3ZB9A0t7P&v=1.0"></script>
	<title>驾车导航</title>
</head>
<body>

<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	//////////////////////////
	
	
	  var p1 = new BMap.Point(${p1store_lngx}, ${p1store_laty});
      var p2 = new BMap.Point(${store_lngx}, ${store_laty});
      var storeName = '${store}' ;
		// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(${store_lngx}, ${store_laty});
	map.centerAndZoom(point, 15);
	map.addControl(new BMap.ZoomControl());          
	var opts = {
		width : 300,    // 信息窗口宽度
		height: 50,     // 信息窗口高度
		title :  storeName // 信息窗口标题
	}
	var infoWindow = new BMap.InfoWindow("点击红色图标将进入路线查询", opts);  // 创建信息窗口对象
	map.openInfoWindow(infoWindow,point); //开启信息窗口

	var marker1 = new BMap.Marker(new BMap.Point(${store_lngx}, ${store_laty}));  // 创建标注
	map.addOverlay(marker1);              // 将标注添加到地图中
	marker1.addEventListener("click", function(){
	

		var start = {
			// name:"王府井",
			 latlng:p1
		}
		var end = {
		    //	name:"西单",
			 latlng:p2
		}
		var opts = {
			mode:BMAP_MODE_DRIVING,
			region:"上海"
		}

		var ss = new BMap.RouteSearch();
		ss.routeCall(start,end,opts);
	}); 
	
	
	///////////////////////////
	// 百度地图API功能
/* 	var map = new BMap.Map("l-map");
	map.centerAndZoom(new BMap.Point(121.39723, 31.174566), 12);

	 var transit = new BMap.DrivingRoute(map, {
		renderOptions: {
			map: map,
			panel: "r-result",
			enableDragging : true //起终点可进行拖拽
		},  
	});
	 
  var p1 = new BMap.Point(${p1store_lngx}, ${p1store_laty});
 var p2 = new BMap.Point(${store_lngx}, ${store_laty});
	transit.search(p1,p2); */

</script>