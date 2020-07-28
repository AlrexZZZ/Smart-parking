<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="../js/date/skin/WdatePicker.css" />
<link rel="stylesheet"
	href="../ui/dtGrid/dependents/fontAwesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="../ui/dtGrid/jquery.dtGrid.css" />
<link rel="stylesheet" href="../styles/wccreport.css" />
<script src="../js/date/WdatePicker.js" type="text/javascript"></script>
<script src="../js/jquery.min.js" type="text/javascript"></script>
<script src="../js/jquery.widget.min.js" type="text/javascript"></script>
<script src="../ui/metro/js/metro.min.js" type="text/javascript"></script>
<script src="../js/hcharts/js/highcharts.js" type="text/javascript"></script>
<script src="../js/hcharts/js/modules/exporting.js"
	type="text/javascript"></script>
<script src="../js/hcharts/js/highcharts-more.js" type="text/javascript"></script>
<script src="../ui/dtGrid/jquery.dtGrid.js" type="text/javascript"></script>
<script src="../ui/dtGrid/i18n/zh-cn.js" type="text/javascript"></script>

<script type="text/javascript">
	function getSumData() {
		var platformUser = $("#platformUser1").val();
		var commentStartTimeId = $("#commentStartTimeId1").val();
		var commentEndTimeId = $("#commentEndTimeId1").val();
		$.post("/ump/reportfriends/saveFriendsSumFromWx", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId
		}, function(data) {
			alert(data);
			if (data == "1") {
				alert("数据取出成功");
			} else {
				alert("数据取出失败");
			}
		});
	}

	function getFromData() {
		var platformUser = $("#platformUser2").val();
		var commentStartTimeId = $("#commentStartTimeId3").val();
		var commentEndTimeId = $("#commentEndTimeId3").val();
		$.post("/ump/reportfriends/saveFriendsFromsFromWx", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId
		}, function(data) {
			alert(data);
			if (data == "1") {
				alert("数据取出成功");
			} else {
				alert(data);
				alert("数据取出失败");
			}
		});
	}

	//定义图表1	
	var option = {
		chart : {
			renderTo : 'container1'
		},
		exporting : {
			enabled : false
		},
		title : {
			text : '粉丝增长报表',
			x : -20
		//center
		},
		subtitle : {
			text : '',
			x : -20
		},
		xAxis : {
			labels : {
				rotation : -45,
				step : 3
			},
			categories : [ 1, 2, 3, 4, 5 ]
		},
		yAxis : {
			title : {
				text : '数量'
			},
			min : 0,
			allowDecimals:false,
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},

		//legend : {
		//layout : 'vertical',
		//align : 'right',
		//verticalAlign : 'middle',
		//borderWidth : 0
		//},
		series : [ {
			name : '粉丝数',
			data : []
		}, {
			name : '新增粉丝数',
			data : []
		}, {
			name : '取消关注数',
			data : []
		} ]
	};
	//粉丝增长来源
	var option2 = {
		chart : {
			type : 'column',
			renderTo : 'container2'
		},
		title : {
			text : '粉丝增长来源'
		},
		exporting : {
			enabled : false
		},
		xAxis : {
			labels : {
				rotation : -45,
				step : 3
			},
			categories : []
		},
		yAxis : {
			min : 0,
			title : {
				text : '增长数量'
			},
			allowDecimals:false,
			stackLabels : {
				enabled : true,
				style : {
					fontWeight : 'bold',
					color : (Highcharts.theme && Highcharts.theme.textColor)
							|| 'gray'
				}
			}
		},
		// legend: {
		// align: 'right',
		// x: -70,
		//  verticalAlign: 'top',
		//   y: 20,
		//   floating: true,
		//   backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
		//   borderColor: '#CCC',
		//   borderWidth: 1,
		//    shadow: false
		//  },
		tooltip : {
			formatter : function() {
				return '<b>' + this.x + '</b><br/>' + this.series.name + ': '
						+ this.y + '<br/>' + '总数: ' + this.point.stackTotal;
			}
		},
		plotOptions : {
			column : {
				stacking : 'normal',
				dataLabels : {
					enabled : true,
					color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
							|| 'white'
				}
			}
		},
		series : [ {
			name : '公众号搜索',
			data : []
		}, {
			name : '门店二维码',
			data : []
		}, {
			name : '图文二维码',
			data : []
		}, {
			name : '名片分享',
			data : []
		}, {
			name : '图文右上角菜单',
			data : []
		}, {
			name : '其他',
			data : []
		} ]
	};

	function querySumData() {
		var platformUser = $("#platformUser1").val();
		var title = $("#platformUser1").find("option:selected").attr("title");
		var dateType = $("#dateType1").val();
		var insert = new Date(title.replace(/-/g, "/")).getTime();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId1").val();
			var commentEndTimeId = $("#commentEndTimeId1").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId2").val();
			var commentEndTimeId = $("#commentEndTimeId2").val();
		}

		if (commentStartTimeId == "") {
			alert("开始时间不可为空");
			return false;
		}
		if (commentEndTimeId == "") {
			alert("结束时间不可为空");
			return false;
		}
		if (commentEndTimeId <= commentStartTimeId) {
			alert("结束时间应大于开始时间");
			return false;
		}
		var d = new Date();
		if (dateType == 0) {
			var start = new Date(commentStartTimeId.replace(/-/g, "/"))
					.getTime();
			var end = new Date(commentEndTimeId.replace(/-/g, "/")).getTime();
			/* if (start < insert) {
				alert("开始时间不可大于平台添加时间");
				return false;
			}
			if (d <= end) {
				alert("结束时间应小于当前时间");
				return false;
			} */
			if (end - start > 30 * 24 * 60 * 60 * 1000) {
				alert("最大时间跨度为30天");
				return false;
			}
		} else {
			var start = new Date((commentStartTimeId + "-01")
					.replace(/-/g, "/")).getTime();
			var end = new Date((commentEndTimeId + "-01").replace(/-/g, "/"))
					.getTime();
			/* if (start < insert) {
				alert("开始时间不可大于平台添加时间");
				return false;
			} 
			if (d <= end) {
				alert("结束时间应小于当前时间");
				return false;
			}*/
			if (end - start > 365 * 24 * 60 * 60 * 1000) {
				alert("最大时间跨度为12个月");
				return false;
			}
		}
		$.post("/ump/reportfriends/queryFriendsSum", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
			"dateType" : dateType
		}, function(data) {
			option.xAxis.categories = [];
			option.series[0].data = [];
			option.series[1].data = [];
			option.series[2].data = [];
			if (data != "") {
				//转为数组
				objs = eval('(' + data + ')');
				if (objs.length > 0) {
					if (objs.length > 20) {
						option.xAxis.labels.step = 4;
						option.xAxis.labels.rotation = 0;
					} else if (objs.length < 10) {
						option.xAxis.labels.step = 1;
						option.xAxis.labels.rotation = 0;
					} else {
						option.xAxis.labels.step = 2;
						option.xAxis.labels.rotation = 0;
					}
					for (var i = 0; i < objs.length; i++) {
						option.series[0].data.push(objs[i].cumulateUser);
						option.series[1].data.push(objs[i].newNum);
						option.series[2].data.push(objs[i].cancelNum);
						option.xAxis.categories.push(objs[i].dateStr);
					}
				}
			}
			var chartBar = new Highcharts.Chart(option);
		});
		dtGridOption_2_1_1.params.platformUser = platformUser;
		dtGridOption_2_1_1.params.startTime = commentStartTimeId;
		dtGridOption_2_1_1.params.endTime = commentEndTimeId;
		dtGridOption_2_1_1.params.dateType = dateType;
		grid_2_1_1.load();
	}

	function queryFromData() {
		var platformUser = $("#platformUser2").val();
		var dateType = $("#dateType2").val();
		var title = $("#platformUser2").find("option:selected").attr("title");
		var insert = new Date(title.replace(/-/g, "/")).getTime();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId3").val();
			var commentEndTimeId = $("#commentEndTimeId3").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId4").val();
			var commentEndTimeId = $("#commentEndTimeId4").val();
		}
		if (commentStartTimeId == "") {
			alert("开始时间不可为空");
			return false;
		}
		if (commentEndTimeId == "") {
			alert("结束时间不可为空");
			return false;
		}
		if (commentEndTimeId <= commentStartTimeId) {
			alert("结束时间应大于开始时间");
			return false;
		}
		var d = new Date();
		if (dateType == 0) {
			var start = new Date(commentStartTimeId.replace(/-/g, "/"))
					.getTime();
			var end = new Date(commentEndTimeId.replace(/-/g, "/")).getTime();
		/* 	if (start < insert) {
				alert("开始时间不可大于平台添加时间");
				return false;
			}
			if (d <= end) {
				alert("结束时间应小于当前时间");
				return false;
			} */
			if (end - start > 30 * 24 * 60 * 60 * 1000) {
				alert("最大时间跨度为30天");
				return false;
			}
		} else {
			var start = new Date((commentStartTimeId + "-01")
					.replace(/-/g, "/")).getTime();
			var end = new Date((commentEndTimeId + "-01").replace(/-/g, "/"))
					.getTime();
			/* if (start < insert) {
				alert("开始时间不可大于平台添加时间");
				return false;
			}
			if (d <= end) {
				alert("结束时间应小于当前时间");
				return false;
			} */
			if (end - start > 365 * 24 * 60 * 60 * 1000) {
				alert("最大时间跨度为12个月");
				return false;
			}
		}
		$.post("/ump/reportfriends/queryFriendsFrom", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
			"dateType" : dateType
		}, function(data) {
			
			option2.xAxis.categories = [];
			option2.series[0].data = [];
			option2.series[1].data = [];
			option2.series[2].data = [];
			option2.series[3].data = [];
			option2.series[4].data = [];
			option2.series[5].data = [];
			if (data != "") {
				//转为数组
				objs = eval('(' + data + ')');
				if (objs.length > 0) {
					if (objs.length > 20) {
						option2.xAxis.labels.step = 4;
						option2.xAxis.labels.rotation = 0;
					} else if (objs.length < 10) {
						option2.xAxis.labels.step = 1;
						option2.xAxis.labels.rotation = 0;
					} else {
						option2.xAxis.labels.step = 2;
						option2.xAxis.labels.rotation = 0;
					}
					for (var i = 0; i < objs.length; i++) {
						option2.series[0].data.push(objs[i].findNumCount);
						option2.series[1].data.push(objs[i].storeCount);
						option2.series[2].data.push(objs[i].imageCount);
						option2.series[3].data.push(objs[i].shareCount);
						option2.series[4].data.push(objs[i].menuCount);
						option2.series[5].data.push(objs[i].otherCount);
						option2.xAxis.categories.push(objs[i].dateStr);
					}
				}
			}
			var chartBar = new Highcharts.Chart(option2);
		});
		dtGridOption_2_1_2.params.platformUser = platformUser;
		dtGridOption_2_1_2.params.startTime = commentStartTimeId;
		dtGridOption_2_1_2.params.endTime = commentEndTimeId;
		dtGridOption_2_1_2.params.dateType = dateType;
		grid_2_1_2.load();
	}

	//定义表格  粉丝增长曲线
	var dtGridColumns_2_1_1 = [ {
		id : 'platformUserName',
		title : '公众平台',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'dateStr',
		title : '日期',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;',
	}, {
		id : 'cumulateUser',
		title : '粉丝数',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'newNum',
		title : '新增粉丝数',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'cancelNum',
		title : '粉丝取消关注数',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'

	} ];
	//粉丝增长来源
	var dtGridColumns_2_1_2 = [ {
		id : 'platformUserName',
		title : '公众平台',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'dateStr',
		title : '日期',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;',
	}, {
		id : 'findNumCount',
		title : '公众号搜索',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'storeCount',
		title : '门店二维码',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'imageCount',
		title : '图文二维码',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'shareCount',
		title : '名片分享',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'menuCount',
		title : '图文右上角菜单',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'otherCount',
		title : '其它',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'

	} ];
	dtGridOption_2_1_1 = {
		ajaxLoad : true,
		params : {},
		//loadAll : true,
		loadURL : '/ump/reportfriends/queryDataGridSum',
		columns : dtGridColumns_2_1_1,
		gridContainer : 'dtGridContainer_2_1_1',
		toolbarContainer : 'dtGridToolBarContainer_2_1_1',
		tools : '',
		pageSize : 10
	};
	dtGridOption_2_1_2 = {
		ajaxLoad : true,
		params : {},
		//loadAll : true,
		loadURL : '/ump/reportfriends/queryDataGridFrom',
		columns : dtGridColumns_2_1_2,
		gridContainer : 'dtGridContainer_2_1_2',
		toolbarContainer : 'dtGridToolBarContainer_2_1_2',
		tools : '',
		pageSize : 10
	};
	var grid_2_1_1 = $.fn.DtGrid.init(dtGridOption_2_1_1);
	var grid_2_1_2 = $.fn.DtGrid.init(dtGridOption_2_1_2);
	$(function() {
		var chartBar = new Highcharts.Chart(option);
		var chartBar = new Highcharts.Chart(option2);
		grid_2_1_1.load();
		grid_2_1_2.load();
	})

	function changeDateType(id) {
		var dateType = $("#dateType" + id).val();
		if (dateType == 0) {
			$("#dayDiv" + id).show();
			$("#monthDiv" + id).hide();
		} else {
			$("#monthDiv" + id).show();
			$("#dayDiv" + id).hide();
		}
	}

	function exportSum() {
		var platformUser = $("#platformUser1").val();
		var dateType = $("#dateType1").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId1").val();
			var commentEndTimeId = $("#commentEndTimeId1").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId2").val();
			var commentEndTimeId = $("#commentEndTimeId2").val();
		}
		var url = "/ump/reportfriends/exportExcelSum?platform=" + platformUser
				+ "&startTime=" + commentStartTimeId + "&endTime="
				+ commentEndTimeId + "&dateType=" + dateType;
		window.location.href = url;
	}
	function exportFrom() {
		var platformUser = $("#platformUser2").val();
		var dateType = $("#dateType2").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId3").val();
			var commentEndTimeId = $("#commentEndTimeId3").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId4").val();
			var commentEndTimeId = $("#commentEndTimeId4").val();
		}
		var url = "/ump/reportfriends/exportExcelFrom?platform=" + platformUser
				+ "&startTime=" + commentStartTimeId + "&endTime="
				+ commentEndTimeId + "&dateType=" + dateType;
		window.location.href = url;
	}
</script>
</head>
<body>
	<!-- 	<div class="report">
		<div class="report_box">
			<ul>
				<li class="actives"><a href="/ump/reportfriends/reportPage"><img src="/ump/images/report1.png">粉丝报表</a></li>
			</ul>
			<ul>
				<li><a href="/ump/reportMassPicText/reportPage"><img src="/ump/images/report2.png">内容报表</a></li>
			</ul>
			<ul>
				<li><a href="/ump/reportmessages/reportPage"><img src="/ump/images/report3.png">消息报表</a></li>
			</ul>
		</div>
	</div> -->
	<!-- <div class="tabs_box"> -->
		<div class="tab-control" data-role="tab-control">
			<ul class="tabs">
				<li class="active"><a href="#_page_1"><i class="on-left"><img
							src="../images/b_img.png"></i>粉丝增长</a></li>
				<li><a href="#_page_2"><i class="on-left"><img
							src="../images/b_img.png"></i>粉丝增长来源</a></li>
			</ul>
			<div class="frames">
				<div class="frame" id="_page_1" style="display: block;">
					<div class="pt" style="float:left;">
						<!-- <label>组织架构：</label> <select name="orglist"
							id="orglist">
							<c:forEach items="${orgList}" var="org">
								<option value="${org.id}">${org.name}</option>
							</c:forEach>
						</select> -->
						<label>公众平台：</label> 
						<select name="platformUser"  id="platformUser1">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}"
									title="${fn:substring(platformUser.insertTime, 0, 10)}">${platformUser.account}</option>
							</c:forEach>
						</select>
					</div>
					<div class="pt_date">
						<div class="pt_date_left">
							<div class="titleDiv">
								<label>统计周期： </label> <select name="dateType" id="dateType1"
									onchange="changeDateType(1);">
									<option value="0">按天</option>
									<option value="1">按月</option>
								</select>
							</div>
							<div id="dayDiv1" class="dateDiv">
								<input type="text" class="Wdate" id="commentStartTimeId1"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId1"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为30天）
							</div>
							<div id="monthDiv1" class="dateDiv" style="display: none;">
								<input type="text" class="Wdate" id="commentStartTimeId2"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId2"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为12个月）
							</div>
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="querySumData();">
						</div>
					</div>
					<div id="container1" style="width: 100%; min-width:320px;height: 280px;margin: 0 auto;"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportSum();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_1" class="dt-grid-container tb_defult">
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
					<div id="dtGridToolBarContainer_2_1_1"
						class="dt-grid-toolbar-container toolbar_defult">
						<SPAN></SPAN>
					</div>
				</div>
				<div class="frame" id="_page_2" style="display: none;">
					<div class="pt">
						<label>公众平台：</label> <select name="platformUser"
							id="platformUser2">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}"
									title="${fn:substring(platformUser.insertTime, 0, 10)}">${platformUser.account}</option>
							</c:forEach>
						</select>
					</div>
					<div class="pt_date">
						<div class="pt_date_left">
							<div class="titleDiv">
								<label>统计周期： </label> <select name="dateType" id="dateType2"
									onchange="changeDateType(2);">
									<option value="0">按天</option>
									<option value="1">按月</option>
								</select>
							</div>
							<div id="dayDiv2" class="dateDiv">
								<input type="text" class="Wdate" id="commentStartTimeId3"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId3"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为30天）
							</div>
							<div id="monthDiv2" class="dateDiv" style="display: none;">
								<input type="text" class="Wdate" id="commentStartTimeId4"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId4"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为12个月）
							</div>
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="queryFromData();">
						</div>
					</div>
					<div id="container2" style="width: 100%;min-width:320px; height: 300px;"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportFrom();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_2" class="dt-grid-container tb_defult" >
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
					<div id="dtGridToolBarContainer_2_1_2"
						class="dt-grid-toolbar-container toolbar_defult">
						<SPAN></SPAN>
					</div>
				</div>
			</div>
<!-- 		</div> -->
	</div>
</body>
</html>