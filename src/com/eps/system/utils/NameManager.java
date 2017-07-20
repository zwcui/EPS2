package com.eps.system.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.CodeLibrary;
import com.eps.bean.Org;
import com.eps.bean.User;
import com.eps.bean.UserRole;
import com.eps.dao.service.UserService;
import com.eps.system.cache.Cache;
import com.eps.system.cache.CacheManager;

public class NameManager {
	
	public static String getItemName(String codeNo,String itemNo){
		
		CodeLibrary codeLibrary = getCodeLibrary(codeNo,itemNo);
		
		if(codeLibrary != null) 
			return codeLibrary.getItemName();
		else
			return itemNo;
	}
	
	public static CodeLibrary getCodeLibrary(String codeNo,String itemNo){
		Map<String,Map<String,CodeLibrary>> codeMap = new TreeMap<String, Map<String,CodeLibrary>>();
		
		Cache codeCache = CacheManager.getCacheInfo("codes");
		codeMap = (Map<String,Map<String,CodeLibrary>>) codeCache.getValue();
		
		Map<String,CodeLibrary> libraryMap = codeMap.get(codeNo);
		CodeLibrary codeLibrary = libraryMap.get(itemNo);
		
		return codeLibrary;
	}
	
	public static String getUserName(String userID){
		
		if(userID != null && !"".equals(userID)){
			Map<String,User> userMap = new HashMap<String, User>();
			
			Cache userCache = CacheManager.getCacheInfo("users");
			userMap = (Map<String, User>) userCache.getValue();
			User user = userMap.get(userID);
			if(user != null)
				return user.getUserName();
			else
				return userID;
		}else{
			return "";
		}
	}
	
	public static String getOrgName(String orgID){
		
		if(orgID != null && !"".equals(orgID)){
			Map<String,Org> orgMap = new HashMap<String, Org>();
			
			Cache orgCache = CacheManager.getCacheInfo("orgs");
			orgMap = (Map<String, Org>) orgCache.getValue();
			Org org = orgMap.get(orgID);
			if(org != null) 
				return org.getOrgName();
			else
				return orgID;
		}else{
			return "";
		}
	}
	
	public static Map<String,String> getAllOrg(){
		Map<String,String> orgMap = new HashMap<String, String>();
		Cache orgCache = CacheManager.getCacheInfo("orgs");
		
		Map<String,Org> orgBeanMap = (Map<String, Org>) orgCache.getValue();
		for(String key : orgBeanMap.keySet()){
			Org orgBean = orgBeanMap.get(key);
			orgMap.put(orgBean.getOrgID(), orgBean.getOrgName());
		}
		
		return orgMap;
	}
	
	public static boolean hasRole(String userID,String roleID) throws IOException{
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("userID", userID);
		paramMap.put("roleID", roleID);
		
		UserRole userrole = userService.getUserRole(paramMap);
		if(userrole == null){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 根据codeno返回所有item
	 * @param codeNo
	 * @return
	 */
	public static Map<String,Map<String,String>> getAllItemByCodeNo(String codeNo){
		Cache codeCache = CacheManager.getCacheInfo("codes");
		Map<String,Map<String,CodeLibrary>> codeMap = (Map<String,Map<String,CodeLibrary>>) codeCache.getValue();
		Map<String,CodeLibrary> code = codeMap.get(codeNo);
		
		Map<String,Map<String,String>> map = new TreeMap<String, Map<String,String>>();
		
		for(String key : code.keySet()){
			Map<String,String> cm = new TreeMap<String, String>();
			CodeLibrary codeLibrary = code.get(key);
			cm.put(codeLibrary.getItemNo(), codeLibrary.getItemName());
			map.put(codeLibrary.getSortNo(), cm);
		}
		return map;
	}

	/**
	 * 判断当前机构是否能够查看order
	 * @param orgID
	 * @return
	 */
	public static boolean isShowOrder(String orgID,String orderStatus){
		CodeLibrary codeLibrary = getCodeLibrary("OrderStatus",orderStatus);
		if(codeLibrary != null){
			if(codeLibrary.getAttribute1() != null && codeLibrary.getAttribute1().contains(orgID) && orderStatus.equals(codeLibrary.getItemNo())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据机构返回可查看订单状态
	 * @param orgID
	 * @return
	 */
	public static String getOrderStatusFromOrg(String orgID){
		Map<String,Map<String,CodeLibrary>> codeMap = new HashMap<String, Map<String,CodeLibrary>>();
		
		Cache codeCache = CacheManager.getCacheInfo("codes");
		codeMap = (Map<String,Map<String,CodeLibrary>>) codeCache.getValue();
		
		StringBuffer orgIDs = new StringBuffer();
		
		Map<String,CodeLibrary> code = codeMap.get("OrderStatus");
		for(String key : code.keySet()){
			CodeLibrary codeLibrary = code.get(key);
			if(codeLibrary.getAttribute1().contains(orgID)){
				orgIDs.append(codeLibrary.getCodeNo());
				orgIDs.append(",");
			}
		}
		
		orgIDs.deleteCharAt(orgIDs.length()-1);
		
		return orgIDs.toString();
	}
	
	public static String convertCodeSource(String codeNo) {
		Map<String,Map<String,CodeLibrary>> codeMap = new HashMap<String, Map<String,CodeLibrary>>();
		
		Cache codeCache = CacheManager.getCacheInfo("codes");
		codeMap = (Map<String,Map<String,CodeLibrary>>) codeCache.getValue();
		
		StringBuffer codeSource = new StringBuffer("\"{value:");
		Map<String,CodeLibrary> libraryMap = codeMap.get(codeNo);
		for(String key : libraryMap.keySet()){
			CodeLibrary codeLibrary = libraryMap.get(key);
			codeSource.append(codeLibrary.getItemNo());
			codeSource.append(":");
			codeSource.append(codeLibrary.getItemName());
			codeSource.append(";");
		}
		codeSource.delete(codeSource.length()-1, codeSource.length());
		codeSource.append("}\"");
		
		return codeSource.toString();
	}
	
	public static void main(String[] args) throws IOException{
		System.out.println("===");
	}
}
