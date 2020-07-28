var selectChannelSetting = {
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
	var zTree = $.fn.zTree.getZTreeObj("selectVocChannTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectVocChannTreeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "", vid = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	}
	if (v.length > 0)
	v = v.substring(0, v.length - 1);
	vid = vid.substring(0, vid.length - 1);
	$("#channelVals").val(v);
	$("#vocChannIds").val(vid);
	
	$("#vocShopVals").val('');
	$("#vocShopIds").val('');
}
function selectVocChannMenu() {
	var SelObj = $("#channelVals");
	var SelOffset = $("#channelVals").offset();
	$("#selectvocChannContent").css({
	}).slideDown("fast");
	$("body").bind("mousedown", selectChannlOnBodyDown);
}
function selectChannelHideMenu() {
	var chanVals = $("#channelVals").val();
	var channelIds = $("#vocChannIds").val();
	if($.trim(chanVals) != ""){
		$("#channelMsg").html(" ");
		loadVocShopsTree('/ump/vocshops/tree',channelIds,true,null);
	}else{
		$("#channelMsg").html("请选择平台!");
	}
	$("#selectvocChannContent").fadeOut("fast");
	$("body").unbind("mousedown", selectChannlOnBodyDown);
}
function selectChannlOnBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "channelVals"
			|| event.target.id == "selectvocChannContent" || $(event.target).parents(
			"#selectvocChannContent").length > 0)) {
		selectChannelHideMenu();
	}
}
var flagChannel = true;
var idss;
function loadUmpChannelTree(url,umpChanneIds) {
	if($.trim(umpChanneIds) == ""){
		flagChannel = true;
	}else{
		idss = umpChanneIds.split(',');
		flagChannel = false;
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
			$.fn.zTree.init($("#selectVocChannTreeDemo"), selectChannelSetting, zNodes);
			if(flagChannel == false){
				var zTree = $.fn.zTree.getZTreeObj("selectVocChannTreeDemo");
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
				$("#channelVals").val(v);
			}
		}
	});
};







