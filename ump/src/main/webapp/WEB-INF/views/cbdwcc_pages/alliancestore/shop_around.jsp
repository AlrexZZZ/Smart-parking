<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>周边特惠</title>
     <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<script type="text/javascript">
	function getInfo(value){
		
		var platId=$("#platId_").val();
		
    	var str	="showCreate?type="+value;
    	str+="&platId="+platId;
    	console.log(str);
		window.location.href = str;
		
	
}
	function getQin(value){
		
		var str	="showAlliancestore?id="+value;
    	console.log(str);
		window.location.href = str;
	}
</script>
<body>
    <div class="container"><!--container-->
    
     <div class="float_head" onclick="show_downlist()" id="float_head2"><!--float_head-->
       <div class="float_head_title" id="float_head_title2">${type.name }</div>     
       <div class="float_head_arrow"><img src="/ump/cbdwccui/images/community_index_03.png" alt=""></div>   
     </div> <!--float_head end--> 
              
    <div class="float_div" id="float_div2"><!--float_div-->
      <ul>
      	<c:forEach items="${type1 }" var="br">
          <li onclick="getInfo('${br.id}')" >${br.name}</li>
        </c:forEach>
      </ul>
    </div><!--float_div end-->
    <input type="hidden" value="${platId}" name="platId" id="platId_">
    <div class="newpro_content" id="newpro_content2">
     <div class="new_img"><img id="imgid" src="${type.img }" alt=""></div><!--new_img-->  
    
     <div class="shop_listcon" id="shop_listcon"><!--shop_listcon-->
      <ul>
      <c:forEach items="${wccAlliancestore }"  var="br">
          <li onclick="getQin(${br.id})">
            <div class="shop_listL">
              <div class="shop_name">${br.storeName }</div>
                <div class="shop_intro">
                	<div style="width:400px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;" title="
						${br.storeIntro}">
   	 				${br.storeIntro}    
				</div>    
                </div>
                
                <div class="shop_address">${br.storeAddress }</div>
                <div class="shop_tel">${br.storePhone }</div>
                
            </div>
             
            <div class="shop_listR"><img src="/ump/cbdwccui/images/images/shop_05.png" alt=""></div>
          </li>
          </c:forEach>
          
      </ul>
     </div><!--shop_listcon end-->
    	
    </div>
    		   <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>
    </div><!--container end-->
    	
<script type="text/javascript">
    

var floathead="float_head2";//浮动头的ID
var floatdivId="float_div2";//下拉菜单层的ID
var floatheadtitle="float_head_title2"//浮动头的title的ID
var newprotitleId="newpro_content2";//周边特惠内容的ID
var listshopID="shop_listcon";//周边特惠列表容器id
    
    
window.onload=function(){
    //使内容容器填满浏览器窗口
   var winHeight=$(window).height();
   var WinHeightS=parseInt(winHeight)-50;
   var newpro_contentHeight=$("#"+newprotitleId).height();
   var mus=parseInt(WinHeightS)-parseInt(newpro_contentHeight);
   if(mus > 0){$("#"+newprotitleId).css("height",WinHeightS+"px") }
    
    //电话号码赋给对应的拨打图标
    var shoplist_length=$("#"+listshopID+">ul>li").length;
    var i=0;
    for( i;i < shoplist_length; i++){
      var tel=$("#"+listshopID+">ul>li").eq(i).children("div .shop_listL")
      .children("div .shop_tel").text();
      $("#"+listshopID+">ul>li").eq(i).children("div .shop_listR")
          .children("img").wrap("<a href='tel:"+tel+"'></a>");   
    };
};    
    
//点击显示、隐藏下拉菜单
function show_downlist(){
  $("#"+floatdivId).show();
};

$("#"+floatdivId+"> ul > li").click(function(){
    var listIndex=$(this).index();
    var licontent=$("#"+floatdivId+"> ul > li").eq(listIndex).text();
    $("#"+floatheadtitle).text(licontent)
    $("#"+floatdivId).hide();
})

$("#"+newprotitleId).click(function(){
    $("#"+floatdivId).hide();
});
   



</script>
</body>
</html>