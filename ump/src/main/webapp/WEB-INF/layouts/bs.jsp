<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${material.title}</title>
		<!-- jQuery -->
		<script type="text/javascript" src="../dependents/jquery/jquery.min.js"></script>
		<!-- bootstrap -->
		<script type="text/javascript" src="../dependents/bootstrap/js/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="../dependents/bootstrap/css/bootstrap.min.css" />
		<!--[if lt IE 9]>
		    <script src="../dependents/bootstrap/plugins/ie/html5shiv.js"></script>
		    <script src="../dependents/bootstrap/plugins/ie/respond.js"></script>
		<![endif]-->
		<!--[if lt IE 8]>
		    <script src="../dependents/bootstrap/plugins/ie/json2.js"></script>
		<![endif]-->
		<!-- font-awesome -->
		<link rel="stylesheet" type="text/css" href="../dependents/fontAwesome/css/font-awesome.min.css" media="all" />
		<!-- dtGrid -->
		<script type="text/javascript" src="../ui/dtgrid/jquery.dtGrid.js"></script>
		<script type="text/javascript" src="../ui/dtgrid/i18n/en.js"></script>
		<link rel="stylesheet" type="text/css" href="../ui/dtgrid/jquery.dtGrid.css" />
		<!-- datePicker -->
		<script type="text/javascript" src="../dependents/datePicker/WdatePicker.js" defer="defer"></script>
		<link rel="stylesheet" type="text/css" href="../dependents/datePicker/skin/WdatePicker.css" />
		<link rel="stylesheet" type="text/css" href="../dependents/datePicker/skin/default/datepicker.css" />
</head>
<body>
	<tiles:insertAttribute name="body" />
</body>
</html>

