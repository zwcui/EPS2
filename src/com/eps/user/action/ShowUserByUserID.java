package com.eps.user.action;

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

public class ShowUserByUserID extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String modelName = req.getParameter("modelName");
		String userID = req.getParameter("userID");
		if(modelName == null || "".equals(modelName)){
			System.out.println("==δ����modelName==");
			return;
		}
		
		HttpSession httpsession = req.getSession();
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("modelName", modelName);
		paramMap.put("userID", userID);
		paramMap.put("CurUserID", (String) httpsession.getAttribute("userID"));
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		UserManager manamger = new UserManager();
		String res = manamger.showUserByUserID(webApplicationContext, paramMap);
		
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"��������");
			PrintWriter out = resp.getWriter();
			out.println(res.substring(res.indexOf("@")+1));
			out.flush();
			out.close();
		}
		
	}

}
