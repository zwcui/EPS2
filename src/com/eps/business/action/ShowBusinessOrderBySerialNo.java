package com.eps.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class ShowBusinessOrderBySerialNo extends HttpServlet {

	public ShowBusinessOrderBySerialNo(){
		System.out.println("�����ڷ��ʡ�������������");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		BusinessOrderManager manager = new BusinessOrderManager();
		
		String serialNo = req.getParameter("serialNo");
		if(serialNo == null){
			System.out.println("==δ������ˮ��==");
			return;
		}
		
		String modelName = req.getParameter("modelName");
		if(modelName == null || "".equals(modelName)){
			System.out.println("==δ����modelName==");
			return;
		}
		
		HttpSession httpsession = req.getSession();
		String userID = (String) httpsession.getAttribute("userID");
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("serialNo", serialNo);
		paramMap.put("modelName", modelName);
		paramMap.put("CurUserID", userID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		String res = manager.showBusinessOrderBySerialNo(webApplicationContext, paramMap);
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"��������");
			PrintWriter out = resp.getWriter();
			out.println(res.split("@")[1]);
			out.flush();
			out.close();
		}
	}

}
