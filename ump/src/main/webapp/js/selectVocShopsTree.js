var selectVocShopSetting = {
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
	var zTree = $.fn.zTree.getZTreeObj("selectVocShopTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectVocShopTreeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "", vid = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	}
	if (v.length > 0)
	v = v.substring(0, v.length - 1);
	
	vid = vid.substring(0, vid.length - 1);
	$("#vocShopVals").val(v);
	$("#vocShopIds").val(vid);
	
	$("#vocBrandVals").val('');
	$("#vocBrandIds").val('');
	$("#vocAccountVals").val('');
	$("#vocAccountIds").val('');
}

function selectVocShopMenu() {
	var SelObj = $("#vocShopVals");
	var SelOffset = $("#vocShopIds").offset();
	$("#selectVocShopContent").css({
	}).slideDown("fast");

	$("body").bind("mousedown", selectVocShopOnBodyDown);
}
function selectVocShopHideMenu() {
	var vocShopVals = $("#vocShopVals").val();
	var vocShopIds = $("#vocShopIds").val();
	if($.trim(vocShopVals) != ""){
		$("#vocShopMsg").html(" ");
		loadVocBrandTree('/ump/vocbrands/tree',vocShopIds,true);
		loadVocAccountTree('/ump/vocaccounts/tree',vocShopIds,true);
	}else{
		$("#vocShopMsg").html("请选择店铺!");
	}
	$("#selectVocShopContent").fadeOut("fast");
	$("body").unbind("mousedown", selectVocShopOnBodyDown);
}
function selectVocShopOnBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "vocShopVals"
			|| event.target.id == "selectVocShopContent" || $(event.target).parents(
			"#selectVocShopContent").length > 0)) {
		selectVocShopHideMenu();
	}
}

var shopflag = true;
var ids;
function loadVocShopsTree(url,channelIds,type,channeIds) {
	if(type == true){
		ids = channelIds;
		shopflag = true;
	}else{
		ids = channeIds;
		shopflag = false;
		var idss = channelIds.split(',');
	}
	var zNodes = new Array();
	if(ids != ""){
		$.ajax({
			url : url,
			datatype : "text",
			type : "POST",
			data:{'channelIds':ids},
			error : function(msg) {
				console.log(msg);
			},
			success : function(data) {
				var str = '[' + data + ']';
				zNodes = eval('(' + str + ')');
				$.fn.zTree.init($("#selectVocShopTreeDemo"), selectVocShopSetting, zNodes);
				if(shopflag == false){
					var zTree = $.fn.zTree.getZTreeObj("selectVocShopTreeDemo");
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
					$("#vocShopVals").val(v);
				}
			}
		});
	}
};








