package com.eps.user.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.UserRole;

import net.sf.json.JSONObject;

public class AddUserRole extends HttpServlet {

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
			System.out.println("==未传入参数==");
			return;
		}
		
		UserManager manager = new UserManager();
		
		UserRole userRoleBean = new UserRole();
		String userID = row.has("userID")?row.getString("userID"):"";
		String roleID = row.has("roleID")?row.getString("roleID"):"";
		
		if(userID == null || "".equals(userID)){
			System.out.println("==未传入userID==");
			return;
		}
		
		if(roleID == null || "".equals(roleID)){
			System.out.println("==未传入roleID==");
			return;
		}
		
		userRoleBean.setUserID(userID);
		userRoleBean.setRoleID(roleID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		manager.addUserRole(webApplicationContext, userRoleBean);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("result", "true@添加岗位成功!");
		out.println(jsonRes.toString());
		out.flush();
		out.close();
		
	}

}
