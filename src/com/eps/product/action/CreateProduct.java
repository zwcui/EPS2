package com.eps.product.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Product;
import com.eps.system.utils.DBHelper;
import com.eps.system.utils.DateHelper;

import net.sf.json.JSONObject;

public class CreateProduct extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		ProductManager manager = new ProductManager(webApplicationContext);

		HttpSession httpSession = req.getSession();
		
		String jsonStr = req.getParameter("row");
		JSONObject row = JSONObject.fromObject(jsonStr);
		if(row == null){
			System.out.println("==未传入参数==");
			return;
		}
		
		Product productBean = new Product();
		String productID = row.has("productID")?row.getString("productID"):"";
		String productName = row.has("productName")?row.getString("productName"):"";
		String isInUse = row.has("isInUse")?row.getString("isInUse"):"";
		String isLocked = row.has("isLocked")?row.getString("isLocked"):"";
		String inputUserID = httpSession.getAttribute("userID")+"";
		String inputOrgID = httpSession.getAttribute("orgID")+"";
		String inputTime = DateHelper.getCurrentDateAndTime();
		
		if(productID == null) productID = "";
		if(productName == null) productName = "";
		if(isInUse == null) isInUse = "";
		if(isLocked == null) isLocked = "";
		
		productBean.setSerialNo(DBHelper.generateKey("PRODUCT_LIBRARY"));
		productBean.setProductID(productID);
		productBean.setProductName(productName);
		productBean.setIsInUse(isInUse);
		productBean.setIsLocked(isLocked);
		productBean.setInputUserID(inputUserID);
		productBean.setInputOrgID(inputOrgID);
		productBean.setInputTime(inputTime);
		
		manager.createProduct(productBean);
		
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
