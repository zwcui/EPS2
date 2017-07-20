package com.eps.org.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Org;
import com.eps.system.cache.SqlSessionManager;
import com.eps.system.utils.DBHelper;

import net.sf.json.JSONObject;

public class CreateOrg extends HttpServlet {

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
		
		OrgManager manager = new OrgManager();
		
		Org org = new Org();
		String orgID = row.has("orgID")?row.getString("orgID"):"";
		String orgName = row.has("orgName")?row.getString("orgName"):"";
		String orgLevel = row.has("orgLevel")?row.getString("orgLevel"):"";
		String status = row.has("status")?row.getString("status"):"";
		String sortNo = row.has("sortNo")?row.getString("sortNo"):"";
		
		if(orgID == null || "".equals(orgID)){
			System.out.println("==未传入orgID==");
			return;
		}
		if(orgName == null) orgName = "";
		if(orgLevel == null) orgLevel = "";
		if(sortNo == null) sortNo = "";
		if(status == null) status = "";
		
		org.setSerialNo(DBHelper.generateKey("ORG_INFO"));
		org.setOrgID(orgID);
		org.setOrgName(orgName);
		org.setOrgLevel(orgLevel);
		org.setStatus(status);
		org.setSortNo(sortNo);
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		manager.createOrg(webApplicationContext, org);
		
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
