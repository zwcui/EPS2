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

public class UpdateUser extends HttpServlet {

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
		
		User userBean = new User();
		String userID = row.has("userID")?row.getString("userID"):"";
		String userName = row.has("userName")?row.getString("userName"):"";
		String phone = row.has("phone")?row.getString("phone"):"";
		String telePhone = row.has("telePhone")?row.getString("telePhone"):"";
		String mail = row.has("mail")?row.getString("mail"):"";
		String superior = row.has("superior")?row.getString("superior"):"";
		String status = row.has("status")?row.getString("status"):"";
		String password = row.has("password")?row.getString("password"):"";
		String orgID = row.has("orgID")?row.getString("orgID"):"";
		String serialNo = row.has("serialNo")?row.getString("serialNo"):"";
		
		if(userID == null || "".equals(userID)){
			System.out.println("==未传入userID==");
			return;
		}
		if(userName == null) userName = "";
		if(phone == null) phone = "";
		if(telePhone == null) telePhone = "";
		if(mail == null) mail = "";
		if(superior == null) superior = "";
		if(status == null) status = "";
		if(password == null) password = "";
		if(orgID == null) orgID = "";
		
		userBean.setUserID(userID);
		userBean.setUserName(userName);
		userBean.setPhone(phone);
		userBean.setTelephone(telePhone);
		userBean.setMail(mail);
		userBean.setSuperior(superior);
		userBean.setStatus(status);
		userBean.setPassword(password);
		userBean.setOrgID(orgID);
		userBean.setSerialNo(serialNo);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		manager.updateUser(webApplicationContext, userBean);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("result", "true@保存成功!");
		out.println(jsonRes.toString());
		out.flush();
		out.close();
		
	}

}
