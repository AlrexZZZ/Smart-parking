


$(document).ready(function(){

	$.ajax({
		url : "/ump/wccFees/getProInfo",
		type : "POST",
		data : {'name':'name'},
		error : function(msg) {
		},
		success : function(e) {
	/*		var arry=new Array();
			var str=e.split(",");
			for (var i=0;i<str.length;i++){
				arry.push(str[i].subString(0,str[i].lastIndexOf(")")));
			}*/
			
			if(e.length > 0){
				
				//手机号创建联想输入		
				$('#search-form1').autocomplete({
					hints: e.split(","),
					width: 300,
					height: 30,
					id:'f1',
					// 给当前input组件赋值
					inputValue:$("#cellphone_").val(),
					onBlur:function(e){
						//失去焦点时
						console.log(e);
						var onblueValue = e.delegateTarget.text;
						//$("#cellphone_").value(onblueValue);
						
					},
					onSubmit: function(text){
					//	alert(text);
						//$("#cellphone_").value(text);
					
					$('#message').html('Selected: <b>' + text+ '</b>');
						
				//再次请求ajax给下面的门牌号，姓名赋值
						
						$.ajax({
							url : "/ump/wccFees/getProInfo",
							type : "POST",
							data : {'phoneNum':text.split(")")[1]},
							error : function(msg) {
							},
							success : function(e) {
								var obj = eval("["+e+"]");
								//console.log(obj[0]);
								$('#uname_').val(obj[0].tempname);
								$('#doorNum_').val(obj[0].tempdoorplate);
								$('#itemNameId_').val(obj[0].tempappartment.itemName);
								$('#cbdWccProprietor_').val(obj[0].id);
								
							}
						});
						///////////////////////////////////
						
						
					}
				});
				
				
			}
		}
	});
	
	
	
/*	
	$('#search-form1').autocomplete({
		hints: t,
		width: 300,
		height: 30,
		onBlur:function(e){
			//失去焦点时
			var onblueValue = e.currentTarget.value;
			$('#listTip_').val(onblueValue);
		},
		onSubmit: function(text){
			alert(text);
			$('#listTip_').val(text);
			$('#message').html('Selected: <b>' + text + '</b>');			
		}
	});
	
	
	$('#search-form-tel').autocomplete({
		hints: t,
		width: 300,
		height: 30,
		onBlur:function(text){
			console.log(text.currentTarget.value);
		},
		onSubmit: function(text){
			
			$('#listTip_').val(text);
			$('#message').html('Selected: <b>' + text + '</b>');			
		}
	});*/
	
	
});
