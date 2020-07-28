<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>小区基本概况</title>
<link rel="stylesheet" type="text/css"
	href="/ump/cbdwccui/styles/mystyle.css">
	<link rel="stylesheet" type="text/css"
	href="/ump/ui/kindeditor/themes/default/default.css">
<script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.0.min.js"></script>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<body class="withbg ke-content">
	<div class="container">
		<!--container-->

		<div class="detail_head">
			<div class="head_title">${wccItemotherinfo.itemPk.itemName }</div>
			<div class="head_date">
				<h1>${dateStr }</h1>
				<h2>${wccAppartment.itemIntro}</h2>
			</div>
		</div>

	<!-- 	<div class="detail_img">
			<img src="${wccItemotherinfo.otherPic }" alt="">
		</div> -->
	<div class="detail_img">
		<div class="detail_article" >
			${wccItemotherinfo.otherIntro }
		</div>
</div>
		<div class="detail_back">
			<input type="button" value="返   回"
				onclick="location.href='javascript:history.go(-1)'">
		</div>

		    <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>
	</div>
	<!--container end-->
</body>
</html>