package com.eps.dao.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.Order;
import com.eps.bean.OrderInfo;
import com.eps.dao.OrderDAO;

@Service
@Transactional
public class OrderService extends BaseService {

	private OrderDAO orderDAO;

	@Resource(name="orderDAO")
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	
	public void insertBusinessOrder(Order order){
		logger.info("insertBusinessOrder()");
		orderDAO.insertBusinessOrder(order);
	}
	
	public Order queryBusinessOrderBySerialNo(String serialNo){
		logger.info("queryBusinessOrderBySerialNo()");
		return orderDAO.queryBusinessOrderBySerialNo(serialNo);
	}

	public List<Order> queryBusinessOrderByState(String state){
		logger.info("queryBusinessOrderByState()");
		return orderDAO.queryBusinessOrderByState(state);
	}

	public List<Order> queryBusinessOrderByProductID(String productID){
		logger.info("queryBusinessOrderByProductID()");
		return orderDAO.queryBusinessOrderByProductID(productID);
	}

	public List<Order> queryBusinessOrderByStateAndUserID(Map paramMap){
		logger.info("queryBusinessOrderByStateAndUserID()");
		return orderDAO.queryBusinessOrderByStateAndUserID(paramMap);
	}

	public List<Order> queryBusinessOrderByCondition(Map paramMap){
		logger.info("queryBusinessOrderByCondition()");
		return orderDAO.queryBusinessOrderByCondition(paramMap);
	}

	public List<Order> queryBusinessOrderAndTaskByTask(Map paramMap){
		logger.info("queryBusinessOrderAndTaskByTask()");
		return orderDAO.queryBusinessOrderAndTaskByTask(paramMap);
	}

	public int updateBusinessOrder(Order order){
		logger.info("updateBusinessOrder()");
		return orderDAO.updateBusinessOrder(order);
	}

	public int deleteBusinessOrderBySerialNo(String serialNo){
		logger.info("deleteBusinessOrderBySerialNo()");
		return orderDAO.deleteBusinessOrderBySerialNo(serialNo);
	}

	public void insertBusinessOrderInfo(OrderInfo orderInfo){
		logger.info("insertBusinessOrderInfo()");
		orderDAO.insertBusinessOrderInfo(orderInfo);
	}
	
	public OrderInfo queryBusinessOrderInfoBySerialNo(String serialNo){
		logger.info("queryBusinessOrderInfoBySerialNo()");
		return orderDAO.queryBusinessOrderInfoBySerialNo(serialNo);
	}
	
	public List<OrderInfo> queryBusinessOrderInfoByOrderSerialNo(String orderSerialNo){
		logger.info("queryBusinessOrderInfoByOrderSerialNo()");
		return orderDAO.queryBusinessOrderInfoByOrderSerialNo(orderSerialNo);
	}
	
	public int updateBusinessOrderInfoBySerialNo(OrderInfo orderInfo){
		logger.info("updateBusinessOrderInfoBySerialNo()");
		return orderDAO.updateBusinessOrderInfoBySerialNo(orderInfo);
	}
	
	public int deleteBusinessOrderInfoByOrderSerialNo(String orderSerialNo){
		logger.info("deleteBusinessOrderInfoByOrderSerialNo()");
		return orderDAO.deleteBusinessOrderInfoByOrderSerialNo(orderSerialNo);
	}
	
	public int deleteBusinessOrderInfoBySerialNo(String serialNo){
		logger.info("deleteBusinessOrderInfoBySerialNo()");
		return orderDAO.deleteBusinessOrderInfoBySerialNo(serialNo);
	}
	
	
}
