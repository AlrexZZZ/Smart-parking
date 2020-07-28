var selectVocAccountSetting = {
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
	var zTree = $.fn.zTree.getZTreeObj("selectVocAccountTreeDemo");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("selectVocAccountTreeDemo"),
	nodes = zTree.getCheckedNodes(true),
	v = "", vid = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	}
	if (v.length > 0)
	v = v.substring(0, v.length - 1);
	
	vid = vid.substring(0, vid.length - 1);
	$("#vocAccountVals").val(v);
	$("#vocAccountIds").val(vid);
}

function selectVocAccountMenu() {
	var SelObj = $("#vocAccountVals");
	var SelOffset = $("#vocAccountIds").offset();
	$("#selectVocAccountContent").css({
	// 如果弹出位置不对，在此修改位置
	// left:SelOffset.left + "px",
	// top:SelOffset.top + SelObj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", selectVocAccountOnBodyDown);
}
function selectVocAccountHideMenu() {
	var vocAccountVals = $("#vocAccountVals").val();
	var vocAccountIds = $("#vocAccountIds").val();
	if($.trim(vocAccountVals) != ""){
		$("#vocAccountMsg").html(" ");
	}else{
		$("#vocAccountMsg").html("请选择电商账号");
	}
	$("#selectVocAccountContent").fadeOut("fast");
	$("body").unbind("mousedown", selectVocAccountOnBodyDown);
}
function selectVocAccountOnBodyDown(event) {
	if (!(event.target.id == "menuBtn" || event.target.id == "vocShopVals"
			|| event.target.id == "selectVocAccountContent" || $(event.target).parents(
			"#selectVocAccountContent").length > 0)) {
		selectVocAccountHideMenu();
	}
}

var shopflag = true;
var ids;
function loadVocAccountTree(url,shoplIds,type,shopIds) {
	if(type == true){
		ids = shoplIds;
		shopflag = true;
	}else{
		ids = shopIds;
		shopflag = false;
		var idss = shoplIds.split(',');
	}
	var zNodes = new Array();
	if(ids != ""){
		$.ajax({
			url : url,
			datatype : "text",
			type : "POST",
			data:{'shoplIds':ids},
			error : function(msg) {
				console.log(msg);
			},
			success : function(data) {
				var str = '[' + data + ']';
				zNodes = eval('(' + str + ')');
				$.fn.zTree.init($("#selectVocAccountTreeDemo"), selectVocAccountSetting, zNodes);
				if(shopflag == false){
					var zTree = $.fn.zTree.getZTreeObj("selectVocAccountTreeDemo");
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
					$("#vocAccountVals").val(v);
				}
			}
		});
	}
};








