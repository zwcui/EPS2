package com.eps.flow.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.system.cache.SqlSessionManager;

public class ShowCurrentFlowOpinion extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String taskSerialNo = req.getParameter("taskSerialNo");
		if(taskSerialNo == null){
			System.out.println("==未传入taskSerialNo==");
			return;
		}
		
		String modelName = req.getParameter("modelName");
		if(modelName == null || "".equals(modelName)){
			System.out.println("==未传入modelName==");
			return;
		}
		
		FlowManager manager = new FlowManager();
		SqlSessionFactory factory = SqlSessionManager.getSqlSessionFactory();
		SqlSession session = factory.openSession();
		
		HttpSession httpSession = req.getSession();
		String userID = (String) httpSession.getAttribute("userID");
			
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("taskSerialNo", taskSerialNo);
		paramMap.put("modelName", modelName);
		paramMap.put("CurUserID", userID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		String res = manager.showCurrentFlowOpinion(webApplicationContext, paramMap);
		
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
