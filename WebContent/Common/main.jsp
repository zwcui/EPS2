<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String sWebRootPath = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ sWebRootPath + "/";
%>
<html lang="zh-cn">
<head>
</head>

<body onload="IFrameResize()">
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="main-container-inner">
			<jsp:include page="menu.jsp" flush="true" />
			
			<div id="PageContent" class="main-content">
				<iframe name="ContentIframe" ID="ContentIframe" frameborder="0" height="100%" width="100%" ></iframe>
<%-- 				<jsp:include page="content.jsp" flush="true" /> --%>
			</div>
			<!-- /.main-content -->

			<div class="ace-settings-container" id="ace-settings-container">
<%-- 				<jsp:include page="settings.jsp" flush="true" /> --%>
<!-- 			/#ace-settings-container -->
		</div>
		<!-- /.main-container-inner -->

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="icon-double-angle-up icon-only bigger-110"></i>
		</a>
	</div>
	<!-- /.main-container -->

	<script type="text/javascript">
	function IFrameResize() {
		//alert(this.document.body.scrollHeight); //弹出当前页面的高度 
		var obj = parent.document.getElementById("ContentIframe"); //取得父页面IFrame对象 
		var nav = parent.document.getElementById("navbar"); //取得父页面navbar对象 
		var side = parent.document.getElementById("sidebar"); //取得父页面navbar对象 
// 		obj.height = this.document.body.scrollHeight - nav.scrollHeight; //调整父页面中IFrame的高度为此页面的高度 
		obj.height = $(window).height()- nav.scrollHeight -5;
	}
	
	function reinitIframe(){
		var iframe = document.getElementById("ContentIframe");
		try{
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.max(bHeight, dHeight);
			iframe.height =  height;
// 			iframe.height =  1320;
		}catch (ex){}
		}
// 	window.setInterval("reinitIframe()", 200);
	</script>
</body>
</html>

