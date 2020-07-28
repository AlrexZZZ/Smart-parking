$(function () {
    KindEditor.ready(function(K) {
        window.editor = K.create('#editor_id',{
        	width : '99%',
            uploadJson : '../../ui/kindeditor/jsp/upload_json.jsp',
            fileManagerJson : '../../ui/kindeditor/jsp/file_manager_json.jsp',
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
			url : '../../ui/kindeditor/jsp/upload_json.jsp?dir=wx_image',
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
				}
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
		var reg=new RegExp("<img\\s\.*?(=gif|.gif|.bmp)","g");
		var content=$('#editor_id').val().trim();
		if ($('#editor_id').val().trim().length > 21000) {
			$('#hint_5').html(" 正文长度不能超过20000字");
			$('#hint_5').attr("color", "red");
			boo = false;
		}
		else if(reg.test(content))
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
	if (boo) {
		editor.sync();
		document.getElementById("materForm").submit();
	}
}
	function inputChange(values,formValues) {
		$('#'+formValues).html($('#'+values).val());
	}
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