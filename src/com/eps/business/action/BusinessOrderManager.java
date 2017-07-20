package com.eps.business.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Order;
import com.eps.bean.OrderInfo;
import com.eps.dao.service.OrderService;
import com.eps.model.action.AnalyseModel;

public class BusinessOrderManager {

	public String createBusinessOrder(WebApplicationContext webApplicationContext, Order order) throws IOException{
		String res = "";
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		orderService.insertBusinessOrder(order);
		res = "true";
		return res;
	}
	
	public String createBusinessOrderInfo(WebApplicationContext webApplicationContext, OrderInfo orderInfo) throws IOException{
		String res = "";
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		orderService.insertBusinessOrderInfo(orderInfo);
		res = "true";
		return res;
	}
	
	public String updateBusinessOrder(WebApplicationContext webApplicationContext, Order order) throws IOException{
		String res = "";
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		orderService.updateBusinessOrder(order);
		res = "true";
		return res;
	}
	
	public String updateBusinessOrderInfo(WebApplicationContext webApplicationContext, OrderInfo orderInfo) throws IOException{
		String res = "";
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		orderService.updateBusinessOrderInfoBySerialNo(orderInfo);
		res = "true";
		return res;
	}
	
	public String showBusinessOrderBySerialNo(WebApplicationContext webApplicationContext, Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		Order order = orderService.queryBusinessOrderBySerialNo(paramMap.get("SerialNo"));
		resList.add(order);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList,webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String showBusinessOrderByCondition(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		List<Order> orderList = orderService.queryBusinessOrderByCondition(paramMap);
		resList.addAll(orderList);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String showBusinessOrderInfoByOrderSerialNo(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		String orderSerialNo = paramMap.get("orderSerialNo");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		List<OrderInfo> orderInfoList = orderService.queryBusinessOrderInfoByOrderSerialNo(orderSerialNo);
		if(orderInfoList != null && orderInfoList.size() > 0) resList.addAll(orderInfoList);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String showBusinessOrderByState(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		String state = paramMap.get("state");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		List<Order> orderList = orderService.queryBusinessOrderByState(state);
		resList.addAll(orderList);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String showBusinessOrderByProductID(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		String productID = paramMap.get("productID");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		List<Order> orderList = orderService.queryBusinessOrderByProductID(productID);
		resList.addAll(orderList);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String showBusinessOrderByStateAndUserID(WebApplicationContext webApplicationContext, Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		List<Order> orderList = orderService.queryBusinessOrderByStateAndUserID(paramMap);
		resList.addAll(orderList);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String showBusinessOrderByTask(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		String state = paramMap.get("state");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		List<Object> resList = new ArrayList<Object>();
		List<Order> orderList = orderService.queryBusinessOrderAndTaskByTask(paramMap);
		resList.addAll(orderList);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resList, webApplicationContext);
		} catch (ClassNotFoundException | NoSuchMethodException
				| SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return "false@"+e.getMessage();
		}
		
		res = "true@"+jsonRes.toString();
		return res;
	}
	
	public String deleteBusinessOrderBySerialNo(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		int orderCount = 0;
		int orderInfoCount = 0;
		
		String serialNo = paramMap.get("serialNo");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");

		orderCount = orderService.deleteBusinessOrderBySerialNo(serialNo);
		System.out.println("==成功删除"+orderCount+"条order");
		orderInfoCount = orderService.deleteBusinessOrderInfoByOrderSerialNo(serialNo);
		System.out.println("==成功删除"+orderInfoCount+"条orderinfo");
		
		res = "true@删除成功";
		return res;
	}
	
	public String deleteBusinessOrderInfoBySerialNo(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		int orderInfoCount = 0;
		String serialNo = paramMap.get("serialNo");
		
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");
		
		orderInfoCount = orderService.deleteBusinessOrderInfoBySerialNo(serialNo);
		System.out.println("==成功删除"+orderInfoCount+"条orderinfo");
		
		res = "true@删除成功";
		return res;
	}
	
}
