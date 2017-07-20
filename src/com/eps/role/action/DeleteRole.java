package com.eps.role.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONObject;

public class DeleteRole extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		JSONObject jsonRes = new JSONObject();
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		
		String jsonStr = req.getParameter("row");
		JSONObject row = JSONObject.fromObject(jsonStr);
		if(row == null){
			jsonRes.put("result", "false@É¾³ýÊ§°Ü£¡");
			PrintWriter out = resp.getWriter();
			out.println(jsonRes.toString());
			out.flush();
			out.close();
			return;
		}
		
		String roleID = row.has("roleID")?row.getString("roleID"):"";
		if(roleID == null || "".equals(roleID)){
			jsonRes.put("result", "false@É¾³ýÊ§°Ü£¡");
			PrintWriter out = resp.getWriter();
			out.println(jsonRes.toString());
			out.flush();
			out.close();
			System.out.println("==Î´´«ÈëroleID==");
			return;
		}
		
		RoleManager manager = new RoleManager();
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("roleID", roleID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		String res = manager.deleteRole(webApplicationContext, paramMap);
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"·µ»ØÊý¾Ý");
			
			jsonRes.put("result", res);
			
		}else{
			jsonRes.put("result", "false@É¾³ýÊ§°Ü£¡");
		}
		
		PrintWriter out = resp.getWriter();
		out.println(jsonRes.toString());
		out.flush();
		out.close();
	}

}
