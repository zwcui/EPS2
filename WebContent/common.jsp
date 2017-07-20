<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
	<%
		HttpSession s = request.getSession();
	
		String userID = request.getParameter("userID");
		System.out.println(userID);
	
		String sWebRootPath = request.getContextPath();
	%>
<head>
		<meta charset="utf-8" />
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="<%=sWebRootPath %>/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=sWebRootPath %>/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="<%=sWebRootPath %>/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- fonts -->

<!-- 		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" /> -->

		<!-- ace styles -->

		<link rel="stylesheet" href="<%=sWebRootPath %>/assets/css/ace.min.css" />
<!-- 		<link rel="stylesheet" href="assets/css/ace-rtl.min.css" /> -->
<!-- 		<link rel="stylesheet" href="assets/css/ace-skins.min.css" /> -->

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="<%=sWebRootPath %>/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="<%=sWebRootPath %>/assets/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="<%=sWebRootPath %>/assets/js/html5shiv.js"></script>
		<script src="<%=sWebRootPath %>/assets/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body>
		<div><jsp:include page="Common/top.jsp" flush="true" /></div>
		<div><jsp:include page="Common/main.jsp" flush="true" /></div>
		<!-- basic scripts -->
		<!--[if IE]>
		<script src="<%=sWebRootPath %>/assets/js/jquery-1.10.2.min.js"></script>
		<![endif]-->
		
		<!--[if !IE]><!--> 
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=sWebRootPath %>/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>
		<!--<![endif]-->
		
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=sWebRootPath %>/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		<script src="<%=sWebRootPath %>/assets/js/bootstrap.min.js"></script>
		<script src="<%=sWebRootPath %>/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->

		<script src="<%=sWebRootPath %>/assets/js/ace-elements.min.js"></script>
		<script src="<%=sWebRootPath %>/assets/js/ace.min.js"></script>
</body>
</html>