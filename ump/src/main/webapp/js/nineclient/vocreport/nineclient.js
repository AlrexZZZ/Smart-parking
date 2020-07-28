/**
 * josn to string
 */
		function josn_to_String(time) {
			var datetime = new Date();
			datetime.setTime(time);
			var year = datetime.getFullYear();
			var month = datetime.getMonth() + 1;// js从0开始取
			var date = datetime.getDate();
			var hour = datetime.getHours();
			var minutes = datetime.getMinutes();
			var second = datetime.getSeconds();

			if (month < 10) {
				month = "0" + month;
			}
			if (date < 10) {
				date = "0" + date;
			}
			if (hour < 10) {
				hour = "0" + hour;
			}
			if (minutes < 10) {
				minutes = "0" + minutes;
			}
			if (second < 10) {
				second = "0" + second;
			}

			var time = year + "-" + month + "-" + date + " " + hour + ":" + minutes
					+ ":" + second; // 2009-06-12 17:18:05
			return time;
		}

/**
 * 时间格式转换 fmt:yyyy-MM-dd yyyy-MM-dd hh:mm:ss
 * 
 * @param fmt
 * @returns
 */
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}
/**
 * 默认 yyyy-MM-dd hh:mm:ss
 * 
 * @param time
 *            long 类型时间
 * @returns
 */
function formatData(time, ftm) {
	if (!(ftm)) {
		ftm = "yyyy-MM-dd hh:mm:ss"
	}
	if(isNaN(time)||time==''){
		return "";
	}
	var date = new Date(time);
	var str = date.Format(ftm);
	return str;
}
/**
 * 时间字符串转日期
 * @param str
 * @returns {Date}
 */
function dateStrToDate(str){
	 var t=new Date(str.replace(/-/g,"/"));
	 return t;
}
function nowDateCompare(obj){
	var str=$(obj).val();
	 var t=new Date(str.replace(/-/g,"/"));
	 if(t.getTime()-new Date().getTime()>0){
		 Notify("选择的时间不能大于当前时间", 2000);
		 $(this).val('');
	 }
}	
// 条形图
var hBarchart = {
	chart : {
		type : 'bar'
	},
	exporting: { enabled:false },
	title : {
		text : '产品问题排行榜Top10',
        style:{
        	
        	fontSize: "16px",
        	fontFamily:'微软雅黑'
        }
	},
	
	subtitle : {
		text : ''
	},

	yAxis : {
		min : 0,
		title : {
			text : '数量 (个)',
			align : 'high'
		},
		labels : {
			overflow : 'justify'
		}
	},
	tooltip : {
		valueSuffix : '个'
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
		name : '差评关键词数',
		data : [ 990, 900, 880, 700, 680, 570, 500, 480, 410, 370 ]
	} ]
};

/**
 * jsonStr={ title:'差评关键词数', categories:
 * ['海飞丝洗面奶','清扬洗面奶','飘柔护发素','清扬护发素','飘柔洗发露', '清扬洗发露', '海飞丝护发素', '潘婷护发素',
 * '海飞丝洗发露', '潘婷洗发露'] data: [990,900,880,700,680,570,500,480,410,370] } 初始化条形图
 */
var inithBarChar = function(title, jsonStr) {
	hBarchart.title.text = title;
	var seriesObj = {
		name : '差评关键词数'
	};
	// 条形图x轴
	var xAxis = {
		title : {
			text : null
		},
		categories : [ '海飞丝洗面奶', '清扬洗面奶', '飘柔护发素', '清扬护发素', '飘柔洗发露', '清扬洗发露',
				'海飞丝护发素', '潘婷护发素', '海飞丝洗发露', '潘婷洗发露' ]
	};
	/**
	 * 输入数据
	 */
	if (jsonStr) {
		var data = [];
		var categories = [];
		seriesObj.name = title;
		$.each(jsonStr.dataObj, function(id, dataObj) {
			categories.push(dataObj.name);
			data.push(dataObj.count);
		});
		var series = [];
		seriesObj.data = data;
		series.push(seriesObj);
		xAxis.categories = categories;
		hBarchart.series = series;
		hBarchart.xAxis = xAxis;
	}
	return hBarchart;
}
/**
 * 饼形图初始化
 */
var initHchartPie = function(title, data) {
	/**
	 * hchar 标题
	 */
	var hchartPie = {
		chart : {
			plotBackgroundColor : null,
			plotBorderWidth : null,
			plotShadow : false
		},
		exporting: { enabled:false },
		title : {
			text : '产品热词占比',
            style:{
            	
            	fontSize: "16px",
            	fontFamily:'微软雅黑'
            }
		},
		tooltip : {
			pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : true,
					color : '#000000',
					connectorColor : '#000000',
					formatter : function() {
						return '<b>' + this.point.name + '</b>: '
								+ Math.round(this.percentage * 100) / 100
								+ ' %';
					}
				}
			}
		},
		series : [ {
			type : 'pie',
			name : '占',
			data : [ [ '效果好', 41.0 ], [ '便宜', 33.0 ], {
				name : '发货快',
				y : 12.0,
				sliced : true,
				selected : true
			}, [ '大品牌', 8.0 ], [ '包装好', 6.0 ]

			]
		} ]

	};
	var pieObj = {
		type : 'pie',
		name : '占'
	};
	if (data) {
		/**
		 * 输入数据
		 */
		var series = [];
		hchartPie.title.text = title;
		pieObj.data = data;
		series.push(pieObj);
		hchartPie.series = series;
	}
	return hchartPie;
}
/**
 * 
 * @param url
 *            请求地址
 * @param param
 *            请求参数
 * @param notifyCallBack
 *            提示函数
 * @param callBack
 *            回调无参函数
 * @param callBackData
 *            回调有参函数
 * @param effectId
 *            标签id
 */
function dataAjax(url, param, notifyCallBack, callBack, callBackData, effectId,columName) {
	$.ajax({
		url : url,
		async : false,
		dataType : "text",
		type : "POST",
		data : param,
		error : function(msg) {
		},
		success : function(data) {
			if (notifyCallBack != undefined && notifyCallBack) {
				notifyCallBack(data);
			}
			if (callBack) {
				callBack();

			}
			if (callBackData != undefined && callBackData) {
				if (effectId != undefined && effectId) {
					if(columName){
						callBackData(data, effectId,columName);
					}else{
						callBackData(data, effectId);
					}
				} else {
					callBackData(data);
				}

			}
		}
	});
}
/**
 * 结束时间要大于开始时间
 * 
 * @param startTime
 * @param endTime
 */
function comPareStartTimeAndEndTime(startTime, endTime) {
	if (startTime && endTime) {
		return endTime > startTime;
	}
	return true;
}
/**
 * 提示
 * 
 * @param msg
 * @param time
 */
function Notify(msg, time) {
	var t;
	if (time) {
		t = time;
	} else {
		t = 5000;
	}
	$.Notify({
		caption : "",
		content : msg,
		timeout : t
	});
}
/**
 * 创建select option
 * 
 * @param data
 *            数据[{name:'a',id:1},{name:'b',id:2}]
 * @param id
 *            selectId
 */
function createSelect(data, id,name) {
	var dataList = eval(data);
	var sel = "";
	var flag = true;
	var showName;
	for (var i = -1; i < dataList.length; i++) {
		if (flag) {
			flag = false;
			sel += "<option value=''>请选择</option>";
		} else {
			if(name){
				showName =  eval('dataList['+i+'].'+name);
			}else{
				showName = dataList[i].name
			}
			sel += "<option value=" + dataList[i].id + ">" +showName
					+ "</option>";
		}
	}
	$("#" + id).html(sel);
}


function showPlatform(id){
	$.ajax({
		url : "/ump/wccplatformusers/platformBeanToJson",
		async : false,
		dataType : "text",
		type : "POST",
		data :{},
		error : function(msg) {
			if(msg == "error"){
				alertCon("操作失败！");
				return false;
			}
		},
		success : function(data) {
			if(data == "error"){
				alertCon("操作失败！");
				return false;
			}else{
				var dataList = eval(data);
				var sel = "";
				var flag = true;
				for (var i = -1; i < dataList.length; i++) {
					if (flag) {
						flag = false;
						sel += "<option value=-1>请选择</option>";
					} else {
						sel += "<option value=" + dataList[i].id + ">" + dataList[i].account
								+ "</option>";
					}
				}
				$("#" + id).html(sel);
            }
		}
	});
}

/**
 * 折线图初始化 title 大标题 subTitle 小标题 YTitle y轴标题 data ={ categories: ['2014/2/1',
 * '2014/2/2', '2014/2/3', '2014/2/4', '2014/2/5', '2014/2/6'] series: [{ name:
 * '大品牌', data: [200, 220, 230, 250, 190, 215] }, { name: '效果好', data:
 * [1000,900,950, 920, 850,780] }, { name: '便宜', data: [800,830,950, 700,
 * 850,900] }, { name: '发货快', data: [300,350,950, 920, 850,780] }] }
 * 
 */
var initHlineChart = function(title, subTitle, Ytitle, data) {
	if (data) {
		var series = data.series;
		var categories = data.categories;
		hLineChart.title.text = title;
		hLineChart.subtitle.text = subTitle;
		hLineChart.xAxis.categories = categories;
	    hLineChart.series = series;
	}
	hLineChart.yAxis.title = Ytitle;
	return hLineChart;
}
/**
 * 折线图
 */
var hLineChart = {
	chart : {
		type : 'line'
	},
	exporting: { enabled:false },
	title : {
		text : '好评标签趋势图',
        style:{
        	
        	fontSize: "16px",
        	fontFamily:'微软雅黑'
        }
	},
	subtitle : {
		text : ''
	},
	xAxis : {  labels: {
        rotation:-45,
        step:3
    },
		categories : [ '2014/2/1', '2014/2/2', '2014/2/3', '2014/2/4',
				'2014/2/5', '2014/2/6' ]
	},
	yAxis : {
		title : {
			text : '数量'
		}
	},
	tooltip : {
		enabled : false,
		formatter : function() {
			return '<b>' + this.series.name + '</b><br/>' + this.x + ': '
					+ this.y + '°C';
		}
	},
	plotOptions : {
		line : {
			dataLabels : {
				enabled : true
			},
			enableMouseTracking : false
		}
	},
	series : [ {
		name : '大品牌',
		data : [ 200, 220, 230, 250, 190, 215 ]
	}, {
		name : '效果好',
		data : [ 1000, 900, 950, 920, 850, 780 ]
	}, {
		name : '便宜',
		data : [ 800, 830, 950, 700, 850, 900 ]
	}, {
		name : '发货快',
		data : [ 300, 350, 950, 920, 850, 780 ]
	} ]
};

var spiderHchart = {
	chart : {
		polar : true,
		type : 'line'
	},
	exporting: { enabled:false },
	title : {
		text : '购买动机分析',
		x : -80
	},
	pane : {
		size : '80%'
	},

	xAxis : {
		categories : [ '去屑效果好', '味道好', '包装好', '价格实惠', '快递很效率', '服务态度好' ],
		tickmarkPlacement : 'on',
		lineWidth : 0
	},

	yAxis : {
		gridLineInterpolation : 'polygon',
		lineWidth : 0,
		min : 0
	},

	tooltip : {
		shared : true,
		pointFormat : '<span style="color:{series.color}">'
	},

	legend : {
		align : 'right',
		verticalAlign : 'top',
		y : 70,
		layout : 'vertical'
	},

	series : [ {
		name : 'XXSKU购买动机',
		data : [ 1, 1, 1, 1, 0, 0 ],
		pointPlacement : 'on'
	} ]
}
/**
 * 蜘蛛网图 title 标题 data={ categories: ['去屑效果好', '味道好', '包装好', '价格实惠',
 * '快递很效率','服务态度好'] series: [{ name: 'XXSKU购买动机', data: [1, 1, 1, 1, 0, 0],
 * pointPlacement: 'on' }] }
 */
var initSpiderHchart = function(title, data) {
	if (data) {
		spiderHchart.title.text = title;
		spiderHchart.xAxis.categories = data.categories;
		spiderHchart.series = data.series;
	}
	return spiderHchart;
}
/**
 * 柱状图
 */
var barHchartY = {
	chart : {
		type : 'column',
		margin : [ 1, 50, 50, 00, 80 ]
	},
	exporting: { enabled:false },
	title : {
		text : '坐席工作量报表',
        style:{
        	
        	fontSize: "16px",
        	fontFamily:'微软雅黑'
        }
	},
	xAxis : {
		categories : [ 'Tony', 'Sandy', 'Jade', 'Jack'

		],
		labels : {
			rotation : -45,
			align : 'right',
			style : {
				fontSize : '13px',
				fontFamily : 'Verdana, sans-serif'
			}
		}
	},
	yAxis : {
		min : 0,
		title : {
			text : ''
		}
	},
	legend : {
		enabled : false
	},
	tooltip : {
		pointFormat : '在2014-2-1至2014-2-7 回复数： <b>{point.y:.1f} </b>',
	},
	series : [ {
		name : 'Population',
		data : [ 340, 280, 210, 180, ],
		dataLabels : {
			enabled : true,
			rotation : -90,
			color : '#FFFFFF',
			align : 'right',
			x : 4,
			y : 10,
			style : {
				fontSize : '13px',
				fontFamily : 'Verdana, sans-serif',
				textShadow : '0 0 3px black'
			}
		}
	} ]
};
/**
 * title:标题 data={ series:[{ name: 'Population', data: [340, 280, 210, 180, ] }]
 * pointFormat:'在2014-2-1至2014-2-7 回复数： <b>{point.y:.1f} </b>', categories:[
 * 'Tony', 'Sandy', 'Jade', 'Jack' ] }
 */
var initBarHchartY = function(title, data) {
	var dataLabels = {
		enabled : true,
		rotation : -90,
		color : '#FFFFFF',
		align : 'right',
		x : 4,
		y : 10,
		style : {
			fontSize : '13px',
			fontFamily : 'Verdana, sans-serif',
			textShadow : '0 0 3px black'
		}
	};
	if (data) {
		var series = data.series;
		for (var i = 0; i < series.length; i++) {
			series[i].dataLabels = dataLabels;
		}
		barHchartY.series = series;
		barHchartY.xAxis.categories = data.categories;
		barHchartY.title.text = title;
		barHchartY.tooltip = data.tooltip;
	}
	return barHchartY;
};
/**
 * 分页
 * 
 * @param model
 *            数据
 * @param query
 *            查询方法
 * @param colspan
 *            占列
 *@param addBtn添加按钮            
 *@param addFunction 添加函数        
 *@param dloagId  添加按钮弹出添加框 div 的ID
 * @returns {String} 返回的分页html
 * 
 */
function Page(model, query, colspan,addBtn,addFunction,dloagId) {
	var id="\'"+dloagId+"\'";
	var turnPage = '';
	//if (model.totalCount > 0) {
		if(addBtn&&dloagId){
			turnPage+= '<a onclick="'+addFunction+'('+id+');"><img onmouseover="imageMouseOn(this);" onmouseout="imageMouseOut(this);" style="cursor: pointer;float:left;" src="/ump/images/w_add_02.png"/></a>';
		}
		else if(addBtn){
			turnPage+= '<a onclick="'+addFunction+'();"><img onmouseover="imageMouseOn(this);" onmouseout="imageMouseOut(this);" style="cursor: pointer;float:left;" src="/ump/images/w_add_02.png"/></a>';
		}
		//首页
		if (model.pageNo > 1){
			turnPage += ' <a class="default"  style="cursor: pointer;" onclick="' + query + '(1)"><img src="/ump/images/resultset_first.png"/></a>';
		}
		//上一页
		if (model.pageNo > 1) {
			turnPage += ' <a class="default"  style="cursor: pointer;margin-left:10px;" onclick="' + query + '('
					+ (model.pageNo - 1) + ')"><img src="/ump/images/resultset_previous.png"/></a>';
		}
		
		turnPage +='<span style="color:#069DD5;height: 22px;line-height: 22px;margin-left:10px;">'+ model.pageNo + '/'
				+ model.totalPage + '</span><INPUT id="_go" style="margin-left:10px;width: 30px;height:22px"/><a class="info" style="width: 28px;height:22px;background-color:#069dd5;color: white;display:inline-block;font-family:Arial;font-size: 10px;line-height: 22px;text-align:center;cursor: pointer;" onclick="getGoPage(this,'+query+','+model.pageNo+','+model.totalPage+')">GO</a>';
		//下一页
		if (model.pageNo != model.totalPage) {
			turnPage += '<a onclick="' + query + '('
					+ (model.pageNo + 1)
					+ ')" style="cursor: pointer;margin-left:10px;"><img src="/ump/images/resultset_next.png"/></a>';
		}
		//末页
		if (model.pageNo != model.totalPage){
			turnPage += ' <a class="default"  style="cursor: pointer;margin-left:5px;" onclick="' + query + '('+model.totalPage+')"><img src="/ump/images/resultset_last.png"/></a>';
		}
		turnPage += '<span style="margin-left:10px;">每页'+'<span style="color:#069DD5;">'+model.pageSize+'</span>条</span>';
		return turnPage;
	//}
}
function addMethod(dloagId,addFunction){
	if(dloagId){
		addFunction(dloagId);
	}
	else{
		addFunction();
	}
	
}
function imageMouseOn(obj){
	$(obj).attr('src','/ump/images/w_add.png')
}
function imageMouseOut(obj){
	$(obj).attr('src','/ump/images/w_add_02.png')
}
function getGoPage(obj,method,page,totalPage){
	if(totalPage<1){
		return;
	}
	var p=$(obj).prev('input').val()
	if(isNaN(p)){
		p=page;
		$(obj).val(p);
		return;
	}else if(totalPage<=p){
		p=totalPage;
		$(obj).val(p);
	}
	method(p);
}
function checkItem(method,id,page,isRemark) {
	$.Dialog({
				overlay : true,
				shadow : true,
				flat : true,
				icon : '<img src="/ump/images/swatch.png">',
				title : 'Flat window',
				content : '',
				width:300,
				height:250,
				padding : 10,
				onShow : function(_dialog) {
					var content = '<div class="grid" style="width:300px;">'
							+ '<div class="row">'
							+ '<div class="span1"></div>'
							+ '<div class="span">'
							+ '<input type="radio" id="_status_1" name="status" checked="checked" value="1"/>'
							+ '<span>审核通过</span></div>'
							+ '<div class="span">'
							+ '<input type="radio" id="_status_2" name="status" value="2"/>'
							+ '<span>审核不通过</span></div>'
							+ '</div>'
							+ '<div id="remark" class="row">'
							+ '<div class="span">备注：</div>'
							+ '<div class="span">'
							+ '<TEXTAREA name="remark" class="gridTextAreaSmall"></TEXTAREA></div>'
							+ '</div>'
							+ '</div>'
							+ '<div class="btnCenterNoP">'
							+ '<a class="button info" id="_checkBtn">确定</a> '
							+ '<button class="warning" type="button" onclick="$.Dialog.close()">取消</button> '
							+ '</div>'
							;
					$.Dialog.title("请选择");
					$.Dialog.content(content);
					$.Metro.initInputs();
					$("#_checkBtn").click(function(){
						method(id,page);
					});
				}
			});
	
}
function waitDialog(){
	$.Dialog({
		overlay : true,
		shadow : true,
		flat : true,
		icon : '<img src="/ump/images/swatch.png">',
		title : 'Flat window',
		content : '',
		width:300,
		height:100,
		padding : 10,
		onShow : function(_dialog) {
			var content = '<div style="width:300px;height:100px;text-align: center;margin-top:150px;">'
					+'<image src="/ump/images/pub/waiting.gif"/>'
					+ '</div>'
					+ '<div class="btnCenterNoP">'
					+ '<button class="info" type="button" onclick="$.Dialog.close()">确定</button> '
					+ '</div>'
					;
			$.Dialog.title("审核中");
			$.Dialog.content(content);
			$.Metro.initInputs();
		}
	});
}
function checkEmailItem(method,html) {
	$.Dialog({
				overlay : true,
				shadow : true,
				flat : true,
				icon : '<img src="/ump/images/swatch.png">',
				title : 'Flat window',
				content : '',
				padding : 10,
				onShow : function(_dialog) {
					$.Dialog.title("请选择");
					$.Dialog.content(html);
					$.Metro.initInputs();
					$("#_checkBtns").click(function(){
						var contents = $("#contentId").val();
						var title = $("#titleId").val();
						var company = $("#companyId").val();
						method(contents,title,company);
					});
				}
			});
}

/**
 *alert展示框 
 */
function alertCon(cons) {
	$.Dialog({
				overlay : true,
				shadow : true,
				flat : true,
				icon : '<img src="/ump/images/swatch.png">',
				title : 'Flat window',
				content : '',
				padding : 10,
				onShow : function(_dialog) {
					var content = '<div class="grid" style="width:180px;">'
							+ '<div class="row">'
							+ '<div style="word-break:break-all;width:100%;">'+cons+'</div>'
							+ '</div>'
							+ '<div class="row">'
							+ '<div class="span2" style="margin:0 auto;text-align:center;">'
							+ '<button class="warning" type="button" onclick="$.Dialog.close()">确定</button> '
							+ '</div></div></div>';
					$.Dialog.title("提示");
					$.Dialog.content(content);
					$.Metro.initInputs();
				}
			});
}
/**
 * 弹出窗居中
 * @param id
 */
function dilogCenter(id){
    $("html").scrollTop("0");
    var winw=$(window).width();
    var winh=$(window).height();
    var winw2=(parseInt(winw)-parseInt($("#"+id).width()))/2;
    var hit=(parseInt(winh)-parseInt($("#"+id).height()))/2;
    $("#"+id).css("left",winw2+"px");
    $("#"+id).css("top",hit+"px");
    $(window).resize(function() {
  	  var winw=$(window).width();
      var winh=$(window).height();
      var winw2=(parseInt(winw)-parseInt($("#"+id).width()))/2;
      var hit=(parseInt(winh)-parseInt($("#"+id).height()))/2;
      $("#"+id).css("left",winw2+"px");
      $("#"+id).css("top",hit+"px");
	 });
}
/**
 * 
 * @param url
 * @param param
 * @returns
 */
function ajaxJsonReturn(url,param){
	var json;
	$.ajax({
		url : url,
		async : false,
		dataType : "text",
		type : "POST",
		data : param,
		error : function(msg) {
		},
		success : function(data) {
			json=data;
		}
	});
	return json;
}

/**
 * 找到阴影 解绑click事件
 * 
 */
function metroOverFlow(id,isClose){
		$(".window-overlay").unbind('click');
}
/**
 * ztree 父节点不显示 删除
 * @param treeId
 * @param treeNode
 * @returns
 */
function setRemoveBtn(treeId, treeNode) {
	return !treeNode.isParent;
}
/**
 * 用于在节点上固定显示用户自定义控件
 * @param treeId
 * @param treeNode
 */
function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#diyBtn_"+treeNode.id).length>0) return;
	var editStr = "<span class='button add' id='diyBtn_" + treeNode.id
		+ "' title='添加' onfocus='this.blur();'></span>";
	aObj.append(editStr);
	var btn = $("#diyBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){
		addBusinessType();
	});
};
/**
 * 设置鼠标移到节点上，在后面显示一个按钮
 * @param treeId
 * @param treeNode
 */
function addHoverDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	if ($("#diyBtn_"+treeNode.id).length>0) return;
	var editStr = "<span class='button add' id='diyBtn_" + treeNode.id
		+ "' title='添加' onfocus='this.blur();'></span>";
	aObj.append(editStr);
	var btn = $("#diyBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){
		
		alert("2222");
	});
};
/**
 * 
 * @param treeId
 * @param treeNode
 */
function removeHoverDom(treeId, treeNode) {
	$("#diyBtn_"+treeNode.id).unbind().remove();
};
/**
 * 节点等级显示图标
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function showIconForTree(treeId, treeNode) {
	return !treeNode.isDefault;
};
function setOptBtn(treeId, treeNode) {
	return !treeNode.isDefault;
}

/**
 * 同步请求方法
 * @param method 指定请求方式 ： get / post
 * @param url  请求的url
 * @returns 请求后的返回值
 */
function requestMethod(method,url) { 
	  var xmlhttp ;
	  if (window.XMLHttpRequest) { 
	  //isIE = false; 
	  xmlhttp = new XMLHttpRequest(); 
	 } 
	  else if (window.ActiveXObject) { 
	 //isIE = true; 
	  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); 
	 } 
	 if(null != url ){
		 var d=new Date();
		 url = url+"&time="+d.getTime();
	 } 
	 xmlhttp.open(method,url, false); 
	 xmlhttp.send(null); 

	 var result = xmlhttp.status; 
	 var returnVal = null;
	  //OK 
	  if(result==200) 
		returnVal =  xmlhttp.responseText;
		
	  xmlhttp = null; 
	  return returnVal;

	} 


//判断字符
function strlen(str) {
     var len = 0;
     for (var i = 0; i < str.length; i++) {
           var c = str.charCodeAt(i); //单字节加1 
           if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                      len++;
           } else {
               len += 2;
           }
	}
       return len;
} 	
