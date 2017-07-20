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

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.FlowTask;
import com.eps.dao.service.FlowService;

public class ShowNextTaskUserAndOrg extends HttpServlet {

	public ShowNextTaskUserAndOrg(){
		System.out.println("您正在访问。。。。。。。");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String res = "";
		String taskSerialNo = req.getParameter("taskSerialNo");
		if(taskSerialNo == null){
			System.out.println("==未传入任务流水号==");
			return;
		}
		String modelName = req.getParameter("modelName");
		if(modelName == null || "".equals(modelName)){
			System.out.println("==未传入modelName==");
			return;
		}
		
		FlowManager manager = new FlowManager();
		
		HttpSession httpsession = req.getSession();
		String userID = (String) httpsession.getAttribute("userID");
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("taskSerialNo", taskSerialNo);
		paramMap.put("modelName", modelName);
		paramMap.put("CurUserID", userID);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		
		FlowTask currentTask = flowService.queryCurrentFlowTask(taskSerialNo);
		String currentPhaseAction = currentTask.getPhaseAction();
		if(currentPhaseAction == null || "".equals(currentPhaseAction)){
			res = "{\"result\":\"false@未签署意见,不能继续提交!\"}";
		}
		else{
			res = manager.getNextTaskUserIDAndOrgID(webApplicationContext,paramMap);
			if(res.contains("@") && "true".equals(res.split("@")[0])){
				res = res.split("@")[1];
			}
		}
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");

		System.out.println(res+"返回数据");
		PrintWriter out = resp.getWriter();
		out.println(res);
		out.flush();
		out.close();
		
	}

}
