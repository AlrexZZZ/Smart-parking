<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>文明生活</title>
    <link rel="stylesheet" type="text/css" href="../cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="./../cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<body class="withbg">
    <div class="container">
      
      <div class="detail_head">
          <div class="head_title">${detailObj.listTip}</div>
          <div class="head_date"><h1><fmt:formatDate value="${detailObj.insertTime}" pattern="yyyy-MM-dd"/></h1><h2>${plt.account}</h2></div>
      </div>
             
<!--       <div class="detail_img">
        <img src="images/elec.png" alt="">
      </div>  -->        
       <div class="detail_img">   
      <div class="detail_article">
${detailObj.detailContent}
      </div> 
        </div>    
      <div class="detail_back">
        <input type="button" value="返   回" onclick="window.history.go(-1)">  
      </div>   
                                                                                      
                                                                                                                                                                                                                           
    </div>
</body>
</html>