<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta charset='utf-8'>
<title>Dashboard</title>
</head>
<body class='main page'>
	<div id='wrapper'>
		<!-- Tools -->
		<section id='tools'>
		<ul class='breadcrumb' id='breadcrumb'>
			<li class='title'>生产计划</li>
			<li><a href="#">生产部</a></li>
			<li class='active'><a href="#">生产车间</a></li>
		</ul>
		<div id='toolbar'>
			<div class='btn-group'>
				<a class='btn' data-toggle='toolbar-tooltip' href='#'
					title='Building'> <i class='glyphicon glyphicon-building'></i>
				</a> <a class='btn' data-toggle='toolbar-tooltip' href='#'
					title='Laptop'> <i class='glyphicon glyphicon-laptop'></i>
				</a> <a class='btn' data-toggle='toolbar-tooltip' href='#'
					title='Calendar'> <i class='glyphicon glyphicon-calendar'></i>
					<span class='badge'>3</span>
				</a> <a class='btn' data-toggle='toolbar-tooltip' href='#' title='Lemon'>
					<i class='glyphicon glyphicon-lemon'></i>
				</a>
			</div>
			<div class='label label-danger'>预警</div>
			<div class='label label-info'>提示</div>
		</div>
		</section>
	</div>
	<!-- Footer -->
	<!-- Javascripts -->
	<script src="/js/jquery-1.12.4.js" type="text/javascript"></script>
	<script src="/js/jquery-ui.js" type="text/javascript"></script>
	<script src="/js/modernizr.min.js" type="text/javascript"></script>
	<script src="/js/application-985b892b.js" type="text/javascript"></script>
	<!-- Google Analytics -->
	<script>
		$(document).ready(function() {
			$('#zlight-nav').zlightMenu();
		});
	</script>
</body>
</html>
