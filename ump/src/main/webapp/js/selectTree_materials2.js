var selectSetting2 = {
	check : {
		enable : true,
		chkboxType : {
			"Y" : "",
			"N" : ""
		}
	},
	view : {
		showLine : false,
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick22,
		onCheck : onCheck22
	}
};

function beforeClick22(treeId, treeNode) {

	var zTree2 = $.fn.zTree.getZTreeObj("selectTreeDemo2");
	zTree2.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck22(e, treeId, treeNode) {
	$("#SelVals2").val("");
	var zTree2 = $.fn.zTree.getZTreeObj("selectTreeDemo2"), nodes = zTree2
			.getCheckedNodes(true), v2 = "", vid2 = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v2 += nodes[i].name + ",";
		vid2 += nodes[i].id + ",";
	}
	if (v2.length > 0)
		v2 = v2.substring(0, v2.length - 1);
	vid2 = vid2.substring(0, vid2.length - 1);
	var SelObj2 = $("#SelVals2");
	var selIdsObj2 = $("#SelIds2");
	SelObj2.attr("value", v2);
	selIdsObj2.attr("value", vid2);
	
	$("#SelVals2").val(v2);
	$("#SelIds2").val(vid2);
}

function selectShowMenu2() {
	
	var SelObj2 = $("#SelVals2");
	var SelOffset2 = $("#SelVals2").offset();
	$("#selectMenuContent2").css({
	// 如果弹出位置不对，在此修改位置
	// left:SelOffset.left + "px",
	// top:SelOffset.top + SelObj.outerHeight() + "px"
	}).slideDown("fast");
	
	$("body").bind("mousedown", selectOnBodyDown2);
}
function slelctHideMenu2() {
	$("#selectMenuContent2").fadeOut("fast");
	$("body").unbind("mousedown", selectOnBodyDown2);
	
}
function selectOnBodyDown2(event) {
	if (!(event.target.id == "menuBtn2" || event.target.id == "SelVals2"
			|| event.target.id == "selectMenuContent2" || $(event.target).parents(
			"#selectMenuContent2").length > 0)) {
	/*	if($("#SelIds").val().length!=0){
			var WccMaterialsType = $('#WccMaterialsType').val();
			var SelIds = $('#SelIds').val();
		//	window.location.href='/ump/wccmaterialses/wxarticlelist?WccMaterialsType='+WccMaterialsType+'&sortFieldName=insertTime&sortOrder=DESC&SelIds='+SelIds;
		}*/
		slelctHideMenu2();
		
	}
}

function loadTree2(url) {
	var zNodes2 = new Array();
	$.ajax({
		url : url,
		datatype : "text",
		type : "POST",
		error : function(msg) {
			console.log(msg);
		},
		success : function(data2) {
			
			var str2 = '[' + data2 + ']';
			zNodes2 = eval('(' + str2 + ')');
			$.fn.zTree.init($("#selectTreeDemo2"), selectSetting2, zNodes2);
		}
	});
};

function loadsearchCondiTree(url,inputText){
	var zNodes = new Array();
	$.ajax({
		url : url,
		datatype : "text",
		type : "POST",
		data : {inputText:inputText},
		error : function(msg) {
			console.log(msg);
		},
		success : function(data) {
			var str = '[' + data + ']';
			zNodes = eval('(' + str + ')');
			$.fn.zTree.init($("#selectTreeDemo2"), selectSetting2, zNodes);
		}
	});
}

function loadsearchCondiTree2(url,inputText){
	var zNodes = new Array();
	$.ajax({
		url : url,
		datatype : "text",
		type : "POST",
		data : {inputText:inputText},
		error : function(msg) {
			console.log(msg);
		},
		success : function(data) {
			var str = '[' + data + ']';
			zNodes = eval('(' + str + ')');
			$.fn.zTree.init($("#selectTreeDemo"), selectSetting, zNodes);
		}
	});
}