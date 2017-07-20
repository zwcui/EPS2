package com.eps.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.Order;
import com.eps.bean.OrderInfo;

@MapperScan
public interface OrderDAO {

	public void insertBusinessOrder(Order order);
	
	public Order queryBusinessOrderBySerialNo(String serialNo);

	public List<Order> queryBusinessOrderByState(String state);

	public List<Order> queryBusinessOrderByProductID(String productID);

	public List<Order> queryBusinessOrderByStateAndUserID(Map paramMap);
//	public Order queryBusinessOrderByStateAndUserID(@Param("state")String state, @Param("userID")String userID);

	public List<Order> queryBusinessOrderByCondition(Map paramMap);

	public List<Order> queryBusinessOrderAndTaskByTask(Map paramMap);

	public int updateBusinessOrder(Order order);

	public int deleteBusinessOrderBySerialNo(String serialNo);
	
	public void insertBusinessOrderInfo(OrderInfo orderInfo);
	
	public OrderInfo queryBusinessOrderInfoBySerialNo(String serialNo);
	
	public List<OrderInfo> queryBusinessOrderInfoByOrderSerialNo(String orderSerialNo);
	
	public int updateBusinessOrderInfoBySerialNo(OrderInfo orderInfo);
	
	public int deleteBusinessOrderInfoByOrderSerialNo(String orderSerialNo);
	
	public int deleteBusinessOrderInfoBySerialNo(String serialNo);

}
