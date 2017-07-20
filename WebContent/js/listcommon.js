var infogridmaxrsizetimes = 20;
var infogriddefaultwidth = 1000;
function resizeGrid(grid_selector) {
//	alert(document.body.clientHeight);
//	var height =  document.body.clientHeight-200;
//	$(grid_selector).jqGrid('setGridHeight',height);
}

function resizeInfoGrid(grid_selector) {
//	var buttonAreaH = $("#listtoolbar").height();
//	alert(buttonAreaH);
//	var height =  document.body.clientHeight-264 + buttonAreaH;
//	$(grid_selector).jqGrid('setGridHeight',height);
}

function openNewPage(url,windowName,windowFeatures,optionalArg4){
	var openUrl = url;//弹出窗口的url
	var iWidth=$(window).width(); //弹出窗口的宽度;
	var iHeight=$(window).height()-100; //弹出窗口的高度;
	var iTop = (window.screen.availHeight-50-iHeight)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
	if(typeof(windowFeatures) == "undefined" || windowFeatures.length == 0){
		windowFeatures = "modal=yes,scrollbars=no,height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft;
	}
	window.open(openUrl,windowName,windowFeatures,optionalArg4);
}
