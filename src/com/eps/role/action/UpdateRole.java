package com.eps.role.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Role;

import net.sf.json.JSONObject;

public class UpdateRole extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String jsonStr = req.getParameter("row");
		JSONObject row = JSONObject.fromObject(jsonStr);
		if(row == null){
			System.out.println("==δ�������==");
			return;
		}
		
		RoleManager manager = new RoleManager();
		
		Role roleBean = new Role();
		String roleID = row.has("roleID")?row.getString("roleID"):"";
		String roleName = row.has("roleName")?row.getString("roleName"):"";
		String status = row.has("status")?row.getString("status"):"";
		
		if(roleID == null || "".equals(roleID)){
			System.out.println("==δ����roleID==");
			return;
		}
		if(roleName == null) roleName = "";
		if(status == null) status = "";
		
		roleBean.setRoleID(roleID);
		roleBean.setRoleName(roleName);
		roleBean.setStatus(status);
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		manager.updateOrg(webApplicationContext, roleBean);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("result", "true@����ɹ�!");
		out.println(jsonRes.toString());
		out.flush();
		out.close();
		
	}

}
