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
	 * 插入角色
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String createRole(WebApplicationContext webApplicationContext,Role role){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		Role roleTmp = userService.getRoleByRoleID(role.getRoleID());
		if(roleTmp != null){
			return "false@已存在该角色编号,角色为   "+roleTmp.getRoleName();
		}
		
		userService.insertRole(role);
		res = "true@新增成功";
		return res;
	}
	
	/**
	 * 更新角色
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
	 * 展示所有角色
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
	 * 删除角色
	 * @param session
	 * @param paramMap
	 * @return
	 */
	public String deleteRole(WebApplicationContext webApplicationContext,Map<String,String> paramMap){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		userService.deleteRoleByRoleID(paramMap.get("roleID"));
		
		res = "true@删除成功";
		return res;
		
	}
	
	/**
	 * 通过roleid展示该岗位下所有用户
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
