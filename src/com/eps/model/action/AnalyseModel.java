package com.eps.model.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.CodeLibrary;
import com.eps.bean.FlowCatalog;
import com.eps.bean.ModelCatalog;
import com.eps.bean.ModelLibrary;
import com.eps.bean.Org;
import com.eps.bean.Product;
import com.eps.bean.User;
import com.eps.bean.UserRole;
import com.eps.dao.service.FlowService;
import com.eps.dao.service.ModelService;
import com.eps.dao.service.OrgService;
import com.eps.dao.service.ProductService;
import com.eps.dao.service.UserService;
import com.eps.system.cache.Cache;
import com.eps.system.cache.CacheManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AnalyseModel {
	
	private static String roleStr;
	
	public static String analyse(String modelName,String userID,List<Object> dataSource,WebApplicationContext webApplicationContext) throws IOException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		ModelService modelService = (ModelService) webApplicationContext.getBean("modelService");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("modelName", modelName);
		List<ModelLibrary> resMap = new ArrayList<ModelLibrary>();
		resMap = modelService.queryModelLibrary(modelName);
		
		JSONObject modelJSON = new JSONObject();
		
		JSONArray modelColArray = new JSONArray();
		JSONArray modelValArray = new JSONArray();
		StringBuffer modelColNameArray = new StringBuffer();
		
		for(Iterator iter = resMap.iterator();iter.hasNext();){
			ModelLibrary model = (ModelLibrary)iter.next();
			//是否可见
//			if(getModelColAblilty(model.getIsVisible(),getUserRole(userID,session))){
				JSONObject jsonCol = new JSONObject();
				if(!isEmpty(model.getColKey()))
					jsonCol.put("name", model.getColKey());
				if(!isEmpty(model.getColKey()))
					jsonCol.put("index", model.getColKey());
				if(!isEmpty(model.getWidth()))
					jsonCol.put("width", model.getWidth());
				if(!isEmpty(model.getSortType()))
					jsonCol.put("sorttype", model.getSortType());
				if(!isEmpty(model.getIsRelizable()))
					jsonCol.put("resize", getModelColAblilty(model.getIsRelizable(),getUserRole(userID, webApplicationContext)));
				if(!isEmpty(model.getIsEditable()))
					jsonCol.put("editable", getModelColAblilty(model.getIsEditable(),getUserRole(userID, webApplicationContext)));
				if(!isEmpty(model.getEditType()))
					jsonCol.put("edittype", model.getEditType());
				if(!isEmpty(model.getEditOptionCode()))
					jsonCol.put("editoptions", getEditOptions(model.getEditOptionCode(), webApplicationContext));
				if(!isEmpty(model.getFormatter()))
					jsonCol.put("formatter", model.getFormatter());
				if(!isEmpty(model.getIsVisible()))
					jsonCol.put("hidden", !getModelColAblilty(model.getIsVisible(),getUserRole(userID, webApplicationContext)));
				if(!isEmpty(model.getUnformat()))
					jsonCol.put("unformat", model.getUnformat());
				if(!isEmpty(model.getColName())){
					modelColNameArray.append("'");
					modelColNameArray.append(model.getColName());
					modelColNameArray.append("',");
				}
				modelColArray.add(jsonCol);
				
//			}
		}
		
		modelJSON.put("colNames", "["+modelColNameArray.deleteCharAt(modelColNameArray.length()-1).toString()+"]");
		
		modelJSON.put("column", modelColArray);
		
		
		ModelCatalog modelCatalog = modelService.queryModelCatalog(modelName);
//		String classPath = modelCatalog.getClassPath();
		
		for(int i=0;i<dataSource.size();i++){
			
//			Class<?> modelClass = (Class<T>) Class.forName(classPath);
			Object modelClass = dataSource.get(i);
			
			JSONObject jsonValue = new JSONObject();
			
			for(Iterator iter2 = resMap.iterator();iter2.hasNext();){
				ModelLibrary model = (ModelLibrary)iter2.next();
					
				String methodName = model.getColValue();
				if(methodName != null && !"".equals(methodName)){
					if(!methodName.toLowerCase().startsWith("com")){
						//首字母字段改成大写
						methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1,methodName.length());
						methodName = "get" + methodName;
						Method method = modelClass.getClass().getMethod(methodName);
						String value = (String) method.invoke(modelClass);
						if(value == null) value = "";
						jsonValue.put(model.getColKey(), value);
					}
				}
			}
			
			for(Iterator iter3 = resMap.iterator();iter3.hasNext();){
				ModelLibrary model = (ModelLibrary)iter3.next();
				
				String methodName = model.getColValue();
				if(methodName != null && !"".equals(methodName) && methodName.toLowerCase().startsWith("com")){
					String className = methodName.substring(0,methodName.lastIndexOf("."));
					String param = methodName.substring(methodName.indexOf("(")+1, methodName.indexOf(")"));
					methodName = methodName.substring(className.length()+1, methodName.lastIndexOf("("));
					Object classObject = null;
					try {
						classObject = Class.forName(className).newInstance();
						Method method = classObject.getClass().getMethod(methodName, String.class);
						String paramValue = jsonValue.getString(param);
						if(param == null) param = "";
						String value = (String) method.invoke(classObject,paramValue);
						jsonValue.put(model.getColKey(), value);
					} catch (InstantiationException e) {
						e.printStackTrace();
						return "false@"+e.getMessage();
					}
				}
			}
			
			
			
			modelValArray.add(jsonValue);
		}
		
		modelJSON.put("data", modelValArray);
		
		return modelJSON.toString();
	}
	
	private static boolean isEmpty(String str){
		if(str == null || "".equals(str))
			return true;
		else
			return false;
	}
	
	/**
	 * 获取当前用户角色
	 * @param userID
	 * @param session
	 * @return
	 */
	private static String getUserRole(String userID, WebApplicationContext webApplicationContext){
		if(roleStr != null && !"".equals(roleStr))
			return roleStr;
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		StringBuffer roleStrBuf = new StringBuffer();
		
		List<UserRole> resList = new ArrayList<UserRole>();
		resList = userService.getUserRoleByUserID(userID);
		
		for(UserRole userRole : resList){
			roleStrBuf.append(userRole.getRoleID());
			roleStrBuf.append(",");
		}
		if(roleStrBuf.length() > 0)
			roleStrBuf.deleteCharAt(roleStrBuf.length()-1);
		roleStr = roleStrBuf.toString();
		return roleStr;
	}
	
	/**
	 * 判断用户角色是否包含在字段角色中
	 * @param modelRole
	 * @param userRole
	 * @return
	 */
	private static boolean getModelColAblilty(String modelRole,String userRole){
		
		if("".equals(modelRole) || "ALL".equals(modelRole))
			return true;
		if("NONE".equals(modelRole))
			return false;
		int count = 0;
		for(String user : userRole.split(",")){
			if(modelRole.indexOf(user) >= 0){
				return true;
			}else{
				count++;
			}
			if(count == userRole.split(",").length){
				return false;
			}
		}
		
		return false;
	}
	
	/**
	 * 根据code返回格式：{value:1:常规;2:定制}
	 * @param codeNo
	 * @return
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private static String getEditOptions(String codeNo, WebApplicationContext webApplicationContext) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		if(codeNo.contains("@")){
			StringBuffer codeSource = new StringBuffer("{value:\"");
			
			//TODO:try to use spel
			String param = codeNo.split("@")[0];
			if("FLOW".equals(param.toUpperCase())){
				FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
				List<FlowCatalog> resMap = flowService.queryAllFlowNo();
				String keyMethod = "get" + codeNo.split("@")[1];
				String valueMethod = "get" + codeNo.split("@")[2];
				for(int i=0;i<resMap.size();i++){
					Object modelClass = resMap.get(i);
					Method method1 = modelClass.getClass().getMethod(keyMethod);
					Method method2 = modelClass.getClass().getMethod(valueMethod);
					String key = (String) method1.invoke(modelClass);
					String value = (String) method2.invoke(modelClass);
					codeSource.append(key);
					codeSource.append(":");
					codeSource.append(value);
					codeSource.append(";");
				}
			}
			if("PRODUCT".equals(param.toUpperCase())){
				ProductService productService = (ProductService) webApplicationContext.getBean("productService");
				List<Product> resMap = productService.queryAllProduct();
				String keyMethod = "get" + codeNo.split("@")[1];
				String valueMethod = "get" + codeNo.split("@")[2];
				for(int i=0;i<resMap.size();i++){
					Object modelClass = resMap.get(i);
					Method method1 = modelClass.getClass().getMethod(keyMethod);
					Method method2 = modelClass.getClass().getMethod(valueMethod);
					String key = (String) method1.invoke(modelClass);
					String value = (String) method2.invoke(modelClass);
					codeSource.append(key);
					codeSource.append(":");
					codeSource.append(value);
					codeSource.append(";");
				}
			}
			if("USER".equals(param.toUpperCase())){
				UserService userService = (UserService) webApplicationContext.getBean("userService");
				List<User> resMap = userService.getAllUser();
				String keyMethod = "get" + codeNo.split("@")[1];
				String valueMethod = "get" + codeNo.split("@")[2];
				for(int i=0;i<resMap.size();i++){
					Object modelClass = resMap.get(i);
					Method method1 = modelClass.getClass().getMethod(keyMethod);
					Method method2 = modelClass.getClass().getMethod(valueMethod);
					String key = (String) method1.invoke(modelClass);
					String value = (String) method2.invoke(modelClass);
					codeSource.append(key);
					codeSource.append(":");
					codeSource.append(value);
					codeSource.append(";");
				}
			}
			if("ORG".equals(param.toUpperCase())){
				OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");
				List<Org> resMap = orgService.getAllOrg();
				String keyMethod = "get" + codeNo.split("@")[1];
				String valueMethod = "get" + codeNo.split("@")[2];
				for(int i=0;i<resMap.size();i++){
					Object modelClass = resMap.get(i);
					Method method1 = modelClass.getClass().getMethod(keyMethod);
					Method method2 = modelClass.getClass().getMethod(valueMethod);
					String key = (String) method1.invoke(modelClass);
					String value = (String) method2.invoke(modelClass);
					codeSource.append(key);
					codeSource.append(":");
					codeSource.append(value);
					codeSource.append(";");
				}
			}
			
			
			codeSource.delete(codeSource.length()-1, codeSource.length());
			codeSource.append("\"}");
			return codeSource.toString();
		}else{
			
			Map<String,Map<String,CodeLibrary>> codeMap = new HashMap<String, Map<String,CodeLibrary>>();
			
			Cache codeCache = CacheManager.getCacheInfo("codes");
			codeMap = (Map<String,Map<String,CodeLibrary>>) codeCache.getValue();
			
			StringBuffer codeSource = new StringBuffer("{value:\"");
			Map<String,CodeLibrary> libraryMap = codeMap.get(codeNo);
			if(libraryMap == null || libraryMap.size() == 0) return "{value:\"\"}";
			for(String key : libraryMap.keySet()){
				CodeLibrary codeLibrary = libraryMap.get(key);
				codeSource.append(codeLibrary.getItemNo());
				codeSource.append(":");
				codeSource.append(codeLibrary.getItemName());
				codeSource.append(";");
			}
			codeSource.delete(codeSource.length()-1, codeSource.length());
			codeSource.append("\"}");
			
			return codeSource.toString();
		}
	}
}
