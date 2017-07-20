<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cn">
<head>
<jsp:include page="../Common/listCommon.jsp" flush="true" />
</head>
<body class='main page' onload="IFrameResize()">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-double-angle-right"></i> <a href="#">机构建设</a></li>
			<li class="active">岗位管理</li>
		</ul>
		<!-- .breadcrumb -->
	</div>

	<div class="page-content" id="pagecontent">
		<div class='panel panel-default grid'>
			<div id="listtoolbar" class="panel-body btn-group">
				<button type="button" class="btn btn-custom-default"
					onclick="createNewRole()">
					<i class="icon-plus-sign purple">新增岗位</i>
				</button>
<!-- 				<button id="viewRoleUserButton" type="button" class="btn btn-custom-default" -->
<!-- 					onclick="viewRoleUser()" data-toggle="modal"> -->
<!-- 					<i class="icon-pencil blue">岗位用户</i> -->
<!-- 				</button> -->
				<button type="button" class="btn btn-custom-default"
					onclick="deleteRole()">
					<i class="icon-trash red">删除岗位</i>
				</button>
			</div>
				<table id="rolelist"></table>
				<div id="grid-pager"></div>

				<script type="text/javascript">
					var $path_base = "/";//this will be used in gritter alerts containing images
				</script>
		</div>
	</div>
	<!-- /.page-content -->

	<div id="modal" class="modal container fade" tabindex="-1">
		<div class="modal-dialog" style="width: 1000px" role="document">
			<div class="modal-content"></div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- inline scripts related to this page -->
	
	<script>
	var b = new Base64(); 
	var sourceDataUrl = "${pageContext.request.contextPath}/ShowAllRole";
	var grid_data;
	
	function initData() {
		var data = null;
		data = {
			modelName:'RoleList',
		}
		$
				.ajax({
					url : sourceDataUrl,
					dataType : 'json',
					type : 'POST',
					data : data,
					success : function(response) {
						var grid_data = response.data;
						var colnames = response.colNames;
						var colmodel = modelPreTreat(response.column);
						var lastSel;
						var grid_selector = "#rolelist";
						var pager_selector = "#grid-pager";
						jQuery(grid_selector).jqGrid({
// 							direction: "rtl",
							data: grid_data,
							datatype: "local",
							height: 'auto',
							colNames:colnames,
							colModel:colmodel,
							viewrecords : true,
							rowNum : rowNum,
							rownumbers: true,
							rowList: [ 10, 20, 30,40,50,60,100,150 ],
							pager : pager_selector,
							altRows : true,
							// 							toppager: true,
// 							multiselect : true,
							//multikey: "ctrlKey",
							multiboxonly : true,

							loadComplete : function() {
								var table = this;
								setTimeout(
									function() {
										styleCheckbox(table);

										updateActionIcons(table);
										updatePagerIcons(table);
										enableTooltips(table);
										centerGrid();
									}, 0);
							},

							ondblClickRow : function(id) {
								if (id && id !== lastSel) {
									jQuery(grid_selector)
											.restoreRow(
													lastSel);
									lastSel = id;
								}
								makeEditable(grid_selector,
										id);
							},

							editurl : 'clientArray',//nothing is saved
							// 							editurl: "${pageContext.request.contextPath}/UpdateBusinessOrder",//nothing is saved
							caption : "岗位列表",
							autowidth : true
						});
							//enable search/filter toolbar
							// 						jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})

							//switch element when editing inline
							function aceSwitch(cellvalue, options, cell) {
								setTimeout(
										function() {
											$(cell)
													.find(
															'input[type=checkbox]')
													.wrap(
															'<label class="inline" />')
													.addClass(
															'ace ace-switch ace-switch-5')
													.after(
															'<span class="lbl"></span>');
										}, 0);
							}

							//enable datepicker
							function pickDate(cellvalue, options, cell) {
								setTimeout(function() {
									$(cell).find('input[type=text]')
											.datepicker({
												format : 'yyyy-mm-dd',
												autoclose : true
											});
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
							jQuery(grid_selector)
									.jqGrid(
											'navGrid',
											pager_selector,
											{ //navbar options
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
												recreateForm : true,
												beforeShowForm : function(e) {
													var form = $(e[0]);
													form
															.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-titlebar')
															.wrapInner(
																	'<div class="widget-header" />')
													style_edit_form(form);
												},
											},
											{
												//new record form
												closeAfterAdd : true,
												recreateForm : true,
												viewPagerButtons : false,
												beforeShowForm : function(e) {
													var form = $(e[0]);
													form
															.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-titlebar')
															.wrapInner(
																	'<div class="widget-header" />')
													style_edit_form(form);
												},
											},
											{
												//delete record form
												recreateForm : true,
												beforeShowForm : function(e) {
													var form = $(e[0]);
													if (form.data('styled'))
														return false;

													form
															.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-titlebar')
															.wrapInner(
																	'<div class="widget-header" />')
													style_delete_form(form);

													form.data('styled', true);
												},
												onClick : function(e) {
													alert(1);
												}
											},
											{
												//search form
												recreateForm : true,
												afterShowSearch : function(e) {
													var form = $(e[0]);
													form
															.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-title')
															.wrap(
																	'<div class="widget-header" />')
													style_search_form(form);
												},
												afterRedraw : function() {
													style_search_filters($(this));
												},
												multipleSearch : true,
											/**
											multipleGroup:true,
											showQuery: true
											 */
											},
											{
												//view record form
												recreateForm : true,
												beforeShowForm : function(e) {
													var form = $(e[0]);
													form
															.closest(
																	'.ui-jqdialog')
															.find(
																	'.ui-jqdialog-title')
															.wrap(
																	'<div class="widget-header" />')
												}
											})

							function style_edit_form(form) {
								//enable datepicker on "sdate" field and switches for "stock" field
								form.find('input[name=sdate]').datepicker({
									format : 'yyyy-mm-dd',
									autoclose : true
								}).end().find('input[name=stock]').addClass(
										'ace ace-switch ace-switch-5').wrap(
										'<label class="inline" />').after(
										'<span class="lbl"></span>');

								//update buttons classes
								var buttons = form.next().find(
										'.EditButton .fm-button');
								buttons.addClass('btn btn-sm').find(
										'[class*="-icon"]').remove();//ui-icon, s-icon
								buttons.eq(0).addClass('btn-primary').prepend(
										'<i class="icon-ok"></i>');
								buttons.eq(1).prepend(
										'<i class="icon-remove"></i>')

								buttons = form.next().find('.navButton a');
								buttons.find('.ui-icon').remove();
								buttons.eq(0).append(
										'<i class="icon-chevron-left"></i>');
								buttons.eq(1).append(
										'<i class="icon-chevron-right"></i>');
							}

							function style_delete_form(form) {
								var buttons = form.next().find(
										'.EditButton .fm-button');
								buttons.addClass('btn btn-sm').find(
										'[class*="-icon"]').remove();//ui-icon, s-icon
								buttons.eq(0).addClass('btn-danger').prepend(
										'<i class="icon-trash"></i>');
								buttons.eq(1).prepend(
										'<i class="icon-remove"></i>')
							}

							function style_search_filters(form) {
								form.find('.delete-rule').val('X');
								form.find('.add-rule').addClass(
										'btn btn-xs btn-primary');
								form.find('.add-group').addClass(
										'btn btn-xs btn-success');
								form.find('.delete-group').addClass(
										'btn btn-xs btn-danger');
							}
							function style_search_form(form) {
								var dialog = form.closest('.ui-jqdialog');
								var buttons = dialog.find('.EditTable')
								buttons.find('.EditButton a[id*="_reset"]')
										.addClass('btn btn-sm btn-info').find(
												'.ui-icon').attr('class',
												'icon-retweet');
								buttons.find('.EditButton a[id*="_query"]')
										.addClass('btn btn-sm btn-inverse')
										.find('.ui-icon').attr('class',
												'icon-comment-alt');
								buttons.find('.EditButton a[id*="_search"]')
										.addClass('btn btn-sm btn-purple')
										.find('.ui-icon').attr('class',
												'icon-search');
							}

							function beforeDeleteCallback(e) {
								var form = $(e[0]);
								if (form.data('styled'))
									return false;

								form.closest('.ui-jqdialog').find(
										'.ui-jqdialog-titlebar').wrapInner(
										'<div class="widget-header" />')
								style_delete_form(form);

								form.data('styled', true);
							}

							function beforeEditCallback(e) {
								var form = $(e[0]);
								form.closest('.ui-jqdialog').find(
										'.ui-jqdialog-titlebar').wrapInner(
										'<div class="widget-header" />')
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
								var replacement = {
									'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
									'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
									'ui-icon-seek-next' : 'icon-angle-right bigger-140',
									'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
								};
								$(
										'.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
										.each(
												function() {
													var icon = $(this);
													var $class = $.trim(icon
															.attr('class')
															.replace('ui-icon',
																	''));

													if ($class in replacement)
														icon
																.attr(
																		'class',
																		'ui-icon '
																				+ replacement[$class]);
												})
							}

							function enableTooltips(table) {
								$('.navtable .ui-pg-button').tooltip({
									container : 'body'
								});
								$(table).find('.ui-pg-div').tooltip({
									container : 'body'
								});
							}
							
							//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');
						},
						error:function(xhr,errorText,errorType){
							alert("加载数据请求失败！");
						},
					})
		}
		jQuery(function($) {
			initData();
		});

		<!--当前index进入可编辑状态 - start-->
		function makeEditable(grid_selector, index) {
			$(grid_selector).jqGrid('editRow', index, {
				keys : true, //这里按[enter]保存
				url : 'clientArray',
				mtype : "POST",
				restoreAfterError : true,
				extraparam : {},
				oneditfunc : function(rowid) {
				},
				aftersavefunc : function(rowid, res) {
					saveRecord(rowid);
				}
			});
		};
		<!--当前index进入可编辑状态 - end-->

		<!--新增岗位 - start-->
		function createNewRole() {
			var grid_selector = "#rolelist";
			var index = 0;
			var dataRow = {
				state : '1',
			};
			$(grid_selector).resetSelection();
			$(grid_selector).jqGrid("addRowData", index, dataRow, "first");
			$(grid_selector).jqGrid('setSelection', index);
			makeEditable(grid_selector, index);
		}
		<!--新增岗位 - end-->

		<!--保存岗位 - start-->
		function saveRecord(id) {
			var grid_selector = "#rolelist";
			var record = jQuery(grid_selector).jqGrid('getRowData', id);
			var url = '${pageContext.request.contextPath}/UpdateRole';
			if (typeof (record.roleID) == "undefined"
					|| record.roleID.length == 0) {
				url = '${pageContext.request.contextPath}/CreateRole';
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
					refreshGrid(grid_selector);
				}
			})
		}
		<!--保存岗位 - end-->

		function refreshGrid(grid_selector) {
			$.ajax({
				url : sourceDataUrl,
				dataType : 'json',
				type : 'POST',
				data : {
					modelName : 'RoleList',
				},
				success : function(response) {
					var grid_data = response.data;
					jQuery(grid_selector).clearGridData();
					jQuery(grid_selector).setGridParam({
						data : grid_data
					}).trigger("reloadGrid");
				}
			})
		}

		<!--删除岗位 - start-->
		function deleteRole() {
			var grid_selector = "#rolelist";
			var index = $(grid_selector).jqGrid("getGridParam", "selrow");
			if (index == null || typeof (index) == "undefined" || index.length == 0) {
				alert("请选择！");
				return;
			}
			var selectRow = jQuery(grid_selector).jqGrid('getRowData', index);
			if (selectRow.length < 1
					|| typeof (selectRow.roleID) == "undefined"
					|| (selectRow.roleID).length == 0) {
				refreshGrid(grid_selector);
			} else {
				var data = null;
				data = {
					row : JSON.stringify(selectRow),
				}
				$
						.ajax({
							url : '${pageContext.request.contextPath}/DeleteRole',
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
								refreshGrid(grid_selector);
							}
						})
			}
		}
		<!--删除岗位 - end-->

		<!--查看岗位下用户 - start-->
		function viewRoleUser() {
			var grid_selector = "#rolelist";
			var index = $(grid_selector).jqGrid("getGridParam", "selrow");
			if (index == null || typeof (index) == "undefined" || index.length == 0) {
				alert("请选择！");
				return;
			}
			var selectRow = jQuery(grid_selector).jqGrid('getRowData', index);
			if (selectRow.length < 1
					|| typeof (selectRow.roleID) == "undefined"
					|| (selectRow.roleID).length == 0) {
				alert("当前新增岗位未保存，无法查看岗位用户！");
				return;
			} else {
				var sPageTitle = "<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("岗位管理","UTF-8"),"UTF-8")%>";
				var sRoleID = b.encode(selectRow.roleID);
				var openUrl = "../OrgManage/roleusers.jsp?RoleID="+sRoleID+"&ParentPageTitle="+sPageTitle;
				openNewPage(openUrl,"newWin");
			}
		}
		<!--查看岗位下用户 - end-->

		$(window).on('resize', centerGrid);
		function centerGrid() {
			var grid_selector = "#rolelist";
			$(grid_selector).setGridWidth($(window).width() * 0.96);
		}
		/* center modal */
// 		function centerModals() {
// 			$("#userlist").setGridWidth($(window).width() * 0.96);
// 			$('.modal').each(
// 					function(i) {
// 						var $clone = $(this).clone().css('display', 'block')
// 								.appendTo('body');
// 						var top = Math.round(($clone.height() - $clone.find(
// 								'.modal-content').height()) / 4);
// 						top = top > 0 ? top : 0;
// 						$clone.remove();
// 						$(this).find('.modal-content').css("margin-top", top);
// 					});
// 		}
// 		$('.modal').on('show.bs.modal', centerModals);
// 		$(window).on('resize', centerModals);
	</script>
</body>
</html>
