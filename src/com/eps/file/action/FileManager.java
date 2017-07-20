package com.eps.file.action;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.DocumentPath;
import com.eps.dao.service.FileService;
import com.eps.model.action.AnalyseModel;

public class FileManager {

	/**
	 * 获取文件列表
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showFileList(WebApplicationContext webApplicationContext, Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		String orderSerialNo = paramMap.get("orderSerialNo");
		
		FileService fileService = (FileService) webApplicationContext.getBean("fileService");
		List<DocumentPath> pathList = fileService.queryDocumentByOrderSerialNo(orderSerialNo);
		
		List<Object> resList = new ArrayList<Object>();
		resList.addAll(pathList);
		
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	
	/**
	 * 删除文件
	 * @param session
	 * @param productBean
	 * @return
	 */
	public String deleteFile(WebApplicationContext webApplicationContext, Map<String,String> paramMap){
		
		String res = "";
		int orderCount = 0;
		String serialNo = paramMap.get("serialNo");
		
		List<DocumentPath> resList = new ArrayList<DocumentPath>();
		
		FileService fileService = (FileService) webApplicationContext.getBean("fileService");
		DocumentPath path = fileService.queryDocumentBySerialNo(serialNo);
		if(path != null){
			resList.add(path);
			File file = new File(path.getSavePath());
			if(file.exists()){
				file.delete();
			}
		}
		orderCount = fileService.deleteFileBySerialNo(serialNo);
		System.out.println("==成功删除"+orderCount+"条File");
		
		res = "true@删除成功";
		return res;		
	}
	
	/**
	 * 新增文件
	 * @param session
	 * @param productBean
	 * @return
	 */
	public String insertFile(WebApplicationContext webApplicationContext, DocumentPath documentPath){
		
		String res = "";
		
		List<DocumentPath> resList = new ArrayList<DocumentPath>();
		
		FileService fileService = (FileService) webApplicationContext.getBean("fileService");
		int orderCount = fileService.insertDocumentInfo(documentPath);
		System.out.println("==成功新增"+orderCount+"条File");
		
		res = "true@删除成功";
		return res;		
	}
	
}
