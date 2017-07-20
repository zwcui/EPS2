package com.eps.file.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.DocumentPath;
import com.eps.dao.service.FileService;

public class DownLoadFileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String serialNo = req.getParameter("serialNo");
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("serialNo", serialNo);
		
		FileService fileService = (FileService) webApplicationContext.getBean("fileService");
		DocumentPath path = fileService.queryDocumentBySerialNo(serialNo);
		
		String savePath = path.getSavePath();
		String fileName = path.getFileName();
		File file = new File(savePath);
		if(!file.exists()){
			req.setAttribute("message", "您要下载的资源已被删除！！");
			return;
		}
		String realName = fileName.substring(fileName.indexOf("_")+1);
		resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
		FileInputStream in = new FileInputStream(savePath);
		OutputStream out = resp.getOutputStream();
		byte buffer[] = new byte[1024];
		int len = 0;
		while((len=in.read(buffer))>0){
			out.write(buffer, 0, len);
		}
		in.close();
		out.close();
		
		updateDownloadTimes(webApplicationContext,serialNo);
		
	}

	
	private String updateDownloadTimes(WebApplicationContext webApplicationContext,String serialNo){
		//TODO:更新下载次数
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("SerialNo", serialNo);
		
//		
//		int col = session.update("com.eps.bean.beaninterface.DocumentInterface.updateFlowTask", paramMap);
//		if(col ==0){
//			session.rollback();
//			return "false@更新DownloadTimes失败";
//		}
//		
		
//		return col+"";
		return "";
	}
	
	
	
	
	
	
	
	
	
}
