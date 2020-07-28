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
						try{
							var fup =$("input[name='imgFile']")
							var size = fup[0].files[0].size; 
							var kbsize=size/1024;
							var filename=fup[0].files[0].name;
							var ValidationExpression=/^.*[^a][^b][^c]\.(?:jpg|png)$/
							if(kbsize>1024)
								{
								alert("文件超过大小限制");
								}
						    else if(!ValidationExpression.test(filename))
								{
								alert("文件不合法");
								}
							else
								{
								uploadbutton.submit();
								}
							}
							catch (e)
							{
								uploadbutton.submit();	
							};
					});

					//监听文本框的数据变化
					$('#form_1,#form_2,#form_3,#form_5,#form_6,#form_7,#form_8,#form_9')
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
										+ "<input name='c8_"+cloumn+"' type='hidden' id='c8_"+cloumn+"'/>"
										+ "<input name='c9_"+cloumn+"' type='hidden' id='c9_"+cloumn+"'/>"
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
										+ "<input name='c8_"+cloumn+"' type='hidden' id='c8_"+cloumn+"'/>"
										+ "<input name='c9_"+cloumn+"' type='hidden' id='c9_"+cloumn+"'/>"
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
		var reg=new RegExp("<img\\s\.*?(=gif|.gif|.bmp)","g");
		var tempcontent="";
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
				
				try
				{
					tempcontent=decodeURIComponent($('#c5_' + i).val(),"UTF-8").trim()
				}
				catch(e)
				{
					
				}
				if (tempcontent.length > 25000) {
					$('#hint_5').html(" 正文长度不能超过20000字");
					$('#hint_5').attr("color", "red");
					boo = false;
				}
				else if(reg.test(tempcontent))
				{
					$('#hint_5').html(" 包含不合法的图片内容（仅支持jpg|png）");
					$('#hint_5').attr("color", "red");
					boo =false;
				}
				else {
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