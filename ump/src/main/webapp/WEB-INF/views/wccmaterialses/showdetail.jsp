<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<link href="/ump/styles/style.css" type="text/css" rel="stylesheet">
</head>
<c:if test="${not empty material.content}">
<div class="container">

	<div class="head_cont"><div id="title" align="left" class="b_name" >${material.title}</div></div>
	
	<div class="b_auto"><fmt:formatDate value="${material.insertTime}" pattern="yyyy-MM-dd" /> ${material.token}</div>
	
	<div id="content" class="b_new_cot"><c:if test="${material.urlBoolean}"><img src="${material.thumbnailUrl}"/></c:if><div>${material.content}</div></div>
</div>
</c:if>
<c:if test="${empty material.content}">
<script type="text/javascript">
var url = "${material.remakeUrl}";
	location.href = url;
</script>
</c:if>
