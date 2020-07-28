<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	function getChannelData() {
		var platformUser = $("#platformUser3").val();
		var commentStartTimeId = $("#commentStartTimeId5").val();
		var commentEndTimeId = $("#commentEndTimeId5").val();
		$.post("/ump/reportcontents/saveContentChannelFromWx", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId
		}, function(data) {
			if (data == "1") {
				alert("数据取出成功");
			} else {
				alert("数据取出失败");
			}
		});
	}

	function getTopData() {
		var platformUser = $("#platformUser2").val();
		var commentStartTimeId = $("#commentStartTimeId3").val();
		var commentEndTimeId = $("#commentEndTimeId3").val();
		$.post("/ump/reportcontents/saveContentTopFromWx", {
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

	//粉丝增长来源
	var option3 = {
		chart : {
			type : 'column',
			renderTo : 'container3'
		},
		title : {
			text : '转发渠道报表'
		},
		exporting : {
			enabled : false
		},
		xAxis : {
			categories : []
		},
		yAxis : {
			min : 0,
			allowDecimals:false,
			title : {
				text : '数量'
			},
			stackLabels : {
				enabled : true,
				style : {
					fontWeight : 'bold',
					color : (Highcharts.theme && Highcharts.theme.textColor)
							|| 'gray'
				}
			}
		},
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
			name : '好友转发',
			data : []
		}, {
			name : '朋友圈',
			data : []
		}, {
			name : '腾讯微博',
			data : []
		}, {
			name : '其他',
			data : []
		} ]
	};
	var option2 = {
		chart : {
			type : 'bar',
			renderTo : 'container2'
		},
		exporting : {
			enabled : false
		},
		title : {
			text : '图文影响力TOP5'
		},
		xAxis : {
			categories : [],
			title : {
				text : null
			}
		},
		yAxis : {
			min : 0,
			allowDecimals:false,
			title : {
				text : '次数',
				align : 'high'
			},
			labels : {
				overflow : 'justify'
			}
		},
		plotOptions : {
			bar : {
				dataLabels : {
					enabled : true
				}
			}
		},
		legend : {
			layout : 'vertical',
			align : 'right',
			verticalAlign : 'top',
			x : -40,
			y : 100,
			floating : true,
			borderWidth : 1,
			backgroundColor : '#FFFFFF',
			shadow : true
		},
		credits : {
			enabled : false
		},
		series : [ {
			name : '收藏量',
			data : []
		}, {
			name : '转发量',
			data : []
		}, {
			name : '阅读量',
			data : []
		} ]
	}

	function queryChannelData() {
		var platformUser = $("#platformUser3").val();
		var dateType = $("#dateType3").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId5").val();
			var commentEndTimeId = $("#commentEndTimeId5").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId6").val();
			var commentEndTimeId = $("#commentEndTimeId6").val();
		}
		var queryType = $("#queryType3").val();
		$.post("/ump/reportcontents/queryContentChannel", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
			"dateType" : dateType,
			"queryType" : queryType
		}, function(data) {
			option3.xAxis.categories = [];
			option3.series[0].data = [];
			option3.series[1].data = [];
			option3.series[2].data = [];
			option3.series[3].data = [];
			if (data != "") {
				//转为数组
				objs = eval('(' + data + ')');
				if (objs.length > 0) {
					for (var i = 0; i < objs.length; i++) {
						option3.series[0].data.push(objs[i].forward);
						option3.series[1].data.push(objs[i].circle);
						option3.series[2].data.push(objs[i].blog);
						option3.series[3].data.push(objs[i].other);
						option3.xAxis.categories.push(objs[i].dateStr);
					}
				}
			}
			var chartBar = new Highcharts.Chart(option3);
		});
		dtGridOption_2_1_3.params.platformUser = platformUser;
		dtGridOption_2_1_3.params.startTime = commentStartTimeId;
		dtGridOption_2_1_3.params.endTime = commentEndTimeId;
		dtGridOption_2_1_3.params.dateType = dateType;
		dtGridOption_2_1_3.params.queryType = queryType;
		grid_2_1_3.load();
	}

	function queryTopData() {
		var platformUser = $("#platformUser3").val();
		var commentStartTimeId = $("#commentStartTimeId5").val();
		var commentEndTimeId = $("#commentEndTimeId5").val();
		$.post("/ump/reportcontents/queryContentTop", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
		}, function(data) {
			option2.xAxis.categories = [];
			option2.series[0].data = [];
			option2.series[1].data = [];
			option2.series[2].data = [];
			if (data != "") {
				//转为数组
				objs = eval(data);
				if (objs.length > 0) {
					for (var i = 0; i < objs.length; i++) {
						option2.series[2].data.push(objs[i].intPageReadUser);
						option2.series[1].data.push(objs[i].shareUser);
						option2.series[0].data.push(objs[i].addToFavUser);
						option2.xAxis.categories.push(objs[i].title);
					}
				}
			}
			var chartBar = new Highcharts.Chart(option2);
		});
		dtGridOption_2_1_2.params.platformUser = platformUser;
		dtGridOption_2_1_2.params.startTime = commentStartTimeId;
		dtGridOption_2_1_2.params.endTime = commentEndTimeId;
		grid_2_1_2.load();
	}

	var dtGridColumns_2_1_2 = [ {
		id : 'platformUserName',
		title : '公众平台',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'ranking',
		title : '排行',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;',
	}, {
		id : 'title',
		title : '文章名',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'intPageReadUser',
		title : '阅读量',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'shareUser',
		title : '转发量',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'addToFavUser',
		title : '收藏量',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'

	} ];
	dtGridOption_2_1_2 = {
		ajaxLoad : true,
		params : {},
		//loadAll : true,
		loadURL : '/ump/reportcontents/queryDataGridTop',
		columns : dtGridColumns_2_1_2,
		gridContainer : 'dtGridContainer_2_1_2',
		toolbarContainer : 'dtGridToolBarContainer_2_1_2',
		tools : '',
		pageSize : 10
	};
	dtGridOption_2_1_3 = {
		ajaxLoad : true,
		params : {},
		//loadAll : true,
		loadURL : '/ump/reportcontents/queryDataGridChannel',
		columns : dtGridColumns_2_1_3,
		gridContainer : 'dtGridContainer_2_1_3',
		toolbarContainer : 'dtGridToolBarContainer_2_1_3',
		tools : '',
		pageSize : 10
	};
	//粉丝增长来源
	var dtGridColumns_2_1_3 = [ {
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
		id : 'forward',
		title : '好友转发',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'circle',
		title : '朋友圈',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'blog',
		title : '腾讯微博',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'other',
		title : '其它',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'

	} ];
	dtGridOption_2_1_3 = {
		ajaxLoad : true,
		params : {},
		//loadAll : true,
		loadURL : '/ump/reportcontents/queryDataGridChannel',
		columns : dtGridColumns_2_1_3,
		gridContainer : 'dtGridContainer_2_1_3',
		toolbarContainer : 'dtGridToolBarContainer_2_1_3',
		tools : '',
		pageSize : 10
	};
	var grid_2_1_2 = $.fn.DtGrid.init(dtGridOption_2_1_2);
	var grid_2_1_3 = $.fn.DtGrid.init(dtGridOption_2_1_3);
	$(function() {
		var chartBar = new Highcharts.Chart(option2);
		var chartBar = new Highcharts.Chart(option3);
		grid_2_1_2.load();
		grid_2_1_3.load();
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

	function exportChannel() {
		var platformUser = $("#platformUser3").val();
		var dateType = $("#dateType3").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId5").val();
			var commentEndTimeId = $("#commentEndTimeId5").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId6").val();
			var commentEndTimeId = $("#commentEndTimeId6").val();
		}
		var queryType = $("#queryType3").val();
		var url = "/ump/reportcontents/exportExcelChannel?platform="
				+ platformUser + "&startTime=" + commentStartTimeId
				+ "&endTime=" + commentEndTimeId + "&dateType=" + dateType
				+ "&queryType=" + queryType;
		window.location.href = url;
	}
	function exportTop() {
		var platformUser = $("#platformUser2").val();
			var commentStartTimeId = $("#commentStartTimeId3").val();
			var commentEndTimeId = $("#commentEndTimeId3").val();
		var url = "/ump/reportcontents/exportExcelTop?platform="
				+ platformUser + "&startTime=" + commentStartTimeId
				+ "&endTime=" + commentEndTimeId;
		window.location.href = url;
	}
</script>
</head>
<body>
	<div class="topmenu"
		style="background: none repeat scroll 0 0 #069dd5; clear: both; height: 72px; margin: 0 auto; min-width: 1200px; overflow: hidden; width: 100%;">
		<div class="topmenu_L"
			style="float: left; height: 72px; overflow: hidden; width: 50%;">
			<img src="/ump/images/pub/logo.png" alt=""
				style="height: auto; margin-left: 20px; margin-top: 10px; max-width: 267px; width: 40%;">
		</div>
	</div>
	<div class="tabs_box">
		<div class="tab-control" data-role="tab-control">
			<ul class="tabs">
				<li class="active"><a href="#_page_1"><i class="on-left"><img
							src="../images/b_img.png"></i>图文群发报表</a></li>
				<li><a href="#_page_2"><i class="on-left"><img
							src="../images/b_img.png"></i>图文影响力TOP5</a></li>
				<li><a href="#_page_3"><i class="on-left"><img
							src="../images/b_img.png"></i>转发渠道报表</a></li>
			</ul>
			<div class="frames">
				<div class="frame" id="_page_1" style="display: block;">....</div>
				<div class="frame" id="_page_2" style="display: none;">
					<div class="pt">
						<label>公众平台：</label>
						 <select name="platformUser"  id="platformUser2" onchange="this.parentNode.nextSibling.value=this.value">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}">${platformUser.account}</option>
							</c:forEach>
						</select>
						<input name="box" style="width:100px;position:absolute;left:0px;">
					</div>
					<div class="pt_date">
						<div class="pt_date_left">
							<label>统计周期： </label>
							<div id="dayDiv2" class="dateDiv">
								<input type="text" class="Wdate" id="commentStartTimeId3"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId3"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为30天）
							</div>
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="queryTopData();">
							<input type="button" value="取数据" onclick="getTopData();">
						</div>
					</div>
					<div id="container2" style="width: 100%; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportTop();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_2" class="dt-grid-container">
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
					<div id="dtGridToolBarContainer_2_1_2"
						class="dt-grid-toolbar-container">
						<SPAN></SPAN>
					</div>
				</div>
				<div class="frame" id="_page_3" style="display: none;">
					<div class="pt">
					<div  class="span3">
						<label>公众平台：</label> <select name="platformUser"  id="platformUser3">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}">${platformUser.account}</option>
							</c:forEach>
						</select> 
						</div>
					<div class="span3">
						<label>统计维度： </label> <select name="queryType" id="queryType3">
							<option value="0">人数</option>
							<option value="1">次数</option>
						</select>
						</div>
					</div>
					<div class="pt_date">
						<div class="pt_date_left">
							<label>统计周期： </label> <select name="dateType" id="dateType3"
								onchange="changeDateType(3);">
								<option value="0">按天</option>
								<option value="1">按月</option>
							</select>
							<div id="dayDiv3" class="dateDiv">
								<input type="text" class="Wdate" id="commentStartTimeId5"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId5"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为30天）
							</div>
							<div id="monthDiv3" class="dateDiv" style="display: none;">
								<input type="text" class="Wdate" id="commentStartTimeId6"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId6"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为12个月）
							</div>
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="queryChannelData();">
							<input type="button" value="取数据" onclick="getChannelData();">
						</div>
					</div>
					<div id="container3" style="width: 100%; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportChannel();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_3" class="dt-grid-container">
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
					<div id="dtGridToolBarContainer_2_1_3"
						class="dt-grid-toolbar-container">
						<SPAN></SPAN>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>