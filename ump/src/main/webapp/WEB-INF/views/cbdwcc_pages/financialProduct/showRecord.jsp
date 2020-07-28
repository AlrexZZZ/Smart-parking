<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
    <meta charset="UTF-8">
    <title>理财产品</title>
    <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
	<meta content="telephone=no" name="format-detection" />
	<script type="text/javascript">
		$(document).ready(function() {
			showFinancial();
		});
	
		function showFinancial() {
	        var product = document.getElementById("financialProduct");
			var productId = "product_"+$('#financialProduct option:selected').val();
		//	alert(productId);
			var productText =  $("#financialProduct").find("option:selected").text();
			for (var i = 0; i < product.options.length; i++) {
				document.getElementById("product_"+product.options[i].value).style.display = "none";
			}
			if (productText != "请选择") {
				document.getElementById(productId).style.display = "block";
			}
		}
		
		function go() {
			var saleBeginDate = $("#saleBeginDate").val();
			var saleEndDate = $("#saleEndDate").val();
			var myDate = new Date();
			if (myDate < saleBeginDate || myDate > saleEndDate) {
				alert("此理财产品不在预约期内");
				return false;
			}
			var financialProductId = $('#financialProduct option:selected').val();
			var friendId = $("#friendId").val();
			var platId = $("#platId").val();
			var url = '/ump/pageFinancialProduct?form&financialProductId='+financialProductId+'&friendId='+friendId+'&platId='+platId;
			location.href = url;
		}
	</script>
</head>
<body class="withbg">
	<input type="hidden" id="friendId" name="friendId" value="${friendId}" />
	<input type="hidden" id="platId" name="platId" value="${platId}" />
    <div class="container"><!--container-->
      <div class="search_down_lc"><!--search_down-->
     	<font color="#990033"> 请选择您要预约的产品：</font>
          <select id="financialProduct" style="font-size:16px;width: 150px;" onchange="showFinancial();">
          	<c:forEach items="${financialProducts}" var="data" varStatus="status">
          		<option value="${data.id}" style="font-size:16px;">${data.financialName}</option>
          	</c:forEach>
          </select>
      </div> <!--search_down end-->
	<c:forEach items="${financialProducts}" var="data" varStatus="status">
		<input type="hidden" id="saleBeginDate" value="${data.saleBeginDate}" />
		<input type="hidden" id="saleEndDate" value="${data.saleEndDate}" />
		
		<div  id="product_${data.id}" style="">
      <div class="detail_img">
        <img src="${data.themeImage}" alt="">
      </div>         
      <div class="detail_article">
				<div>
					风险等级：${data.riskLevel}<BR>
					产品类型：${data.productType}<BR>
					销售起日：${fn:substring(data.saleBeginDate, 0, 10)}<BR>
					销售止日：${fn:substring(data.saleEndDate, 0, 10)}<BR>
					起息日：${fn:substring(data.valueDate, 0, 10)}<BR>
					到期日：${fn:substring(data.endDate, 0, 10)}<BR>
					到账日：${fn:substring(data.arrivalDate, 0, 10)}<BR>
					投资期限（天）：${data.investmentHorizon}<BR>
					预期收益率：${data.expectedYield}<BR>
					产品起点金额（元）：${data.minMoney}<BR>
					产品上限金额（元）：${data.maxMoney}<BR>
					递增金额（元）：${data.increasingMoney}<BR>
					<!-- 微信预约总额（元）：${data.totalMoney}<BR> -->
				</div>
      </div> 
      </div>
	</c:forEach>
      <div class="detail_back">
        <input type="button" value="预  约" onclick="go();">  
      </div>
    </div><!--container end-->
</body>



