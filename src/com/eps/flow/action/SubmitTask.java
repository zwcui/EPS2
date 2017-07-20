package com.eps.flow.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SubmitTask extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String taskSerialNo = req.getParameter("taskSerialNo");
		if(taskSerialNo == null || "".equals(taskSerialNo)){
			System.out.println("==未传入taskSerialNo==");
			return;
		}
		
		String nextTaskStr = req.getParameter("nextTask");
		JSONArray nextTaskArray = JSONArray.fromObject(nextTaskStr);
		
		//test
//		JSONArray nextTaskArray = new JSONArray();
//		JSONObject json = new JSONObject();
//		json.put("nextTaskUserID", "jywen");
//		json.put("nextTaskOrgID", "0001");
//		nextTaskArray.add(json);
		
		FlowManager flowManager = new FlowManager();
		String res = "";
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		for(int i=0;i<nextTaskArray.size();i++){
			JSONObject nextTask = nextTaskArray.getJSONObject(i);
			String userID = nextTask.getString("nextTaskUserID");
			String orgID = nextTask.getString("nextTaskOrgID");
			res = flowManager.submitTask(taskSerialNo, userID, orgID,webApplicationContext);
		}
		
		JSONObject jsonRes = new JSONObject();
		if(res.contains("@") && "true".equals(res.split("@")[0])){
			
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=gb2312");

			System.out.println(res.split("@")[1]+"返回数据");
			jsonRes.put("result", res);
			
		}else{
			jsonRes.put("result", "false@"+res.split("@")[1]);
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");

		PrintWriter out = resp.getWriter();
		out.println(jsonRes.toString());
		out.flush();
		out.close();
		
	}

}
