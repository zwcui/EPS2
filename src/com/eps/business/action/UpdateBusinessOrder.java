package com.eps.business.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Order;
import com.eps.system.utils.DateHelper;

import net.sf.json.JSONObject;

public class UpdateBusinessOrder extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		BusinessOrderManager manager = new BusinessOrderManager();

		HttpSession httpSession = req.getSession();
		
		String jsonStr = req.getParameter("row");
		JSONObject row = JSONObject.fromObject(jsonStr);
		if(row == null){
			System.out.println("==未传入参数==");
			return;
		}
		
		Order order = new Order();
		String serialNo = row.has("serialNo")?row.getString("serialNo"):"";
		String businessType = row.has("businessType")?row.getString("businessType"):"";
		String orderType = row.has("orderType")?row.getString("orderType"):"";
		String flowNo = row.has("flowNo")?row.getString("flowNo"):"";
		String count = row.has("count")?row.getString("count"):"";
		String state = row.has("state")?row.getString("state"):"";
		String inputUserID = httpSession.getAttribute("userID")+"";
		String inputOrgID = httpSession.getAttribute("orgID")+"";
		String inputTime = DateHelper.getCurrentDateAndTime();
		String operateUserID = "";
		String operateOrgID = "";
		String updateTime = "";
		String orderDate = row.has("orderDate")?row.getString("orderDate"):"";
		String shouldFinishDate = row.has("shouldFinishDate")?row.getString("shouldFinishDate"):"";
		String adviseFinishDate = row.has("adviseFinishDate")?row.getString("adviseFinishDate"):"";
		String customerName = row.has("customerName")?row.getString("customerName"):"";
		String customerAddr = row.has("customerAddr")?row.getString("customerAddr"):"";
		String customerPhone = row.has("customerPhone")?row.getString("customerPhone"):"";
		String remark = row.has("remark")?row.getString("remark"):"";
		String productID = row.has("productID")?row.getString("productID"):"";
		String orderNo = row.has("orderNo")?row.getString("orderNo"):"";
		String orderBackRemark = row.has("orderBackRemark")?row.getString("orderBackRemark"):"";
		String orderRecord = row.has("orderRecord")?row.getString("orderRecord"):"";
		String skipCasting = row.has("skipCasting")?row.getString("skipCasting"):"";
		String skipMetalWorking = row.has("skipMetalWorking")?row.getString("skipMetalWorking"):"";
		String skipAssembly = row.has("skipAssembly")?row.getString("skipAssembly"):"";
		String skipPainting = row.has("skipPainting")?row.getString("skipPainting"):"";
		String skipInstrument = row.has("skipInstrument")?row.getString("skipInstrument"):"";
		
		if(serialNo == null || "".equals(serialNo)){
			System.out.println("==未传入serialno==");
			return;
		}
		if(businessType == null) businessType = "";
		if(orderType == null) orderType = "";
		if(flowNo == null) flowNo = "";
		if(count == null) count = "";
		if(state == null) state = "";
		if(orderDate == null) orderDate = "";
		if(shouldFinishDate == null) shouldFinishDate = "";
		if(adviseFinishDate == null) adviseFinishDate = "";
		if(customerName == null) customerName = "";
		if(customerAddr == null) customerAddr = "";
		if(customerPhone == null) customerPhone = "";
		if(remark == null) remark = "";
		if(productID == null) productID = "";
		if(orderNo == null) orderNo = "";
		if(orderBackRemark == null) orderBackRemark = "";
		if(orderRecord == null) orderRecord = "";
		if(skipCasting == null) skipCasting = "";
		if(skipMetalWorking == null) skipMetalWorking = "";
		if(skipAssembly == null) skipAssembly = "";
		if(skipPainting == null) skipPainting = "";
		if(skipInstrument == null) skipInstrument = "";
		
		order.setSerialNo(serialNo);
		order.setBusinessType(businessType);
		order.setOrderType(orderType);
		order.setFlowNo(flowNo);
		order.setCount(count);
		order.setState(state);
		order.setInputUserID(inputUserID);
		order.setInputOrgID(inputOrgID);
		order.setInputTime(inputTime);
		order.setOperateUserID(operateUserID);
		order.setOperateOrgID(operateOrgID);
		order.setUpdateTime(updateTime);
		order.setOrderDate(orderDate);
		order.setShouldFinishDate(shouldFinishDate);
		order.setAdviseFinishDate(adviseFinishDate);
		order.setCustomerName(customerName);
		order.setCustomerAddr(customerAddr);
		order.setCustomerPhone(customerPhone);
		order.setRemark(remark);
		order.setProductID(productID);
		order.setOrderNo(orderNo);
		order.setOrderBackRemark(orderBackRemark);
		order.setOrderRecord(orderRecord);
		order.setSkipCasting(skipCasting);
		order.setSkipMetalWorking(skipMetalWorking);
		order.setSkipAssembly(skipAssembly);
		order.setSkipPainting(skipPainting);
		order.setSkipInstrument(skipInstrument);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		manager.updateBusinessOrder(webApplicationContext, order);
		
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
