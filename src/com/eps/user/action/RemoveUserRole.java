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

public class RemoveUserRole extends HttpServlet {

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
			jsonRes.put("result", "false@ɾ��ʧ�ܣ�");
			PrintWriter out = resp.getWriter();
			out.println(jsonRes.toString());
			out.flush();
			out.close();
			return;
		}
		
		String userID = row.has("userID")?row.getString("userID"):"";
		String roleID = row.has("roleID")?row.getString("roleID"):"";
		if(userID == null || "".equals(userID)){
			jsonRes.put("result", "false@ɾ��ʧ�ܣ�");
			PrintWriter out = resp.getWriter();
			out.println(jsonRes.toString());
			out.flush();
			out.close();
			System.out.println("==δ����userID==");
			return;
		}
		
		if(roleID == null || "".equals(roleID)){
			jsonRes.put("result", "false@ɾ��ʧ�ܣ�");
			PrintWriter out = resp.getWriter();
			out.println(jsonRes.toString());
			out.flush();
			out.close();
			System.out.println("==δ����roleID==");
			return;
		}
		
		UserManager manager = new UserManager();
		
		UserRole userRole = new UserRole();
		userRole.setRoleID(roleID);
		userRole.setUserID(userID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		String res = manager.deleteUserRole(webApplicationContext, userRole);
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"��������");
			
			jsonRes.put("result", res);
			
		}else{
			jsonRes.put("result", "false@ɾ��ʧ�ܣ�");
		}
		
		PrintWriter out = resp.getWriter();
		out.println(jsonRes.toString());
		out.flush();
		out.close();
	}

}
