package com.eps.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.FlowTask;
import com.eps.bean.Order;
import com.eps.bean.OrderInfo;
import com.eps.dao.service.FlowService;
import com.eps.dao.service.OrderService;
import com.eps.product.action.ProductManager;
import com.eps.system.cache.CacheManager;
import com.eps.system.utils.DateHelper;
import com.eps.system.utils.NameManager;

import net.sf.json.JSONObject;

public class ExportXLS {

	public String export(String orderSerialNo,String templateNo) throws IOException, InvalidFormatException{
		
		if(orderSerialNo == null || "".equals(orderSerialNo)){
			return "false@未传入订单号";
		}
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		
		List<Order> resList = new ArrayList<Order>();
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("serialNo", orderSerialNo);
		
		Order order = orderService.queryBusinessOrderBySerialNo(orderSerialNo);
		
		if(order == null){
			return "false@未查询到对应订单信息";
		}
		
		//技术审核信息
		paramMap.put("phaseNo", "technology");
		List<FlowTask> techTaskList = flowService.queryFlowOpinionForExport(paramMap);
		FlowTask techTask = null;
		if(techTaskList.size() > 0)
			techTask = techTaskList.get(0);
		
		//生产审核信息
		paramMap.put("phaseNo", "product");
		List<FlowTask> prodTaskList = flowService.queryFlowOpinionForExport(paramMap);
		FlowTask prodTask = null;
		if(prodTaskList.size() > 0)
			prodTask = prodTaskList.get(0);
		
		List<OrderInfo> resInfoList =  new ArrayList<OrderInfo>();
		paramMap.clear();
		paramMap.put("orderSerialNo", orderSerialNo);
		resInfoList = orderService.queryBusinessOrderInfoByOrderSerialNo(orderSerialNo);
//		if(resInfoList.size() == 0){
//			return "false@未查询到对应订单详情信息";
//		}
		
		Map<String,Map<String,String>> docList = (Map<String, Map<String, String>>) CacheManager.getCacheInfo("documentCache").getValue();
		Map<String, String> property = docList.get(templateNo);
		String urlTemp = property.get("path");
		String startLineStr = property.get("startline");
		String infoCol = property.get("infocol");
				
		
		File xlsTemp = new File(urlTemp);
		XSSFWorkbook wdTemp = (XSSFWorkbook) WorkbookFactory.create(new FileInputStream(xlsTemp));
		
		XSSFSheet sheetTemp = wdTemp.getSheetAt(0);  
		
		JSONObject json = JSONObject.fromObject(order);
		JSONObject techJSON = JSONObject.fromObject(techTask);
		JSONObject prodJSON = JSONObject.fromObject(prodTask);
		
		//标准模板赋值
        int trLengthStart = sheetTemp.getLastRowNum();  
        for(int i=0;i<trLengthStart;i++){
        	XSSFRow row = sheetTemp.getRow(i);  
        	int tdLengthStart = row.getLastCellNum(); 
        	for(int j=0;j<tdLengthStart;j++){
        		Cell cell = row.getCell(j);  
        		String cellStr = cell.getStringCellValue();
        		if(cellStr != null && cellStr.startsWith("$")){
        			String key = cellStr.substring(cellStr.indexOf("${")+2,cellStr.lastIndexOf("}")); 
        			if(key.toLowerCase().startsWith("technology_")){
        				key = key.substring("technology_".length());
        				if(!techJSON.isEmpty()){
	        				if(key.toLowerCase().startsWith("user"))
	        					cellStr = techJSON.has(key)?NameManager.getUserName(techJSON.getString(key)):"";
	        				else if(key.toLowerCase().contains("phaseaction"))
	        					cellStr = techJSON.has(key)?NameManager.getItemName("PhaseAction", techJSON.getString(key)):"";
	        				else	
	        					cellStr = techJSON.has(key)?techJSON.getString(key):"";
        				}else{
        					cellStr = "";
        				}
        			}else if(key.toLowerCase().startsWith("product_")){
        				key = key.substring("product_".length());
        				if(!prodJSON.isEmpty()){
	        				if(key.toLowerCase().startsWith("user"))
	        					cellStr = prodJSON.has(key)?NameManager.getUserName(prodJSON.getString(key)):"";
	    					else if(key.toLowerCase().contains("phaseaction"))
	        					cellStr = prodJSON.has(key)?NameManager.getItemName("PhaseAction", prodJSON.getString(key)):"";
	    					else
	        					cellStr = prodJSON.has(key)?prodJSON.getString(key):"";
        				}else{
        					cellStr = "";
        				}
        			}else if(key.toLowerCase().startsWith("productid")){
        				cellStr = json.has(key)?ProductManager.getProductName(json.getString(key)):"";
        			}else{
        				cellStr = json.has(key)?json.getString(key):"";
        			}
        		}
        		cell.setCellValue(cellStr);
        	}
        }
        
        //添加详情字段
        if(resInfoList != null && resInfoList.size() > 0){
	        if(infoCol != null && !"".equals(infoCol) && startLineStr != null && !"".equals(startLineStr)){
	        	String[] cols = infoCol.split("\\|");
	        	int startLine = Integer.parseInt(startLineStr) - 1;
	        	
        		XSSFFont font = wdTemp.createFont();
        		font.setFontHeight((short)250);//字体变大
        		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);//加粗
		        
        		for(int i=0;i<+resInfoList.size();i++){
		        	XSSFRow row = sheetTemp.createRow(startLine);  
		        	JSONObject jsonInfo = JSONObject.fromObject(resInfoList.get(i));
		        	for(int j=0;j<cols.length;j++){
		        		int cellNum = Integer.parseInt(cols[j].split("@")[0]) - 1;
		        		Cell cell = row.createCell(cellNum);  
		        		String key = cols[j].split("@")[1];
		        		String value = jsonInfo.has(key)?jsonInfo.getString(key):"";
		        		
		        		//格式
			        	XSSFCellStyle style = wdTemp.createCellStyle();
		        		style.setBorderBottom(XSSFCellStyle.BORDER_THIN);//边框
		        		style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		        		style.setBorderRight(XSSFCellStyle.BORDER_THIN);
		        		style.setFont(font);
		        		style.setWrapText(true);//自动换行
		        		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
		        		//制造标准不居中
		        		if("standard".equals(key.toLowerCase())){
		        			style.setAlignment(XSSFCellStyle.ALIGN_LEFT);
		        		}else{
		        			style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		        		}
		        		
		        		cell.setCellValue(value);
		        		cell.setCellStyle(style);
		        	}
		        	startLine++;
		        }
	        }
        }
		
        String fileName = "";
        String filePath = "";
        try  
        {  
        	fileName = orderSerialNo + "_" + DateHelper.getCurrentDate().replace("/", "") + ".xlsx";
        	filePath = CacheManager.getGlobalParam("ExportFilePath");
        	File fp = new File(filePath);
        	if(!fp.exists()) fp.mkdirs();
        	filePath += fileName;
            FileOutputStream fout = new FileOutputStream(filePath);  
            wdTemp.write(fout);  
            fout.close();  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
            return "false@创建失败";
        }  
		
		
		return "true@创建成功@"+filePath;
	}
	
	// 复制源表中的合并单元格
	private void MergerRegion(XSSFSheet sheetCreat, XSSFSheet sheet,int firstRow) {
        int sheetMergerCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergerCount; i++) {
            CellRangeAddress mergedRegionAt = sheet.getMergedRegion(i);
            mergedRegionAt.setFirstRow(firstRow+i);
            sheetCreat.addMergedRegion(mergedRegionAt);
        }
	}
	
}
