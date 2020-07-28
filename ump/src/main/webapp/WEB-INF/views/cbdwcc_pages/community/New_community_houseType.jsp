<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
     <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
    <title>社区概览</title>
    <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
</head>
<body class="greybg">
    <div class="container"><!--container-->
    
     <div class="float_head" ><!--float_head-->
       <div class="float_head_title">${wccItemotherinfo.introTitle }</div>  
       <div class="head_title_back"><input type="button" value="返 回" onclick="location.href='javascript:history.go(-1)'"></div>     
     </div> <!--float_head end--> 
    
    <div class="newpro_content">
      
      <!-------------------图片轮播------------------->
      <div data-role="widget" data-widget="slider_5" class="slider_5">
					<section>
						<div id="slider_5_wrap" class="slider_5_wrap">
							<ul >
							<c:forEach items="${itemImg }" var="br">
								<li>
									<a href="javascript:;">
										${br}
									</a>
								</li>
								</c:forEach>
							</ul>
							<div id="slider_5_indicate" class="slider_5_indicate"></div>
						</div>
					</section>
				</div>

<!-------------------图片轮播end------------------>
      
    </div>
       <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>
    </div><!--container end-->


<script type="text/javascript" src="/ump/cbdwccui/js/swipe_min.js"></script>  
<script type="text/javascript">
//图片轮播  
window.onload=function(){
 var winHeight=$(window).height();
 var WinHeightS=parseInt(winHeight)-50;
 var TP_LBLIST_height=$("#slider_5_wrap>ul>li").height(); 
 var mus=parseInt(WinHeightS)-parseInt(TP_LBLIST_height);
    if(mus < 0){
    $("#slider_5_wrap>ul>li").css("height",WinHeightS+"px");
    $("#slider_5_wrap>ul>li>a>img").css("height",WinHeightS+"px");   
    }else{
     $("#slider_5_wrap>ul>li").css("height",WinHeightS+"px");
     $("#slider_5_wrap>ul>li>a>img").css("height",WinHeightS+"px");  
    }
};
  
var slider_5 = (function(){
		var that = {};
		that.initSlider = function(){
			that.slider = new Swipe(document.getElementById('slider_5_wrap'), {
			 speed:500,
			 //auto:2000,
			 loop:true,
			 indicate:"#slider_5_indicate"
		   });
			 return that;
		 }
			 return that;
		 })().initSlider();

    
</script>
</body>
</html>