package com.eps.org.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Org;
import com.eps.dao.service.OrgService;
import com.eps.model.action.AnalyseModel;


public class OrgManager {

	/**
	 * �������
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String createOrg(WebApplicationContext webApplicationContext,Org org){
		String res = "";
		OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");
		
		Org orgTmp = orgService.getOrgByOrgID(org.getOrgID());
		if(orgTmp != null){
			return "false@�Ѵ��ڸû������,����Ϊ   "+orgTmp.getOrgName();
		}
		
		orgService.insertOrg(org);
		
		res = "true@�����ɹ�";
		return res;
	}
	
	/**
	 * ���»���
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String updateOrg(WebApplicationContext webApplicationContext,Org org){
		String res = "";
		OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");
		orgService.updateOrg(org);
		
		res = "true";
		return res;
	}
	
	/**
	 * չʾ���л���
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showAllOrg(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");

		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		List<Object> resList = new ArrayList<Object>();
		resList.addAll(orgService.getAllOrg());
		
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
	
	/**
	 * ɾ������
	 * @param session
	 * @param paramMap
	 * @return
	 */
	public String deleteOrg(WebApplicationContext webApplicationContext,Map<String,String> paramMap){
		String res = "";
		OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");

		int count = orgService.deleteOrg(paramMap.get("orgID"));
		System.out.println("==�ɹ�ɾ��"+count+"��Org");
		
		res = "true@ɾ���ɹ�";
		return res;
		
	}
	
}
