<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="/ump/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=iBprAAMUokAEOI81LGvjqev3ZB9A0t7P"></script>
<!-- <script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=iBprAAMUokAEOI81LGvjqev3ZB9A0t7P&v=1.0"></script> -->



			<div id="allmap" style="width:100%;height:500px;"></div>

		
		</div>
</div>

<script>
var s1,s2;
function getLocation()
  {
  if (navigator.geolocation)
    {
    navigator.geolocation.getCurrentPosition(showPosition);
    }
  else{x.innerHTML="Geolocation is not supported by this browser.";}
  }
function showPosition(position)
  {
   s2 = position.coords.latitude;
   s1 = position.coords.longitude;
  }
  window.onload = function (){
	  getLocation();
  }
</script>

<script type="text/javascript">
	//////////////////////////
	
	


</script>


<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 16);
	function showInfo(e){
		if(confirm("确定在这里添加站点吗？")){
			document.getElementById("lngId").value=e.point.lng;
			document.getElementById("latId").value=e.point.lat;
			//alert(e.point.lng + ", " + e.point.lat);
		}
			
	}
	function addClick(){
		map.addEventListener("click", showInfo);
	}
	function removeClick(){
		map.removeEventListener("click", showInfo);
	}

	function attribute(e){
		var p = e.target;
	//	alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);  
	
var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(p.getPosition().lng, p.getPosition().lat), 11);

var p1 = new BMap.Point(s1,s2);
var p2 = new BMap.Point(p.getPosition().lng,p.getPosition().lat);

var driving = new BMap.DrivingRoute(map, {renderOptions:{map: map, autoViewport: true}});
driving.search(p1, p2);		
		/////////////////////////////////
	}
 	 $.ajax( {  
		      url:'/ump/pageBding/showMap',// 跳转到 action  
		      type:'post',  
		      async : false,
		      success:function(data) {
		  		var json_data = new Array();
		    	  console.log(data);
		    	  var objList = eval(data);
		      for(var j=0;j<objList.length;j++){
		    	 
		    	  json_data.push([objList[j].lng,objList[j].lat]);
		      }
		     
		     
	 	    		var pointArray = new Array();
		    		for(var i=0;i<json_data.length;i++){
		    			var marker = new BMap.Marker(new BMap.Point(json_data[i][0], json_data[i][1])); // 创建点
		    			var label = new BMap.Label(objList[i].dormBuildName+"【剩余车位:"+objList[i].dormBuildDetail+"】",{offset:new BMap.Size(10,-5)});
		    			marker.setLabel(label);
		    			map.addOverlay(marker);    //增加点
		    			pointArray[i] = new BMap.Point(json_data[i][0], json_data[i][1]);
		    			marker.addEventListener("click",attribute);
		    		}
		    		map.setViewport(pointArray); 
		      },  
		      error : function() {  
 
		      }  
		 });  
</script>