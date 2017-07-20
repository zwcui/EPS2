<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
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
			<li class="active">个人资料</li>
		</ul>
		<!-- .breadcrumb -->
	</div>

	<div class="page-content" id="pagecontent" >
						<div class="row">
							<div class="col-xs-12">
		<div class='panel panel-default grid' >
			<div class="modal-header no-padding">
					<div class="table-header">
						个人资料设置
					</div>
				</div>
				<form class="form-horizontal" role="form" id="userinfoform" action="" onsubmit = "return UserInfoSave()" method="post">
					<div class="space-12"></div>
					<div class="space-12"></div>
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="userID"> 账号 </label>
	
						<div class="col-sm-9">
							<input readonly=""  type="text" id="userID" name="userID" placeholder="" class="col-xs-10 col-sm-5" />
						</div>
					</div>
	
					<div class="space-2"></div>
	
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="userName"> 姓名 </label>
	
						<div class="col-sm-9">
							<input type="text" id="userName" name="userName" placeholder="必填项" class="col-xs-10 col-sm-5" />
						</div>
					</div>
	
					<div class="space-2"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="phone"> 手机 </label>
	
						<div class="col-sm-9">
							<input type="text" id="phone" name="phone" placeholder="" class="col-xs-10 col-sm-5" />
						</div>
					</div>
	
					<div class="space-2"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="telePhone"> 电话</label>
	
						<div class="col-sm-9">
							<input type="text" id="telePhone" name="telePhone" placeholder="" class="col-xs-10 col-sm-5" />
						</div>
					</div>
	
					<div class="space-12"></div>
					
					<div class="form-group">
						<label class="col-sm-3 control-label no-padding-right" for="mail"> 邮箱 </label>
	
						<div class="col-sm-9">
							<input type="text" id="mail" name="mail" placeholder="" class="col-xs-10 col-sm-5" />
						</div>
					</div>
	
					<div class="clearfix form-actions">
						<div class="col-md-offset-3 col-md-9">
							<button class="btn btn-info" type="submit">
								<i class="icon-ok bigger-110"></i>
								保存
							</button>
	
							&nbsp; &nbsp; &nbsp;
							<button class="btn" type="reset">
								<i class="icon-undo bigger-110"></i>
								清除
							</button>
						</div>
					</div>
			</div>
	</div>
	</div>
	</div>
	<!-- /.page-content -->

	<!-- inline scripts related to this page -->
	
	<script>
	var b = new Base64(); 
	var userinfoDataUrl = "${pageContext.request.contextPath}/ShowUserByUserID";
	var userinfo_data;
	var sUserID = "<%=sUserID%>";

	function initUserInfo(){
		var data = null;
		data = {
			userID : sUserID,
			modelName:"UserList"
		}
		$.ajax({
			url : userinfoDataUrl,
			dataType : 'json',
			type : 'POST',
			data : data,
			success : function(response) {
				userinfo_data = response.data;
				loadData(userinfo_data[0]);
			},
			error:function(xhr,errorText,errorType){
				alert("用户详情加载失败！");
			}
			})
	}
			
	<!--保存用户详情  - start-->
	function UserInfoSave(){
		var form_selector= "#userinfoform";
		var userName = $("#userName").val();
		var phone = $("#phone").val();
		var telePhone = $("#telePhone").val();
		var mail = $("#mail").val();
		var userdata = userinfo_data[0];
		userdata.userName = userName;
		userdata.phone = phone;
		userdata.telePhone = telePhone;
		userdata.mail = mail;
		var options = {
				   url:'${pageContext.request.contextPath}/UpdateUser',
				   data:{row:JSON.stringify(userdata)} ,
				   success:function(response){
						response = jQuery.parseJSON(response);
						var result = response.result;
						if (typeof (result) == "undefined"
								|| result.length == 0) {
							result = "修改信息失败！";
						} else {
							result = result.split("@")[1];
						}
						alert(result);
						initUserInfo();
						return false;
					},
					error:function(xhr,errorText,errorType){
						alert("修改信息失败！");
						initUserInfo(); 
						return false;
					},
			    };
					
				$(form_selector).ajaxSubmit(options);
				return false;
	}
	<!--保存用户详情  - end-->
	
	$(document).ready(function() { 
		initUserInfo();
	});
	
	function loadData(jsonStr){
		var obj = jsonStr;
		var key,value,tagName,type,arr;
		for(x in obj){
			key = x;
			value = obj[x];
			$("[name='"+key+"'],[name='"+key+"[]']").each(function(){
				tagName = $(this)[0].tagName;
				type = $(this).attr('type');
				if(tagName=='INPUT'){
					if(type=='radio'){
						$(this).attr('checked',$(this).val()==value);
					}else if(type=='checkbox'){
						arr = value.split(',');
						for(var i =0;i<arr.length;i++){
							if($(this).val()==arr[i]){
								$(this).attr('checked',true);
								break;
							}
						}
					}else{
						$(this).val(value);
					}
				}else if(tagName=='SELECT' || tagName=='TEXTAREA'){
					$(this).val(value);
				}
				
			});
		}
	}
	</script>
</body>
</html>
