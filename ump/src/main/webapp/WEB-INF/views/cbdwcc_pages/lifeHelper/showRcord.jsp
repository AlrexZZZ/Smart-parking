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
    
     <div class="float_head"><!--float_head-->
       <div class="float_head_title" id="float_head_title1">生活资讯</div>        
     </div> <!--float_head end--> 
              
        <div class="newpro_content">
         <div class="lifehelper_con"><!--lifehelper_con-->
          <ul>
          
          <c:forEach items="${list }" var="li"  varStatus="">
     <!--      <c:if test="${null != li.otherUrl }">
          <li onclick="location.href='${li.otherUrl }'">
              <div class="lifehelper_img"><img src="${li.backgroundImg}" alt=""></div>
                <div class="lifehelper_wen">${li.listTip }kyouliajie</div>  
              </li>
          </c:if>
           <c:if test="${ li.otherUrl == '' }">
          <li onclick="location.href='/ump/pageLifeHelper/showLifeDetail?id=${li.id}'">
              <div class="lifehelper_img"><img src="${li.backgroundImg}" alt=""></div>
                <div class="lifehelper_wen">${li.otherUrl ==''}-wulianjie</div>  
              </li>
          </c:if> -->
       
          <c:if test="${li.otherUrl.trim() !=''}">
          <li onclick="location.href='${li.otherUrl}'">
             <div class="lifehelper_img"><img src="${li.backgroundImg}" alt=""></div>
              <div class="lifehelper_wen">${li.backgroundTip}</div>  
             </li> 
          </c:if>
           <c:if test="${li.otherUrl.trim() ==''}">
          <li onclick="location.href='/ump/pageLifeHelper/showLifeDetail?id=${li.id}'">
             <div class="lifehelper_img"><img src="${li.backgroundImg}" alt=""></div>
             <div class="lifehelper_wen">${li.backgroundTip}</div>  
             </li> 
          </c:if>  
          
        
          
      </c:forEach>
      <!-- 根据数据量来显示 ‘敬请期待的个数’ -->
      <c:if test="${listSize == 1}">
         <li>
                <div class="lifehelper_img"><img src="/ump/images/life_helper_18.png" alt=""></div>
                <div class="lifehelper_wen">敬请期待</div>   
         </li>
         <li>
                <div class="lifehelper_img"><img src="/ump/images/life_helper_18.png" alt=""></div>
                <div class="lifehelper_wen">敬请期待</div>   
         </li>
      </c:if>
      <c:if test="${listSize == 2}">
         <li>
                <div class="lifehelper_img"><img src="/ump/images/life_helper_18.png" alt=""></div>
                <div class="lifehelper_wen">敬请期待</div>   
         </li>
      </c:if>
          </ul>
         </div><!--lifehelper_con end-->
            
        </div>
    
    </div><!--container end-->
    
</body>
</html>

