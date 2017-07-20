package com.eps.user.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.User;

import net.sf.json.JSONObject;

public class UpdateUserPassword extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		UserManager manager = new UserManager();
		
		User userBean = new User();
		String userID = req.getParameter("userID");
		String oldpassword = req.getParameter("oldpassword");
		String newpassword = req.getParameter("newpassword");
		
		if(userID == null || "".equals(userID) || oldpassword == null || "".equals(oldpassword) || newpassword == null || "".equals(newpassword) ){
			System.out.println("==未传入用户名密码==");
			return;
		}
		
		userBean.setUserID(userID);
		userBean.setPassword(oldpassword);
		userBean.setNewpassword(newpassword);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		String message = manager.updateUserPassword(webApplicationContext, userBean);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("result", message);
		out.println(jsonRes.toString());
		out.flush();
		out.close();
		
	}

}
