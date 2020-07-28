<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	function changeDateType(id) {
		var dateType = $("#dateType" + id).val();
		if (id == 2) {
			if (dateType == 0) {
				$("#dayDiv" + id).show();
				$("#monthDiv" + id).hide();
				$("#hourDiv" + id).hide();
			} else if (dateType == 1) {
				$("#monthDiv" + id).show();
				$("#dayDiv" + id).hide();
				$("#hourDiv" + id).hide();
			} else {
				$("#monthDiv" + id).hide();
				$("#dayDiv" + id).hide();
				$("#hourDiv" + id).show();
			}
		} else {
			if (dateType == 0) {
				$("#dayDiv" + id).show();
				$("#monthDiv" + id).hide();

			} else {
				$("#monthDiv" + id).show();
				$("#dayDiv" + id).hide();
			}
		}
	}

	var options1 = {
		chart : {
			type : 'bar',
			renderTo : 'containers1'
		},
		exporting : {
			enabled : false
		},
		title : {
			text : '关键词TOP20'
		},
		xAxis : {
			labels : {
				rotation : 0,
				step : 0
			},
			categories : [],
		},
		yAxis : {
			min : 0,
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
			name : '出现次数',
			data : []
		} ]
	}

	function queryAutoData() {
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
			} */
			if (end - start > 365 * 24 * 60 * 60 * 1000) {
				alert("最大时间跨度为12个月");
				return false;
			}
		}
		$.post("/ump/reportmessages/queryAuto", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
			"dateType" : dateType
		}, function(data) {
			options1.xAxis.categories = [];
			options1.series[0].data = [];
			if (data != "") {
				//转为数组
				objs = eval(data);
				if (objs.length > 0) {
					for (var i = 0; i < objs.length; i++) {
						options1.series[0].data.push(objs[i].appearNum);
						options1.xAxis.categories.push(objs[i].keyWord);
					}
				}
			}
			var chartBar = new Highcharts.Chart(options1);
		});
		dtGridOption_2_1_1.params.platformUser = platformUser;
		dtGridOption_2_1_1.params.startTime = commentStartTimeId;
		dtGridOption_2_1_1.params.endTime = commentEndTimeId;
		dtGridOption_2_1_1.params.dateType = dateType;
		grid_2_1_1.load();
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
		id : 'ranking',
		title : '排行',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;',
	}, {
		id : 'keyWord',
		title : '消息关键词',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'appearNum',
		title : '出现次数',
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
		loadURL : '/ump/reportmessages/queryDataGridAuto',
		columns : dtGridColumns_2_1_1,
		gridContainer : 'dtGridContainer_2_1_1',
		toolbarContainer : 'dtGridToolBarContainer_2_1_1',
		tools : '',
		pageSize : 10
	};
	function exportTop() {
		var platformUser = $("#platformUser1").val();
		var dateType = $("#dateType1").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId1").val();
			var commentEndTimeId = $("#commentEndTimeId1").val();
		} else {
			var commentStartTimeId = $("#commentStartTimeId2").val();
			var commentEndTimeId = $("#commentEndTimeId2").val();
		}
		var url = "/ump/reportmessages/exportExcelTop?platform=" + platformUser
				+ "&startTime=" + commentStartTimeId + "&endTime="
				+ commentEndTimeId + "&dateType=" + dateType;
		window.location.href = url;
	}

	//互动消息统计
	var option2 = {
		chart : {
			type : 'column',
			renderTo : 'containers2'
		},
		title : {
			text : '互动消息统计'
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
			name : '文本消息',
			data : []
		}, {
			name : '图片消息',
			data : []
		}, {
			name : '语音消息',
			data : []
		}, {
			name : '视频消息',
			data : []
		}, {
			name : '第三方应用消息',
			data : []
		} ]
	};

	function queryMessageData() {
		var platformUser = $("#platformUser2").val();
		var queryType = $("#queryType2").val();
		var dateType = $("#dateType2").val();
		var title = $("#platformUser2").find("option:selected").attr("title");
		var insert = new Date(title.replace(/-/g, "/")).getTime();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId3").val();
			var commentEndTimeId = $("#commentEndTimeId3").val();
		} else if (dateType == 1) {
			var commentStartTimeId = $("#commentStartTimeId4").val();
			var commentEndTimeId = $("#commentEndTimeId4").val();
		} else {
			var commentTimeId = $("#commentTimeId").val();
		}
		if (dateType == 2) {
			if (commentTimeId == "") {
				alert("查询时间不可为空");
				return false;
			}
			var d = new Date();
			var start = new Date(commentTimeId.replace(/-/g, "/")).getTime();
			/* if (start < insert) {
				alert("查询时间不可大于平台添加时间");
				return false;
			} */
			$.post("/ump/reportmessages/queryMessage", {
				"platform" : platformUser,
				"commentTimeId" : commentTimeId,
				"dateType" : dateType,
				"queryType" : queryType
			}, function(data) {
				option2.xAxis.categories = [];
				option2.series[0].data = [];
				option2.series[1].data = [];
				option2.series[2].data = [];
				option2.series[3].data = [];
				option2.series[4].data = [];
				if (data != "") {
					//转为数组
					objs = eval(data);
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
							option2.series[0].data.push(objs[i].textCount);
							option2.series[1].data.push(objs[i].imageCount);
							option2.series[2].data.push(objs[i].voiceCount);
							option2.series[3].data.push(objs[i].vodieCount);
							option2.series[4].data.push(objs[i].otherCount);
							option2.xAxis.categories.push(objs[i].dateStr);
						}
					}
				}
				var chartBar = new Highcharts.Chart(option2);
			});
			dtGridOption_2_1_2.params.platformUser = platformUser;
			dtGridOption_2_1_2.params.commentTimeId = commentTimeId;
			dtGridOption_2_1_2.params.queryType = queryType;
			dtGridOption_2_1_2.params.dateType = dateType;
			grid_2_1_2.load();
		} else {

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
				var end = new Date(commentEndTimeId.replace(/-/g, "/"))
						.getTime();
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
				var start = new Date((commentStartTimeId + "-01").replace(/-/g,
						"/")).getTime();
				var end = new Date((commentEndTimeId + "-01")
						.replace(/-/g, "/")).getTime();
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

			$.post("/ump/reportmessages/queryMessage", {
				"platform" : platformUser,
				"startTime" : commentStartTimeId,
				"endTime" : commentEndTimeId,
				"dateType" : dateType,
				"queryType" : queryType
			}, function(data) {
				option2.xAxis.categories = [];
				option2.series[0].data = [];
				option2.series[1].data = [];
				option2.series[2].data = [];
				option2.series[3].data = [];
				option2.series[4].data = [];
				if (data != "") {
					//转为数组
					objs = eval(data);
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
							option2.series[0].data.push(objs[i].textCount);
							option2.series[1].data.push(objs[i].imageCount);
							option2.series[2].data.push(objs[i].voiceCount);
							option2.series[3].data.push(objs[i].vodieCount);
							option2.series[4].data.push(objs[i].otherCount);
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
			dtGridOption_2_1_2.params.queryType = queryType;
			grid_2_1_2.load();
		}
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
		id : 'dateStr',
		title : '时间',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;',
	}, {
		id : 'textCount',
		title : '文本消息',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'imageCount',
		title : '图片消息',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'voiceCount',
		title : '语音消息',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'vodieCount',
		title : '视频消息',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'otherCount',
		title : '第三方应用消息',
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
		loadURL : '/ump/reportmessages/queryDataGridMessage',
		columns : dtGridColumns_2_1_2,
		gridContainer : 'dtGridContainer_2_1_2',
		toolbarContainer : 'dtGridToolBarContainer_2_1_2',
		tools : '',
		pageSize : 10
	};

	//互动消息分布统计
	var option3 = {
		chart : {
			type : 'column',
			renderTo : 'containers3'
		},
		title : {
			text : '消息分布统计'
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
			name : '1-5次',
			data : []
		}, {
			name : '6-10次',
			data : []
		}, {
			name : '10次以上',
			data : []
		} ]
	};

	function queryDistributeData() {
		var platformUser = $("#platformUser3").val();
		var dateType = $("#dateType3").val();
		var title = $("#platformUser3").find("option:selected").attr("title");
		var insert = new Date(title.replace(/-/g, "/")).getTime();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId5").val();
			var commentEndTimeId = $("#commentEndTimeId5").val();
		} else if (dateType == 1) {
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
			} */
			if (end - start > 365 * 24 * 60 * 60 * 1000) {
				alert("最大时间跨度为12个月");
				return false;
			}
		}

		$.post("/ump/reportmessages/queryMessageDistribute", {
			"platform" : platformUser,
			"startTime" : commentStartTimeId,
			"endTime" : commentEndTimeId,
			"dateType" : dateType,
		}, function(data) {
			option3.xAxis.categories = [];
			option3.series[0].data = [];
			option3.series[1].data = [];
			option3.series[2].data = [];
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
						option3.series[0].data.push(objs[i].aCount);
						option3.series[1].data.push(objs[i].bCount);
						option3.series[2].data.push(objs[i].cCount);
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
		grid_2_1_3.load();
	}
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
		title : '时间',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;',
	}, {
		id : 'aCount',
		title : '1-5次',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'bCount',
		title : '6-10次',
		type : 'string',
		columnClass : 'text-center',
		fastQuery : true,
		fastQueryType : 'eq',
		headerStyle : 'background:white;text-align:center;'
	}, {
		id : 'cCount',
		title : '10次以上',
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
		loadURL : '/ump/reportmessages/queryDataGridMessageDistributeDate',
		columns : dtGridColumns_2_1_3,
		gridContainer : 'dtGridContainer_2_1_3',
		toolbarContainer : 'dtGridToolBarContainer_2_1_3',
		tools : '',
		pageSize : 10
	};

	var grid_2_1_1 = $.fn.DtGrid.init(dtGridOption_2_1_1);
	var grid_2_1_2 = $.fn.DtGrid.init(dtGridOption_2_1_2);
	var grid_2_1_3 = $.fn.DtGrid.init(dtGridOption_2_1_3);
	$(function() {
		var chartBar = new Highcharts.Chart(options1);
		var chartBar = new Highcharts.Chart(option2);
		var chartBar = new Highcharts.Chart(option3);
		grid_2_1_1.load();
		grid_2_1_2.load();
		grid_2_1_3.load();
	})

	function exportExcelMessage() {
		var platformUser = $("#platformUser2").val();
		var dateType = $("#dateType2").val();
		var queryType = $("#queryType2").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId3").val();
			var commentEndTimeId = $("#commentEndTimeId3").val();
			var url = "/ump/reportmessages/exportExcelMessage?platform="
					+ platformUser + "&startTime=" + commentStartTimeId
					+ "&endTime=" + commentEndTimeId + "&dateType=" + dateType
					+ "&queryType=" + queryType;
			window.location.href = url;
		} else if (dateType == 1) {
			var commentStartTimeId = $("#commentStartTimeId4").val();
			var commentEndTimeId = $("#commentEndTimeId4").val();
			var url = "/ump/reportmessages/exportExcelMessage?platform="
					+ platformUser + "&startTime=" + commentStartTimeId
					+ "&endTime=" + commentEndTimeId + "&dateType=" + dateType
					+ "&queryType=" + queryType;
			window.location.href = url;
		} else {
			var commentTimeId = $("#commentTimeId").val();
			var url = "/ump/reportmessages/exportExcelMessage?platform="
					+ platformUser + "&commentTimeId=" + commentTimeId
					+ "&dateType=" + dateType + "&queryType=" + queryType;
			window.location.href = url;
		}
	}

	function exportExcelDistribute() {
		var platformUser = $("#platformUser3").val();
		var dateType = $("#dateType3").val();
		if (dateType == 0) {
			var commentStartTimeId = $("#commentStartTimeId5").val();
			var commentEndTimeId = $("#commentEndTimeId5").val();
		} else if (dateType == 1) {
			var commentStartTimeId = $("#commentStartTimeId6").val();
			var commentEndTimeId = $("#commentEndTimeId6").val();
		}
		var url = "/ump/reportmessages/exportExcelDistribute?platform="
				+ platformUser + "&startTime=" + commentStartTimeId
				+ "&endTime=" + commentEndTimeId + "&dateType=" + dateType;
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
				<li><a href="/ump/reportMassPicText/reportPage"><img src="/ump/images/report2.png">内容报表</a></li>
			</ul>
			<ul>
				<li class="actives"><a href="/ump/reportmessages/reportPage"><img src="/ump/images/report3.png">消息报表</a></li>
			</ul>
		</div>
	</div> -->
	<div class="tabs_box">
		<div class="tab-control" data-role="tab-control">
			<ul class="tabs">
				<li class="active"><a href="#_page_1"><i class="on-left"><img
							src="../images/b_img.png"></i>关键词top20</a></li>
				<li><a href="#_page_2"><i class="on-left"><img
							src="../images/b_img.png"></i>互动消息统计</a></li>
				<li><a href="#_page_3"><i class="on-left"><img
							src="../images/b_img.png"></i>消息分布统计</a></li>
			</ul>
			<div class="frames">
				<div class="frame" id="_page_1" style="display: block;">
					<div class="pt">
						<label>公众平台：</label> <select name="platformUser"
							id="platformUser1">
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
							<input type="button" value="查 询" onclick="queryAutoData();">
						</div>
					</div>
					<div id="containers1"
						style="width: 100%; min-width: 320px; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportTop();" style="cursor: pointer;"><i
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
					<div  class="span3">
						<label>公众平台：</label> <select name="platformUser"
							id="platformUser2">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}"
									title="${fn:substring(platformUser.insertTime, 0, 10)}">${platformUser.account}</option>
							</c:forEach>
						</select> 
						</div>
						<div  class="span3">
						<label>统计维度： </label> <select name="queryType" id="queryType2">
							<option value="0">人数</option>
							<option value="1">次数</option>
						</select>
						</div>
					</div>
					<div class="pt_date">
						<div class="pt_date_left">
							<div class="titleDiv">
								<label>统计周期： </label> <select name="dateType" id="dateType2"
									onchange="changeDateType(2);">
									<option value="2">按小时</option>
									<option value="0">按天</option>
									<option value="1">按月</option>
								</select>
							</div>
							<div id="hourDiv2" class="dateDiv">
								<input type="text" class="Wdate" id="commentTimeId"
									placeholder="请选择时间"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',disabledDates:['%y-%M-%d {%H-1}\\:..\\:..','%y-%M-%d {%H+1}\\:..\\:..']})" />
							</div>
							<div id="dayDiv2" class="dateDiv" style="display: none;">
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
							<input type="button" value="查 询" onclick="queryMessageData();">
						</div>
					</div>
					<div id="containers2"
						style="width: 100%; min-width: 320px; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportExcelMessage();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_2" class="dt-grid-container tb_defult">
						<DIV>
							<SPAN></SPAN>
						</DIV>
					</div>
					<div id="dtGridToolBarContainer_2_1_2"
						class="dt-grid-toolbar-container toolbar_defult">
						<SPAN></SPAN>
					</div>
				</div>
				<div class="frame" id="_page_3" style="display: none;">
					<div class="pt">
						<label>公众平台：</label> <select name="platformUser"
							id="platformUser3">
							<c:forEach items="${platformUsers}" var="platformUser">
								<option value="${platformUser.id}"
									title="${fn:substring(platformUser.insertTime, 0, 10)}">${platformUser.account}</option>
							</c:forEach>
						</select>
					</div>
					<div class="pt_date">
						<div class="pt_date_left">
							<div class="titleDiv">
								<label>统计周期： </label> <select name="dateType" id="dateType3"
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
							<input type="button" value="查 询" onclick="queryDistributeData();">
						</div>
					</div>
					<div id="containers3"
						style="width: 100%; min-width: 320px; height: 400px"></div>
					<div
						style="height: 46px; background: #edf1f9; border: 1px solid #e4e4e4; border-bottom: none;"
						class="buttondiv">
						<a onclick="exportExcelDistribute();" style="cursor: pointer;"><i
							class="on-left"><img width="14" height="14"
								src="/ump/resources/images/export.png"></i>导出报表 </a>
					</div>
					<div id="dtGridContainer_2_1_3" class="dt-grid-container tb_defult">
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