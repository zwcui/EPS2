package com.eps.role.action;

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

public class ShowRoleUsersByRoleID extends HttpServlet {

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
		
		String roleID = req.getParameter("roleID");
		if(roleID == null || "".equals(roleID)){
			System.out.println("==未传入roleID==");
			return;
		}
		
		HttpSession httpsession = req.getSession();
		String curuserID = (String) httpsession.getAttribute("userID");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("modelName", modelName);
		paramMap.put("CurUserID", curuserID);
		paramMap.put("roleID", roleID);
		
		RoleManager manamger = new RoleManager();
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		String res = manamger.showRoleUsersByRoleID(webApplicationContext, paramMap);
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.substring(res.indexOf("@")+1)+"返回数据");
			PrintWriter out = resp.getWriter();
			out.println(res.substring(res.indexOf("@")+1));
			out.flush();
			out.close();
		}
		
	}

}
