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
	$(function() {
		massPicTextList(1);
	})

	function goPage(page) {
		var pageStr = $("#pageStr").val();
		if ($.trim(pageStr) == "") {
			alert("请输入需要跳转的页数！");
			return;
		}
		if (isNaN(pageStr)) {
			alert("格式错误，请输入数字！");
			return;
		}
		if (pageStr > page) {
			alert("你输入的页数大于最大页数");
			return;
		}
		if (pageStr <= 0) {
			alert("你输入的页数不符合要求");
			return;
		}
		massPicTextList(pageStr);
	}

	function queryMassPicText() {
		massPicTextList(1);
	}

	function massPicTextList(pageNo) {
		var start = 0;
		var limit = 2;
		if (pageNo > 0) {
			start = (pageNo - 1) * limit;
		}
		var startTime = $('#startTimeId').val();
		var endTime = $('#endTimeId').val();
		var platformId = $('#platformUser').val();
		console.log('startTime=' + startTime);
		console.log('endTime=' + endTime);
		console.log('platformId=' + platformId);
		if (startTime && endTime && startTime > endTime) {
			alert('开始时间大于结束时间');
			return;
		}
		var parmam = {
			start : start,
			limit : limit,
			platformId : platformId,
			startTime : startTime,
			endTime : endTime
		};

		$.ajax({
			url : "/ump/reportMassPicText/massPicTextList",
			type : "POST",
			data : parmam,
			error : function(msg) {
				alert("error " + msg);
			},
			success : function(text) {
				$("#resContent").html(text);
			}
		});
	}

	//定义图表	
	var option = {
		chart : {
			renderTo : 'container'
		},
		exporting : {
			enabled : false
		},
		title : {
			text : '',
			x : -20
		//center
		},
		subtitle : {
			text : '',
			x : -20
		},
		xAxis : {
			//			labels : {
			//				rotation : -45,
			//				step : 3
			//			},
			categories : []
		},
		yAxis : {
			min : 0,
			allowDecimals:false,
			title : {
				text : '数量'
			},
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
			name : '阅读量',
			data : []
		}, {
			name : '原文阅读量',
			data : []
		}, {
			name : '转发量',
			data : []
		}, {
			name : '收藏量',
			data : []
		} ]
	};

	var option2 = {
		chart : {
			renderTo : 'container2'
		},
		exporting : {
			enabled : false
		},
		title : {
			text : '',
			x : -20
		//center
		},
		subtitle : {
			text : '',
			x : -20
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
			name : '阅读量',
			data : []
		}, {
			name : '原文阅读量',
			data : []
		}, {
			name : '转发量',
			data : []
		}, {
			name : '收藏量',
			data : []
		} ]
	};
</script>
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
			labels : {
				rotation : -45,
				step : 3
			},
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
	var options2 = {
		chart : {
			type : 'bar',
			renderTo : 'containers2'
		},
		exporting : {
			enabled : false
		},
		title : {
			text : '图文影响力TOP5'
		},
		xAxis : {
			labels : {
				rotation : -45,
				step : 3
			},
			categories : [],
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
		var title = $("#platformUser3").find("option:selected").attr("title");
		var insert = new Date(title.replace(/-/g, "/")).getTime();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId5").val();
			var commentEndTimeId = $("#commentEndTimeId5").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId6").val();
			var commentEndTimeId = $("#commentEndTimeId6").val();
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
				objs = eval(data);
				if (objs.length > 0) {
					if (objs.length > 20) {
						option3.xAxis.labels.step = 4;
						option3.xAxis.labels.rotation = 0;
					} else if (objs.length < 10) {
						option3.xAxis.labels.step = 1;
						option3.xAxis.labels.rotation = 0;
					} else {
						option3.xAxis.labels.step = 2;
						option3.xAxis.labels.rotation = 0;
					}
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
		var platformUser = $("#platformUser2").val();
		var commentStartTimeId = $("#commentStartTimeId3").val();
		var commentEndTimeId = $("#commentEndTimeId3").val();
		var title = $("#platformUser2").find("option:selected").attr("title");
		var insert = new Date(title.replace(/-/g, "/")).getTime();
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
		var start = new Date(commentStartTimeId.replace(/-/g, "/")).getTime();
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
		$.post("/ump/reportcontents/queryContentTop", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
		}, function(data) {
			options2.xAxis.categories = [];
			options2.series[0].data = [];
			options2.series[1].data = [];
			options2.series[2].data = [];
			if (data != "") {
				//转为数组
				objs = eval(data);
				if (objs.length > 0) {
					if (objs.length > 20) {
						options2.xAxis.labels.step = 4;
						options2.xAxis.labels.rotation = 0;
					} else if (objs.length < 10) {
						options2.xAxis.labels.step = 1;
						options2.xAxis.labels.rotation = 0;
					} else {
						options2.xAxis.labels.step = 2;
						options2.xAxis.labels.rotation = 0;
					}
					for (var i = 0; i < objs.length; i++) {
						options2.series[2].data.push(objs[i].intPageReadUser);
						options2.series[1].data.push(objs[i].shareUser);
						options2.series[0].data.push(objs[i].addToFavUser);
						options2.xAxis.categories.push(objs[i].title);
					}
				}
			}
			var chartBar = new Highcharts.Chart(options2);
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
		headerStyle : 'background:white;text-align:center;',
		resolution : function(value, record, column, grid, dataNo,
				columnNo) {
			var content = '';
			content += '<a href="" target="_blank" style="text-decoration:underline">'+value.title+'</a>';
		   return content;
		}
		
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
		var chartBar = new Highcharts.Chart(options2);
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
		var url = "/ump/reportcontents/exportExcelTop?platform=" + platformUser
				+ "&startTime=" + commentStartTimeId + "&endTime="
				+ commentEndTimeId;
		window.location.href = url;
	}
</script>
</head>
<body>
	<!-- <div class="report">
		<div class="report_box">
			<ul>
				<li><a href="/ump/reportfriends/reportPage"><img src="/ump/images/report1.png">粉丝报表</a></li>
			</ul>
			<ul>
				<li class="actives"><a href="/ump/reportMassPicText/reportPage"><img
						src="/ump/images/report2.png">内容报表</a></li>
			</ul>
			<ul>
				<li><a href="/ump/reportmessages/reportPage"><img src="/ump/images/report3.png">消息报表</a></li>
			</ul>
		</div>
	</div> -->
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
				<div class="frame" id="_page_1" style="display: block;">
					<div class="pt">
						<label>公众平台：</label> <select name="platformUser" id="platformUser">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}">${platformUser.account}</option>
							</c:forEach>
						</select>
					</div>
					<div class="pt_date">
						<div class="pt_date_left">

							群发时间：<input type="text" class="Wdate"
								style="height: 28px; border: 1px solid #e5e5e5;"
								id="startTimeId" placeholder="小于结束时间"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
								type="text" style="height: 28px; border: 1px solid #e5e5e5;"
								class="Wdate" id="endTimeId" placeholder="大于开始时间"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="queryMassPicText();">
						</div>
					</div>
					<!-- <div id="container" style="min-width: 700px; height: 400px"></div>
					<div id="container2" style="min-width: 700px; height: 400px"></div> -->
					<input type="hidden" id="limit" value="${limit}" />
					<div id="resContent">
						<!--注释 -->
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
								<label>统计周期： </label>
							</div>
							<div id="dayDiv2" class="dateDiv">
								<input type="text" class="Wdate" id="commentStartTimeId3"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId3"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为30天）
							</div>
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="queryTopData();">
						</div>
					</div>
					<div id="containers2"
						style="width: 100%; min-width: 320px; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none; overflow: hidden; clear: both;"
						class="buttondiv">
						<a onclick="exportTop();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_2"
						class="dt-grid-container  tb_defult">
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
				</div>
				<div class="frame" id="_page_3" style="display: none;">
					<div class="pt">
					<div  class="span3">
						<label>公众平台：</label> <select name="platformUser"
							id="platformUser3">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}"
									title="${fn:substring(platformUser.insertTime, 0, 10)}">${platformUser.account}</option>
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
							<div class="titleDiv">
								<label>统计周期： </label>
							</div>
							<div class="titleDiv">
								<select name="dateType" id="dateType3"
									onchange="changeDateType(3);">
									<option value="0">按天</option>
									<option value="1">按月</option>
								</select>
							</div>
							<div id="dayDiv3" class="dateDiv">
								<input type="text" class="Wdate" id="commentStartTimeId5"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId5"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为30天）
							</div>
							<div id="monthDiv3" class="dateDiv" style="display: none;">
								<input type="text" class="Wdate" id="commentStartTimeId6"
									placeholder="小于结束时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />至：<input
									type="text" class="Wdate" id="commentEndTimeId6"
									placeholder="大于开始时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />（最大时间跨度为12个月）
							</div>
						</div>
						<div class="pt_date_bt">
							<input type="button" value="查 询" onclick="queryChannelData();">
						</div>
					</div>
					<div id="container3"
						style="width: 100%; min-width: 320px; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportChannel();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_3"
						class="dt-grid-container  tb_defult">
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
					<div id="dtGridToolBarContainer_2_1_3"
						class="dt-grid-toolbar-container toolbar_defult">
						<SPAN></SPAN>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>