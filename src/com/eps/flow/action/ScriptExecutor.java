package com.eps.flow.action;

import java.lang.reflect.Method;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.FlowTask;

public class ScriptExecutor {

	/**
	 * Ö´ÐÐscript
	 * @param script
	 * @param param
	 * @param flowBean
	 * @param session
	 * @return
	 */
	public static String execute(String script,String param,FlowTask flowtask,WebApplicationContext webApplicationContext){
		String res = "";
		try{
			FlowActionScript action = (FlowActionScript) Class.forName(script).newInstance();
			
			if(param != null && !"".equals(param)){
				String[] parameters = param.split(",");
				for(String paramStr : parameters){
					String key = paramStr.split("=")[0];
					String value = paramStr.split("=")[1];
					if(value.startsWith("$")){
						value = value.substring(2, value.length()-1);
						String getMethodName = "get" + value;
						Method method = flowtask.getClass().getMethod(getMethodName);
						value = (String) method.invoke(flowtask);
					}
					String setMethodName = "set" + key;
					Method method = action.getClass().getMethod(setMethodName,String.class);
					method.invoke(action, value);
				}
			}
			action.setWebApplicationContext(webApplicationContext);
			action.execute();
			res = "true";
		}catch(Exception e){
			e.printStackTrace();
			res = "false";
		}
		
		return res;
	}
	
}
