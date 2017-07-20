<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<meta charset='utf-8'>
<title>Dashboard</title>
</head>
<body class='main page'>
	<%
		HttpSession s = request.getSession();
		String sWebRootPath = request.getContextPath();
	%>
	<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
		id="ace-settings-btn">
		<i class="icon-cog bigger-150"></i>
	</div>

	<div class="ace-settings-box" id="ace-settings-box">
		<div>
			<div class="pull-left">
				<select id="skin-colorpicker" class="hide">
					<option data-skin="default" value="#438EB9">#438EB9</option>
					<option data-skin="skin-1" value="#222A2D">#222A2D</option>
					<option data-skin="skin-2" value="#C6487E">#C6487E</option>
					<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
				</select>
			</div>
			<span>&nbsp; 选择皮肤</span>
		</div>

		<div>
			<input type="checkbox" class="ace ace-checkbox-2"
				id="ace-settings-navbar" /> <label class="lbl"
				for="ace-settings-navbar"> 固定导航条</label>
		</div>

		<div>
			<input type="checkbox" class="ace ace-checkbox-2"
				id="ace-settings-sidebar" /> <label class="lbl"
				for="ace-settings-sidebar"> 固定滑动条</label>
		</div>

		<div>
			<input type="checkbox" class="ace ace-checkbox-2"
				id="ace-settings-breadcrumbs" /> <label class="lbl"
				for="ace-settings-breadcrumbs">固定面包屑</label>
		</div>

		<div>
			<input type="checkbox" class="ace ace-checkbox-2"
				id="ace-settings-rtl" /> <label class="lbl" for="ace-settings-rtl">切换到左边</label>
		</div>

		<div>
			<input type="checkbox" class="ace ace-checkbox-2"
				id="ace-settings-add-container" /> <label class="lbl"
				for="ace-settings-add-container"> 切换窄屏 <b></b>
			</label>
		</div>
	</div>
	</div>
</body>
</html>
