<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>创建单图文消息</title>
<link rel="stylesheet" type="text/css" href="../../styles/mystyle.css" />
<link rel="stylesheet" href="../../ui/kindeditor/plugins/code/prettify.css" />
<script charset="utf-8" src="../../ui/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="../../ui/kindeditor/lang/zh_CN.js"></script>
<script charset="utf-8"	src="../../ui/kindeditor/plugins/code/prettify.js"></script>

<style>
.metro ul, .metro ol {
    margin-left: 0px;
    padding-left: 0px;
}
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
<script type="text/javascript">
	var cloumn = 1;//列表数量
	var id_editor;
	var theid;//当前记载数据的ID;
	var is_loaded = false; //编辑器是否加载
	var list_index = 1; //当前列表

	$(function() {
		localStorage.clear();//初始化缓存
		KindEditor.ready(function(K) {
					window.editor = K.create('#editor_id',
									{
										width : '99%',
										uploadJson : '../../ui/kindeditor/jsp/upload_json.jsp',
										fileManagerJson : '../../ui/kindeditor/jsp/file_manager_json.jsp',
										allowFileManager : true,
										items : [ 'fontname', 'fontsize', '|',
												'forecolor', 'hilitecolor',
												'bold', 'italic', 'underline',
												'removeformat', '|',
												'justifyleft', 'justifycenter',
												'justifyright',
												'insertorderedlist',
												'insertunorderedlist', '|',
												'emoticons', 'image', 'link' ],
										afterChange : function() {
											//如果加载完成，同步编辑器提交数据
												localStorage
														.setItem(list_index,
																this.html());
												this.sync();
												var content = '#c5_'
														+ list_index;
												$(content).val(
														$('#editor_id').val());
										}
									});

					K('#filemanager').click(function() {
						editor.loadPlugin('filemanager',function() {
							editor.plugin.filemanagerDialog({
								viewType : 'VIEW',
								dirName : 'wx_image',
								clickFn : function(
										url,
										title) {
									//判断是否弟一条 封面
									if (list_index < 2) {
										//封面处理
										K('#url').val(url);
										$("#cover_editor").empty();
										$("#cover").empty();
										var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='144px' src='"+url+
						                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
										$("#cover_editor").append(td);
										$("#cover").append(
												"<img src='"+url+"'/>");
										K('#url').val(url);
										$('#c3_1').val(url);
										editor.hideDialog();
									} else {
										//当前编辑器 
										var thumbnail_id = "#c3_"
												+ list_index;
										var thumbnail_img = "#thumbnail_"
												+ list_index;
										$(thumbnail_id).val(url);
										var thumbnail_pic = "<img src='" + url + "' />"
										$("#cover_editor").empty();
										var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='260px' src='"+url+
						                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
										$("#cover_editor").append(td);
										$(thumbnail_img).empty();
										$(thumbnail_img).append(
												thumbnail_pic);
										editor.hideDialog();
									}
								}
							});
				});
			});

					var uploadbutton = K
							.uploadbutton({
								button : K('#uploadButton'),
								fieldName : 'imgFile',
								url : '../../ui/kindeditor/jsp/upload_json.jsp?dir=wx_image',
								afterUpload : function(data) {
									if (data.error === 0) {
										var url = K.formatUrl(data.url,
												'absolute');
										//判断是否弟一条 封面
										if (list_index < 2) {
											//封面处理
											K('#url').val(url);
											$("#cover_editor").empty();
											$("#cover").empty();
											var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='144px' src='"+url+
							                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
											$("#cover_editor").append(td);
											$("#cover").append(
													"<img src='"+url+"'/>");
											K('#url').val(url);
											$('#c3_1').val(url);
										} else {
											//当前编辑器 
											var thumbnail_id = "#c3_"
													+ list_index;
											var thumbnail_img = "#thumbnail_"
													+ list_index;
											$(thumbnail_id).val(url);
											var thumbnail_pic = "<img src='" + url + "' />"
											$("#cover_editor").empty();
											var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='260px' src='"+url+
							                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
											$("#cover_editor").append(td);
											$(thumbnail_img).empty();
											$(thumbnail_img).append(
													thumbnail_pic);
										}
										// 								if (id_editor == 'duo_sl_bu') {
										// 									K('#url').val(url);
										// 									$("#cover_editor").empty();
										// 									var url = K.formatUrl(data.url,'absolute');
										// 									var dnd = '#pic_'+last_id.substring(9);
										// 									$(dnd).val(url);
										// 									var td = "<tr><td width='40%' align='left' valign='top' class='fm_tu'> <input type='image' src='"+url+
// 	                 "'/></td><td width='40%' align='left' valign='bottom' class='fm_delet'><span class='del_cover'>删除</span></td></tr>";
										// 									$("#cover_editor").append(td);
										// 									K('#url').val(url);
										// 								} else {

										// 								}

									} else {
										alert(data.message);
									}
								},
								afterError : function(str) {

								}
							});

					uploadbutton.fileBox.change(function(e) {
						uploadbutton.submit();
					});

					//监听文本框的数据变化
					$('#form_1,#form_2,#form_3,#form_5,#form_6,#form_7')
							.on(
									'change',
									function() {
										var v = $(this).val();
										var num = $(this).attr('id').substr(5);
										var current_col = "#c" + num + "_"
												+ list_index;
										$(current_col).val(v);
										if(num==1){
											$("#formTitle_"+list_index).html(v);
										}
										if(num==4){
											if($('#form_4:checked').val()==undefined){
												$(current_col).val(false);
											}else{
												$(current_col).val(true);
											}
										}
									});

					$('#editor_id').on('change', function() {
						if (last_id != undefined) {
							var v = $(this).val();
							var dnd = '#content_' + last_id.substring(9);
							$(dnd).val(v);
						}
					});
				});

		$("body").on("click", ".del_cover", function() {
			$(this).parent().parent().remove();
			$("#cover").empty();
		});

		$('#add_more_news')
				.on(
						'click',
						function() {
							if(cloumn==1){
								cloumn++;
								var a = "<ul>"
										+ "<input name='c1_"+cloumn+"' type='hidden' id='c1_"+cloumn+"'/>"
										+ "<input name='c2_"+cloumn+"' type='hidden' id='c2_"+cloumn+"'/>"
										+ "<input name='c3_"+cloumn+"' type='hidden' id='c3_"+cloumn+"'/>"
										+ "<input name='c4_"+cloumn+"' type='hidden' id='c4_"+cloumn+"'/>"
										+ "<input name='c5_"+cloumn+"' type='hidden' id='c5_"+cloumn+"'/>"
										+ "<input name='c6_"+cloumn+"' type='hidden' id='c6_"+cloumn+"'/>"
										+ "<input name='c7_"+cloumn+"' type='hidden' id='c7_"+cloumn+"'/>"
										+ "<li onMouseOver='duo_listshow(this)' onMouseOut='duo_listhid(this)'> <div class='duotw_L_listw' id='formTitle_"+cloumn+"'>标 题</div> <div class='duotw_L_listt' id='thumbnail_"+ cloumn +"'><span>缩略图</span></div> <div class='duotw_L_edbu2'><!--duotw_L_edbu2--> <div class='duotw_L_edbu2l'><img src='../../images/tuwen_index_27.png'  alt='' id='item_"
										+ cloumn
										+ "' onClick='showeditor(this)'/></div> <div class='duotw_L_edbu2r'><img src='../../images/tuwen_index_29.png'  alt='删除' onClick='deletlist("+cloumn+")'/></div> </div> </li></ul>"
								$('#news_list').append(a);
								$('#cloumn').val(cloumn);
								
							}else if (1<cloumn&&cloumn<8) {
								cloumn++;
								var a = "<ul>"
										+ "<input name='c1_"+cloumn+"' type='hidden' id='c1_"+cloumn+"'/>"
										+ "<input name='c2_"+cloumn+"' type='hidden' id='c2_"+cloumn+"'/>"
										+ "<input name='c3_"+cloumn+"' type='hidden' id='c3_"+cloumn+"'/>"
										+ "<input name='c4_"+cloumn+"' type='hidden' id='c4_"+cloumn+"'/>"
										+ "<input name='c5_"+cloumn+"' type='hidden' id='c5_"+cloumn+"'/>"
										+ "<input name='c6_"+cloumn+"' type='hidden' id='c6_"+cloumn+"'/>"
										+ "<input name='c7_"+cloumn+"' type='hidden' id='c7_"+cloumn+"'/>"
										+ "<li onMouseOver='duo_listshow(this)' onMouseOut='duo_listhid(this)' style='border-top: 1px solid #e5e5e5;'> <div class='duotw_L_listw' id='formTitle_"+cloumn+"'>标 题</div> <div class='duotw_L_listt' id='thumbnail_"+ cloumn +"'><span>缩略图</span></div> <div class='duotw_L_edbu2'><!--duotw_L_edbu2--> <div class='duotw_L_edbu2l'><img src='../../images/tuwen_index_27.png'  alt='' id='item_"
										+ cloumn
										+ "' onClick='showeditor(this)'/></div> <div class='duotw_L_edbu2r'><img src='../../images/tuwen_index_29.png'  alt='删除' onClick='deletlist("+cloumn+")'/></div> </div> </li></ul>"
								$('#news_list').append(a);
								$('#cloumn').val(cloumn);
							} else {
								alert('最多支持7条小图文消息 ');
							}

						});

	});

	$(document).ready(function() {
		cloumn = $('#cloumn').val();
		for(var indexs=1 ; indexs<=cloumn; indexs++){
			localStorage.setItem(indexs,$("#c5_"+indexs).val());
		}
	});
	
	
	function submit_form() {
		var boo = true;
		for (var i = 1; i <= cloumn; i++) {
			showeditor('<img id="item_'
					+ i
					+ '" onclick="showeditor(this)" alt="" src="../../images/tuwen_index_27(2).png" style="margin-top: 30px;">');
			if ($('#c1_' + i).val().trim().length == 0
					|| $('#c1_' + i).val().trim().length > 64) {
				$('#hint_1').html("标题不能为空且长度不能超过64字");
				boo = false;
			} else {
				$('#hint_1').html("");
			}
			if ($('#c3_' + i).val().length == 0) {
				$('#hint_3').html("请上传或选择图片");
				boo = false;
			} else {
				$('#hint_3').html("");
			}
			if ($('#c5_' + i).val().length == 0
					&& $('#c7_' + i).val().length == 0) {
				$('#hint_5').html("（正文和URL必填其一）");
				$('#hint_6').html("（最多256个字符；如正文为空，图文消息将跳转到该URL）");
				$('#hint_5').attr("color", "red");
				$('#hint_6').attr("color", "red");
				boo = false;
			} else {
				if ($('#c5_' + i).val().trim().length > 21000) {
					$('#hint_5').html(" 正文长度不能超过20000字");
					$('#hint_5').attr("color", "red");
					boo = false;
				} else {
					$('#hint_5').html("");
					$('#hint_6').html("");
				}
			}
			if (!boo) {
				return false;
			}
		}
		if (cloumn == 1) {
			alert("多图文至少需要2条消息，请添加自图文信息后重新保存。");
			boo = false;
		}
		if (boo) {
			document.getElementById("form1").submit();
		}
	}
	
	//删除图片
	 $("body").on("click", ".mydel",function () {
		 if (list_index < 2) {
              $(this).parent().parent().remove();
              $("#cover").empty();
              $('#c3_1').val('');
		 }else{
			 $(this).parent().parent().remove();
			 var thumbnail_img = "#thumbnail_"
					+ list_index;
			 $(thumbnail_img).html("<span>缩略图</span>");
             var thumbnail_id = "#c3_"
					+ list_index;
			$(thumbnail_id).val('');
		 }
    });
	//url部分
	function changeUrlSelect(){
   	var id = $("#urlSelect").val();
   	if($.trim(id) == "99"){
   		$("#form_7").val('');
   		$("#urlTABEL").hide();
   		return;
   	}
   	$.post("/ump/wccmenus/checkUrlById",{
				"id":id,
				async : false,
				dataType : "json",
			},function(data){
				$("#fade").toggle();
				$("#urlTABEL").show();
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
	    	$("#form_7").val(url);
	    	 var thumbnail_id = "#c7_"
					+ list_index;
			$(thumbnail_id).val(url);
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
    function checkUrl() {
		if($('#form_4:checked').val()==undefined){
			$("#c4_"+list_index).val(false);
		}else{
			$("#c4_"+list_index).val(true);
		}
	}
</script>
</head>

<body>
	<form action="../updatmulti" method="post" id="form1">
		<input type="hidden" name="cloumn" value="${cloumn}" id="cloumn" />
		<input type="hidden" name="id" value="${wccMaterials.id}" id="id" />
		<div class="containout" id="containout"
			style="position: relative; z-index: 0;">
			<div class="contain_ed_bu" id="contain">
				<div class="topic">素材管理</div>
				<div class="top_menu">
					<div class="menu">
						<ul>
							<li style="border-bottom: 5px solid #069dd5;"><a
								href="../../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC">图文消息</a></li>
							<li><a
								href="../../wccmaterialses/wxarticlelist?WccMaterialsType=PICTURE&sortFieldName=insertTime&sortOrder=DESC">图片</a></li>
							<li><a
								href="../../wccmaterialses/wxarticlelist?WccMaterialsType=SOUNDS&sortFieldName=insertTime&sortOrder=DESC">语音</a></li>
							<li><a
								href="../../wccmaterialses/wxarticlelist?WccMaterialsType=VIDEO&sortFieldName=insertTime&sortOrder=DESC">视频</a></li>
						</ul>
					</div>
				</div>

				<div class="middle_menu">
					<div class="middle_menu_tu">
						<img src="../../images/tuwen_editor.png" alt="" />
					</div>
					<div class="middle_menu_w">
						<h1>
							<a
								href="../../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC">图文消息</a>
						</h1>
						<h2>/</h2>
						<h3>编辑多图文消息</h3>
					</div>
					<div class="ch_weixinpt">
						<div class="middle_search_lr">
						<c:if test="${plats.size() eq 0}">
							<script type="text/javascript">
								alert('请先添加公众平台账号');
								window.location.href='../../wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC';
							</script>
						</c:if>
							公众平台:
							<!-- 
							 <select id="select1">
							</select>-->
							<select name="wccPlatformUsers">
								<c:forEach items="${plats}" var="p">
									<c:if test="${p.id == wccMaterials.wccPlatformUsers.id }">
										<option value="${p.id}" selected="selected">${p.account}</option>
									</c:if>
									<c:if test="${p.id != wccMaterials.wccPlatformUsers.id }">
										<option value="${p.id}">${p.account}</option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="editor_con">
					<div>
						<input type="hidden" name="c1_1" id="c1_1" value="${wccMaterials.title}"/>
						<input type="hidden" name="c2_1" id="c2_1" value="${wccMaterials.token}"/>
						<input type="hidden" name="c3_1" id="c3_1" value="${wccMaterials.thumbnailUrl}"/>
						<input type="hidden" name="c4_1" id="c4_1" value="${wccMaterials.urlBoolean}"/> 
						<input type="hidden" name="c5_1" id="c5_1" value='${wccMaterials.content}'/> 
						<input type="hidden" name="c6_1" id="c6_1" value=""/>
						<input type="hidden" name="c7_1" id="c7_1" value="${wccMaterials.remakeUrl}"/>
					</div>
					<!--左边列表区域-->
					<div class="editor_L">
						<div class="duotw_L_dandiv">
							<!--封面区域-->
							<div class="duotw_L_dandiv_imglist"
								onmouseover="showad(this,'duotw_L_edbu2')"
								onmouseout="hidad('duotw_L_edbu2')">
								<div class="little_black" id="formTitle_1">${wccMaterials.title}</div>
								<div class="build_L_dandiv_img" id="cover"><img src="${wccMaterials.thumbnailUrl}"></div>
								<div class="duotw_L_edbu" id="duotw_L_edbu2">
									<img src="../../images/tuwen_index_27.png" alt="" id="item_1"
										onclick="showeditor(this)" />
								</div>
							</div>

							<!--动态增加列表区域-->
							<div class="duotw_L_dandiv_newlist" id="news_list">
								<ul></ul>
								<c:forEach items="${wccMaterials.children}" var="material" varStatus="maSta" >
									<ul>
										<input name="c1_${maSta.count+1}" type="hidden"	id="c1_${maSta.count+1}" value="${material.title}"/>
										<input name="c2_${maSta.count+1}" type="hidden" id="c2_${maSta.count+1}" value="${material.token}"/>
										<input name="c3_${maSta.count+1}" type="hidden" id="c3_${maSta.count+1}" value="${material.thumbnailUrl}"/>
										<input name="c4_${maSta.count+1}" type="hidden" id="c4_${maSta.count+1}" value="${material.urlBoolean}"/>
										<input name="c5_${maSta.count+1}" type="hidden" id="c5_${maSta.count+1}" value='${material.content}'/>
										<input name="c6_${maSta.count+1}" type="hidden" id="c6_${maSta.count+1}" value=""/>
										<input name="c7_${maSta.count+1}" type="hidden" id="c7_${maSta.count+1}" value="${material.remakeUrl}"/>
										<li onMouseOver="duo_listshow(this)" onMouseOut="duo_listhid(this)">
											<div class="duotw_L_listw" id="formTitle_${maSta.count+1}">${material.title}</div>
											<div class="duotw_L_listt" id="thumbnail_${maSta.count+1}">
												<img src="${material.thumbnailUrl}">
											</div>
											<div class="duotw_L_edbu2">
												<!--duotw_L_edbu2-->
												<div class="duotw_L_edbu2l">
													<img src="../../images/tuwen_index_27.png" alt=''
														id='item_${maSta.count+1}' onClick='showeditor(this)' />
												</div>
												<div class='duotw_L_edbu2r'>
													<img src='../../images/tuwen_index_29.png' alt='删除'
														onClick='deletlist(${maSta.count+1})' />
												</div>
											</div>
										</li>
									</ul>
								</c:forEach>
							</div>

							<!--增加列表按钮-->
							<div class="duotw_L_click" id="add_more_news">
								<img src="../../images/editor_click_03.png" alt="" />
							</div>
						</div>
					</div>

					<!--右边编辑区域-->
					<div class="editor_R">
						<div class="dtw_r" id="news_editor">
							<img src="../../images/eidtor_allow_07.png" width="17" height="28"
								alt="" class="dtw_allow" />
							<div class="dtw_r_r">
								<div class="dtwrrform">

									<div class="form_tit">标 题</div>
									<div class="form_wenben">
										<input type="text" name="title" id="form_1" value="${wccMaterials.title}"/><div
											style="color: red;" id ="hint_1"></div>
									</div>

									<div class="form_tit">
										作 者<span>（选填）</span>
									</div>
									<div class="form_wenben">
										<input type="text" id="form_2" value="${wccMaterials.token}">
									</div>

									<div class="form_tit">
										封 面<span>（jpg，图片尺寸：900*500）</span>
									</div>

									<div class="sctu">
										<div class="form_wenben_tk">
											<input name="thumbnailUrl" type="hidden" id="url" /> <input
												type="button" id="uploadButton" value="上传" />
										</div>
										<div class="form_wenben_tk">
											<input type="button" id="filemanager" value="从图库中选择" />
										</div>
										<font color="red" id="hint_3" style="line-height: 30px;"></font>
									</div>

									<!--图片封面动态编辑区域-->
									<div class="form_wenben">
										<table width="100%" border="0" cellspacing="0" cellpadding="0"
											id="cover_editor">
											<tbody>
												<tr>
													<td width="260px" valign="top" align="left" class="fm_tu">
														<img width='260px' height='144px' src="${wccMaterials.thumbnailUrl}" />
													</td>
													<td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td>
												</tr>
											</tbody>
											</table>
									</div>

									<div class="form_wenben2" id="urlCheck">
									<c:if test="${wccMaterials.urlBoolean ==true}">
										<input type="checkbox" id="form_4" checked="checked" onclick="checkUrl();">
									<span>封面图片显示在正文中</span>
									</c:if>
									<c:if test="${wccMaterials.urlBoolean ==false or empty wccMaterials.urlBoolean}">
										<input type="checkbox" id="form_4" onclick="checkUrl();">
									<span>封面图片显示在正文中</span>
									</c:if>
									</div>

									<div class="form_tit">
										正 文<font color="#7d7d7d" id="hint_5">（正文和URL必填其一）</font>
									</div>

									<div>
										<input type="hidden" id="editor_value" />
									</div>

									<!--文本编辑器-->
									<div class="form_wenben" id="editor_z" style="width: 99%;">
										<textarea id="editor_id" name="content" onfocus="test();"
											style="width: 500px; height: 200px;">
											 ${wccMaterials.content} 
										</textarea>
									</div>

									<div class="form_tit">
										<span>URL</span><font id="hint_6" color="#7d7d7d">（最多256个字符；如正文为空，图文消息将跳转到该URL）</font>
									</div>
									<div class="form_wenben" id="duofenywlj">
										<select id="urlSelect" style="width: 135px;" onchange="changeUrlSelect();">
											<option value="99" >链接</option>
											<option value="1" >微内容</option>
											<option value="2">抽奖活动</option>
											<option value="4">活动管理</option>
										</select>
										<input type="text" id="form_7" name="remakeUrl" style="width: 69%;" value="${wccMaterials.remakeUrl}"/>
									</div>
									<div class="form_wenben" id="2code">
											<span>二维码关联：</span>
											<span>
											<input id="codeStatus" type="hidden" name="codeStatus" value="${wccMaterials.codeStatus}" />
											<c:if test="${wccMaterials.codeStatus}">
												<img id="activeOn" onclick="changeOnOff()" style="vertical-align: top;"
													src="../../images/ON.png" />
												<img src="../../images/OFF.png" id="activeOff"
													onclick="changeOnOff()" style="display: none;vertical-align: top;" />
											</c:if>
											<c:if test="${!wccMaterials.codeStatus}">
												<img id="activeOn" onclick="changeOnOff()" style="display: none;vertical-align: top;" 
													src="../../images/ON.png" />
												<img src="../../images/OFF.png" id="activeOff" style="vertical-align: top;"
													onclick="changeOnOff()" />
											</c:if>
											</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="save_look_tuwen">
					<a class="button info" onclick="submit_form()">保 存</a>
					<a onclick="window.location.href='/ump/wccmaterialses/wxarticlelist?WccMaterialsType=IMAGETEXT&sortFieldName=insertTime&sortOrder=DESC'"
						class="button warning" >取 消</a>
				</div>
			</div>
		</div>
	</form>

	<script type="text/javascript">
		//左边点击的列表编号
		var last_id;
		function showeditor(left) {
			flag = true;
			var offset = $(left).offset();
			var height = $(left).parent().parent().height();
			var id = $(left).attr("id");
			list_index = id.substr(5);
			loadData(list_index);
			is_loaded = true;
			last_id = id;
			var idenglish = id.replace(/[^\a-\z\A-\Z\_]/g, '');
			id_editor = idenglish;

			//改变编辑器的高度
			if (list_index == 1) {
				$("#news_editor").css("margin-top", 0);
			} else if (list_index == 2) {
				$("#news_editor").css("margin-top", 200);
			} else if (list_index == 3) {
				$("#news_editor").css("margin-top", 290);
			} else if (list_index == 4) {
				$("#news_editor").css("margin-top", 367);
			} else if (list_index == 5) {
				$("#news_editor").css("margin-top", 450);
			} else if (list_index == 6) {
				$("#news_editor").css("margin-top", 530);
			} else if (list_index == 7) {
				$("#news_editor").css("margin-top", 600);
			} else {
				$("#news_editor").css("margin-top", 680);
			}
		}

		function loadData(id) {
			//加载编辑器内容
			for (var int = 1; int < 8; int++) {
				var editor_form = "#form_" + int;
				var cloumn = "#c" + int + "_" + id;
				$(editor_form).val($(cloumn).val());

			}
			if(id==1){
				$('#picSize').html("（jpg，图片尺寸：900*500）");
				$('#2code').show();
			}else{
				$('#2code').hide();
				$('#picSize').html("（jpg，图片尺寸：200X200）");
			}
			$('#hint_1').html("");
			$('#hint_3').html("");
			$('#urlSelect').val(99);
			if($('#c4_' + id).val() == 'false' || $('#c4_' + id).val()=="" ){
				$('#urlCheck').html('<input type="checkbox" id="form_4"  onclick="checkUrl();"><span>封面图片显示在正文中</span>');
			}else{
				$('#urlCheck').html('<input type="checkbox" id="form_4" checked="checked"  onclick="checkUrl();"><span>封面图片显示在正文中</span>');
			}
			editor.sync();
			editor.html($('#c5_' + id).val());
			$('#form_7').val($('#c7_' + id).val());
			if ($('#c5_' + id).val().length == 0
					&& $('#c7_' + id).val().length == 0) {
				$('#hint_5').attr("color", "#7d7d7d");
				$('#hint_6').attr("color", "#7d7d7d");
			} else {
				$('#hint_5').html("");
				$('#hint_6').html("");
			}
			if($('#c3_'+id).val()!=''){
				var url = $('#c3_'+id).val(); 
				$("#cover_editor").empty();
				if(id==1){
					var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='144px' src='"+url+
	                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
				}else{
					var td = "<tr><td width='260px' align='left' valign='top' class='fm_tu'> <img width='260px' height='260px' src='"+url+
	                 "'/></td><td align='left' valign='bottom' class='fm_delet'><span class='mydel'>删除</span></td></tr>";
				}
				$("#cover_editor").append(td);
			}else{
				$("#cover_editor").empty();
			}
			//var column_form = "#c"+id+"_"+list_index;
			editor.html(localStorage.getItem(id));
			$(editor_form).val();
			//			$('#form_1').val($(dnd).val());
			//			$('#url').val($(pic).val());
		}

		function duo_listshow(alist) {
			var hlist = parseInt($(alist).height()) + 20;
			var hhlist = parseInt(hlist) / 2 - 10;
			$(alist).children("div .duotw_L_edbu2").css({
				"height" : hlist,
				"display" : "block"
			});

			var i = $(alist).find("div .duotw_L_edbu2l , div .duotw_L_edbu2r")
					.children("img").css("margin-top", hhlist);

			$(alist).find("div .duotw_L_edbu2l ").children("img")
					.mouseover(
							function() {
								$(alist).find("div .duotw_L_edbu2l ").children(
										"img").attr("src",
										"../../images/tuwen_index_27(2).png");
							});
			$(alist).find("div .duotw_L_edbu2l ").children("img").mouseout(
					function() {
						$(alist).find("div .duotw_L_edbu2l ").children("img")
								.attr("src", "../../images/tuwen_index_27.png");
					});

			$(alist).find("div .duotw_L_edbu2r ").children("img")
					.mouseover(
							function() {
								$(alist).find("div .duotw_L_edbu2r ").children(
										"img").attr("src",
										"../../images/tuwen_index_29(2).png");
							});
			$(alist).find("div .duotw_L_edbu2r ").children("img").mouseout(
					function() {
						$(alist).find("div .duotw_L_edbu2r ").children("img")
								.attr("src", "../../images/tuwen_index_29.png");
					});
		};

		function duo_listhid(alisth) {
			$(alisth).children("div .duotw_L_edbu2").css("display", "none");
		}

		function showad(a, b) {
			var h = $(a).height() + "px";
			var hh = parseInt(h) / 2 - 11;
			$("#" + b).css({
				"height" : h,
				"display" : "block"
			});
			$("#" + b).children("img").css("margin-top", hh + "px");
			$("#" + b).children("img").mouseover(
					function() {
						$("#" + b).children("img").attr("src",
								"../../images/tuwen_index_27(2).png");
					});
			$("#" + b).children("img").mouseout(
					function() {
						$("#" + b).children("img").attr("src",
								"../../images/tuwen_index_27.png");
					});
		};

		function hidad(d) {
			$("#" + d).css("display", "none");
		}

		function deletlist(id) {
// 			var pid = $(me).parent().parent().parent()
// 					.find("input").attr("id").substr(4);
			//$(me).parent().parent().parent().parent().remove();
			var newvar = $('#cloumn').val();
			//如果删除的不是最后一个
			if(id!=newvar){
				//循环列表，将后面图文的值提前，并将缩略图替代，替换标题，文本编辑框内值提前
				for(var var_index=id ; var_index<=newvar;var_index++){
					for (var int = 1; int < 8; int++) {
						var editor_form ="#c" + int + "_" + var_index;
						var cloumns = "#c" + int + "_" + (var_index+1);
						$(editor_form).val($(cloumns).val());
					}
					if($("#c1_"+(var_index+1)).val()==""){
						$("#formTitle_"+var_index).html("标 题");
					}else{
						$("#formTitle_"+var_index).html($("#c1_"+(var_index+1)).val());
					}
					var thumbnail_img = "#thumbnail_" + var_index;
					var thumbnail_pic = "<img src='" + $("#c3_"+(var_index+1)).val() + "' />"
					$(thumbnail_img).empty();
					if($("#c3_"+(var_index+1)).val()==""){
						$(thumbnail_img).append("<span>缩略图</span>");
					}else{
						$(thumbnail_img).append(thumbnail_pic);
					}
					localStorage.setItem(var_index,localStorage.getItem(var_index+1));
				}
				//删除最后一个图文，以及文本框内值
				$("#c1_"+newvar).parent().remove();
				localStorage.removeItem(newvar);
			}else{
				$("#c1_"+newvar).parent().remove();
				localStorage.removeItem(newvar);
			}
			
			$('#cloumn').val(newvar - 1);
			cloumn = $('#cloumn').val();
			showeditor($("#item_1"));
		}

	</script>
	<div id="urlTABEL" class="myUrl" style="display: none;height: auto;width:300px;padding-top:5px;padding-bottom:5px;background:#fff;max-height: 350px;min-height:100px;height:auto;z-index: 9999;">
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
