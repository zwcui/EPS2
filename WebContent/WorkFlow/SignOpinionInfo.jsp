<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<%
	HttpSession s = request.getSession();
	String sWebRootPath = request.getContextPath();
	String sRightType = request.getParameter("RightType");
	
	String sTaskSerialNo = request.getParameter("TaskSerialNo");
	String sParentPageTitle = java.net.URLDecoder.decode(request.getParameter("ParentPageTitle"),"UTF-8");
	sParentPageTitle = java.net.URLDecoder.decode(sParentPageTitle,"UTF-8");
	if(sRightType == null) sRightType = "All";
	if(sTaskSerialNo == null) sTaskSerialNo = "";
	if(sParentPageTitle == null) sParentPageTitle = "";
	
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
			<li class="active"><%=sParentPageTitle %></li>
			<li class="active">签署意见</li>
		</ul>
		<!-- .breadcrumb -->
	</div>

	<div class="page-content" id="pagecontent" style="height:400px">
		<div class='panel panel-default grid' >
			<div class="modal-header no-padding">
					<div class="table-header">
						签署意见
					</div>
				</div>
				<form id="flowOpinionForm" action="" method="post">
					<div class="radio" >
						<label> <input name="phaseAction" type="radio" value="01"
							class="ace" /> <span class="lbl"> 同意</span>
						</label>
						<label> <input name="phaseAction" type="radio" value="02"
							class="ace" /> <span class="lbl"> 拒绝</span>
						</label>
						<%
						if("0003,0004".contains((String)s.getAttribute("orgID"))){
						%>	
						<label> <input name="phaseAction" type="radio" value="03"
							class="ace" /> <span class="lbl"> 退回销售</span>
						</label>
						<%} %>	
					</div>
					<div >
						<label class="sr-only" for="phaseOpinion">意见详情</label>
						<textarea class="form-control" id="phaseOpinion" name="phaseOpinion" placeholder="意见详情(不超过120个字)"></textarea>
					</div>
					<div>
						<label class="sr-only" for="taskSerialNo">流程编号</label>
						<input type="hidden" id="taskSerialNo" name="taskSerialNo"  />
					</div>
				</form>
			</div>
		
		<div class="modal-footer no-margin-top">
				<button type="button" class="btn btn-sm btn-primary pull-left" onclick="self.close()"><i class="icon-remove"></i>
																		取消</button>
					<button type="button" class="btn btn-sm btn-success pull-left" onclick="SignOpinionSave('#flowOpinionForm')"><i class="icon-save"></i>
																		保存</button>
			</div>
	</div>
	<!-- /.page-content -->

	<!-- inline scripts related to this page -->
	
	<script>
	var b = new Base64(); 
	var opinionnfoDataUrl = "${pageContext.request.contextPath}/ShowCurrentFlowOpinion";
	var opinionInfo_data;
	var sTaskSerialNo = "";
	var sRightType = "";

	function initOpinionInfo(){
		$("#taskSerialNo").val(sTaskSerialNo);
		var data = null;
		data = {
			taskSerialNo : sTaskSerialNo,
			modelName:"FlowOpinion"
		}
		$.ajax({
			url : opinionnfoDataUrl,
			dataType : 'json',
			type : 'POST',
			data : data,
			success : function(response) {
				opinionInfo_data = response.data;
				loadData(opinionInfo_data[0]);
			},
			error:function(xhr,errorText,errorType){
				alert("意见详情加载失败！");
			}
			})
	}
			
	<!--保存签署意见  - start-->
	function SignOpinionSave(form_selector){
		var phaseOpinion = $("#phaseOpinion").val();
		var taskSerialNo = $("#taskSerialNo").val();
		var phaseAction = $("input[name='phaseAction']:checked").val();
		if(typeof(taskSerialNo) == "undefined" || taskSerialNo.length == 0){
			alert("未加载流程编号，无法保存意见");
			return;
		}
		if(typeof(phaseAction) == "undefined" || phaseAction.length == 0){
			alert("请选择处理意见!");
			return;
		}
//			if(phaseAction == "2" && (typeof(phaseOpinion) == "undefined" || phaseOpinion.length == 0)){
		if(typeof(phaseOpinion) == "undefined" || phaseOpinion.length == 0){
			alert("请填写意见详情！");
			return;
		}
		var url = '${pageContext.request.contextPath}/UpdateFlowOpinion';
		var data = {
			taskSerialNo : taskSerialNo,
			phaseAction : phaseAction,
			phaseOpinion : phaseOpinion,
		}
		$.ajax({
			url : url,
			dataType : 'json',
			type : 'POST',
			data : data,
			success : function(response) {
				var result = response.result;
				if (typeof (result) == "undefined"
						|| result.length == 0) {
					result = "保存意见失败！";
				} else {
					result = result.split("@")[1];
				}
				alert(result);
			},
			error:function(xhr,errorText,errorType){
				alert("保存意见失败！");
			},
		})
//			var sReutrn = $(form_selector).submit();
	}
	<!--保存签署意见  - end-->
	
	<!--签署意见并提交  - start-->
	function SignOpinionSubmit(form_selector){
		var phaseOpinion = $("#phaseOpinion").val();
		var taskSerialNo = $("#taskSerialNo").val();
		var phaseAction = $("input[name='phaseAction']:checked").val();
		if(typeof(taskSerialNo) == "undefined" || taskSerialNo.length == 0){
			alert("未加载流程编号，无法保存意见");
			return;
		}
		if(typeof(phaseAction) == "undefined" || phaseAction.length == 0){
			alert("请选择处理意见!");
			return;
		}
		if(phaseAction == "2" && (typeof(phaseOpinion) == "undefined" || phaseOpinion.length == 0)){
			alert("请填写否决理由！");
			return;
		}
		$(form_selector).submit();
		return;
	}
	<!--签署意见并提交 - end-->
	
	$(document).ready(function() { 
		sTaskSerialNo = b.decode("<%=sTaskSerialNo%>");
		sRightType = "<%=sRightType %>";
		initOpinionInfo();
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
