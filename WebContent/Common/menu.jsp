<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="com.eps.system.utils.NameManager"%>
<%@page import="com.eps.product.action.ProductManager"%>
<%@page import="com.eps.system.cache.CacheManager"%>
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
		if(s.getAttribute("userID") == null){
			return;
		}
		Map<String,String> CurUserRole = (Map<String,String>)s.getAttribute("roles");
	%>
	<a class="menu-toggler" id="menu-toggler" href="#"> <span
				class="menu-text"></span>
			</a>

			<div class="sidebar" id="sidebar">
				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'fixed')
					} catch (e) {
					}
				</script>

				<div class="sidebar-shortcuts" id="sidebar-shortcuts">
<!-- 					<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large"> -->
<!-- 						<button class="btn btn-success"> -->
<!-- 							<i class="icon-signal"></i> -->
<!-- 						</button> -->

<!-- 						<button class="btn btn-info"> -->
<!-- 							<i class="icon-pencil"></i> -->
<!-- 						</button> -->

<!-- 						<button class="btn btn-warning"> -->
<!-- 							<i class="icon-group"></i> -->
<!-- 						</button> -->

<!-- 						<button class="btn btn-danger"> -->
<!-- 							<i class="icon-cogs"></i> -->
<!-- 						</button> -->
<!-- 					</div> -->

					<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span> <span class="btn btn-info"></span>

						<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
					</div>
				</div>
				<!-- #sidebar-shortcuts -->

				<ul class="nav nav-list">
					<li class="active"><a id="menu_01" href="#" > <i
							class="icon-dashboard"></i> <span class="menu-text"> 工作台 </span><b
							class="arrow icon-angle-down"></b>
					</a>
					<ul class="submenu">
							<li ><a id="menu_0101" href="<%=sWebRootPath%>/WorkFlow/FlowList.jsp?dealFlag=N" target="ContentIframe"> <i
									id="menu_i_0101" class="icon-double-angle-right"></i> 待处理
							</a></li>
							<li><a id="menu_0102" href="<%=sWebRootPath%>/WorkFlow/FlowList.jsp?dealFlag=Y" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> 已处理
							</a></li>
						</ul>
					</li>

					<li class="active"><a id="menu_02" href="#" class="dropdown-toggle"> <i
							class="icon-edit"></i> <span class="menu-text"> 订单 </span> <b
							class="arrow icon-angle-down"></b>
					</a>

						<ul class="submenu">
							<%
							if("0001".equals((String)s.getAttribute("orgID")) || "System".equals((String)s.getAttribute("orgID")) ){
							%>
							<li><a id="menu_0201" href="<%=sWebRootPath%>/Orders/orders.jsp" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> 订单制作
							</a></li>
							<%
							}
							%>

							<%
							TreeMap<String,Map<String,String>> AllStates = (TreeMap<String,Map<String,String>>)NameManager.getAllItemByCodeNo("OrderStatus");
							for(String key:AllStates.keySet()){
								Map<String,String> item = AllStates.get(key);
								boolean viewStateFlag = false;
								for(String itemno:item.keySet()){
									viewStateFlag = NameManager.isShowOrder((String)s.getAttribute("orgID"), itemno);
									if( viewStateFlag == true){
										String sOrderState = java.net.URLEncoder.encode(itemno,"UTF-8");
										sOrderState = java.net.URLEncoder.encode(sOrderState,"UTF-8");
										String sTitle = java.net.URLEncoder.encode(item.get(itemno),"UTF-8");
										sTitle = java.net.URLEncoder.encode(sTitle,"UTF-8");
							%>
							<li><a id="menu_0202_<%=itemno%>" href="<%=sWebRootPath%>/Orders/vieworders.jsp?OrderState=<%=sOrderState%>&Title=<%=sTitle%>" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> <%=item.get(itemno)%>
							</a></li>
							<%
								}
								}
							}
							%>
							
						</ul></li>
						
						<li class="active"><a id="menu_03" href="#" class="dropdown-toggle"> <i
							class="icon-edit"></i> <span class="menu-text"> 产品分类 </span> <b
							class="arrow icon-angle-down"></b>
					</a>

						<ul class="submenu">
							<%
							TreeMap<String,String> products = (TreeMap<String,String>)ProductManager.getProductTree();
							for(String key:products.keySet()){
								String sProductID = java.net.URLEncoder.encode(key,"UTF-8");
								sProductID = java.net.URLEncoder.encode(sProductID,"UTF-8");
								String sTitle = java.net.URLEncoder.encode(products.get(key),"UTF-8");
								sTitle = java.net.URLEncoder.encode(sTitle,"UTF-8");
							%>
							<li><a id="menu_0301_<%=key%>" href="<%=sWebRootPath%>/Orders/productorders.jsp?ProductID=<%=sProductID%>&Title=<%=sTitle%>" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> <%=products.get(key)%>
							</a></li>
							<%
							}
							%>
							
						</ul></li>
					
					<%
							if(CurUserRole.containsKey("EPS0099")){
							%>	
					<li class="active"><a href="#" class="dropdown-toggle"> <i
							class="icon-flag"></i> <span class="menu-text"> 机构建设 </span> <b
							class="arrow icon-angle-down"></b>
					</a>

						<ul class="submenu">
							<li><a href="<%=sWebRootPath%>/OrgManage/productlist.jsp" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> 产品管理
							</a></li>
							<li><a href="<%=sWebRootPath%>/OrgManage/orglist.jsp" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> 机构管理
							</a></li>

							<li><a href="<%=sWebRootPath%>/OrgManage/rolelist.jsp" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> 岗位管理
							</a></li>
							<li><a href="<%=sWebRootPath%>/OrgManage/userlist.jsp" target="ContentIframe"> <i
									class="icon-double-angle-right"></i> 用户管理
							</a></li>
						</ul></li>
						<%
							}
							%>
				</ul>
				<!-- /.nav-list -->

				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="icon-double-angle-left"
						data-icon1="icon-double-angle-left"
						data-icon2="icon-double-angle-right"></i>
				</div>

				<script type="text/javascript">
					try {
						ace.settings.check('sidebar', 'collapsed')
					} catch (e) {
					}
				</script>
			</div>
</body>
</html>
