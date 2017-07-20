package com.eps.file;

import net.sf.json.JSONObject;

public class FileConsts {

	public static JSONObject colSerialNo = JSONObject.fromObject("{\"field\":\"serialNo\",\"title\":\"流水号\"}");
	
	public static JSONObject colFileName = JSONObject.fromObject("{\"field\":\"fileName\",\"title\":\"文件名\"}");
	
	public static JSONObject colFileType = JSONObject.fromObject("{\"field\":\"fileType\",\"title\":\"文件类型\"}");
	
	public static JSONObject colSavePath = JSONObject.fromObject("{\"field\":\"savePath\",\"title\":\"保存路径\"}");
	
	public static JSONObject colStatus = JSONObject.fromObject("{\"field\":\"status\",\"title\":\"文件状态\",\"type\":\"itemno\"}");
	
	public static JSONObject colUploadUserID = JSONObject.fromObject("{\"field\":\"uploadUserID\",\"title\":\"上传用户\",\"type\":\"userid\"}");
	
	public static JSONObject colUploadOrgID = JSONObject.fromObject("{\"field\":\"uploadOrgID\",\"title\":\"上传机构\",\"type\":\"orgid\"}");
	
	public static JSONObject colUploadTime = JSONObject.fromObject("{\"field\":\"uploadTime\",\"title\":\"上传时间\"}");
	
	public static JSONObject colDownloadTimes = JSONObject.fromObject("{\"field\":\"downloadTimes\",\"title\":\"下载次数\"}");
	
	public static JSONObject colOrderSerialNo = JSONObject.fromObject("{\"field\":\"orderSerialNo\",\"title\":\"订单号\"}");
	
}
