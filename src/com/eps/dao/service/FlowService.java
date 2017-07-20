package com.eps.dao.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.FlowCatalog;
import com.eps.bean.FlowInstance;
import com.eps.bean.FlowModel;
import com.eps.bean.FlowObject;
import com.eps.bean.FlowRelative;
import com.eps.bean.FlowTask;
import com.eps.dao.FlowDAO;

@Service
@Transactional
public class FlowService extends BaseService {

	private FlowDAO flowDAO;

	@Resource(name="flowDAO")
	public void setFlowDAO(FlowDAO flowDAO) {
		this.flowDAO = flowDAO;
	}
	
	public void createFlowInstance(FlowInstance flowInstance){
		logger.info("createFlowInstance()");
		flowDAO.createFlowInstance(flowInstance);
	}
	
	public void createFlowTask(FlowTask flowTask){
		logger.info("createFlowTask()");
		flowDAO.createFlowTask(flowTask);
	}
	
	public void createFlowObject(FlowObject flowObject){
		logger.info("createFlowObject()");
		flowDAO.createFlowObject(flowObject);
	}
	
	public void createFlowRelative(FlowRelative flowRelative){
		logger.info("createFlowRelative()");
		flowDAO.createFlowRelative(flowRelative);
	}
	
	public List<FlowModel> queryFlowModelByFlowNo(String flowNo){
		logger.info("queryFlowModelByFlowNo()");
		return flowDAO.queryFlowModelByFlowNo(flowNo);
	}
	
	public FlowTask queryCurrentFlowTask(String taskSerialNo){
		logger.info("queryCurrentFlowTask()");
		return flowDAO.queryCurrentFlowTask(taskSerialNo);
	}
	
	public FlowTask queryCurrentFlowTask2(String taskSerialNo){
		logger.info("queryCurrentFlowTask2()");
		return flowDAO.queryCurrentFlowTask2(taskSerialNo);
	}
	
	public List<FlowTask> queryFlowTaskByFlowSerialNo(String flowSerialNo){
		logger.info("queryFlowTaskByFlowSerialNo()");
		return flowDAO.queryFlowTaskByFlowSerialNo(flowSerialNo);
	}
	
	public FlowInstance queryCurrentFlowInstance(String flowSerialNo){
		logger.info("queryCurrentFlowInstance()");
		return flowDAO.queryCurrentFlowInstance(flowSerialNo);
	}
	
	public FlowObject queryCurrentFlowObject(String flowSerialNo){
		logger.info("queryCurrentFlowObject()");
		return flowDAO.queryCurrentFlowObject(flowSerialNo);
	}
	
	public int updateFlowTask(FlowTask flowTask){
		logger.info("updateFlowTask()");
		return flowDAO.updateFlowTask(flowTask);
	}
	
	public void updateFlowInstance(FlowInstance flowInstance){
		logger.info("updateFlowInstance()");
		flowDAO.updateFlowInstance(flowInstance);
	}
	
	public FlowModel queryFlowModelByFlowNoAndPhaseNo(Map paramMap){
		logger.info("queryFlowModelByFlowNoAndPhaseNo()");
		return flowDAO.queryFlowModelByFlowNoAndPhaseNo(paramMap);
	}
	
	public FlowCatalog queryFlowCatalog(String flowNo){
		logger.info("queryFlowCatalog()");
		return flowDAO.queryFlowCatalog(flowNo);
	}
	
	public List<FlowTask> queryFlowOpinionForExport(Map paramMap){
		logger.info("queryFlowOpinionForExport()");
		return flowDAO.queryFlowOpinionForExport(paramMap);
	}
	
	public List<FlowCatalog> queryAllFlowNo(){
		logger.info("queryAllFlowNo()");
		return flowDAO.queryAllFlowNo();
	}

	
}
