<jsp:directive.page contentType="text/html;charset=UTF-8" />
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<title>信息绑定</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- loading mui -->
	<link rel="stylesheet" type="text/css" href="/ump/qcloud/css/mui.min.css">
	<!-- custorm style -->
	<link rel="stylesheet" type="text/css" href="/ump/qcloud/css/style.css">
	<!-- 自定义样式 -->
	<style type="text/css">
			.mui-preview-image.mui-fullscreen {
				position: fixed;
				z-index: 20;
				background-color: #000;
			}
			.mui-preview-header,
			.mui-preview-footer {
				position: absolute;
				width: 100%;
				left: 0;
				z-index: 10;
			}
			.mui-preview-header {
				height: 44px;
				top: 0;
			}
			.mui-preview-footer {
				height: 50px;
				bottom: 0px;
			}
			.mui-preview-header .mui-preview-indicator {
				display: block;
				line-height: 25px;
				color: #fff;
				text-align: center;
				margin: 15px auto 4;
				width: 70px;
				background-color: rgba(0, 0, 0, 0.4);
				border-radius: 12px;
				font-size: 16px;
			}
			.mui-preview-image {
				display: none;
				-webkit-animation-duration: 0.5s;
				animation-duration: 0.5s;
				-webkit-animation-fill-mode: both;
				animation-fill-mode: both;
			}
			.mui-preview-image.mui-preview-in {
				-webkit-animation-name: fadeIn;
				animation-name: fadeIn;
			}
			.mui-preview-image.mui-preview-out {
				background: none;
				-webkit-animation-name: fadeOut;
				animation-name: fadeOut;
			}
			.mui-preview-image.mui-preview-out .mui-preview-header,
			.mui-preview-image.mui-preview-out .mui-preview-footer {
				display: none;
			}
			.mui-zoom-scroller {
				position: absolute;
				display: -webkit-box;
				display: -webkit-flex;
				display: flex;
				-webkit-box-align: center;
				-webkit-align-items: center;
				align-items: center;
				-webkit-box-pack: center;
				-webkit-justify-content: center;
				justify-content: center;
				left: 0;
				right: 0;
				bottom: 0;
				top: 0;
				width: 100%;
				height: 100%;
				margin: 0;
				-webkit-backface-visibility: hidden;
			}
			
			.mui-zoom {
				-webkit-transform-style: preserve-3d;
				transform-style: preserve-3d;
			}
			.mui-slider .mui-slider-group .mui-slider-item img {
				width: auto;
				height: auto;
				max-width: 100%;
				max-height: 100%;
			}
			.mui-android-4-1 .mui-slider .mui-slider-group .mui-slider-item img {
				width: 100%;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-slider-group .mui-slider-item {
				display: inline-table;
			}
			.mui-android-4-1 .mui-slider.mui-preview-image .mui-zoom-scroller img {
				display: table-cell;
				vertical-align: middle;
			}
			.mui-preview-loading {
				position: absolute;
				width: 100%;
				height: 100%;
				top: 0;
				left: 0;
				display: none;
			}
			.mui-preview-loading.mui-active {
				display: block;
			}
			.mui-preview-loading .mui-spinner-white {
				position: absolute;
				top: 50%;
				left: 50%;
				margin-left: -25px;
				margin-top: -25px;
				height: 50px;
				width: 50px;
			}
			.mui-preview-image img.mui-transitioning {
				-webkit-transition: -webkit-transform 0.5s ease, opacity 0.5s ease;
				transition: transform 0.5s ease, opacity 0.5s ease;
			}
			@-webkit-keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@keyframes fadeIn {
				0% {
					opacity: 0;
				}
				100% {
					opacity: 1;
				}
			}
			@-webkit-keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			@keyframes fadeOut {
				0% {
					opacity: 1;
				}
				100% {
					opacity: 0;
				}
			}
			img {
				max-width: 100%;
				height: auto;
			}
		</style>
</head>
    <!-- jquery.min.js 2.2.1-->
	<script type="text/javascript" src="/ump/js/jquery-2.1.1.js"></script>
	<!-- loading mui.min.js -->
	<script type="text/javascript" src="/ump/qcloud/js/mui.min.js"></script>
	<!-- loading mui.zoom.js -->
	<script type="text/javascript" src="/ump/qcloud/js/mui.zoom.js"></script>
	<!-- loading mui.previewimage -->
	<script type="text/javascript" src="/ump/qcloud/js/mui.previewimage.js"></script>
	<script>
		mui.previewImage();
	</script>
<script type="text/javascript">

function saveOpertor(){
	alert('saving data now !');
	//$("#saveFormDataId").submit();
	$.ajax({
		url : "/ump/pageBding/saveBding",
		type : "POST",
		data : {
			openId:$('#openId').val(),
			fname:$("#fnameId").val(),
			stuColleage:$("#stuColleageId").val(),
			fphone:$("#fphoneId").val(),
			stuNo:$("#stuNoId").val()
		},
		async : false,
		error : function(request) {
	  alert('网络连接异常，请稍后再试!');
		},
		success : function(data) {
	  alert('绑定成功!');
		}
	});
}

</script>
<body>
	<!-- 导航栏 -->
	<header id="header" class="mui-bar mui-bar-nav">
		<h1 class="mui-title">XXXX大学</h1>
		<a class="mui-action-back mui-btn mui-btn-blue mui-btn-link mui-btn-nav mui-pull-left" href="javascript:history.go(-1)"><span class="mui-icon mui-icon-left-nav"></span></a>
		<a class="mui-icon mui-icon-bars mui-pull-right" href="#topPopover"></a>

	</header>
	<!-- 右上角弹出菜单 -->
	<div id="topPopover" class="mui-popover">
			<div class="mui-popover-arrow"></div>
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">
							<a href="query.html">我要查询</a>
						</li>
						<li class="mui-table-view-cell"><a href="vote.html">我要投票</a>
						</li>
						<li class="mui-table-view-cell"><a href="rate.html">我要评价</a>
						</li>
						<li class="mui-table-view-cell"><a href="enroll.html">我要报名</a>
						</li>
						<li class="mui-table-view-cell"><a href="payment.html">我要缴费</a>
						</li>
						<li class="mui-table-view-cell"><a href="personCenter.html">个人中心</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- 主内容部分 -->
	<div class="content">
		<section class="xueqi">
			<div class="class">
				<label id="classResult">报名信息登记</label>
			</div>
		</section>
		<section class="enroll">
		
			<form id="saveFormDataId" action="/ump/pageBding/saveBding" class="mui-input-group">
				<div class="mui-input-row">
					<label>姓名：</label>
					<input type="hidden" name="openId" id="openId_" />
					<input type="text" name="fname" id="fnameId" class="mui-input-clear" placeholder="请输入您的姓名" data-input-clear="5"><span class="mui-icon mui-icon-clear mui-hidden"></span>
				</div>
				<div class="mui-input-row">
					<label>学号：</label>
					<input type="number" name="stuNo" id="stuNoId" class="mui-input-clear" placeholder="请输入您的学号" data-input-clear="5"><span class="mui-icon mui-icon-clear mui-hidden"></span>
				</div>
				<div class="mui-input-row">
					<label>电话：</label>
					<input type="number" name="fphone" id="fphoneId" class="mui-input-clear" placeholder="请输入您的联系方式" data-input-clear="5"><span class="mui-icon mui-icon-clear mui-hidden"></span>
				</div>
				<div class="mui-button-row">
					<button type="button" class="mui-btn mui-btn-primary" onclick="saveOpertor();">提交</button>&nbsp;&nbsp;
					<button type="button" class="mui-btn mui-btn-danger" onclick="return false;">取消</button>
				</div>
			</form>
		</section>
	</div>

	<div id="__MUI_PREVIEWIMAGE" class="mui-slider mui-preview-image mui-fullscreen">
		<div class="mui-preview-header">
			<span class="mui-preview-indicator"></span>
		</div>
		<div class="mui-slider-group"></div>
		<div class="mui-preview-footer mui-hidden"></div>
		<div class="mui-preview-loading">
			<span class="mui-spinner mui-spinner-white"></span>
		</div>
	</div>
</body>
</html>