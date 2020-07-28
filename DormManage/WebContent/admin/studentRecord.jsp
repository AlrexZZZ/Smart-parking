<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	function studentDelete(studentId) {
		if(confirm("您确定要删除这个值守员吗？")) {
			window.location="student?action=delete&studentId="+studentId;
		}
	}
</script>
<div class="data_list">
		<div class="data_list_title">
			站点值守员管理
		</div>
		<form name="myForm" class="form-search" method="post" action="student?action=search">
				<button class="btn btn-success" type="button" style="margin-right: 50px;" onclick="javascript:window.location='student?action=preSave'">添加</button>
				<span class="data_search">
					<select id="buildToSelect" name="buildToSelect" style="width: 110px;">
					<option value="">全部站点</option>
					<c:forEach var="dormBuild" items="${dormBuildList }">
						<option value="${dormBuild.dormBuildId }" ${buildToSelect==dormBuild.dormBuildId?'selected':'' }>${dormBuild.dormBuildName }</option>
					</c:forEach>
					</select>
					<select id="searchType" name="searchType" style="width: 80px;">
					<option value="name">姓名</option>
					<option value="number" ${searchType eq "number"?'selected':'' }>值守员工号</option>
					<option value="dorm" ${searchType eq "dorm"?'selected':'' }>所属楼层</option>
					</select>
					&nbsp;<input id="s_studentText" name="s_studentText" type="text"  style="width:120px;height: 30px;" class="input-medium search-query" value="${s_studentText }">
					&nbsp;<button type="submit" class="btn btn-info" onkeydown="if(event.keyCode==13) myForm.submit()">搜索</button>
				</span>
		</form>
		<div>
			<table class="table table-hover table-striped table-bordered">
				<tr>
					<th>编号</th>
					<th>值守员工号</th>
					<th>姓名</th>
					<th>性别</th>
					<th>站点</th>
					<th>所属楼层</th>
					<th>电话</th>
					<th width="15%">操作</th>
				</tr>
				<c:forEach  varStatus="i" var="student" items="${studentList }">
					<tr>
						<td>${i.count+(page-1)*pageSize }</td>
						<td>${student.userName }</td>
						<td>${student.name }</td>
						<td>${student.sex }</td>
						<td>${student.dormBuildName==null?"无":student.dormBuildName }</td>
						<td>${student.dormName }</td>
						<td>${student.tel }</td>
						<td><button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='student?action=preSave&studentId=${student.studentId }'">修改</button>&nbsp;
							<button class="btn btn-mini btn-info" type="button" onclick="javascript:window.location='student?action=record&studentId=${student.studentId }'">站点出勤管理</button>&nbsp;
							<button class="btn btn-mini btn-danger" type="button" onclick="studentDelete(${student.studentId })">删除</button></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div align="center"><font color="red">${error }</font></div>
		<div class="pagination pagination-centered">
			<ul>
				${pageCode }
			</ul>
		</div>
</div>