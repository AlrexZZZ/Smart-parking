<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
    <title>我的一卡通</title>
    <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
</head>
<body class="flower_bg">
    <div class="container"><!--container-->
    
     <div class="float_head" ><!--float_head-->
       <div class="float_head_title">VIP通道</div>  
       <div class="head_title_back"><input type="button" value="我的一卡通" onclick="location.href='/ump/pageMyCard/showMyCard?id=${wcc.id}'"></div>     
     </div> <!--float_head end--> 
    
    <div class="newpro_content">
     <div class="new_img"><img src="${wcc.cardPic }" alt=""></div><!--new_img-->
    <div class="vip_bu_con"> 
     <input type="button" value="账户查询" class="vip_bu" onclick="location.href='${wcc.cardUrl}'">
     <input type="button" value="更多金融服务"  class="vip_bu" onclick="location.href='${wcc.jrUrl}'">
    </div>   
    </div>
       <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>
    </div><!--container end-->



</body>
</html>