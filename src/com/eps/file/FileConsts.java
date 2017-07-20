package com.eps.file;

import net.sf.json.JSONObject;

public class FileConsts {

	public static JSONObject colSerialNo = JSONObject.fromObject("{\"field\":\"serialNo\",\"title\":\"��ˮ��\"}");
	
	public static JSONObject colFileName = JSONObject.fromObject("{\"field\":\"fileName\",\"title\":\"�ļ���\"}");
	
	public static JSONObject colFileType = JSONObject.fromObject("{\"field\":\"fileType\",\"title\":\"�ļ�����\"}");
	
	public static JSONObject colSavePath = JSONObject.fromObject("{\"field\":\"savePath\",\"title\":\"����·��\"}");
	
	public static JSONObject colStatus = JSONObject.fromObject("{\"field\":\"status\",\"title\":\"�ļ�״̬\",\"type\":\"itemno\"}");
	
	public static JSONObject colUploadUserID = JSONObject.fromObject("{\"field\":\"uploadUserID\",\"title\":\"�ϴ��û�\",\"type\":\"userid\"}");
	
	public static JSONObject colUploadOrgID = JSONObject.fromObject("{\"field\":\"uploadOrgID\",\"title\":\"�ϴ�����\",\"type\":\"orgid\"}");
	
	public static JSONObject colUploadTime = JSONObject.fromObject("{\"field\":\"uploadTime\",\"title\":\"�ϴ�ʱ��\"}");
	
	public static JSONObject colDownloadTimes = JSONObject.fromObject("{\"field\":\"downloadTimes\",\"title\":\"���ش���\"}");
	
	public static JSONObject colOrderSerialNo = JSONObject.fromObject("{\"field\":\"orderSerialNo\",\"title\":\"������\"}");
	
}
