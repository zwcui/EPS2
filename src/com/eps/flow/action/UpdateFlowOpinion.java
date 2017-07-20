package com.eps.flow.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONObject;

public class UpdateFlowOpinion extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String taskSerialNo = req.getParameter("taskSerialNo");
		String phaseAction = req.getParameter("phaseAction");
		String phaseOpinion = req.getParameter("phaseOpinion");

		if(taskSerialNo == null || "".equals(taskSerialNo)){
			System.out.println("==Î´´«ÈëtaskSerialNo==");
			return;
		}
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		FlowManager manager = new FlowManager();
		String res = manager.updateFlowOpinion(taskSerialNo, phaseAction, phaseOpinion,webApplicationContext);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("result", res);
		out.println(jsonRes.toString());
		out.flush();
		out.close();
		
	}

}
