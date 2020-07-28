function updatePassword() {
			var id = $('#useId').val();
			var operatorName = $('#operatorName').val();
			var password = $('#password').val();
			var repass = $('#repass').val();
			var flagOperator = $('#flagOperator').val();
			if (operatorName.length == 0) {
				$('#userHint1').html("用户名不可为空");
				return false;
			}else{
				$('#userHint1').html("");
			}
			var reg = /^[\@A-Za-z0-9\!\#\$\%\^\*\.\~]{6,}$/;
			if (!reg.test(password)) {
				$('#userHint2').html('字母、数字或者英文符号，最短6位');
				return false;
			}else{
				$('#userHint2').html('');
			}
			if (password != repass) {
				$('#userHint3').html("密码不一致");
				return false;
			}else{
				$('#userHint3').html("");
			}
			if (flagOperator == 1) {
				$.post("/ump/puboperators/updatePassword", {
					"id" : id,
					"operatorName" : operatorName,
					"password" : password,
				}, function(data) {
					if (data == 0) {
						alert("修改成功！");
						$.Dialog.close();
						location.href = "/ump/loginOut";
					} else {
						alert("修改失败！");
					}
				});
			} else {
				$.post("/ump/umpoperators/updatePassword", {
					"id" : id,
					"operatorName" : operatorName,
					"password" : password,
				}, function(data) {
					if (data == 0) {
						alert("修改成功！");
						$.Dialog.close();
						location.href = "/ump/loginOut";
					} else {
						alert("修改失败！");
					}
				});

			}
		}
		var htmlVar = "";
		function openDialog() {
			if ($('#center').html()) {
				htmlVar = $('#center').html();
			} else {
				$('#center').html(htmlVar);
			}
			$.Dialog({
				overlay : true,
				shadow : true,
				flat : true,
				icon : '<img src="/ump/resources/images/people.png"/>',
				title : '个人中心',
				content : '',
				padding : 20,
				onShow : function(_dialog) {
					$('#center').html("");
					$.Dialog.content(htmlVar);
					$.Metro.initInputs();
				}
			});

		}
		function exit() {
			$.Dialog.close();
		}
		var exitVar = "";
		function loginOut() {
			if ($('#exitDialog').html()) {
				exitVar = $('#exitDialog').html();
			} else {
				$('#exitDialog').html(exitVar);
			}
			$.Dialog({
				overlay : true,
				shadow : true,
				flat : true,
				icon : '<img src="/ump/resources/images/tuichu.png"/>',
				title : '提醒',
				content : '',
				padding : 20,
				height : 100,
				width : 100,
				onShow : function(_dialog) {
					$('#exitDialog').html("");
					$.Dialog.content(exitVar);
					$.Metro.initInputs();
				}
			});
		}
		
		
function showPublic(){
	$("#pubMenu").show();
	$("#meun1").show();
	$("#meun2").hide();
	$("#meun3").hide();
	/*$("#shezhiStr").show();*/
	$("#shezhiStr").hide();
	$("#wccServiceDate").hide();
	$("#vocServiceDate").hide();
	//$("#shezhilocation").show();
	/*$("#voclocation").hide();
	$("#wcclocation").hide();
	$("#oneshezhiMenuName").hide();
	$("#sonshezhiMenuName").hide();*/
	$("#goSYZN").attr('href','http://www.9client.com/wechat-web/home.do?method=getSmartHome&amp;title=wcc'); 
}

function showWCCMenu(){
	$("#meun1").hide();
	$("#meun2").show();
	$("#meun3").hide();
	$("#shezhiStr").hide();
	$("#voclocation").hide();
	//$("#shezhiStr2").hide();
	$("#vocServiceDate").hide();
	$("#wccServiceDate").show();
	/*$("#wcclocation").show();
	$("#shezhilocation").hide();
	$("#onewccMenuName").hide();
	$("#sonwccMenuName").hide();*/
	$("#vocSHZN").hide();
	$("#wccSHZN").show();
	$("#goSYZN").attr('href','http://www.9client.com/wechat-web/home.do?method=getSmartHome&title=wcc');
}
function showVOCMenu(){
	$("#meun1").hide();
	$("#meun2").hide();
	$("#meun3").show();
	$("#shezhiStr").hide();
	//$("#shezhiStr2").hide();
	$("#wccServiceDate").hide();
	$("#vocServiceDate").show();
	$("#voclocation").show();
	//$("#wcclocation").hide();
	//$("#shezhilocation").hide();
	//$("#onevocMenuName").hide();
	//$("#sonvocMenuName").hide();
	$("#goSYZN").attr('href','http://www.9client.com/wechat-web/home.do?method=getSmartHome&title=voc'); 
	$("#vocSHZN").show();
	$("#wccSHZN").hide();
}

/*function onewccMenuAuthoritys(displayName){
	$("#onewccMenuName").show();
	$("#sonwccMenuName").hide();
	$("#onewccMenuName").html(displayName);
}

function sonwccMenuAuthoritys(onedisplayName,displayName){
	$("#onewccMenuName").show();
	$("#sonwccMenuName").show();
	$("#sonwccMenuName").html(displayName);
	$("#onewccMenuName").html(onedisplayName);
}

function onevocMenuAuthoritys(displayName){
	$("#onevocMenuName").show();
	$("#sonvocMenuName").hide();
	$("#onevocMenuName").html(displayName);
}

function sonvocMenuAuthoritys(onedisplayName,displayName){
	$("#onevocMenuName").show();
	$("#sonvocMenuName").show();
	$("#onevocMenuName").html(onedisplayName);
	$("#sonvocMenuName").html(displayName);
}

function oneshezhiMenuAuthoritys(displayName){
	$("#oneshezhiMenuName").show();
	$("#sonshezhiMenuName").hide();
	$("#oneshezhiMenuName").html(displayName);
}

function sonshezhiMenuAuthoritys(onedisplayName,displayName){
	$("#oneshezhiMenuName").show();
	$("#sonshezhiMenuName").show();
	$("#oneshezhiMenuName").html(onedisplayName);
	$("#sonshezhiMenuName").html(displayName);
}*/

function proposeDelog(){
		if ($('#sendEmail').html()) {
		exitVar2 = $('#sendEmail').html();
	} else {
		$('#sendEmail').html(exitVar2);
	}
	$.Dialog({
		overlay : true,
		shadow : true,
		flat : true,
		icon : '<img src="/ump/resources/images/tuichu.png"/>',
		title : '建议内容',
		content : '',
		padding : 20,
		height : 100,
		width : 100,
		onShow : function(_dialog) {
			$('#sendEmail').html("");
			$.Dialog.content(exitVar2);
			$.Metro.initInputs();
		}
	});
	}
	
	function commitPropose(flagOperator){
		var content = $("#adviceContent").val();
		if($.trim(content) == ""){
			$("#conetStr").text("请输入建议内容！");
			return;
		}
		
		$.post("/ump/umpconfigs/proposeContent", {
		"flagOperator" : flagOperator,
		"content" : content,
	}, function(data) {
		if (data == 0) {
			alert("建议发送成功！");
			$.Dialog.close();
		} else {
			alert("建议发送失败！");
		}
	});
	}