package com.eps.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.FlowCatalog;
import com.eps.bean.FlowInstance;
import com.eps.bean.FlowModel;
import com.eps.bean.FlowObject;
import com.eps.bean.FlowRelative;
import com.eps.bean.FlowTask;

@MapperScan
public interface FlowDAO {

	public void createFlowInstance(FlowInstance flowInstance);
	
	public void createFlowTask(FlowTask flowTask);
	
	public void createFlowObject(FlowObject flowObject);
	
	public void createFlowRelative(FlowRelative flowRelative);
	
	public List<FlowModel> queryFlowModelByFlowNo(String flowNo);
	
	public FlowTask queryCurrentFlowTask(String taskSerialNo);
	
	public FlowTask queryCurrentFlowTask2(String taskSerialNo);
	
	public List<FlowTask> queryFlowTaskByFlowSerialNo(String flowSerialNo);
	
	public FlowInstance queryCurrentFlowInstance(String flowSerialNo);
	
	public FlowObject queryCurrentFlowObject(String flowSerialNo);
	
	public int updateFlowTask(FlowTask flowTask);
	
	public int updateFlowInstance(FlowInstance flowInstance);
	
	public FlowModel queryFlowModelByFlowNoAndPhaseNo(Map paramMap);
	
	public FlowCatalog queryFlowCatalog(String flowNo);
	
	public List<FlowTask> queryFlowOpinionForExport(Map paramMap);
	
	public List<FlowCatalog> queryAllFlowNo();
}
