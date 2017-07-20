package com.eps.flow.action.script;

import com.eps.bean.Order;
import com.eps.dao.service.OrderService;
import com.eps.flow.action.FlowActionScript;

public class UpdateOrderStatus extends FlowActionScript {

	private String objectNo;
	
	private String state;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}


	@Override
	public Object execute() {
		
		Order order = null;
		OrderService orderService = (OrderService) getWebApplicationContext().getBean("orderService");
		
		order = orderService.queryBusinessOrderBySerialNo(objectNo);
		order.setState(state);
		orderService.updateBusinessOrder(order);
		
		return "";
	}

}
