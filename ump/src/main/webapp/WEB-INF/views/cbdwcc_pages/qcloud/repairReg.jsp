<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<title>web</title>
<style>
	body {
		margin: 0;
		padding: 0;
		background-color: #F7F7F7;
		font-family: '汉仪大隶书繁';
	}

	form {
		max-width: 640px;
		width: 100%;
		margin: 24px auto;
		font-size: 20px;
	}

	label {
		display: block;
		margin: 10px 10px 15px;
		font-size: 16px;
	}

	input,select {
		display: block;
		width: 100%;
		height: 40px;
		font-size: 16px;
		margin-top: 10px;
		padding: 6px 10px;
		color: #333;
		border: 1px solid #CCC;
		box-sizing: border-box;
	}
	textarea {
		display: block;
		width: 100%;
		height: 100px;
		font-size: 16px;
		margin-top: 10px;
		padding: 6px 10px;
		color: #333;
		border: 1px solid #CCC;
		box-sizing: border-box;
	}

	meter, progress {
		display: block;
		width: 100%;
		margin-top: 10px;
	}

	.btn {
		margin-top: 30px;
	}

	.btn input {
		color: #FFF;
		background-color: green;
		border: 0 none;
		outline: none;
		cursor: pointer;
	}

</style>
</head>
<script type="text/javascript" src="/ump/js/jquery-2.1.1.js"></script>
<script type="text/javascript">
function saveRepair(){
	
	
	var fname = $('#fnameId').val();
	var dept = $('#deptId').val();
	var area = $('#areaId').val();
	var stuNo = $('#stuNoId').val();
	var fphone = $('#fphoneId').val();
	var address = $('#addressId').val();
	var problemType = $('#problemTypeId').val();
	var isBack = $('#isBackId').val();
	var problemDes = $('#problemDesId').val();
	
	if(fname.trim() == ''){
		alert('请填写姓名');
		return;
	}
	if(dept.trim() == ''){
		alert('请填写所在系部');
		return;
	}
	if(area.trim() == ''){
		alert('请填写区域');
		return;
	}

	if(stuNo.trim() == ''){
		alert('请填写学号');
		return;
	}
	if(fphone.trim() == ''){
		alert('请填写联系电话');
		return;
	}
	if(address.trim() == ''){
		alert('请填写地址');
		return;
	}
	if(problemType.trim() == ''){
		alert('请选择故障分类');
		return;
	}
	if(isBack.trim() == ''){
		alert('请选择是否备案');
		return;
	}
	if(problemDes.trim() == ''){
		alert('请填写故障描述');
		return;
	}
	
	var param = {
			 fname : $('#fnameId').val(),
			 dept : $('#deptId').val(),
			 area : $('#areaId').val(),
			 stuNo : $('#stuNoId').val(),
			 fphone : $('#fphoneId').val(),
			 address : $('#addressId').val(),
			 problemType : $('#problemTypeId').val(),
			 isBack : $('#isBackId').val(),
			 problemDes : $('#problemDesId').val()
	}
	console.log(param);
	
	$.ajax({
		url : "/ump/pageBding/saveRepair",
		type : "POST",
		data : param,
	/* 	async : false, */
		error : function(request) {
	  alert('网络连接异常，请稍后再试!');
		},
		success : function(data) {
	    alert('数据提交成功，我们尽快联系您！');
		}
	});
}

</script>
<body>
<form action="">
	<fieldset>
		<legend>维修报名登记</legend>
		<label for="">
			姓名:     <input type="text" name="fname" id="fnameId" required autofocus placeholder="请输入姓名">
		</label>
		<label>系部:
		<select name="dept" id="deptId">
		<option value="">请选择</option>
		<option value="软件工程">软件工程</option>
		<option value="计算机科学与技术">计算机科学与技术</option>
		<option value="物管">物管</option>
		<option value="建筑">建筑</option>
		<option value="其它">其它</option>
		</select>
		</label>
		<label for="">
			所在区: <input name="area" type="text"  max="100" id="areaId" placeholder="比如：132区队" />
		</label>

	<label for="">
			学号:     <input type="text" name="stuNo" id="stuNoId" required autofocus placeholder="请输入您的学号">
		</label>
		<label for="">
			联系电话: <input type="tel" name="fphone" id="fphoneId" pattern="1\d{10}" placeholder="请输入手机号码">
		</label>
			<label for="">
			联系地址: <input type="text" name="address" max="100" id="addressId" placeholder="比如：4号公寓101A" />
		</label>

		<label>故障分类:
		<select name="problemType" id="problemTypeId">
		<option value="">请选择</option>
		<option value="硬件故障">硬件故障</option>
		<option value="软件故障">软件故障</option>
		<option value="装机组合">装机组合</option>
		<option value="软件组合">软件组合</option>
		<option value="其它">其它</option>
		</select>
		</label>
		<div>
		<label >
		是否资料备案
		<select name="isBack" id="isBackId">
		<option value="">请选择</option>
		<option value="是">是</option>
		<option value="否">否</option>
		</select>
	   </label>
	   </div>
		<label for="">
			故障描述：<textarea name="problemDes" id="problemDesId" cols="10" rows="5" ></textarea>
		</label>
	

		<label for="" class="btn">
			<input type="button" onclick="saveRepair();" value="保存">
		</label>
	</fieldset>
</form>
<div style="text-align:center;">
</div>


</body>
</html>