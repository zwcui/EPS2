package com.eps.role.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Role;
import com.eps.dao.service.UserService;
import com.eps.model.action.AnalyseModel;


public class RoleManager {

	/**
	 * �����ɫ
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String createRole(WebApplicationContext webApplicationContext,Role role){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		Role roleTmp = userService.getRoleByRoleID(role.getRoleID());
		if(roleTmp != null){
			return "false@�Ѵ��ڸý�ɫ���,��ɫΪ   "+roleTmp.getRoleName();
		}
		
		userService.insertRole(role);
		res = "true@�����ɹ�";
		return res;
	}
	
	/**
	 * ���½�ɫ
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String updateOrg(WebApplicationContext webApplicationContext,Role role){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		userService.updateRole(role);
		
		res = "true";
		return res;
	}
	
	/**
	 * չʾ���н�ɫ
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showAllRole(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		List<Object> resList = new ArrayList<Object>();
		resList.addAll(userService.getAllRole());
		
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
	
	/**
	 * ɾ����ɫ
	 * @param session
	 * @param paramMap
	 * @return
	 */
	public String deleteRole(WebApplicationContext webApplicationContext,Map<String,String> paramMap){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		userService.deleteRoleByRoleID(paramMap.get("roleID"));
		
		res = "true@ɾ���ɹ�";
		return res;
		
	}
	
	/**
	 * ͨ��roleidչʾ�ø�λ�������û�
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showRoleUsersByRoleID(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		UserService userService = (UserService) webApplicationContext.getBean("userService");

		
		List<Object> resList = new ArrayList<Object>();
		resList.addAll(userService.getUserFromRoleID(paramMap.get("roleID")));
		
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
	
}
