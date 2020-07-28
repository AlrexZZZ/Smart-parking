<jsp:directive.page contentType="text/html;charset=UTF-8" />
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>联系我们</title>
    <link rel="stylesheet" type="text/css" href="../cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="./../cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<body class="greybg">
    <div class="container"><!--container-->
    
     <div class="new_img"><img src="${wcc.imgPath }" alt=""></div><!--new_img-->  
    
     <div class="contact_list" id="contact_list"><!--new product list-->
       <ul>
       
       <c:forEach  items="${list }" var="li" varStatus="">
            <li>
            <div class="contactlist_title">
              <div class="contactlist_title_wen">${li.contactUnit }</div>
              <div class="contactlist_title_arrow"><img src="/ump/images/new_4.png" alt=""></div>    
            </div>
            
            <div class="contactlist_num">${li.contactWay }</div>
           </li>
       
       </c:forEach>
       </ul>
     </div><!--new product list end-->
    
   
    
    </div><!--container end-->
    
<script type="text/javascript">
   var contactlistId="contact_list";//联系我们列表层id
   var  shujiantou="/ump/images/new_4.png";//竖着的箭头路径
   var  shujiantou2="/ump/images/contect_08.png";//横着的箭头路径
    
   $("#"+contactlistId+"> ul >li").click(function(){
     var a=$("#"+contactlistId+"> ul >li");
     var listIndex=$(this).index();
     
     a.eq(listIndex).children('div .contactlist_num').toggle(); 
       
     var status=a.eq(listIndex).children('div .contactlist_num').css("display");
     var b=a.eq(listIndex)
     
       if(status == "none"){
           b.children("div .contactlist_title").children("div .contactlist_title_arrow")
           .children("img").attr('src',shujiantou)
           .css({"width":"8px","margin-top":"18px"});
       }
       else{
           b.children("div .contactlist_title").children("div .contactlist_title_arrow")
           .children("img").attr('src',shujiantou2)
           .css({"width":"14.28px","margin-top":"24px"});
       }
   }); 
</script>
</body>
</html>

