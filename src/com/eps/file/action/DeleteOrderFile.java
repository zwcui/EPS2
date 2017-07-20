package com.eps.file.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import net.sf.json.JSONObject;

public class DeleteOrderFile extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String serialNo = req.getParameter("serialNo");
		if(serialNo == null || "".equals(serialNo)){
			System.out.println("==未传入流水号==");
			return;
		}
		
		FileManager manager = new FileManager();
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("serialNo", serialNo);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		String res = manager.deleteFile(webApplicationContext, paramMap);
		
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
