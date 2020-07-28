<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="js/date/skin/WdatePicker.css" />
<script src="js/date/WdatePicker.js" type="text/javascript"></script>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function query() {
		var url = $("#url").val();
		var platformUser = $("#platformUser").val();
		var startTime = $("#startTimeId").val();
		var endTime = $("#endTimeId").val();
		$.post("/ump/wxtests/getWxDate", {
			"url" : url,
			"platformUser" : platformUser,
			"startTime" : startTime,
			"endTime" : endTime
		}, function(data) {
			alert(data);
		});
	}
</script>
</head>
<body>
	<table>
		<tr>
			<td>url <input name="url" id="url" /></td>
			<td><a
				href="http://mp.weixin.qq.com/wiki/3/ecfed6e1a0a03b5f35e5efac98e864b7.html"
				target="_blank">获取url</a></td>
		</tr>
		<tr>
			<td><label>公众平台：</label> <select name="platformUser"
				id="platformUser">
					<c:forEach items="${platformUsers}" var="platformUser">
						<option value="${platformUser.id}">${platformUser.account}</option>
					</c:forEach>
			</select>
		</tr>
		<tr>
			<td>时间： <input type="text" class="Wdate"
				style="height: 28px; border: 1px solid #e5e5e5;" id="startTimeId"
				placeholder="小于结束时间"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：
				<input type="text" style="height: 28px; border: 1px solid #e5e5e5;"
				class="Wdate" id="endTimeId" placeholder="大于开始时间"
				onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" /></td>
		</tr>
		<tr>
			<td><button onclick="query();">查询</button></td>
		</tr>
	</table>

</body>
</html>