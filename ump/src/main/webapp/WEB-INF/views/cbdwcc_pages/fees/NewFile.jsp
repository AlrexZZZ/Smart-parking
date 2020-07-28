<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <title>物管费信息</title>
    <link rel="stylesheet" type="text/css" href="../cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="./../cbdwccui/js/jquery-1.9.1.js"></script>
    
    <link rel="stylesheet" href="date/skin/WdatePicker.css" type="text/css">
    <script type="text/javascript" src="/ump/js/date/calendar.js"></script>
     <script type="text/javascript" src="/ump/js/weixin.js"></script>
    <script type="text/javascript" src="/ump/js/date/WdatePicker.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<script type="text/javascript">
function queryInfo(){

	var date = $("#dateId").val();
	var fid = ${friendId};
	// return;
	window.location.href="/ump/pageFees/showLifeDetail?friendId='"+fid+"'&date="+date;
/* 	$.ajax({
		url : "",
		async: false ,
		type : "POST",
		data : {
			'date':$("#dateId").val()
			},
		error : function(msg){
		},
		success : function(e) {
			
			
		}
	}); */
}

</script>
<body class="greybg">
    <div class="container"><!--container-->
    
     <div class="float_head"><!--float_head-->
       <div class="float_head_title" id="float_head_title1">物业费账单</div>        
     </div> <!--float_head end--> 
              
        <div class="newpro_content">
          
        <div class="search_date"><!--search_date-->
          <input type="text" id="dateId" class="Wdate input_month" onclick="WdatePicker({dateFmt:'yyyy-MM'})" placeholder="请选择年月" />          
           <input type="button" onclick="queryInfo();" value="搜  索" class="input_search"> 
        </div> <!--search_date end-->       
        
        <div class="tablelist"><!--tablelist-->
         <div class="color_tishi">
            <span class="colorblack"></span>已交款项
            <span class="colorred"></span>未交款项 
         </div>
          
          <c:forEach items="${dataMap}" var="dm">
          

           <div class="tablelisat_con"><!--表格列表con-->
         <div class="mouth_title">${fn:split(dm.key,'_')[0]}</div>
         <table>
             <tr>
              <th>项目</th>
              <th>摘要</th>
              <th>年月</th>
              <th>金额</th> 
             </tr>
         <c:forEach items="${dm.value}" var="detailObj">
         
               <tr>
                <td>${detailObj.itemName}</td>
                <td>${detailObj.summary}</td>
                <td>${detailObj.monthStr}</td>
                <c:if test="${detailObj.state == 0}">
                <td style="color:red;">${detailObj.amount}</td>
         		
                </c:if>
                <c:if test="${detailObj.state == 1}">
                <td>${detailObj.amount}</td> 
                </c:if>
                
             </tr>
         
         </c:forEach>    
       
             
     
             
             <tr>
              <th  colspan="2">未交总金额数</th>
              <th  colspan="2">${fn:split(dm.key,'_')[1]}</th>     
             </tr>
         </table>    
        </div>
          
          </c:forEach>
               
            
        </div><!--tablelist end--> 
            
        </div>
    
    </div><!--container end-->
    
 
    
</body>
</html>

