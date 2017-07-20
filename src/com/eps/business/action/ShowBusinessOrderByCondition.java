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

public class ShowBusinessOrderByCondition extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String modelName = req.getParameter("modelName");
		if(modelName == null || "".equals(modelName)){
			System.out.println("==未传入modelName==");
			return;
		}
		
		String serialNo = req.getParameter("serialNo");
		String businessType = req.getParameter("businessType");
		String orderType = req.getParameter("orderType");
		String flowNo = req.getParameter("flowNo");
		String count = req.getParameter("count");
		String state = req.getParameter("state");
		String inputUserID = req.getParameter("inputUserID");
		String inputOrgID = req.getParameter("inputOrgID");
		String inputTime = req.getParameter("inputTime");
		
		if(serialNo == null) serialNo = "";
		if(businessType == null) businessType = "";
		if(orderType == null) orderType = "";
		if(flowNo == null) flowNo = "";
		if(count == null) count = "";
		if(state == null) state = "";
		if(inputUserID == null) inputUserID = "";
		if(inputOrgID == null) inputOrgID = "";
		if(inputTime == null) inputTime = "";
		
		HttpSession httpsession = req.getSession();
		String userID = (String) httpsession.getAttribute("userID");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("serialNo", serialNo);
		paramMap.put("businessType", businessType);
		paramMap.put("orderType", orderType);
		paramMap.put("flowNo", flowNo);
		paramMap.put("count", count);
		paramMap.put("state", state);
		paramMap.put("inputUserID", inputUserID);
		paramMap.put("inputOrgID", inputOrgID);
		paramMap.put("inputTime", inputTime);
		paramMap.put("modelName", modelName);
		paramMap.put("CurUserID", userID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		BusinessOrderManager manager = new BusinessOrderManager();
		String res = manager.showBusinessOrderByCondition(webApplicationContext, paramMap);
		
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
