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

public class ShowBusinessOrderByTask extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession httpSession = req.getSession();
		String userID = (String) httpSession.getAttribute("userID");
		String orgID = (String) httpSession.getAttribute("orgID");
		String dealFlag = req.getParameter("dealFlag");
		
		String modelName = req.getParameter("modelName");
		if(modelName == null || "".equals(modelName)){
			System.out.println("==未传入modelName==");
			return;
		}
		
		Map<String,String> paramMap = new HashMap<String, String>();		
		paramMap.put("userID", userID);
		paramMap.put("orgID", orgID);
		paramMap.put("taskState", "N".equals(dealFlag)?"1":"3");
		paramMap.put("modelName", modelName);
		paramMap.put("CurUserID", userID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		BusinessOrderManager manager = new BusinessOrderManager();
		String res = manager.showBusinessOrderByTask(webApplicationContext, paramMap);
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"返回数据");
			PrintWriter out = resp.getWriter();
			out.println(res.split("@")[1]);
			out.flush();
			out.close();
		}
		
		
	}

}
