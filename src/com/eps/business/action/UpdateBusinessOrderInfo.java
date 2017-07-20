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

import com.eps.bean.OrderInfo;

import net.sf.json.JSONObject;

public class UpdateBusinessOrderInfo extends HttpServlet {

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
		
		OrderInfo orderInfo = new OrderInfo();
		String serialNo = row.has("serialNo")?row.getString("serialNo"):"";
		String orderSerialNo = row.has("serialNo")?row.getString("orderSerialNo"):"";
		String standard = row.has("standard")?row.getString("standard"):"";
		String num = row.has("num")?row.getString("num"):"";
		String price = row.has("price")?row.getString("price"):"";
		String businessSum = row.has("businessSum")?row.getString("businessSum"):"";
		String productID = row.has("productID")?row.getString("productID"):"";
		String productType = row.has("productType")?row.getString("productType"):"";
		String latusRectum = row.has("latusRectum")?row.getString("latusRectum"):"";
		String material = row.has("material")?row.getString("material"):"";
		String casting_S_DATE = row.has("casting_S_DATE")?row.getString("casting_S_DATE"):"";
		String casting_A_DATE = row.has("casting_A_DATE")?row.getString("casting_A_DATE"):"";
		String casting_REMARK = row.has("casting_REMARK")?row.getString("casting_REMARK"):"";
		String metalWorking_S_DATE = row.has("metalWorking_S_DATE")?row.getString("metalWorking_S_DATE"):"";
		String metalWorking_A_DATE = row.has("metalWorking_A_DATE")?row.getString("metalWorking_A_DATE"):"";
		String metalWorking_REMARK = row.has("metalWorking_REMARK")?row.getString("metalWorking_REMARK"):"";
		String assembling_S_DATE = row.has("assembling_S_DATE")?row.getString("assembling_S_DATE"):"";
		String assembling_A_DATE = row.has("assembling_A_DATE")?row.getString("assembling_A_DATE"):"";
		String assembling_REMARK = row.has("assembling_REMARK")?row.getString("assembling_REMARK"):"";
		String painting_S_DATE = row.has("painting_S_DATE")?row.getString("painting_S_DATE"):"";
		String painting_A_DATE = row.has("painting_A_DATE")?row.getString("painting_A_DATE"):"";
		String painting_REMARK = row.has("painting_REMARK")?row.getString("painting_REMARK"):"";
		String instrument_S_DATE = row.has("instrument_S_DATE")?row.getString("instrument_S_DATE"):"";
		String instrument_A_DATE = row.has("instrument_A_DATE")?row.getString("instrument_A_DATE"):"";
		String instrument_REMARK = row.has("instrument_REMARK")?row.getString("instrument_REMARK"):"";
		
		if(serialNo == null || "".equals(serialNo)){
			System.out.println("==未传入serialno==");
			return;
		}
		if(orderSerialNo == null) orderSerialNo = "";
		if(standard == null) standard = "";
		if(num == null) num = "";
		if(price == null) price = "";
		if(businessSum == null) businessSum = "";
		if(productID == null) productID = "";
		if(productType == null) productType = "";
		if(latusRectum == null) latusRectum = "";
		if(material == null) material = "";
		
		orderInfo.setSerialNo(serialNo);
		orderInfo.setOrderSerialNo(orderSerialNo);
		orderInfo.setStandard(standard);
		orderInfo.setNum(num);
		orderInfo.setPrice(price);
		orderInfo.setBusinessSum(businessSum);
		orderInfo.setProductID(productID);
		orderInfo.setProductType(productType);
		orderInfo.setLatusRectum(latusRectum);
		orderInfo.setMaterial(material);
		orderInfo.setCasting_S_DATE(casting_S_DATE);
		orderInfo.setCasting_A_DATE(casting_A_DATE);
		orderInfo.setCasting_REMARK(casting_REMARK);
		orderInfo.setMetalWorking_S_DATE(metalWorking_S_DATE);
		orderInfo.setMetalWorking_A_DATE(metalWorking_A_DATE);
		orderInfo.setMetalWorking_REMARK(metalWorking_REMARK);
		orderInfo.setAssembling_S_DATE(assembling_S_DATE);
		orderInfo.setAssembling_A_DATE(assembling_A_DATE);
		orderInfo.setAssembling_REMARK(assembling_REMARK);
		orderInfo.setPainting_S_DATE(painting_S_DATE);
		orderInfo.setPainting_A_DATE(painting_A_DATE);
		orderInfo.setPainting_REMARK(painting_REMARK);
		orderInfo.setInstrument_S_DATE(instrument_S_DATE);
		orderInfo.setInstrument_A_DATE(instrument_A_DATE);
		orderInfo.setInstrument_REMARK(instrument_REMARK);
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		manager.updateBusinessOrderInfo(webApplicationContext, orderInfo);
		
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=gb2312");
		PrintWriter out = resp.getWriter();
		JSONObject jsonRes = new JSONObject();
		jsonRes.put("result", "true@保存订单明细成功!");
		out.println(jsonRes.toString());
		out.flush();
		out.close();
	}

}
