<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<jsp:include page="common.jsp" flush="true" />
</head>

<body>
</body>
<script type="text/javascript">
$(document).ready(function () {
	$('ul.submenu > li').click(function (e) {
	$('ul.submenu > li').removeClass('active');
	$(this).addClass('active');

	});
// 	$("#menu_i_0101").trigger("click"); 
	$('ul.nav >:first-child > ul.submenu >:first-child > a >i').trigger("click"); 
});
</script>
</html>

