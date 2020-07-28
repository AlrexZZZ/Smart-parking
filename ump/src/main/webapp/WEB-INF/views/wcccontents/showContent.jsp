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
<script src="/ump/js/jquery.min.js" type="text/javascript"></script>
<script src="/ump/js/wContent-web.js" type="text/javascript"></script>
<script src="/ump/js/hiddenOptionMenu.js"></script>
<link href="/ump/styles/style.css" type="text/css" rel="stylesheet">
<title>微内容</title>
<style>
*{ padding:0px ; margin:0px;}
.container2{ width:100%; max-width:640px; margin:0px auto; clear:both; overflow:hidden}
.huifu{ width:100%; margin:0px auto; clear:both; overflow:hidden}
.p_title{ width:96%; height:30px; line-height:30px; font-size:16px; font-family:"微软雅黑"; border-bottom:1px solid #e5e5e5; margin:0px auto}
.p_title_left{ width:30%;height:30px; line-height:30px; font-size:16px; font-family:"微软雅黑"; border-bottom:1px solid #069dd5; float:left}
.p_title img{ width:16px; height:16px; margin-top:10px; float:left; margin-right:10px; }
.huifu_list{ width:95%; margin:0px auto; clear:both; overflow:hidden; margin-top:10px;}
.huifu_left { width:8%; float:left}
.huifu_left img{ width:100%; height:auto; border:0px; border-radius:50%; -webkit-border-radius:50%;}
.huifu_right{ width:88%; float:right; font-size:14px; line-height:20px;}
.huifu_right p span{ color:#069dd5}
</style>
</head>
<body>
<div class="container">
	<input id="contentId" type="hidden" value="${wcccontent.id}"/>
	<input id="imgSer" type="hidden" value="${imgSer}"/>
	<input id="headImg" type="hidden" value="${wccFriend.headImg}"/>
	<input id="friendName" type="hidden" value="${wccFriend.nickName}"/>
	<input id="friendid" type="hidden" value=" ${wccFriend.id}"/>
    <div class="head_cont">
    	<div id="title" align="left" class="b_name" >${wcccontent.title}</div>
    </div>
	<div class="b_auto">
		<fmt:formatDate value="${wcccontent.insertTime}" pattern="yyyy-MM-dd" />
	</div>
	<div id="content" class="b_new_cot">${wcccontent.content}</div>
	<c:if test="${wcccontent.isPrizeCommit == true}">
	    <div class="dshare" style="float: right;">
	    	<ul>
	        	<li id="praise"><img id="praStr" src="/ump/images/praise.png" style="cursor: pointer;"> <a href="javascript:void(0);"><span id="praiseNum">${wcccontent.praiseCount}</span></a></li>
	            <li class="dshare_03" id="comment"> <a href="javascript:void(0);"></a></li>
	        </ul>
	    </div>
		<div class="container2" >
			  <div class="huifu">
			    	<div class="p_title">
			        <div class="p_title_left"><img src="/ump/images/huifu.png" width="20" height="20">全部评论</div></div>
			        <div id="pl">
			        <c:if test="${!empty wccCommentList}">
			        	<c:forEach items="${wccCommentList}" var="data" varStatus="status">
			        		<c:if test="${status.index<=3}">
						        <div class="huifu_list" id="dis${data.id}">
						        	<div class="huifu_left"><img src="${data.wccFriend.headImg}" width="33" height="33"></div>
						            <div class="huifu_right"><p><span style="padding-right: 5px;">${data.wccFriend.nickName}:</span><span style="color:black">${data.content}</span></p>
						            	<time>
						            		<fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd" />
						            	</time>
						            </div>
						        </div>
					        </c:if>
					         <c:if test="${status.index==4 }">
								<div id="more${data.id}" style="text-align: center;padding-top: 10px;">
									<a style="color: #069dd5;text-align: center;"
										href="javascript:disMore('dis${data.id}','more${data.id}');">全部共有 <span id="commCount" style="color:  #069dd5;">${commentCount}</span> 回复
									</a>
								</div>
							</c:if>
							<c:if test="${status.index>=4}">
						        <div class="huifu_list" id="dis${data.id}" style="display: none;">
						        	<div class="huifu_left"><img src="${data.wccFriend.headImg}" width="33" height="33"></div>
						            <div class="huifu_right"><p><span style="padding-right: 5px;">${data.wccFriend.nickName}:</span><span style="color:black">${data.content}</span></p>
						            	<time><fmt:formatDate value="${data.createTime}" pattern="yyyy-MM-dd" /></time>
						            </div>
						        </div>
					        </c:if>
					        <c:if test="${status.last==true}">
								<c:set var="commentCount" value="${status.count}" />
								<input type="hidden" name="commentCount" id="commentCount" value="${status.count}" />
							</c:if>
				        </c:forEach>
			        </c:if>
			        </div>
			  </div>
			</div>
		</c:if>
    </div>
    
    
    <div id="return" style="display:none">
    	<div class="popup">
		    <div class="area">
			<textarea id="commContent" style="-webkit-appearance: none;">请输入内容</textarea>    </div>
		    <table width="80%" border="0" class="nav_01">
		      <tr>
		        <td width="49%"  align="right">500</td>
		        <td width="12%"><input name="" type="reset" value="取消" id="nav_input01" > </td>
		        <td width="12%" ><input type="submit" value="确认" id="nav_input02" ></td>
		      </tr>
		    </table>
 	   </div>
  </div>
</body>
<script src="/ump/js/weixinImageView.js" type="text/javascript"></script>
</html>
