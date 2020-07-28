
<%
	response.setContentType("text/html;charset=utf-8");
%>
<html>
<style>
@IMPORT url("/ump/styles/searchDialog.css");
@IMPORT url("/ump/styles/url.css");
</style>
<script type="text/javascript">
       $(document).ready(function(){
    	   $("#approve_window").toggle();
    	   $("#fade").toggle();
    	   
       });  
</script>
<DIV style="display: none; width: 400px;" class="search"
	id="approve_window" align="center">
	<div class="close">
		<img src="/ump/images/close.png" width="24" height="24"
			onclick="javascript:closePage('approve_add');"
			style="cursor: pointer;" />
	</div>
	<DIV class="mtitle">${info}</DIV>
	<DIV class="grid">
		<DIV class="row"></DIV>
		<DIV class="row">
			<DIV class="span">${msg}</DIV>
		</DIV>
		<div class="btnCenter">
				<input type="button" class="info" value="${btn}"
					onclick="window.close();"></input>
		</div>
	</DIV>
</DIV>
<div id="fade" class="black_overlay" />
</html>