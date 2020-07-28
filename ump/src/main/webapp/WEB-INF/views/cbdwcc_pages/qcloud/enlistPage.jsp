<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/ump/qcloud/jqMobile/css/jquery.mobile-1.3.2.min.css">
<script src="/ump/qcloud/jqMobile/js/jquery-1.8.3.min.js"></script>
<script src="/ump/qcloud/jqMobile/js/jquery.mobile-1.3.2.min.js"></script>
</head>
<script type="text/javascript">
function saveData(){
	var fname = $("#fnameId").val();
	var stuNo = $("#stuNoId").val();
	var dept = $("#deptId").val();
	var area = $("#areaId").val();
	var phone = $("#phoneId").val();
	var forDept = $("#forDeptId").val();
	var goodAt = $("#goodAtId").val();
	var des = $("#desId").val();
	var radio_ = document.getElementsByName('radio-choice');
    var f = false;
    var gender ; //  性别
    for(var i=0;i<radio_.length;i++){
    	if(radio_[i].checked){
    		f= true;
    		gender = radio_[i].value;
    	}
    }
      if(fname.trim() == ''){
    	  alert('请填写名称');
    	  return;
      }
      if(stuNo.trim() == ''){
    	  alert('请填写学号');
    	  return;
      }
      if(!gender){
    	  alert('请选择性别');
    	  return;
      }
      if(dept.trim() == ''){
    	  alert('请填写系别');
    	  return;
      }
      if(area.trim() == ''){
    	  alert('请填写区队');
    	  return;
      }
      if(phone.trim() == ''){
    	  alert('请填写电话');
    	  return;
      }
      if(forDept.trim() == ''){
    	  alert('请填写应聘部门');
    	  return;
      }
      if(goodAt.trim() == ''){
    	  alert('请填写特长');
    	  return;
      }
      
      var param = {
    		 openId:$("#openId").val(),
     		 fname : $("#fnameId").val(),
     		 stuNo : $("#stuNoId").val(),
     		 dept : $("#deptId").val(),
     		 area : $("#areaId").val(),
     		 phone : $("#phoneId").val(),
     		 forDept : $("#forDeptId").val(),
     		 goodAt : $("#goodAtId").val(),
     		 des : $("#desId").val(),
     		 gender : gender		
     		
     }
     console.log(param);
      
  	$.ajax({
		url : "/ump/pageBding/addEnlist",
		type : "POST",
		data :param ,
	/* 	async : false, */
		error : function(request) {
	  alert('网络连接异常，请稍后再试!');
		},
		success : function(data) {
	  location.reload();
	  alert('数据提交成功!');
		}
	});
      
	}
    

</script>
<body>

<div data-role="page">
<div style="width: 100%;height: 18px;background-color: #5CB85C;text-align: center;color: white;"></div>
<div style="width: 100%;background-color: #5CB85C;text-align: center;color: white;">
<div>预报名表</div>
<div style="width: 100%;height: 18px;background-color: #5CB85C;text-align: center;color: white;"></div>
</div>
  <div data-role="content">
  
    <form method="post" >
      <div data-role="fieldcontain">
      <div>
        <label style="width: 100px;margin-top: 10px" for="lname">学号：</label>
        <input  type="text" name="stuNo" id="stuNoId">
        <input  type="hidden" value="${openId}" id="openId">
      </div>
  <div style="margin-top: 10px;">
          <label style="width: 100px;margin-top: 10px;" for="fname">姓名：</label>
        <input type="text"  name="fname" id="fnameId">
  </div>

<div data-role="fieldcontain">
<fieldset data-role="controlgroup" id="gid">
  <legend style="width: 100px;important!">性别:</legend>
  <input type="hidden"  name="gender" id="genderId">
  <input type="radio" name="radio-choice" id="radio-choice-1" value="男" checked="checked" />
  <label for="radio-choice-1">男</label>
 
  <input type="radio" name="radio-choice" id="radio-choice-2" value="女" />
  <label for="radio-choice-2">女</label>

</fieldset>
</div>

  <div style="margin-top: 10px;">
          <label style="width: 100px;margin-top: 10px;" for="fname">系别：</label>
        <input type="text"  name="dept" id="deptId">
  </div>
    <div style="margin-top: 10px;">
          <label style="width: 100px;margin-top: 10px;" for="fname">区队：</label>
        <input type="text"  name="area" id="areaId">
  </div>
  
    <div style="margin-top: 10px;">
          <label style="width: 100px;margin-top: 10px;" for="fname">电话：</label>
        <input type="text"  name="phone" id="phoneId">
  </div>
    <div style="margin-top: 10px;">
          <label style="width: 100px;margin-top: 10px;" for="fname">应聘部门：</label>
        <input type="text"  name="forDept" id="forDeptId">
  </div>
      <div style="margin-top: 10px;">
          <label style="width: 100px;margin-top: 10px;" for="fname">特长：</label>
        <textarea style="margin-top: 10px;" name="goodAt" id="goodAtId"></textarea>
  </div>
  <div>
    <label style="width: 100px;margin-top: 10px;" for="fname">个人简介：</label>
    <textarea style="margin-top: 10px;" name="des" id="desId"></textarea>
  </div>
  

      </div>
      <input type="button" onclick="saveData();" data-inline="true" value="提交">
    </form>
  </div>
</div>
</body>
</html>