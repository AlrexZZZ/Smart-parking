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
	<title>预约业务</title>
	<link rel="stylesheet" href="/ump/tailong_ui/css/index.css">
</head>
<script type="text/javascript">
var openId = document.getElementById('openId').value;
function appointment(id){
	window.location.href='/ump/pageTLProduct/showTLProductDetail?id='+id+"&openId=${openId}";
}


</script>
<body>

	<div class="main qsq">
		<div class="top">
			请选择您要预约的业务
		</div>
		<div class="list">
<c:forEach items="${productList}" var="li" varStatus="status">
<c:if test="${(status.index%2)==0}">
			<div class="ul">
			<c:if test="${productList[status.index] != null}">
				<div class="li" onclick="appointment(${productList[status.index].id});">
					<div class="yy">
						<div class="con">
							${productList[status.index].productName}
						</div>
						<img src="${productList[status.index].thumbImage}" alt="">
						<div class="des">
							点击预约
						</div>
					</div>
				</div>
			</c:if>	
			<c:if test="${productList[status.index+1] != null}">
 				<div class="li" onclick="appointment(${productList[status.index+1].id});">
					<div class="yy">
						<div class="con">
							${productList[status.index+1].productName}
						</div>
						<img src="${productList[status.index+1].thumbImage}" alt="">
						<div class="des">
							点击预约
						</div>
					</div>
				</div> 
				</c:if>
			</div>

</c:if>

</c:forEach>


		</div>
	</div>
</body>
</html>