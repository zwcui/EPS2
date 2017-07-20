package com.eps.flow.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONObject;

public class CreateFlow extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String serialNo = req.getParameter("serialNo");
		if(serialNo == null || "".equals(serialNo)){
			System.out.println("==未传入流水号==");
			return;
		}
		String flowNo = req.getParameter("flowNo");
		if(flowNo == null || "".equals(flowNo)){
			System.out.println("==未传入流程编号==");
			return;
		}
		
		HttpSession session = req.getSession();
		
		String objectType = "BUSINESS_ORDER";
		String userID = (String) session.getAttribute("userID");
		String orgID = (String) session.getAttribute("orgID");
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		FlowManager flowManager = new FlowManager();
		String res = flowManager.createInstance(webApplicationContext, serialNo, objectType, flowNo, userID, orgID);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		
		JSONObject jsonRes = new JSONObject();
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"返回数据");
			
			jsonRes.put("result", res);
			
		}else{
			jsonRes.put("result", "false@删除失败！");
		}
		
		PrintWriter out = resp.getWriter();
		out.println(jsonRes.toString());
		out.flush();
		out.close();

	}

}
