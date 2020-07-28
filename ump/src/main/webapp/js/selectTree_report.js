var selectSetting = {
	check : {
		enable : true,
		chkStyle: "radio",
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
		beforeClick : beforeClick,
		onCheck : onCheck
	}
};

function beforeClick(treeId, treeNode) {

	var zTree = $.fn.zTree.getZTreeObj("selectTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	$("#SelVals").val("");
	
	var zTree = $.fn.zTree.getZTreeObj("selectTreeDemo"), nodes = zTree
			.getCheckedNodes(true), v = "", vid = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	vid = vid.substring(0, vid.length - 1);
	var SelObj = $("#SelVals");
	var selIdsObj = $("#platformUser");
	SelObj.attr("value", v);
	selIdsObj.attr("value", vid);
	
	$("#SelVals").val(v);
	$("#platformUser").val(vid);
}

function selectShowMenu() {
	var SelObj = $("#SelVals");
	var SelOffset = $("#SelVals").offset();
	$("#selectMenuContent").css({
	// 如果弹出位置不对，在此修改位置
	// left:SelOffset.left + "px",
	// top:SelOffset.top + SelObj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", selectOnBodyDown);
}
function slelctHideMenu() {
	$("#selectMenuContent").fadeOut("fast");
	$("body").unbind("mousedown", selectOnBodyDown);
}
function selectOnBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "SelVals"
			|| event.target.id == "selectMenuContent" || $(event.target).parents(
			"#selectMenuContent").length > 0)) {
		if($("#platformUser").val().length!=0){
			var WccMaterialsType = $('#WccMaterialsType').val();
			var SelIds = $('#platformUser').val();
//			window.location.href='/ump/wccmaterialses/wxarticlelist?WccMaterialsType='+WccMaterialsType+'&sortFieldName=insertTime&sortOrder=DESC&SelIds='+SelIds;
		}
		slelctHideMenu();
	}
}

function loadTree(url) {
	var zNodes = new Array();
	$.ajax({
		url : url,
		datatype : "text",
		type : "POST",
		error : function(msg) {
			console.log(msg);
		},
		success : function(data) {
			var str = '[' + data + ']';
			zNodes = eval('(' + str + ')');
			$.fn.zTree.init($("#selectTreeDemo"), selectSetting, zNodes);
			
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
			$.fn.zTree.init($("#selectTreeDemo"), selectSetting, zNodes);
		}
	});
}



