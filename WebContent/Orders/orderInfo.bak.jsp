<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String sRightType = request.getParameter("RightType");
	HttpSession s = request.getSession();
	String sCurOrgID = (String)s.getAttribute("orgID");
	if(sRightType == null) sRightType = "All";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单详情</title>
</head>
<body class='main page' width="100%">
	<div class="page-content" id="modalpage" role="document" >
		<div class="">
			<div class="modal-header no-padding">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">
					<span class="red">&times;</span>
				</button>
				<h4 class="modal-title" id="viewOrderInfoModalLabel">查看订单</h4>
			</div>
			<div class="modal-body container no-padding" >
			<% if("All".equals(sRightType) || ("Flow".equals(sRightType) && "0001".equals(sCurOrgID))){%>
				<div id="orderinfotoolbar" class="panel-body btn-group">
					<button type="button" class="btn btn-custom-default"
						onclick="createNewOrderInfo()">
						<i class="icon-plus-sign purple">新增</i>
					</button>
					<button type="button" class="btn btn-custom-default"
						onclick="deleteOrderInfo()">
						<i class="icon-trash red">删除</i>
					</button>
				</div>
				<%}%>
				<table id="orderinfolist" class="table table-striped table-bordered table-hover no-margin-bottom no-border-top"></table>
				<div id="orderinfolist-pager"></div>
				<script type="text/javascript">
// 					var $path_base = "/";//this will be used in gritter alerts containing images
				</script>
			</div>
			<div class="modal-footer no-margin-top">
				<button type="button" class="btn btn-sm btn-primary pull-left" data-dismiss="modal"><i class="icon-remove"></i>
																		取消</button>
				<button type="button" class="btn btn-sm btn-success pull-left" data-dismiss="modal"><i class="icon-ok"></i>
																		确定</button>
			</div>
		</div>
	</div>
	<script>
	
	var orderInfoDataUrl = "${pageContext.request.contextPath}/ShowBusinessOrderInfoBySerialNo";
	var orderInfo_data;
	var sOrderSerialNo = "";
	var sIsInEdit = false;
	var sRightType = "<%=sRightType %>";
	function initOrderInfo() {
		var orderlist_selector="#orderlist";
		var orderindex = $(orderlist_selector).jqGrid("getGridParam", "selrow");
		if (orderindex == null || typeof (orderindex) == "undefined" || orderindex.length == 0) {
			alert("请选择！");
			return;
		}
		var selectRow = jQuery(orderlist_selector).jqGrid('getRowData', orderindex);  
		sOrderSerialNo = selectRow.serialNo;
		var data = null;
		data = {
			serialNo : sOrderSerialNo,
			modelName:'BusinessOrderInfo',
		}
		$
				.ajax({
					url : orderInfoDataUrl,
					dataType : 'json',
					type : 'POST',
					data : data,
					success : function(response) {
						var orderInfo_data = response.data;
						var colnames = response.colNames;
						var colmodel = modelPreTreat(response.column);
						var lastSel;
						var grid_selector = "#orderinfolist";
						var pager_selector = "#orderinfolist-pager";
						jQuery(grid_selector).jqGrid({
// 							direction: "rtl",
							data: orderInfo_data,
							datatype: "local",
							height: 250,
							colNames:colnames,
							colModel:colmodel,
					
							viewrecords : true,
							rowNum:10,
							rowList:[10,20,30],
							pager : pager_selector,
							altRows: true,
// 							toppager: true,
// 							multiselect: true,
							//multikey: "ctrlKey",
					        multiboxonly: true,
					
							loadComplete : function() {
								var table = this;
								setTimeout(function(){
									styleCheckbox(table);
									
									updateActionIcons(table);
									updatePagerIcons(table);
									enableTooltips(table);
									autoFitPageWidth(table,grid_selector,infogridmaxrsizetimes);
								}, 0);
							},
							ondblClickRow: function(id){
								 if(sRightType == "ReadOnly") return;
							     if(id && id!==lastSel){ 
							        jQuery(grid_selector).restoreRow(lastSel); 
							        lastSel=id; 
							     }
							     makeEditable(grid_selector,id);
							     sIsInEdit = true;
							   },

							editurl: 'clientArray',//nothing is saved
							caption: "订单列表",
// 							shrinkToFit:true,
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
					
						function modelPreTreat(colModel){
							var cellLenth = colModel.length; 
					        for ( var i = 0; i < cellLenth; i++) {  
					            if(colModel[i].unformat == "pickDate"){
					            	colModel[i].unformat = pickDate;
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
					
						function autoFitPageWidth(table,grid_selector,counttimes){
							var sWidth = $("#modalpage").width();
							var sCount = 0;
							if(sWidth <= 0 && sCount < counttimes){
								sCount = sCount + 1;
								setTimeout(function(){
									autoFitPageWidth(table,grid_selector,counttimes);
								}, 0);
							}else{
								if(sWidth <= 0 ) sWidth = infogriddefaultwidth;
								$(grid_selector).setGridWidth(sWidth);
							}
						}
						
						//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
					},
					error:function(xhr,errorText,errorType){
						alert("加载数据请求失败！");
					},
				})
	}
	
	$(document).ready(function() { 
		initOrderInfo();
	});
		function refreshOrderInfo(grid_selector) {
			sIsInEdit = false;
			$.ajax({
				url : orderInfoDataUrl,
				dataType : 'json',
				type : 'POST',
				data : {
					serialNo : sOrderSerialNo,
					modelName : 'BusinessOrderInfo',
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
	
		<!--创建订单详情条目 -start-->
		function createNewOrderInfo() {
			var grid_selector="#orderinfolist";
			var index = 0;
			var dataRow = { orderSerialNo : sOrderSerialNo };  
			$(grid_selector).resetSelection();     
			$(grid_selector).jqGrid("addRowData", index, dataRow, "first"); 
			$(grid_selector).jqGrid('setSelection',index);
			makeEditable(grid_selector,index);
			sIsInEdit = true;
		}
		<!--创建订单详情条目 - end-->

		<!--保存订单详情条目  - start-->
		function saveRecord(id) {
			var grid_selector="#orderinfolist";
			var record = jQuery(grid_selector).jqGrid('getRowData', id);  
			var url = '${pageContext.request.contextPath}/UpdateBusinessOrderInfo';
			if (typeof (record.serialNo) == "undefined"
					|| record.serialNo.length == 0) {
				url = '${pageContext.request.contextPath}/CreateBusinessOrderInfo';
			}
			var data = {
				row : JSON.stringify(record),
			}
			$.ajax({
				url : url,
				dataType : 'json',
				type : 'POST',
				data : data,
				success : function(response) {
					var result = response.result;
					if (typeof (result) == "undefined" || result.length == 0) {
						result = "保存失败！";
					} else {
						result = result.split("@")[1];
					}
					alert(result);
					refreshOrderInfo(grid_selector);
				},
				error:function(xhr,errorText,errorType){
					alert("保存失败！");
				},
			})
		}
		<!--保存订单详情条目  - end-->

		<!--删除订单详情条目 - start-->
		function deleteOrderInfo() {
			var grid_selector="#orderinfolist";
			var index = $(grid_selector).jqGrid("getGridParam", "selrow");
			if (index == null || typeof (index) == "undefined" || index.length == 0) {
				alert("请选择！");
				return;
			}
			var selectRow = jQuery(grid_selector).jqGrid('getRowData', index);  
			if (selectRow.length < 1
					|| typeof (selectRow.serialNo) == "undefined"
					|| (selectRow.serialNo).length == 0) {
				refreshOrderInfo(grid_selector);
			} else {
				var data = null;
				data = {
						serialNo : selectRow.serialNo,
				}
				$
						.ajax({
							url : '${pageContext.request.contextPath}/DeleteBusinessOrderInfo',
							dataType : 'json',
							type : 'POST',
							data : data,
							success : function(response) {
								var result = response.result;
								if (typeof (result) == "undefined"
										|| result.length == 0) {
									result = "删除失败！";
								} else {
									result = result.split("@")[1];
								}
								alert(result);
								refreshOrderInfo(grid_selector);
							},
							error:function(xhr,errorText,errorType){
								alert("删除订单失败！");
							},
						})
			}
		}
		<!--删除订单详情条目 - end-->
		
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
			
// 		$(function() {
// 			$(window).resize(
// 					function() {
// 						$("#orderinfolist").setGridWidth(
// 								$(window).width() * 0.96);
// // 						$("#charDataTab").setGridWidth(
// // 								document.body.clientWidth * 0.99);
// 					});
// 		});

		$("#modal").on("hidden.bs.modal", function() {
						$(this).removeData("bs.modal");
		});

// 		$("#modal").on("hide.bs.modal", function() {
// 			if(sIsInEdit == true){
// 				if(confirm("还有未保存的订单细目，是否确认离开？")){
// 					sIsInEdit = false;
// 					return true;
// 				}
// 				else 
// 					return false;

// 			}
// 		});
	</script>
</body>
</html>