<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String sWebRootPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath~~~~"+basePath);
System.out.println("path~~~~"+path);
%>
<html lang="zh-cn">
<head>

    <title>Sign in</title>
    <meta content='lab2023' name='author'>
    <meta content='' name='description'>
    <meta content='' name='keywords'>
    
	<link rel=stylesheet type=text/css  href="<%=sWebRootPath %>/assets/css/bootstrap.css" rel="stylesheet" />
	<link rel=stylesheet type=text/css  href="<%=sWebRootPath %>/assets/css/font-awesome.min.css" />
	
<%-- 	<link rel=stylesheet type=text/css  href="<%=sWebRootPath %>/bootstrap-3.3.6-dist/css/bootstrap-theme.css" rel="stylesheet"> --%>
	<link rel=stylesheet type=text/css  href="<%=sWebRootPath %>/assets/css/bootstrap-table.css" rel="stylesheet">
    <link rel=stylesheet type=text/css  href="<%=sWebRootPath %>/css/application-a07755f5.css" rel="stylesheet" type="text/css" />
    <link rel=stylesheet type=text/css  href="<%=sWebRootPath %>/css/signin.css" rel="stylesheet">
  </head>
  <body class='login'>
    <div class='wrapper'>
      <div class='row'>
        <div class='col-lg-12'>
          <div class='brand text-center'>
            <h1>
              <div>
              <img src="images/test/signhead7.png" alt="" class="logo-icon ">
              </div>
            </h1>
          </div>
        </div>
      </div>
      <div class='row'>
        <div class='col-lg-12'>
          <form name="loginform" id="loginform" class="form-signin" action="login.do" method="post" >
            <fieldset class='text-center'>
              <legend>欢迎访问，请登录</legend>
              <div class='form-group'>
                <input class='form-control' placeholder='账号' type='text' name="userid" id="userid" required autofocus>
              </div>
              <div class='form-group'>
                <input class='form-control' placeholder='密码' type='password' name="password" id="password" required>
              </div>
              <div class='text-center'>
                <div class='checkbox'>
                  <label>
                    <input type='checkbox'>
                    	记住我
                  </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
                <br>
<!--                 <a href="forgot_password.html">忘记密码?</a> -->
              </div>
            </fieldset>
          </form>
        </div>
      </div>
      
<%--       <a href="${pageContext.request.contextPath}/login.do">User Login</a> --%>
      
    </div>
	<!--[if IE]>
	<script src="<%=sWebRootPath %>/assets/js/jquery-1.10.2.min.js"></script>
	<![endif]-->
	
	<!--[if !IE]><!--> 
	<script type="text/javascript">
		window.jQuery || document.write("<script src='<%=sWebRootPath %>/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
	</script>
	<!--<![endif]-->
	<script src="<%=sWebRootPath %>/assets/js/bootstrap.min.js"></script>
<!-- 	<script src="/js/jquery-1.12.4.js"></script> -->
<%-- 	<script src="<%=sWebRootPath %>/bootstrap-3.3.6-dist/js/bootstrap.js"></script> --%>
	<script src="<%=sWebRootPath %>/assets/js/bootstrap-table.js"></script>
	<script type="text/javascript" language="javascript" src="<%=sWebRootPath %>/assets/js/jquery.form.js"></script>
	<script type="text/javascript">
	function doSubmit(){
		var form_selector = "#loginform";
		var data = $(form_selector).serialize();
		var options = {
				   url:"${pageContext.request.contextPath}/login.do",
				   data:data ,
				   success:function(response){
						alert("success");
						alert(response);
// 						response = jQuery.parseJSON(response);
// 						var result = response.result;
// 						if (result == null || typeof (result) == "undefined"
// 								|| result.length == 0) {
// 							alert("登录失败！");
// 						}else if(result.split("@")[0] == "true") {
// 							window.location = "welcome.jsp";
// 						}
// 						else{
// 							result = result.split("@")[1];
// 							alert(result);
// 						}
					},
					error:function(xhr,errorText,errorType){
						alert("登录失败！");
					},
			    };
					
				$(form_selector).ajaxSubmit(options);
				return false;
				
// 			$.ajax({
// 		          url: "${pageContext.request.contextPath}/login.do",
// 		          type: "POST",
// 		          dataType: "json",
// 		          data: data,
// 		          success: function(response) {
// 		            alert("success");
// 		          },
// 		          error: function() {
// 		            alert("error");
// 		          }
// 		        });		
				
				
				
	}
	</script>
</body>
</html>