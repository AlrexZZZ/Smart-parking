<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">


function getInfo(value,text){
	
	var platId=$("#platId").val();
    var str	="createCommunity?id="+value+"&platId="+platId;
    console.log(str);
	window.location.href = str;
}
</script>
<head>
    <meta charset="UTF-8">
    <title>文明生活</title>
    <link rel="stylesheet" type="text/css" href="/ump/cbdwccui/styles/mystyle.css">
    <script type="text/javascript" src="/ump/cbdwccui/js/jquery-1.9.1.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta content="telephone=no" name="format-detection" />
</head>
<body class="greybg">
	<div class="container">
		<!--container-->
		<div class="search_down">
			<!--search_down-->
			<input type="hidden" id="platId" value="${platId }"/>
			<select id="selectId" onchange="javascript:getInfo(this.value,this.options[this.selectedIndex].text);">
			<c:forEach items="${list }" var="br">
			<c:if test="${br.id != id }">
			<option id="id"  value="${br.id }" onchange="location.href='createCommunity?id=${br.id}'">${br.itemName }</option>
			</c:if>
			<c:if test="${br.id == id }">
			<option id="id" selected="selected" value="${br.id }" onchange="location.href='createCommunity?id=${br.id}'">${br.itemName }</option>
			</c:if>
			</c:forEach>
			</select>
		</div>
		<!--search_down end-->

		<div class="pic_word_list">
			<!--pic_word_list-->
			<div class="fir_pic">
				<img src='${wccAppartment.itemPick}' alt="">
				<div class="pic_blackdiv">${wccAppartment.itemIntro }</div>
			</div>

			<ul>
			<c:forEach items="${setWcc }" var="wcc">
				<li onclick="location.href='${wcc.url }?id=${wcc.id }&appId=${wcc.itemPk.id }'">
					<div class="word_list_L">${wcc.otherTitle }</div>
					<div class="word_list_R">
						<img src="${wcc.otherPic }" alt="">
					</div>
				</li>
			</c:forEach>
			</ul>

		</div>
		<!--pic_word_list end-->
    <div style="font-size: 12px;text-align: center;width: 100%;margin: 0px  auto;margin-top: 3px; ">交通银行广东省分行@版权所有</div>

	</div>
	<!--container end-->
</body>