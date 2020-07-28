<jsp:directive.page contentType="text/html;charset=UTF-8" />
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文明生活</title>
    <link rel="stylesheet" type="text/css" href="../cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="./../cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<body class="greybg">
    <div class="container"><!--container-->
        
      <div class="pic_word_list"><!--pic_word_list-->
        <div class="fir_pic">
          <img src="${wcc.imgPath }" alt="">
          <div class="pic_blackdiv">文明生活</div>  
        </div>  
        
        <ul>
        
        <c:forEach items="${list }" var="li" varStatus="">
              <li onclick="location.href='/ump/pageCultureLife/showDetail?id=${li.id}'">
             <div class="word_list_L">${li.listTip }</div>  
             <div class="word_list_R"><img src="${li.listImg }" alt=""></div>  
       </li>
        
        </c:forEach>
        
        </ul>
        
      </div> <!--pic_word_list end-->       
             <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;">交通银行广东省分行@版权所有</div>
              
    </div><!--container end-->
    
</body>



