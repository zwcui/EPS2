package com.eps.user.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.User;
import com.eps.bean.UserRole;
import com.eps.dao.service.UserService;
import com.eps.model.action.AnalyseModel;


public class UserManager {

	/**
	 * 插入用户
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String createUser(WebApplicationContext webApplicationContext,User user){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		User userTmp = userService.getUserByUserID(user.getUserID());
		if(userTmp != null){
			return "false@已存在该用户编号,用户为   "+userTmp.getUserName();
		}
		
		userService.insertUser(user);
		res = "true@新增成功";
		return res;
	}
	
	/**
	 * 更新用户
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String updateUser(WebApplicationContext webApplicationContext,User user){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		userService.updateUser(user);
		res = "true";
		return res;
	}
	
	/**
	 * 更新用户密码
	 * @param session
	 * @param userBean
	 * @return
	 */
	public String updateUserPassword(WebApplicationContext webApplicationContext,User user){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");

		int count = userService.updateUserPassword(user);
		
		if(count>0)
		res = "true@修改密码成功！";
		else res = "true@原密码输入不正确！";
		return res;
	}
	
	/**
	 * 展示所有用户
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showAllUser(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		List<Object> resList = new ArrayList<Object>();
		
		List<User> userList = userService.getAllUser();
		resList.addAll(userList);
		
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
	 * 根据userid返回用户
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showUserByUserID(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		List<Object> resList = new ArrayList<Object>();
		resList.add(userService.getUserByUserID(paramMap.get("userID")));
		
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
	 * 通过userid展示该用户所有角色
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showUserRoleByUserID(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		UserService userService = (UserService) webApplicationContext.getBean("userService");

		List<Object> resList = new ArrayList<Object>();
		resList.addAll(userService.getRolesByUserID(paramMap.get("userID")));
		
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
	 * 增加角色
	 * @param session
	 * @param userRoleBean
	 * @return
	 */
	public String addUserRole(WebApplicationContext webApplicationContext,UserRole userRole){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");

		userService.insertUserRole(userRole);
		
		res = "true";
		return res;
	}
	
	/**
	 * 删除角色
	 * @param session
	 * @param paramMap
	 * @return
	 */
	public String deleteUserRole(WebApplicationContext webApplicationContext,UserRole userRole){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		userService.deleteUserRole(userRole);
		
		res = "true@删除成功";
		return res;
	}
	
	/**
	 * 删除用户
	 * @param session
	 * @param paramMap
	 * @return
	 */
	public String deleteUser(WebApplicationContext webApplicationContext,Map<String,String> paramMap){
		String res = "";
		UserService userService = (UserService) webApplicationContext.getBean("userService");

		userService.deleteUser(paramMap.get("userID"));
		
		res = "true@删除成功";
		return res;
	}
	
}
