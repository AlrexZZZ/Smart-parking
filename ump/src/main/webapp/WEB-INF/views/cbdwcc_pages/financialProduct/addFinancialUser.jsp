<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <meta charset="UTF-8">
    <title>理财产品预约</title>
    <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
	<meta content="telephone=no" name="format-detection" />
	<script type="text/javascript">
	
	function changeColor(e){
	
		if(e.value !=  "")
		{
		e.style.color = 'black';	
		}else{
		e.style.color = '#C0C0C0';	
		}
		
	}
	
	</script>
</head>

<body>
    <div class="container"><!--container-->
    
     <div class="float_head" ><!--float_head-->
       <div class="float_head_title">${financialProduct.financialName}</div>  
       <div class="head_title_back"><input type="button" value="返 回" onclick="location.href='javascript:history.go(-1)'"></div>     
     </div> <!--float_head end-->  
              
    <div class="newpro_content">
     <div class="new_img"><img src="${financialProduct.themeImage }" alt=""></div><!--new_img-->  
     
     <div class="bd_fromecontant"><!--表单-->
      <div class="visitor_num">您是今年第<span>${totalNumber+1}</span>位参与产品预约的客户，很高兴
为您服务,您的剩余可预约额度为<span id="totalTip">${totalMy - personTotalMoney}</span>元</div>
	
      <form id="financialUserForm" name="financialUserForm" action="/ump/pageFinancialProduct/addFinancialUser" method="post">
     	<input type="hidden" id="totalTip1" value="${totalMy - personTotalMoney}" />
      <input type="hidden" id="financialProductId" name="financialProductId" value="${financialProduct.id}" />
      <input type="hidden" id="minMoney" name="minMoney" value="${financialProduct.minMoney}" />
      <input type="hidden" id="maxMoney" name="maxMoney" value="${financialProduct.maxMoney}" />
      <input type="hidden" id="increasingMoney" name="increasingMoney" value="${financialProduct.increasingMoney}" />
      <input type="hidden" id="totalMoney" name="totalMoney" value="${financialProduct.totalMoney}" />
      <input type="hidden" id="friendId" name="friendId" value="${friendId}" />
       <input type="hidden" id="platId" name="platId" value="${platId}" />
       <input type="hidden" id="totalMyId" name="totalMyId" value="${totalMy}" />
      <input type="text" id="userName" name="userName" style="color: black;" placeholder="在此输入姓名（必填）" class="input_style input_name">
      <span id="userNameMsg" style="color: red"></span>
      <input type="text" id="phone" name="phone" style="color: black;" placeholder="在此输入联系电话（必填）" class="input_style input_phone">
      <span id="phoneMsg" style="color: red"></span>
      <input type="text" id="email" name="email" style="color: black;" placeholder="在此输入电子邮箱" class="input_style input_mail">
      <span id="emailMsg" style="color: red"></span>
       <div class="search_down_bd"><!--search_down-->
			<select id="credentialTypeId" onchange="changeColor(this);" name="credentialTypeId">
				<option  value="">请选择证件类型（必填）</option>
					<c:forEach items="${credentialTypes}" var="data"
						varStatus="status">
					<option style="color: black;" value="${data.id}">${data.typeName}</option>
				</c:forEach>
			</select>
			<span id="credentialTypeIdMsg" style="color: red"></span>
		</div> <!--search_down end-->
      <input type="text" id="credentialNumber" style="color: black;" name="credentialNumber" placeholder="在此输入证件号码（必填）" class="input_style input_idc">
      <span id="credentialNumberMsg" style="color: red"></span>
      <input type="text" id="productId" style="color: black;" name="productId" value="${financialProduct.financialName}" disabled="disabled" class="input_style input_product">
      <input type="text" id="money" style="color: black;" name="money" placeholder="在此输入申请额度（必填）" class="input_style input_money">
      <span id="moneyMsg" style="color: red"></span>
      <input type="button" value="确  定"  class="input_submit" onclick="save()" >
      </form>     
     </div><!--表单 end-->
     
     <div class="yyi_tishi">
        <span>温馨提示：</span> 
<p>        
1.请您务必正确填写预约信息，确保额度预约成功；<br>
2.预约额度生效后将微信通知，预约额度将为您保留2个自然日，如未能及时购买，需重新提交预约申请。<br>
3.预约额度请登陆网银或手机银行，在“得利宝”菜单下“预约额度产品购买”菜单内购买。
</p>
     </div>
    
    </div>
    
    </div><!--container end-->
    
<!--弹出框-->
	<div class="info_div" id="yy_div">
		<div class="info_div_tit">信息提示</div>
		<div class="info_div_con">
			<!--info_div_con-->
			<div id="addUserMessage">
				业主您好：<br>您的的预约已成功
			</div>
			<div>
				<input type="button" value="确 定" class="yz_submit"
					onclick="hideendiv(this)">
			</div>
		</div>
		<!--info_div_con end-->
	</div>
	<div class="blackcover" id="yy_blackcover"></div>
<!--弹出框 end-->

<script type="text/javascript">


var re_div_ID="yy_div";//弹出层ID
var re_blackcoverID="yy_blackcover"//黑色层ID

function save(){
	// 校验
	if (checkForm()) {
		var url = $("#financialUserForm").attr("action");
		$.ajax({
				cache : true,
				type : "POST",
				url : url,
				data : $("#financialUserForm").serialize(),
				async : false,
				error : function(request) {
					$("#addUserMessage").html("业主您好：<br>您的的预约失败");
					var leftval = (parseInt($(window).width()) - parseInt($("#" + re_div_ID)
							.width())) / 2;
					$(window).scrollTop(0);
					$("#" + re_div_ID).css("left", leftval).show();
					$("#" + re_blackcoverID).show();
				},
				success : function(data) {
					if (data == "error") {
						$("#addUserMessage").html("业主您好：<br>您的的预约失败");
					} else {
						$("#addUserMessage").html("业主您好：<br>您的预约已申请，预约成功后将短信通知您");
					
						var currentMoney = '${totalMy - personTotalMoney}';
						var nowMoney = $("#money").val();
						//$("#totalTip").html(currentMoney - nowMoney);
						document.getElementById("totalTip").innerHTML=parseInt($("#totalTip1").val())-nowMoney;
						$("#totalTip1").val(parseInt($("#totalTip1").val())-nowMoney);
					}
					var leftval = (parseInt($(window).width()) - parseInt($("#" + re_div_ID)
							.width())) / 2;
					$(window).scrollTop(0);
					$("#" + re_div_ID).css("left", leftval).show();
					$("#" + re_blackcoverID).show();
				}
			});
		} else {
			return false;
		}
	}

	/*function showdiv() {
		var leftval = (parseInt($(window).width()) - parseInt($("#" + re_div_ID)
				.width())) / 2;
		$(window).scrollTop(0);
		$("#" + re_div_ID).css("left", leftval).show();
		$("#" + re_blackcoverID).show();
	};*/

	function hideendiv(e) {
		$("#" + re_div_ID).hide();
		$("#" + re_blackcoverID).hide();
	};
	
	function checkForm(){
		var flag = true;
		var userName = $("#userName").val();
		var phone = $("#phone").val();
		var email = $("#email").val();
		var credentialTypeId =  $("#credentialTypeId").val();
		var credentialNumber = $("#credentialNumber").val();
		var money = parseInt($("#money").val());
		var minMoney =  parseInt($("#minMoney").val());
		var maxMoney =  parseInt($("#maxMoney").val());
		var increasingMoney =  parseInt($("#increasingMoney").val());
		var totalMoney =  parseInt($("#totalMoney").val());
		var totalMy=parseInt($("#totalMyId").val());
		if (userName == '') {
			$("#userNameMsg").html('姓名不能为空');
			flag = false;
			return;
		} else {
			$("#userNameMsg").html('');
		}
		
		var reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
		if ($.trim(phone) != "") {
			if (!reg.test(phone)) {
				$("#phoneMsg").html("请输入正确手机号码");
				flag = false;
				return;
			} else {
				$("#phoneMsg").html("");
			}
		} else {
				$("#phoneMsg").html("手机号码不能为空");
				flag = false;
				return;
		}
		
		var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		if (email == "") {
			$("#emailMsg").html("邮箱不能为空");
			flag = false;
			return;
		} else if (!reg.test(email)) {
			$("#emailMsg").html("邮箱格式不正确");
			flag = false;
			return;
		} else {
			$("#emailMsg").html("");
		}
		
		if (credentialTypeId == "") {
			$("#credentialTypeIdMsg").html("证件类型不能为空");
			flag = false;
			return;
		} else {
			$("#credentialTypeIdMsg").html("");
		}
		
		if (credentialNumber == "") {
			$("#credentialNumberMsg").html("证件号码不能为空");
			flag = false;
			return;
		}else {
			$("#credentialNumberMsg").html("");
		
		}
		
		if(credentialTypeId==8){
			
			idCard = trim(credentialNumber.replace(/ /g, ""));               //去掉字符串头尾空格                     
		    if (idCard.length == 15) {   
		        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
		    } else if (idCard.length == 18) {   
		        var a_idCard = idCard.split("");                // 得到身份证数组   
		        if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   //进行18位身份证的基本验证和第18位的验证
		        	$("#credentialNumberMsg").html("");
		        }else {   
		        	$("#credentialNumberMsg").html("身份证号码输入有误！");
					flag = false;
					return;  
		        }   
		    } else {   
		    	$("#credentialNumberMsg").html("身份证必须是15或18位！");
				flag = false;
				return;  
		    }   
			
			
			if( credentialNumber.length!=15 && credentialNumber.length!=18){
				
			}
			else{
				$("#credentialNumberMsg").html("");
			}
		}
		if (money == "") {
			$("#moneyMsg").html("预约金额不能为空");
			flag = false;
			return;
		}else if(parseInt($("#totalTip1").val()) == totalMy){
				if (money < minMoney) {
				$("#moneyMsg").html("预约金额小于最低金额");
				flag = false;
				return;
			}
		
		} else if (money > maxMoney){
			
			$("#moneyMsg").html("预约金额大于最大金额");
			flag = false;
			return;
		}else if(parseInt($("#totalTip1").val()) == totalMy){
			if ((money-minMoney)%increasingMoney != 0) {
				$("#moneyMsg").html("预约金额输入不正确");
				flag = false;
				return;
			}
		} else if(money%increasingMoney != 0){
			$("#moneyMsg").html("预约金额输入不正确");
			flag = false;
			return;
		}else if(parseInt($("#totalTip1").val()) < money){
			//alert("ss");
			$("#moneyMsg").html("您的可预约金额已上限");
			flag = false;
			return;
		}else {
			$("#moneyMsg").html("");
		
			
		}
		//alert(parseInt($("#totalTip1").val()));
		flag=true;
		
		return flag;
		
	/* 	if(!(userName == '' || $.trim(phone) != "" &&  !reg.test(phone) && email == "" && !reg.test(email) && credentialTypeId == "" && redentialNumber == "" && credentialNumber.length!=15 && credentialNumber.length!=18 && money == "" && money > maxMoney && (money-minMoney)%increasingMoney != 0 && parseInt($("#totalTip")).val()<money )){
			flag = true;
		} */
		
	}
	
	var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];    // 加权因子   
	var ValideCode = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ];            // 身份证验证位值.10代表X   
  
	/**  
	 * 判断身份证号码为18位时最后的验证位是否正确  
	 * @param a_idCard 身份证号码数组  
	 * @return  
	 */  
	function isTrueValidateCodeBy18IdCard(a_idCard) {   
	    var sum = 0;                             // 声明加权求和变量   
	    if (a_idCard[17].toLowerCase() == 'x') {   
	        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
	    }   
	    for ( var i = 0; i < 17; i++) {   
	        sum += Wi[i] * a_idCard[i];            // 加权求和   
	    }   
	    valCodePosition = sum % 11;                // 得到验证码所位置   
	    if (a_idCard[17] == ValideCode[valCodePosition]) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	}   
	/**  
	  * 验证18位数身份证号码中的生日是否是有效生日  
	  * @param idCard 18位书身份证字符串  
	  * @return  
	  */  
	function isValidityBrithBy18IdCard(idCard18){   
	    var year =  idCard18.substring(6,10);   
	    var month = idCard18.substring(10,12);   
	    var day = idCard18.substring(12,14);   
	    var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	    // 这里用getFullYear()获取年份，避免千年虫问题   
	    if(temp_date.getFullYear()!=parseFloat(year)   
	          ||temp_date.getMonth()!=parseFloat(month)-1   
	          ||temp_date.getDate()!=parseFloat(day)){   
	            return false;   
	    }else{   
	        return true;   
	    }   
	}   
	  /**  
	   * 验证15位数身份证号码中的生日是否是有效生日  
	   * @param idCard15 15位书身份证字符串  
	   * @return  
	   */  
	  function isValidityBrithBy15IdCard(idCard15){   
	      var year =  idCard15.substring(6,8);   
	      var month = idCard15.substring(8,10);   
	      var day = idCard15.substring(10,12);   
	      var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
	      // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
	      if(temp_date.getYear()!=parseFloat(year)   
	              ||temp_date.getMonth()!=parseFloat(month)-1   
	              ||temp_date.getDate()!=parseFloat(day)){   
	    	  $("#credentialNumberMsg").html("身份证号码输入有误！");
				flag = false;
				return;
	        }else{   
	        }   
	  }   
	//去掉字符串头尾空格   
	function trim(str) {   
	    return str.replace(/(^\s*)|(\s*$)/g, "");   
	}  
</script>

</body>



