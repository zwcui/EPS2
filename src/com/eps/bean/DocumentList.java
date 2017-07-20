package com.eps.bean;

public class DocumentList {
    private String serialNo;

    private String orderSerialNo;

    private String documentName;

    private String documentType;

    private String documentPath;

    private String uploadUserID;

    private String uploadOrgID;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getOrderSerialNo() {
		return orderSerialNo;
	}

	public void setOrderSerialNo(String orderSerialNo) {
		this.orderSerialNo = orderSerialNo;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getUploadUserID() {
		return uploadUserID;
	}

	public void setUploadUserID(String uploadUserID) {
		this.uploadUserID = uploadUserID;
	}

	public String getUploadOrgID() {
		return uploadOrgID;
	}

	public void setUploadOrgID(String uploadOrgID) {
		this.uploadOrgID = uploadOrgID;
	}

}