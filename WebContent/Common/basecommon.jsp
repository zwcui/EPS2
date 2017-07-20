<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset='utf-8'>
<title>This is a jsp for common links and scripts</title>
<link href="../bootstrap-3.3.6-dist/css/bootstrap.css" rel="stylesheet">
<link href="../bootstrap-3.3.6-dist/css/bootstrap-theme.css"
	rel="stylesheet">
<link href="../css/application-a07755f5.css" rel="stylesheet"
	type="text/css" />
<link href="../css/font-awesome.min.css" rel="stylesheet"
	type="text/css" />
<link href="../css/bootstrap-table.css" rel="stylesheet">
</head>
<body>
	<%
		HttpSession s = request.getSession();
		String sWebRootPath = request.getContextPath();
	%>
	<script src="../js/jquery-1.12.4.js"></script>
	<script src="../bootstrap-3.3.6-dist/js/bootstrap.js"></script>
	<script src="../js/bootstrap-table.js"></script>
	<script src="../js/bootstrap-table-editable.js"></script>
	<script src="../js/bootstrap-editable.js"></script>
	<script src="../js/mindmup-editabletable.js"></script>
	<script src="../js/jquery.zlight.menu.1.0.min.js"></script>
</body>
</html>