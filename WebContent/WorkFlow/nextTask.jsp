<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<%
	String sRightType = request.getParameter("RightType");
	HttpSession s = request.getSession();
	String sCurOrgID = (String)s.getAttribute("orgID");
	
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
			<li class="active">选择下一步审批人</li>
		</ul>
		<!-- .breadcrumb -->
	</div>

	<div class="page-content" id="pagecontent">
		<div class='panel panel-default grid'>
				<table id="nextTaskList" class="table table-striped table-bordered table-hover no-margin-bottom no-border-top"></table>
				<div id="nextTaskList-pager"></div>

				<script type="text/javascript">
					var $path_base = "/";//this will be used in gritter alerts containing images
				</script>
		</div>
		
		<div class="modal-footer no-margin-top">
				<button type="button" class="btn btn-sm btn-primary pull-left" data-dismiss="modal" onclick="self.close()"><i class="icon-reply"></i>
				返回</button>
				<button type="button" class="btn btn-sm btn-success pull-left" data-dismiss="modal" onclick="doCommitTask()"><i class="icon-ok"></i>
																		确定</button>
			</div>
	</div>
	<!-- /.page-content -->

	<!-- inline scripts related to this page -->
	
	<script>
	var b = new Base64(); 
	var nextTaskDataUrl = "${pageContext.request.contextPath}/ShowNextTaskUserAndOrg";
	var nextTask_data;
	var sTaskSerialNo = "";
	var sRightType = "";
	function initNextTask() {
		var data= {
			taskSerialNo : sTaskSerialNo,
			modelName:'FlowUser',
		}
		$
				.ajax({
					url : nextTaskDataUrl,
					dataType : 'json',
					type : 'POST',
					data : data,
					success : function(response) {
						var result = response.result;
						if(typeof(result)!="undefined" && result.indexOf("@") > 0 && result.split("@")[0]=="false"){
							alert(result.split("@")[1]);
							return;
// 							self.close();
						}
						var nextTaskList_data = response.data;
						var colnames = response.colNames;
						var colmodel = modelPreTreat(response.column);
						var lastSel;
						var grid_selector = "#nextTaskList";
						var pager_selector = "#nextTaskList-pager";
						jQuery(grid_selector).jqGrid({
// 							direction: "rtl",
							data: nextTaskList_data,
							datatype: "local",
							height: 'auto',
							colNames:colnames,
							colModel:colmodel,
					
							viewrecords : true,
							rowNum : rowNum,
							rownumbers: true,
							rowList:[ 10, 20, 30,40,50,60,100,150 ],
							pager : pager_selector,
							altRows: true,
// 							toppager: true,
// 							multiselect: true,
							//multikey: "ctrlKey",
// 					        multiboxonly: false,
					
							loadComplete : function() {
								var table = this;
								setTimeout(function(){
									styleCheckbox(table);
									
									updateActionIcons(table);
									updatePagerIcons(table);
									enableTooltips(table);
									centerGrid();
								}, 0);
							},
							editurl: 'clientArray',//nothing is saved
							caption: "审批人列表",
							
							autowidth: true,
							shrinkToFit:false,
							autoScroll: false

						});
						//enable search/filter toolbar
// 						jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})
					
						//switch element when editing inline
						function aceSwitch( cellvalue, options, cell ) {
							setTimeout(function(){
								$(cell) .find('input[type=checkbox]')
										.wrap('<label class="inline" />')
									.addClass('ace ace-switch ace-switch-5')
									.after('<span class="lbl"></span>');
							}, 0);
						}
						//enable datepicker
						function pickDate( cellvalue, options, cell ) {
							setTimeout(function(){
								$(cell) .find('input[type=text]')
										.datepicker({format:'yyyy-mm-dd' , autoclose:true}); 
							}, 0);
						}
					
						function datePick(elem) 
						{ 
						   jQuery(elem).datepicker({
								format : 'yyyy-mm-dd',
								autoclose : true
							}); 
						}
						
						function modelPreTreat(colModel) {
							var cellLenth = colModel.length;
							for (var i = 0; i < cellLenth; i++) {
								if (colModel[i].unformat == "pickDate") {
									colModel[i].unformat = pickDate;
								}
								if (colModel[i].edittype == "select") {
									colModel[i].stype = 'select';
									colModel[i].searchoptions = colModel[i].editoptions;
									colModel[i].searchoptions.sopt = ['eq','ne'];
								}
								else if (colModel[i].sorttype == "date") {
									colModel[i].searchoptions = {dataInit:datePick,sopt:['eq','le','ge']};
								}
								else{
									colModel[i].searchoptions = {sopt:['eq','cn']};
								}
							}
							return colModel;
						}
						
						//navButtons
						jQuery(grid_selector).jqGrid('navGrid',pager_selector,
							{ 	//navbar options
								edit: false,
								editicon : 'icon-pencil blue',
								add: false,
								addicon : 'icon-plus-sign purple',
								del: false,
								delicon : 'icon-trash red',
								search: true,
								searchicon : 'icon-search orange',
								refresh: false,
								refreshicon : 'icon-refresh green',
								view: false,
								viewicon : 'icon-zoom-in grey',
							},
							{
								//edit record form
// 								closeAfterEdit: true,
								recreateForm: true,
								beforeShowForm : function(e) {
									var form = $(e[0]);
									form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
									style_edit_form(form);
								},
							},
							{
								//new record form
								closeAfterAdd: true,
								recreateForm: true,
								viewPagerButtons: false,
								beforeShowForm : function(e) {
									var form = $(e[0]);
									form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
									style_edit_form(form);
								},
							},
							{
								//delete record form
								recreateForm: true,
								beforeShowForm : function(e) {
									var form = $(e[0]);
									if(form.data('styled')) return false;
									
									form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
									style_delete_form(form);
									
									form.data('styled', true);
								},
								onClick : function(e) {
									alert(1);
								}
							},
							{
								//search form
								recreateForm: true,
								afterShowSearch: function(e){
									var form = $(e[0]);
									form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
									style_search_form(form);
								},
								afterRedraw: function(){
									style_search_filters($(this));
								}
								,
								multipleSearch: true,
								multipleGroup:true,
// 								showQuery: true
							},
							{
								//view record form
								recreateForm: true,
								beforeShowForm: function(e){
									var form = $(e[0]);
									form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
								}
							}
						)
						
						function style_edit_form(form) {
							//enable datepicker on "sdate" field and switches for "stock" field
							form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
								.end().find('input[name=stock]')
									  .addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
					
							//update buttons classes
							var buttons = form.next().find('.EditButton .fm-button');
							buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
							buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
							buttons.eq(1).prepend('<i class="icon-remove"></i>')
							
							buttons = form.next().find('.navButton a');
							buttons.find('.ui-icon').remove();
							buttons.eq(0).append('<i class="icon-chevron-left"></i>');
							buttons.eq(1).append('<i class="icon-chevron-right"></i>');		
						}
					
						function style_delete_form(form) {
							var buttons = form.next().find('.EditButton .fm-button');
							buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
							buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
							buttons.eq(1).prepend('<i class="icon-remove"></i>')
						}
						
						function style_search_filters(form) {
							form.find('.delete-rule').val('X');
							form.find('.add-rule').addClass('btn btn-xs btn-primary');
							form.find('.add-group').addClass('btn btn-xs btn-success');
							form.find('.delete-group').addClass('btn btn-xs btn-danger');
						}
						function style_search_form(form) {
							var dialog = form.closest('.ui-jqdialog');
							var buttons = dialog.find('.EditTable')
							buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
							buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
							buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
						}
						
						function beforeDeleteCallback(e) {
							var form = $(e[0]);
							if(form.data('styled')) return false;
							
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_delete_form(form);
							
							form.data('styled', true);
						}
						
						function beforeEditCallback(e) {
							var form = $(e[0]);
							form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
							style_edit_form(form);
						}
					
						//it causes some flicker when reloading or navigating grid
						//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
						//or go back to default browser checkbox styles for the grid
						function styleCheckbox(table) {
						/**
							$(table).find('input:checkbox').addClass('ace')
							.wrap('<label />')
							.after('<span class="lbl align-top" />')
					
					
							$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
							.find('input.cbox[type=checkbox]').addClass('ace')
							.wrap('<label />').after('<span class="lbl align-top" />');
						*/
						}
						
					
						//unlike navButtons icons, action icons in rows seem to be hard-coded
						//you can change them like this in here if you want
						function updateActionIcons(table) {
							/**
							var replacement = 
							{
								'ui-icon-pencil' : 'icon-pencil blue',
								'ui-icon-trash' : 'icon-trash red',
								'ui-icon-disk' : 'icon-ok green',
								'ui-icon-cancel' : 'icon-remove red'
							};
							$(table).find('.ui-pg-div span.ui-icon').each(function(){
								var icon = $(this);
								var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
								if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
							})
							*/
						}
						
						//replace icons with FontAwesome icons like above
						function updatePagerIcons(table) {
							var replacement = 
							{
								'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
								'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
								'ui-icon-seek-next' : 'icon-angle-right bigger-140',
								'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
							};
							$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
								var icon = $(this);
								var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
								
								if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
							})
						}
					
						function enableTooltips(table) {
							$('.navtable .ui-pg-button').tooltip({container:'body'});
							$(table).find('.ui-pg-div').tooltip({container:'body'});
						}
					},
					error:function(xhr,errorText,errorType){
						alert("加载数据请求失败！");
					},
				})
	}
	
	<!--提交订单 - start-->
	function commitOrder() {
		var grid_selector="#orderlist";
		var index = $(grid_selector).jqGrid("getGridParam", "selrow");
		if (index == null || typeof (index) == "undefined" || index.length == 0) {
			alert("请选择！");
			return;
		}
		var selectRow = jQuery(grid_selector).jqGrid('getRowData', index);  
		if (selectRow.length < 1
				|| typeof (selectRow.serialNo) == "undefined"
				|| (selectRow.serialNo).length == 0) {
			refreshNextTask(grid_selector);
		} else {
			selectNextUserAndOrg();
		}
	}
	
	function doCommitTask(){
		var nexttask_selector = "#nextTaskList";
		var nexttaskindex = $(nexttask_selector).jqGrid("getGridParam", "selrow");
		if (typeof (nexttaskindex) == "undefined" || nexttaskindex.length == 0) {
			alert("请选择下一步审批人！");
			return;
		}
		var selectnexttask = jQuery(nexttask_selector).jqGrid('getRowData', nexttaskindex);  
		var nextTaskJson = {
				nextTaskUserID:selectnexttask.userID,
				nextTaskOrgID:selectnexttask.orgID
		}
		var url = '${pageContext.request.contextPath}/SubmitTask';
		var data = null;
		data = {
			taskSerialNo : sTaskSerialNo,
			nextTask: "["+JSON.stringify(nextTaskJson)+"]",
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
					result = "提交订单失败！";
				} else {
					result = result.split("@")[1];
				}
				alert(result);
				self.close();
				window.opener.refreshGrid("#orderlist");
			},
			error:function(xhr,errorText,errorType){
				alert("提交订单失败！");
			},
		})
	}
	<!--提交订单 - end-->
	
	function refreshNextTask(grid_selector) {
		sIsInEdit = false;
		$.ajax({
			url : nextTaskDataUrl,
			dataType : 'json',
			type : 'POST',
			data : {
				serialNo : sTaskSerialNo,
				modelName : 'FlowUser',
			},
			success : function(response) {
				var grid_data = response.data;
				jQuery(grid_selector).clearGridData();
				jQuery(grid_selector).setGridParam({
					data : grid_data
				}).trigger("reloadGrid");
			},
			error:function(xhr,errorText,errorType){
				alert("刷新失败！");
			},
		})
	}
	
	$(document).ready(function() { 
		sTaskSerialNo = b.decode("<%=sTaskSerialNo%>");
		sRightType = "<%=sRightType %>";
		initNextTask();
	});
		
	$(window).on('resize', centerGrid);
	function centerGrid() {
		var grid_selector = "#nextTaskList";
		$(grid_selector).setGridWidth($(window).width() * 0.96);
	}
	
		function pageSize() { 
			   var winW, winH; 
			  if(window.innerHeight) {// all except IE 
			   winW = window.innerWidth; 
			   winH = window.innerHeight; 
			  } else if (document.documentElement && document.documentElement.clientHeight) {// IE 6 Strict Mode
			   winW = document.documentElement.clientWidth; 
			   winH = document.documentElement.clientHeight; 
			  } else if (document.body) { // other 
			   winW = document.body.clientWidth; 
			   winH = document.body.clientHeight; 
			  }  // for small pages with total size less then the viewport 
			  return {WinW:winW, WinH:winH}; 
			}  
	</script>
</body>
</html>
