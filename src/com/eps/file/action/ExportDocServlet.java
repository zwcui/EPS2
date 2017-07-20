package com.eps.file.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import net.sf.json.JSONObject;

public class ExportDocServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String orderSerialNo = req.getParameter("orderSerialNo");
		String templateNo = req.getParameter("templateNo");
//		String orderSerialNo = "2016082000000003";
//		String templateNo = "xls_01";
		req.setCharacterEncoding("UTF-8");
		ExportXLS exp = new ExportXLS();
		try {
			String res = exp.export(orderSerialNo, templateNo);
			if(res != null && "true".equals(res.split("@")[0])){
				String filePath = res.split("@")[2];
				File file = new File(filePath);
				if(file != null && !file.exists()){
					System.out.println("文件不存在，路径："+filePath);
					return;
				}
				String fileType = req.getSession().getServletContext().getMimeType(filePath);
				if(fileType == null){
					fileType = "application/octet-stream";
				}
				resp.setContentType(fileType);
				System.out.println("文件类型："+fileType);
				String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
				resp.setHeader("Content-disposition", "attachment;filename=\""+fileName+"\"");

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
				byte[] buffer = new byte[1024];
				int length = 0;
				while((length = bis.read(buffer)) != -1){
					bos.write(buffer, 0, length);
				}
				if(bis != null) bis.close();
				if(bos != null) bos.close();
			}else{
				res = res + "，导出失败!";
				resp.setCharacterEncoding("UTF-8");
				resp.setContentType("text/html;charset=gb2312");
				PrintWriter out = resp.getWriter();
				JSONObject jsonRes = new JSONObject();
				jsonRes.put("result", res);
				out.println(jsonRes.toString());
				out.flush();
				out.close();
			}
		
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		}
	}

}
