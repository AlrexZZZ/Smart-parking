<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>评论回复</title>
</head>
<style>
.faq_box{width:700px; height: auto; padding:0px; clear: both}
.faq_left{width: 650px;height: 110px; float: left}
.faq_right{float: left;background:#069dd5;color:"#fff";cursor: pointer;color:white;font-family:"Microsoft YaHei";padding: 10px}
.faq_left textarea{width:650px; height: 108px; border: 1px solid #e5e5e5;}
.faq_reply{width:700px; height: auto; padding:0px; clear: both;margin-top: 20px;}
.last_words{width:550px;float: left;margin-right: 30px;}
.faq_reply input{width:70px;height: 28px;background:#069dd5;line-height: 28px;}

</style>
<body>
 <div id="replyTableId">
 <c:set var="brandId" scope="session"></c:set>
 <c:set var="commentLevel" scope="session"></c:set>
  <table class="table striped hovered" style="table-layout:fixed">
		<thead>
			<tr>
			    <th>
					平台名称
				</th>
				
				<th>
					评论
				</th>
				
				<th>
					商品名称
				</th>
				
				<th>
					评论时间
				</th>
			</tr>
			</thead>
			 <c:forEach var="comment" items="${list}">
			 <c:set var="brandId" value="${comment.goods.vocBrand.id}" scope="session"></c:set>
			 <c:set var="commentLevel" value="${comment.commentLevel.id}" scope="session"></c:set>
			<tr>
			<input id="hiddenReplyId" name="commnetId" type="hidden" value="${comment.id}"/>
			 <td>${comment.goods.channel.channelName}</td>
			 <td>${comment.commentContent}</td>
			 <td>${comment.goods.name}</td>
			 <td>
			  <fmt:formatDate value="${comment.commentTime}" pattern="yyyy-MM-dd HH:mm:ss" />
			 </td>
			</tr>
			 </c:forEach>
		
		</table>

  <div>
    <div class="faq_box">
			<div class="faq_left"><textarea id="replyContentId" onkeyup="changeNumTips(this)"></textarea></div>
			 <!-- 暂时隐藏 sea 20150109
			<div class="faq_right" onclick="javascript:callTemplate('${brandId}','${commentLevel}');">FAQ<br>调用</div>
               -->
    </div>
    <div class="faq_reply">
     <div class="last_words">还可以输入<span id="numbertips">500</span>字</div>
    <input style="font-family:'Microsoft YaHei';color: #fff;border-radius: 2px ; -webkit-border-radius:2px;"  type="button" value="回 复" onclick="javascript:sendReply(${comment.id});"></div>
  
  </div>
 </div>
</body>
</html>