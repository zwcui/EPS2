package com.eps.bean;

public class OrderDatePlan {
    private String serialNo;

    private String orderInfoSerialNo;

    private String userID;

    private String orgID;

    private String finishDate;

    private String actualFinishDate;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrderInfoSerialNo() {
		return orderInfoSerialNo;
	}

	public void setOrderInfoSerialNo(String orderInfoSerialNo) {
		this.orderInfoSerialNo = orderInfoSerialNo;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getActualFinishDate() {
		return actualFinishDate;
	}

	public void setActualFinishDate(String actualFinishDate) {
		this.actualFinishDate = actualFinishDate;
	}

}