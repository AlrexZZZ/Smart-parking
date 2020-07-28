<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=iBprAAMUokAEOI81LGvjqev3ZB9A0t7P"></script>
<script type="text/javascript">
	function checkForm(){
		var dormBuildName=document.getElementById("dormBuildName").value;
		if(dormBuildName==null||dormBuildName==""){
			document.getElementById("error").innerHTML="名称不能为空！";
			return false;
		}
		return true;
	}
	
	$(document).ready(function(){
		$("ul li:eq(3)").addClass("active");
	});
</script>

<div class="data_list" style="overflow-x:hidden; overflow-y:auto;height: 520px;">
		<div class="data_list_title">
		<c:choose>
			<c:when test="${dormBuild.dormBuildId!=null }">
				修改站点2
			</c:when>
			<c:otherwise>
				添加站点
			</c:otherwise>
		</c:choose>
		</div>
		<form action="dormBuild?action=save" method="post" onsubmit="return checkForm()">
			<div class="data_form" >
				<input type="hidden" id="dormBuildId" name="dormBuildId" value="${dormBuild.dormBuildId }"/>
					<table align="center">
						<tr>
							<td><font color="red">*</font>名称：</td>
							<td><input  type="text" id="dormBuildName"  name="dormBuildName" value="${dormBuild.dormBuildName }"  style="margin-top:5px;height:30px;" /></td>
						</tr>
						<tr>
							<td>&nbsp;简介：</td>
							<td><textarea id="detail" name="detail" rows="2">${dormBuild.detail }</textarea></td>
						</tr>
					    <tr>
							<td>&nbsp;经纬度：</td>
							
							<td>
							
						
							
			<c:choose>
			<c:when test="${dormBuild.dormBuildId!=null }">
			    <input type="text" placeholder="经度" id="lngId" name="lng" value="${dormBuild.lng}" readonly="readonly" style="margin-top:5px;height:30px;width: 100px;" />,
				<input type="text" placeholder="纬度" id="latId" name="lat"  value="${dormBuild.lat}"  readonly="readonly" style="margin-top:5px;height:30px;width: 100px;" />
			</c:when>
			<c:otherwise>
				<input type="text" placeholder="经度" id="lngId" name="lng" readonly="readonly" style="margin-top:5px;height:30px;width: 100px;" />,
				<input type="text" placeholder="纬度" id="latId" name="lat"  readonly="readonly" style="margin-top:5px;height:30px;width: 100px;" />
			</c:otherwise>
		</c:choose>			
							
							
							
							</td>
							
							
				
						    <td style="color: red">*添加经纬度 的时候请点击增加地图点击事件</td>
						</tr>
						
					</table>
					<div align="center">
						<input type="submit" class="btn btn-primary" value="保存"/>
						&nbsp;<button class="btn btn-primary" type="button" onclick="javascript:history.back()">返回</button>
					</div>
					<div align="center">
						<font id="error" color="red">${error }</font>
					</div>
			</div>
		</form>
		<div style="width: 100%;height: 450px;">
			<div id="result">
		<button id="open" onclick="addClick()">增加地图点击事件</button>
		<button id="close" onclick="removeClick()">注销地图点击事件</button>
	</div>
			<div id="allmap" style="width:100%;height:500px;"></div>

		
		</div>
</div>
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
		alert("marker的位置是" + p.getPosition().lng + "," + p.getPosition().lat);    
	}
 	 $.ajax( {  
		      url:'/DormManage/dormBuild?action=drawMap',// 跳转到 action  
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
		    			var label = new BMap.Label(objList[i].dormBuildName+"【剩余车位:"+objList[i].detail+"】",{offset:new BMap.Size(10,-5)});
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