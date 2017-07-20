package com.eps.file.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.DocumentPath;
import com.eps.system.cache.CacheManager;
import com.eps.system.utils.DBHelper;
import com.eps.system.utils.DateHelper;

import net.sf.json.JSONObject;

public class UploadFileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String filePath = CacheManager.getGlobalParam("UploadFilePath");
		if("".equals(filePath)){
			System.out.println("==δ�����ϴ��ļ�·��==");
			return;
		}
		Calendar cal = Calendar.getInstance();
		String year = ""+cal.get(Calendar.YEAR);
		String month = ""+(cal.get(Calendar.MONTH)+1);
		String day = ""+cal.get(Calendar.DAY_OF_MONTH);
		filePath += year + "/" + month + "/" + day + "/";
		File file = new File(filePath);
		if(!file.exists() && !file.isDirectory()){
			file.mkdirs();
		}
				
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		//�����ļ��ϴ�����
		upload.setProgressListener(new ProgressListener() {
			
			@Override
			public void update(long pBytesRead, long pContentLength, int arg2) {
				// TODO Auto-generated method stub
				System.out.println("�ļ���СΪ��" + pContentLength + ",��ǰ�Ѵ���" + pBytesRead);
			}
		});
		upload.setHeaderEncoding("UTF-8");
		upload.setSizeMax(1024*1024*1024);
		
		if(!ServletFileUpload.isMultipartContent(req)){
			return;
		}
		String message = "";
		String orderSerialNo = "";
		FileItem fileItem = null;
		try {
			List<FileItem> fileList = upload.parseRequest(req);
			for(FileItem item : fileList){
				if(item.isFormField()){
					String name = item.getFieldName();
					//�����ͨ����������ݵ�������������
					if(name.toLowerCase().equals("orderserialno"))
						orderSerialNo = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + orderSerialNo);
				}else{
					fileItem = item;
				}
			}
			
			String originalFileName = fileItem.getName();
			System.out.println(originalFileName);
			if(originalFileName==null || originalFileName.trim().equals("")){
				Exception e = new Exception("originalFileName  Ϊ��");
				throw e;
			}
			String filename = originalFileName.substring(originalFileName.lastIndexOf("\\")+1);
			String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
			filename = makeFileName(filename);
			InputStream in = fileItem.getInputStream();
			String realFilePath = filePath + "\\" + filename;
			FileOutputStream out = new FileOutputStream(realFilePath);
			//����һ��������
			byte buffer[] = new byte[1024];
			//�ж��������е������Ƿ��Ѿ�����ı�ʶ
			int len = 0;
			//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			in.close();
			out.close();
			fileItem.delete();
			message = "true@�ļ��ϴ��ɹ���";
			
//			String orderSerialNo = req.getParameter("orderSerialNo");
			if(orderSerialNo == null) orderSerialNo = "";
			HttpSession httpSession = req.getSession();
			String userID = String.valueOf(httpSession.getAttribute("userID"));
			String orgID = String.valueOf(httpSession.getAttribute("orgID"));
			
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			
			DocumentPath documentPath = new DocumentPath();
			documentPath.setSerialNo(DBHelper.generateKey("DOCUMENT_PATH"));
			documentPath.setDownloadTimes("0");
			documentPath.setFileName(originalFileName);
			documentPath.setFileType(fileExtName);
			documentPath.setOrderSerialNo(orderSerialNo);
			documentPath.setSavePath(realFilePath);
			documentPath.setStatus("1");
			documentPath.setUploadOrgID(orgID);
			documentPath.setUploadUserID(userID);
			documentPath.setUploadTime(DateHelper.getCurrentDateAndTime());
			
			FileManager fileManager = new FileManager();
			fileManager.insertFile(webApplicationContext, documentPath);
			
		} catch (FileUploadException e) {
			message = "false@�ļ��ϴ�ʧ�ܣ�";
			e.printStackTrace();
		} catch (Exception e1) {
			message = e1.getMessage();
			e1.printStackTrace();
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		JSONObject jsonRes = new JSONObject();
		
		if(message.contains("@") && "true".equals(message.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(message.split("@")[1]+"��������");
			
			jsonRes.put("result", message);
			
		}else{
			jsonRes.put("result", message);
		}
		
		PrintWriter out = resp.getWriter();
		out.println(jsonRes.toString());
		out.flush();
		out.close();
	}

	private String makeFileName(String filename){  
		//Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
		return filename.split("\\.")[0]  + "__" +  UUID.randomUUID().toString() + "." + filename.split("\\.")[1];
	}
}
