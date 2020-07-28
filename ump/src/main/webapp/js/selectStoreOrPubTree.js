var selectSetting = {
	check : {
		enable : true,
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
	var zTree = $.fn.zTree.getZTreeObj("selectTreeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "", vid = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	}
	if (v.length > 0)
	v = v.substring(0, v.length - 1);
	
	vid = vid.substring(0, vid.length - 1);
	$("#SelVals").val(v);
	$("#SelIds").val(vid);
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
	var id = $("#SelVals").val();
	if($.trim(id) != ""){
		$("#platformMsg").html(" ");
	}else{
		$("#platformMsg").html("请选择公众平台!");
	}
	$("#selectMenuContent").fadeOut("fast");
	$("body").unbind("mousedown", selectOnBodyDown);
}
function selectOnBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "SelVals"
			|| event.target.id == "selectMenuContent" || $(event.target).parents(
			"#selectMenuContent").length > 0)) {
		slelctHideMenu();
	}
}

var flag = true;
function loadTree(url,platformUserIds) {
	if($.trim(platformUserIds) == ""){
		flag = true;
	}else{
		flag = false;
		var idss = platformUserIds.split(',');
	}
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
			if(flag == false){
				var zTree = $.fn.zTree.getZTreeObj("selectTreeDemo");
				var nodes = zTree.transformToArray(zTree.getNodes()),v = "";
				for(n in idss){
					for(i in nodes){
						var id1 = nodes[i].id;
						var id2 = idss[n];
						if(id1==id2){
							zTree.checkNode(nodes[i],true,false);
							v += nodes[i].name + ",";
						}
					}
				}
				if (v.length > 0)
				v = v.substring(0, v.length - 1);
				$("#SelVals").val(v);
			}
		}
	});
};








