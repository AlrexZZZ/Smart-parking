<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>创建单图文消息</title>
<link rel="stylesheet" type="text/css" href="../styles/mystyle.css"/>
<link rel="stylesheet" href="../ui/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="../ui/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="../ui/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8" src="../ui/kindeditor/plugins/code/prettify.js"></script>

<style>
.metro table {
	border: 0;
	background: transparent;
}

img {
    vertical-align: baseline;
}
.metro h1, .metro h2, .metro h3, .metro h4, .metro h5, .metro h6 {
    margin: 0 0;
}
</style>

<!-- 复选框引用js -->
<style>
@IMPORT url("/ump/styles/selectTree.css");
</style>
<SCRIPT type="text/javascript" src="/ump/js/selectTree_materials2.js">
	//selectTree;
</SCRIPT>
<script type="text/javascript">
$(function () {
	
	
    KindEditor.ready(function(K) {
        window.editor = K.create('#editor_id',{
        	width : '99%',
            uploadJson : '../ui/kindeditor/jsp/upload_json.jsp',
            fileManagerJson : '../ui/kindeditor/jsp/file_manager_json.jsp',
            allowFileManager : true,
        	items : [
						'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons', 'image', 'link']
        });
        
		K('#filemanager').click(function() {
			editor.loadPlugin('filemanager', function() {
				editor.plugin.filemanagerDialog({
					viewType : 'VIEW',
					dirName : 'wx_image',
					clickFn : function(url, title) {
						K('#url').val(url);
	                    $("#mytr").empty();
	                    $("#cover").empty();
		                var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='144px' src='"+url+
		                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
	                    $("#mytr").append(td);
	                    $("#cover").append("<img src='"+url+"'/>");
						editor.hideDialog();
					}
				});
			});
		});
		
		var uploadbutton = K.uploadbutton({
			button : K('#uploadButton')[0],
			fieldName : 'imgFile',
			url : '../ui/kindeditor/jsp/upload_json.jsp?dir=wx_image',
			afterUpload : function(data) {
				if (data.error === 0) {
					K('#url').val(url);
                    $("#mytr").empty();
                    $("#cover").empty();
					var url = K.formatUrl(data.url, 'absolute');
	                var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='144px' src='"+url+
	                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
                    $("#mytr").append(td);
                    $("#cover").append("<img src='"+url+"'/>");
					K('#url').val(url);
				} else {
					alert(data.message);
				}
			},
			afterError : function(str) {
				alert('自定义错误信息: ' + str);
			}
		});
		uploadbutton.fileBox.change(function(e) {
			uploadbutton.submit();
		});
	});

    $("body").on("click", ".mydel",function () {
              $(this).parent().parent().remove();
              $("#cover").empty();
              $('#url').val('');
    });
    
   	$('#tj_ywlj2').on('click',function(){
   		$('#ywlj2').css("display", "block");
   	});
   	
   	
   	loadTree2("/ump/wccautokbses/treePlatform");
});

function form_submit(){
	var boo = true;
	editor.sync();
	if ($('#title').val().trim().length == 0
			|| $('#title').val().trim().length > 64) {
		$('#hint_1').html("标题不能为空且长度不能超过64字");
		boo = false;
	} else {
		$('#hint_1').html("");
	}
	if ($('#url').val().length == 0) {
		$('#hint_3').html("请上传或选择图片");
		boo = false;
	} else {
		$('#hint_3').html("");
	}
	if ($('#description').val().length != 0&&$('#description').val().length >120) {
		$('#hint_4').html("摘要长度不能超过120字");
		boo = false;
	} else {
		$('#hint_4').html("");
	}
	if ($('#editor_id').val().length == 0
			&& $('#remakeUrl').val().length == 0) {
		$('#hint_5').html("（正文和URL必填其一）");
		$('#hint_6').html("（最多256个字符；如正文为空，图文消息将跳转到该URL）");
		$('#hint_5').attr("color", "red");
		$('#hint_6').attr("color", "red");
		boo = false;
	} else {
		if ($('#editor_id').val().trim().length > 31000) {
			$('#hint_5').html(" 正文长度不能超过30000字");
			$('#hint_5').attr("color", "red");
			boo = false;
		} else {
			$('#hint_5').html("");
			$('#hint_6').html("");
		}
	}
	if (boo) {
		editor.sync();
		document.getElementById("materForm").submit();
	}
}
	function inputChange(values,formValues) {
		$('#'+formValues).html($('#'+values).val());
	}
	//url部分
	  function changeUrlSelect(){
    	var id = $("#urlSelect").val();
    	if($.trim(id) == "99"){
    		$("#remakeUrl").val('');
    		$("#urlTABEL").hide();
    		return;
    	}
    	$.post("/ump/wccmenus/checkUrlById",{
				"id":id,
				async : false,
				dataType : "json",
			},function(data){
				$("#fade").toggle();
				var model = eval("(" + data + ")");
				if(model.length == 0){
	                $( '#dataBody')[0].innerHTML = '没有数据';
	            } else{
					var table = "";
					for (var i = 0; i < model.length; i++) {
						if(model[i].content.length > 17){
							table += '<li style="cursor: pointer;padding-top:5px;"><a href="javascript:void(0);" id="'+model[i].url+'" onclick=selectUrl(this,"'+ model[i].content+ '") style="cursor: pointer;" title="'+ model[i].content+ '" >';
								table += model[i].content.substr(0,15)+"...";
							table += "</a></li>";
						}else{
							table += '<li style="cursor: pointer;padding-top:5px;"><a href="javascript:void(0);" id="'+model[i].url+'" onclick=selectUrl(this,"'+ model[i].content+ '") style="cursor: pointer;" >';
								table += model[i].content;
							table += "</a></li>";
						}
					}
					$("#dataBody").html(table);
	            }
			  	var x=$("#urlSelect").offset();
  				$("#urlTABEL").show();
  				$("#urlTABEL").css('top',x.top-$("#urlTABEL").height()+17);
		 });
    }
	  function selectUrl(menuUrl,title){
	    	$("#fade").hide();
	    	var url = $(menuUrl).attr('id');
	    	$("#remakeUrl").val(url);
	    	$("#urlSelect").val(99);
	    	$("#urlTABEL").hide();
	    }
    function changeOnOff() {
		if ($("#codeStatus").val() == "true") {
			$("#activeOn").hide();
			$("#activeOff").show();
			$("#codeStatus").val("false");
		} else {
			$("#activeOn").show();
			$("#activeOff").hide();
			$("#codeStatus").val("true");
		}
	}
    function closeDilog(){
   	 $("#urlSelect").val(99);
   	 $("#fade").hide();
   	 $('#urlTABEL').hide();
    }
    
</script>
</head>

<body>
	<sf:form enctype="multipart/form-data" modelAttribute="wccMaterials" action="addsnSubmit" id="materForm" method="POST">
		<div class="contain_ed_bu" id="contain" style="position: relative; z-index: 0;">
			<div class="topic">素材管理</div>
			<div class="top_menu">
				<div class="menu">
					<ul>
						<li style="border-bottom: 5px solid #069dd5;"><a href="../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC">图文消息</a></li>
						<li><a href="../wccmaterialses/wxarticlelist?WccMaterialsType=PICTURE&sortFieldName=insertTime&sortOrder=DESC">图片</a></li>
						<li><a href="../wccmaterialses/wxarticlelist?WccMaterialsType=SOUNDS&sortFieldName=insertTime&sortOrder=DESC">语音</a></li>
						<li><a href="../wccmaterialses/wxarticlelist?WccMaterialsType=VIDEO&sortFieldName=insertTime&sortOrder=DESC">视频</a></li>
					</ul>
				</div>
			</div>

			<div class="middle_menu">
				<div class="middle_menu_tu">
					<img src="../images/tuwen_editor.png"/>
				</div>
				<div class="middle_menu_w">
					<h1>
						<a href="../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC">图文消息</a>
					</h1>
					<h2>/</h2><h3>新建图文消息</h3>
				</div>
				<div class="ch_weixinpt">
					<div class="middle_search_lr">
						<c:if test="${plats.size() eq 0}">
							<script type="text/javascript">
								alert('请先添加公众平台账号');
								window.location.href='../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC';
							</script>
						</c:if>
						<div class="top_search">
					<div class="top_search_lr" style="width: 350px;">
						<span>公众平台：</span>
						<div class="input-control text" style="width: 270px;">
							<input id="SelVals2" type="text" readonly="readonly" value=""
								style="width: 270px;" class="select_cheally"
								onclick="selectShowMenu2();" /> <input id="SelIds2"
								class="select_cheally" name="platformUsers" type="hidden"
								value="${pltIds }" />
							<div id="selectMenuContent2" 
								style="position: absolute; z-index: 99;display: none;">
								<ul id="selectTreeDemo2" class="ztree"
									style="margin-left: 0px; margin-top: 0; border: 1px solid #e5e5e5;"></ul>
							</div></div></div>
						</div>
					</div>
				</div>
			</div>

			<div class="editor_con">
				<div class="editor_L">
					<div class="editor_L_dandiv">
						<div class="editor_L_dandiv_tit" id="formTitle">标 题</div>
						<div class="build_L_dandiv_img" id="cover">封面图片</div>
						<div class="editor_L_dandiv_zaiyao" id="formDesc">摘要</div>
					</div>
				</div>

				<div class="editor_R">
					<div class="dtw_r">
						<img src="../images/eidtor_allow_07.png" width="17" height="28" alt="" class="dtw_allow" />
						<div class="dtw_r_r">
							<div class="dtwrrform">
									<div class="form_tit">标 题</div>
									<div class="form_wenben">
										<input type="text" name="title" id="title" oninput="inputChange('title','formTitle');"><div
											style="color: red;font-size: 15px;" id="hint_1"></div>
									</div>
									<div class="form_tit">
										作 者<span>（选填）</span>
									</div>
									<div class="form_wenben">
										<input type="text" name="token">
									</div>
									<div class="form_tit">
										封 面<span>（jpg，图片尺寸：900*500）</span>
									</div>
									
									<div class="sctu">
										<div class="form_wenben_tk">
											<input name="thumbnailUrl" type="hidden" id="url"/> 
											<input type="button" id="uploadButton" value="上传" />
										</div>
										<div class="form_wenben_tk">
											<input type="button" id="filemanager" value="从图库中选择"/>
										</div>
										<font color="red" id="hint_3" style="line-height: 30px;font-size: 15px;"></font>
									</div>
									<div class="form_wenben">
										<table width="100%" border="0" cellspacing="0" cellpadding="0" id="mytr">
										</table>
									</div>
									<div class="form_wenben2">
										<sf:checkbox path="urlBoolean"/><span>封面图片显示在正文中</span>
									</div>
									<div class="form_tit">
										摘 要<span>（选填）</span>
									</div>
									<div class="form_wenben">
										<textarea name="description" id="description" oninput="inputChange('description','formDesc');"></textarea>
										<font color="red" id="hint_4"></font>
									</div>
									<div class="form_tit">
										正 文<font id="hint_5" color="#7d7d7d">（正文和URL必填其一）</font>
									</div>
									<div class="form_wenben" id="editor_z" style="width: 99%;">
										<textarea id="editor_id" name="content" style="width:300px;height:200px;"></textarea>
									</div>
									<div class="form_tit">
										URL<font id="hint_6" color="#7d7d7d">（最多256个字符；如正文为空，图文消息将跳转到该URL）</font>
									</div>
									<div class="form_wenben" id="ywlj2">
										<select id="urlSelect" name="sort" style="width: 135px;" onchange="changeUrlSelect();">
											<option value="99" >链接</option>
											<option value="1" >微内容</option>
											<option value="2">抽奖活动</option>
											<option value="4">活动管理</option>
										</select>
										<input type="text" name="remakeUrl" id="remakeUrl" style="width: 69%;">
									</div>
									<div class="form_wenben" id="2code">
											<span>二维码关联：</span>
											<span>
											<input id="codeStatus" type="hidden" name="codeStatus" value="false" />
											<img id="activeOn" onclick="changeOnOff()"
												src="../images/ON.png" style="display: none;vertical-align: top;"/>
											<img src="../images/OFF.png" id="activeOff"
												onclick="changeOnOff()" style="vertical-align: top;" />
											</span>
									</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="save_look_tuwen">
				<a class="button info" onclick="form_submit()">保 存</a>
				<a onclick="window.location.href='../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC'"
						class="button warning" >取 消</a>
			</div>
		</div>
	</sf:form>
<div id="urlTABEL" class="myUrl" style="display: none;height: auto;width:300px;padding-top:5px;padding-bottom:5px;background:#fff;max-height: 350px;min-height:100px;height:auto;z-index: 9999">
		  	<div style="height: 30px;line-height:30px;border-bottom:1px solid #e5e5e5;padding-left:10px;padding-right:10px;">
		  		<img src="/ump/images/add.png" /> <span style="padding-left: 10px;padding-top: 5px;">请选择</span><img src="/ump/images/del.png" style="float: right;cursor: pointer;" onclick="closeDilog();"/>
		  	</div>
		  	<div class="kate_wtc_tit" style="font-size:14px;font-weight:bold;">标 题</div>
		  	
		  	<div class="kate_wtc_leibiao">
			  	  <ul style="list-type-style:none;padding:0px;margin:0px;" id="dataBody">
			  	  </ul>
		  	</div>
	</div>
	<div id="fade" class="black_overlay"><!--  --></div>
</body>
</html>


