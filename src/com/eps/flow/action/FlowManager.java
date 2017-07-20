package com.eps.flow.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.FlowCatalog;
import com.eps.bean.FlowInstance;
import com.eps.bean.FlowModel;
import com.eps.bean.FlowObject;
import com.eps.bean.FlowRelative;
import com.eps.bean.FlowTask;
import com.eps.bean.Order;
import com.eps.bean.User;
import com.eps.dao.FlowDAO;
import com.eps.dao.service.FlowService;
import com.eps.dao.service.OrderService;
import com.eps.dao.service.UserService;
import com.eps.model.action.AnalyseModel;
import com.eps.system.utils.DBHelper;
import com.eps.system.utils.DateHelper;

public class FlowManager {

	/**
	 * 初始化流程实例
	 * @param objectNo
	 * @param objectType
	 * @param flowNo
	 * @param userID
	 * @param orgID
	 * @return 
	 * @throws IOException
	 */
	public String createInstance(WebApplicationContext webApplicationContext, String objectNo,String objectType,String flowNo,String userID,String orgID) throws IOException{
		
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		
		FlowInstance flowInstance = new FlowInstance();
		String flowSerialNo = DBHelper.generateKey("FLOW_INSTANCE");
		flowInstance.setSerialNo(flowSerialNo);
		flowInstance.setFlowNo(flowNo);
		flowInstance.setFlowStatus("1");
		flowInstance.setCreateTime(DateHelper.getCurrentDateAndTime());
		
		flowService.createFlowInstance(flowInstance);
		
		FlowObject flowObject = new FlowObject();
		flowObject.setSerialNo(DBHelper.generateKey("FLOW_OBJECT"));
		flowObject.setFlowSerialNo(flowSerialNo);
		flowObject.setObjectType(objectType);
		flowObject.setObjectNo(objectNo);
		
		flowService.createFlowObject(flowObject);
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("flowNo", flowNo);
		FlowCatalog flowCatalog = flowService.queryFlowCatalog(flowNo);
		String defaultPhaseNo = flowCatalog.getDefaultPhaseNo();
		paramMap.put("phaseNo", defaultPhaseNo);
		FlowModel flowModel = flowService.queryFlowModelByFlowNoAndPhaseNo(paramMap);
		
		FlowTask flowTask = new FlowTask();
		flowTask.setTaskSerialNo(DBHelper.generateKey("FLOW_TASK"));
		flowTask.setFlowSerialNo(flowSerialNo);
		flowTask.setUserID(userID);
		flowTask.setOrgID(orgID);
		flowTask.setPhaseNo(defaultPhaseNo);
		flowTask.setTaskState("1");
		flowTask.setCreateTime(DateHelper.getCurrentDateAndTime());
		
		//执行下一节点postscript
		String postScripts = flowModel.getPostScript();
		if(postScripts != null && !"".equals(postScripts)){
			for(String postScript : postScripts.split(";")){
				String script = postScript.split("\\(")[0];
				String param = postScript.substring(script.length()+1, postScript.length()-1);
				ScriptExecutor.execute(script, param, flowTask, webApplicationContext);
			}
		}
		
		flowService.createFlowTask(flowTask);
		
		return "true@"+flowSerialNo;
	}
	
	/**
	 * 返回流程节点  order by sortno
	 * @param flowNo
	 * @param session
	 * @return
	 */
	public static String getPhaseNo(String flowNo,WebApplicationContext webApplicationContext){
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("flowNo", flowNo);
		List<FlowModel> resList = new ArrayList<FlowModel>();
		
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		resList = flowService.queryFlowModelByFlowNo(flowNo);
		
		String phaseNoStr = "";
		for(FlowModel flowModel : resList){
			String phase = flowModel.getPhaseNo();
			phaseNoStr += phase + "@";
		}
		if(!"".equals(phaseNoStr)){
			return "true@"+phaseNoStr;
		}
		return "false@"+phaseNoStr;
	}
	
	/**
	 * 返回跳过后的流程节点  order by sortno
	 * @param flowNo
	 * @param session
	 * @return
	 */
	public static String getSkippedPhaseNo(String phaseNoStr,FlowTask currentTask,WebApplicationContext webApplicationContext){
		Map<String,String> paramMap = new HashMap<String, String>();
		String flowSerialNo = currentTask.getFlowSerialNo();
		paramMap.put("flowSerialNo", flowSerialNo);
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		OrderService orderService = (OrderService) webApplicationContext.getBean("orderService");

		FlowObject flowObject = flowService.queryCurrentFlowObject(flowSerialNo);
		if(flowObject == null) return phaseNoStr;
		
		paramMap.put("serialNo", flowObject.getObjectNo());
		Order order = orderService.queryBusinessOrderBySerialNo(flowObject.getObjectNo());
		if(order == null) return phaseNoStr;
		String skipCasting = order.getSkipCasting();
		String skipMetalWorking = order.getSkipMetalWorking();
		String skipAssembly = order.getSkipAssembly();
		String skipPainting = order.getSkipPainting();
		String skipInstrument = order.getSkipInstrument();
		if(skipCasting == null) skipCasting = "";
		if(skipMetalWorking == null) skipMetalWorking = "";
		if(skipAssembly == null) skipAssembly = "";
		if(skipPainting == null) skipPainting = "";
		if(skipInstrument == null) skipInstrument = "";
		
		String newPhaseNoStr = "";
		String skipPhaseNoStr = "";
		for(String phaseNo : phaseNoStr.split("@")){
			if("casting".equals(phaseNo) && "1".equals(skipCasting)){
				skipPhaseNoStr += phaseNo + "@";
				continue;
			}
			if("metalworking".equals(phaseNo) && "1".equals(skipMetalWorking)){
				skipPhaseNoStr += phaseNo + "@";
				continue;
			}
			if("assembly".equals(phaseNo) && "1".equals(skipAssembly)){
				skipPhaseNoStr += phaseNo + "@";
				continue;
			}
			if("painting".equals(phaseNo) && "1".equals(skipPainting)){
				skipPhaseNoStr += phaseNo + "@";
				continue;
			}
			if("instrument".equals(phaseNo) && "1".equals(skipInstrument)){
				skipPhaseNoStr += phaseNo + "@";
				continue;
			}
			newPhaseNoStr += phaseNo + "@";
		}
		if(newPhaseNoStr.length() > 0)
			newPhaseNoStr = newPhaseNoStr.substring(0, newPhaseNoStr.length()-1);
		if(skipPhaseNoStr.length() > 0)
			skipPhaseNoStr = skipPhaseNoStr.substring(0, skipPhaseNoStr.length()-1);
		
		if(!"".equals(newPhaseNoStr)){
			return "true@"+newPhaseNoStr+"###"+skipPhaseNoStr;
		}
		return "false@"+newPhaseNoStr;
	}
	
	/**
	 * 
	 * @param taskSerialNo
	 * @param userID
	 * @param orgID
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public String submitTask(String taskSerialNo,String userID,String orgID,WebApplicationContext webApplicationContext) throws IOException{
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put("taskSerialNo", taskSerialNo);
		FlowTask currentTask = flowService.queryCurrentFlowTask(taskSerialNo);
		String flowSerialNo = currentTask.getFlowSerialNo();
		paramMap.put("flowSerialNo", flowSerialNo);
		FlowInstance currentInstance = flowService.queryCurrentFlowInstance(flowSerialNo);
		//补充script参数
		FlowObject currentObject = flowService.queryCurrentFlowObject(flowSerialNo);
//		currentTask.setObjectNo(currentObject.getObjectNo());
//		currentTask.setObjectType(currentObject.getObjectType());
		
		if(currentInstance == null){
			return "false@当前流程未开始";
		}
		if(currentTask == null){
			return "false@当前流程未已结束";
		}
		String currentPhaseNo = currentTask.getPhaseNo();
		String currentTaskSerialNo = currentTask.getTaskSerialNo();
		String currentPhaseAction = currentTask.getPhaseAction();
		String flowNo = currentInstance.getFlowNo();
//		String phaseNoStr = this.getPhaseNo(flowNo, session);
		String originalPhaseNoStr = this.getPhaseNo(flowNo, webApplicationContext);
		String tmp = this.getSkippedPhaseNo(originalPhaseNoStr,currentTask,webApplicationContext);//获取跳过后的节点
		String phaseNoStr = tmp.split("###")[0];
		String needSkipPhaseNoStr = tmp.split("###").length > 1?tmp.split("###")[1] : "";
		
		String nextPhaseNo = "";
		
		if(currentPhaseAction == null || "".equals(currentPhaseAction)){
			return "false@未签署意见";
		}
		
		//判断是否有filter
		paramMap.put("flowNo", flowNo);
		paramMap.put("phaseNo", currentPhaseNo);
		FlowModel flowModel = flowService.queryFlowModelByFlowNoAndPhaseNo(paramMap);
		String filter = flowModel.getFilter();
		if(filter == null || "".equals(filter)){
			//无filter按sortno取
			if("true".equals(phaseNoStr.split("@")[0])){
				for(int i = 1;i < phaseNoStr.split("@").length;i++){
					if(currentPhaseNo.equals(phaseNoStr.split("@")[i])){
						nextPhaseNo = phaseNoStr.split("@")[i+1];
						if(!"".equals(nextPhaseNo)) break;
					}
				}
			}else{
				return "false@未找到对应流程节点配置";
			}
		}else{
			AnalyzeFilter analyze = new AnalyzeFilter();
			nextPhaseNo = analyze.getNextPhaseNoFromFilter(webApplicationContext, flowNo, currentPhaseNo, currentPhaseAction);
			
			//currentPhaseAction=01 表示正向，按处理后的顺序skip后向下
			if("01".equals(currentPhaseAction) && needSkipPhaseNoStr.contains(nextPhaseNo)){
				for(int i=0;i<originalPhaseNoStr.split("@").length;i++){
					String phaseNo = originalPhaseNoStr.split("@")[i];
					if("true".equals(phaseNo) || "false".equals(phaseNo))
						continue;
					if(phaseNo.equals(nextPhaseNo) && i != originalPhaseNoStr.split("@").length-1)
						nextPhaseNo = originalPhaseNoStr.split("@")[i+1];
					if(!needSkipPhaseNoStr.contains(nextPhaseNo))
						break;
				}
			}
			
			//currentPhaseAction=02 表示反向，按处理后的顺序skip后向上
			if("02".equals(currentPhaseAction) && needSkipPhaseNoStr.contains(nextPhaseNo)){
				for(int i=originalPhaseNoStr.split("@").length-1;i>=0;i--){
					String phaseNo = originalPhaseNoStr.split("@")[i];
					if("true".equals(phaseNo) || "false".equals(phaseNo))
						continue;
					if(phaseNo.equals(nextPhaseNo) && i != originalPhaseNoStr.split("@").length-1)
						nextPhaseNo = originalPhaseNoStr.split("@")[i-1];
					if(!needSkipPhaseNoStr.contains(nextPhaseNo))
						break;
				}
			}
			
			if(nextPhaseNo == null || "".equals(nextPhaseNo)){
				return "false@未获取到下一节点";
			}
		}
		
		if("".equals(nextPhaseNo)) return "false@此流程已无下一节点";
		
		
		//执行当前节点postscript
		String postScripts = flowModel.getPostScript();
		if(postScripts != null && !"".equals(postScripts)){
			for(String postScript : postScripts.split(";")){
				String script = postScript.split("\\(")[0];
				String param = postScript.substring(script.length()+1, postScript.length()-1);
				ScriptExecutor.execute(script, param, currentTask, webApplicationContext);
			}
		}
		
		
		//更新flow_task
		paramMap.put("taskState", "3");
		paramMap.put("taskSerialNo", currentTaskSerialNo);
		paramMap.put("endTime", DateHelper.getCurrentDateAndTime());
		currentTask.setTaskState("3");
		currentTask.setEndTime(DateHelper.getCurrentDateAndTime());
		int col = flowService.updateFlowTask(currentTask);
		
		//插入flow_task
		FlowTask nextTask = new FlowTask();
		String nextTaskSerialNo = DBHelper.generateKey("FLOW_TASK");
		nextTask.setTaskSerialNo(nextTaskSerialNo);
		nextTask.setFlowSerialNo(flowSerialNo);
		nextTask.setUserID(userID);
		nextTask.setOrgID(orgID);
		nextTask.setPhaseNo(nextPhaseNo);
		nextTask.setCreateTime(DateHelper.getCurrentDateAndTime());
		nextTask.setObjectNo(currentObject.getObjectNo());
		nextTask.setObjectType(currentObject.getObjectType());

		if("pass".equals(nextPhaseNo) || "reject".equals(nextPhaseNo)){
			nextTask.setTaskState("0");
			//更新flow_instance
			paramMap.put("flowStatus", "3");
			currentInstance.setFlowStatus("3");
			flowService.updateFlowInstance(currentInstance);
		}else{
			nextTask.setTaskState("1");
		}
		
		flowService.createFlowTask(nextTask);
		
		//插入flow_relative
		FlowRelative taskRelative = new FlowRelative();
		taskRelative.setSerialNo(DBHelper.generateKey("FLOW_RELATIVE"));
		taskRelative.setTaskSerialNo(currentTaskSerialNo);
		taskRelative.setNextTaskSerialNo(nextTaskSerialNo);
		taskRelative.setFlowSerialNo(flowSerialNo);
		
		flowService.createFlowRelative(taskRelative);
			
		//执行下一节点prescript
		paramMap.put("phaseNo", nextPhaseNo);
		FlowModel nextFlowModel = flowService.queryFlowModelByFlowNoAndPhaseNo(paramMap);
		String preScripts = nextFlowModel.getPreScript();
		if(preScripts != null && !"".equals(preScripts)){
			for(String preScript : preScripts.split(";")){
				String script = preScript.split("\\(")[0];
				String param = preScript.substring(script.length()+1, preScript.length()-1);
				ScriptExecutor.execute(script, param, nextTask, webApplicationContext);
			}
		}
		
		return "true@提交成功";
	}
	
	//TODO:未做针对对个phaseno返回人员和机构
	public String getNextTaskUserIDAndOrgID(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		UserService userService = (UserService) webApplicationContext.getBean("userService");

		FlowTask currentTask = flowService.queryCurrentFlowTask(paramMap.get("taskSerialNo"));
		String currentPhaseNo = currentTask.getPhaseNo();
		String currentPhaseAction = currentTask.getPhaseAction();
		String flowSerialNo = currentTask.getFlowSerialNo();
		paramMap.put("flowSerialNo", flowSerialNo);
		FlowInstance currentInstance = flowService.queryCurrentFlowInstance(flowSerialNo);
		String flowNo = currentInstance.getFlowNo();
		paramMap.put("flowNo", flowNo);
		paramMap.put("phaseNo", currentPhaseNo);
		FlowModel flowModel = flowService.queryFlowModelByFlowNoAndPhaseNo(paramMap);
		
		if(currentInstance == null){
			return "false@当前流程未开始";
		}
		if(currentTask == null){
			return "false@当前流程未已结束";
		}
		
		String originalPhaseNoStr = this.getPhaseNo(flowNo, webApplicationContext);
		String tmp = this.getSkippedPhaseNo(originalPhaseNoStr,currentTask,webApplicationContext);//获取跳过后的节点
		String phaseNoStr = tmp.split("###")[0];
		String needSkipPhaseNoStr = tmp.split("###").length > 1?tmp.split("###")[1] : "";
		String nextPhaseNo = "";
		//判断是否有filter
		String filter = flowModel.getFilter();
		if(filter == null || "".equals(filter)){
			//无filter按sortno取
			if("true".equals(phaseNoStr.split("@")[0])){
				for(int i = 1;i < phaseNoStr.split("@").length;i++){
					if(currentPhaseNo.equals(phaseNoStr.split("@")[i])){
						nextPhaseNo = phaseNoStr.split("@")[i+1];
						if(!"".equals(nextPhaseNo)) break;
					}
				}
			}else{
				return "false@未找到对应流程节点配置";
			}
		}else{
			AnalyzeFilter analyze = new AnalyzeFilter();
			nextPhaseNo = analyze.getNextPhaseNoFromFilter(webApplicationContext, flowNo, currentPhaseNo, currentPhaseAction);
			
			//currentPhaseAction=01 表示正向，按处理后的顺序skip后向下
			if("01".equals(currentPhaseAction) && needSkipPhaseNoStr.contains(nextPhaseNo)){
				for(int i=0;i<originalPhaseNoStr.split("@").length;i++){
					String phaseNo = originalPhaseNoStr.split("@")[i];
					if("true".equals(phaseNo) || "false".equals(phaseNo))
						continue;
					if(phaseNo.equals(nextPhaseNo) && i != originalPhaseNoStr.split("@").length-1)
						nextPhaseNo = originalPhaseNoStr.split("@")[i+1];
					if(!needSkipPhaseNoStr.contains(nextPhaseNo))
						break;
				}
			}
			
			//currentPhaseAction=02 表示反向，按处理后的顺序skip后向上
			if("02".equals(currentPhaseAction) && needSkipPhaseNoStr.contains(nextPhaseNo)){
				for(int i=originalPhaseNoStr.split("@").length-1;i>=0;i--){
					String phaseNo = originalPhaseNoStr.split("@")[i];
					if("true".equals(phaseNo) || "false".equals(phaseNo))
						continue;
					if(phaseNo.equals(nextPhaseNo) && i != originalPhaseNoStr.split("@").length-1)
						nextPhaseNo = originalPhaseNoStr.split("@")[i-1];
					if(!needSkipPhaseNoStr.contains(nextPhaseNo))
						break;
				}
			}
			
			if(nextPhaseNo == null || "".equals(nextPhaseNo)){
				return "false@未获取到下一节点";
			}
		}
		
		if("".equals(nextPhaseNo)) return "false@此流程已无下一节点";
		
		paramMap.put("phaseNo", nextPhaseNo);
		FlowModel nextFlowModel = flowService.queryFlowModelByFlowNoAndPhaseNo(paramMap);
		
		String roleID = nextFlowModel.getRoleID();
		String orgID = nextFlowModel.getOrgID();
		
		paramMap.put("roleID", roleID);
//		List<Map<String,String>> resMap = new ArrayList<Map<String,String>>();
//		resMap = session.selectList("com.eps.mappers.UserMapper.getUserFromRoleID", paramMap);
		
		
		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		List<Object> resList = new ArrayList<Object>();
		List<User> userList = userService.getUserFromRoleID(roleID);
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
	
	
	public static String updateFlowOpinion(String taskSerialNo,String phaseAction,String phaseOpinion,WebApplicationContext webApplicationContext) throws IOException{
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");
		
		FlowTask flowTask = flowService.queryCurrentFlowTask(taskSerialNo);
		flowTask.setPhaseAction(phaseAction);
		flowTask.setPhaseOpinion(phaseOpinion);
		flowTask.setUpdateTime(DateHelper.getCurrentTime());
		
		flowService.updateFlowTask(flowTask);
		
		return "true@保存意见成功";
	}
	
	/**
	 * 查看当前意见
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showCurrentFlowOpinion(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");

		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		List<Object> resMap = new ArrayList<Object>();
		FlowTask flowTask = flowService.queryCurrentFlowTask2(paramMap.get("taskSerialNo"));
		resMap.add(flowTask);
		
		String jsonRes;
		try {
			jsonRes = AnalyseModel.analyse(modelName, userID, resMap,webApplicationContext);
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
	 * 查看该流程所有意见
	 * @param session
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public String showFlowOpinions(WebApplicationContext webApplicationContext,Map<String,String> paramMap) throws IOException{
		FlowService flowService = (FlowService) webApplicationContext.getBean("flowService");

		String res = "";
		String modelName = paramMap.get("modelName");
		String userID = paramMap.get("CurUserID");
		
		List<Object> resList = new ArrayList<Object>();
		List<FlowTask> taskList = flowService.queryFlowTaskByFlowSerialNo(paramMap.get("flowSerialNo"));
		resList.addAll(taskList);
		
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
