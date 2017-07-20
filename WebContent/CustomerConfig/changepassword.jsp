<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<%
	HttpSession s = request.getSession();
	String sWebRootPath = request.getContextPath();
	String sUserID = (String) s.getAttribute("userID");
%>
<head>
<jsp:include page="../Common/listCommon.jsp" flush="true" />
</head>
<body class='main page' >
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li class="active">设置</li>
			<li class="active">修改密码</li>
		</ul>
		<!-- .breadcrumb -->
	</div>

	<div class="page-content" id="pagecontent" >
						<div class="row">
							<div class="col-xs-12">
		<div class='panel panel-default grid' >
			<div class="modal-header no-padding">
					<div class="table-header">
						修改密码
					</div>
				</div>
				<form class="form-horizontal" id="changepasswordform" role="form" action="" method="post" onsubmit="return doChangePassword()">
					<div class="space-12"></div>
					<div class="space-12"></div>
					<div class="form-group">
						<label class="hidden" for="userID"> 用户编号 </label>
	
						<div class="col-sm-9">
							<input class="col-xs-10 col-sm-5" type="hidden" id="userID" name="userID" value = "<%=sUserID%>"/>
						</div>
					</div>
					<div class="space-12"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="oldpassword"> 原密码 <font class = "red">*</font></label>
	
						<div class="col-sm-9">
							<input class="col-xs-10 col-sm-5" type="password" id="oldpassword"  name="oldpassword" placeholder="请输入原密码" required autofocus/>
						</div>
					</div>
	
					<div class="space-4"></div>
	
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="newpassword"> 新密码 <font class = "red" >*</font></label>
	
						<div class="col-sm-9">
							<input type="password" id="newpassword" name="newpassword" placeholder="请输入新密码" onblur="confirmNewPassword()"  class="col-xs-10 col-sm-5" required/>
						</div>
					</div>
	
					<div class="space-4"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="confirmpassword"> 确认密码 <font class = "red">*</font></label>
	
						<div class="col-sm-9">
							<input type="password" id="confirmpassword" name="confirmpassword"  placeholder="请输入确认密码" class="col-xs-10 col-sm-5" onblur="confirmNewPassword()" required/>
						</div>
						<span class="help-inline col-xs-12 col-sm-7">
												<span class="middle red hidden" id="confirmmessage">两次输入密码不一致！</span>
											</span>
					</div>
	
					<div class="space-4"></div>
					
					<div class="space-12"></div>
					<div class="space-12"></div>
					<div class="space-12"></div>
					<div class="space-12"></div>
					
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit" >
								<i class="icon-ok bigger-110"></i>
								确定
							</button>
	
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset" id = "reset">
								<i class="icon-undo bigger-110"></i>
								清空
							</button>
						</div>
					</div>
					</form>
			</div>
	</div>
	</div>
	</div>
	<!-- /.page-content -->

	<!-- inline scripts related to this page -->
	<script type="text/javascript">
	
	function confirmNewPassword(){
		var newpassword = $("#newpassword").val();
		var confirmpassword = $("#confirmpassword").val();
		if(newpassword != null && confirmpassword != null && typeof(newpassword) != "undefined" && typeof(confirmpassword) != "undefined" && confirmpassword.length > 0 && newpassword.length>0 &&  newpassword == confirmpassword){
			$("#confirmmessage").addClass("hidden");
			return true;
		}else{
			$("#confirmmessage").removeClass("hidden");
			return false;
		}
	}
	
	function doChangePassword(){
		if(confirmNewPassword() == false) return;
		var form_selector = "#changepasswordform";
		var data = $(form_selector).serialize();
		var options = {
				   url:'${pageContext.request.contextPath}/UpdateUserPassword',
				   data:data ,
				   success:function(response){
						response = jQuery.parseJSON(response);
						var result = response.result;
						if (typeof (result) == "undefined"
								|| result.length == 0) {
							result = "修改密码失败！";
						} else {
							result = result.split("@")[1];
						}
						alert(result + " 请重新登录！");
						window.open("${pageContext.request.contextPath}/Logon.jsp","_top","");
// 						$("#reset").click(); 
						return false;
					},
					error:function(xhr,errorText,errorType){
						alert("修改密码失败！");
						$("#reset").click();  
						return false;
					},
			    };
					
				$(form_selector).ajaxSubmit(options);
				return false;
	}
	</script>
</body>
</html>
