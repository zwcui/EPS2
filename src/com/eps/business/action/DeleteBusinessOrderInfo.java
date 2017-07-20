package com.eps.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.system.cache.SqlSessionManager;

import net.sf.json.JSONObject;

public class DeleteBusinessOrderInfo extends HttpServlet{
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
		
		String serialNo = req.getParameter("serialNo");
		if(serialNo == null || "".equals(serialNo)){
			jsonRes.put("result", "false@ɾ��ʧ�ܣ�");
			PrintWriter out = resp.getWriter();
			out.println(jsonRes.toString());
			out.flush();
			out.close();
			System.out.println("==δ������ˮ��==");
			return;
		}
		
		BusinessOrderManager manager = new BusinessOrderManager();
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("serialNo", serialNo);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		String res = manager.deleteBusinessOrderInfoBySerialNo(webApplicationContext, paramMap);
		
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
