package com.eps.dao.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eps.bean.ModelCatalog;
import com.eps.bean.ModelLibrary;
import com.eps.dao.ModelDAO;

@Service
public class ModelService extends BaseService{

	private ModelDAO modelDAO;

	@Resource(name="modelDAO")
	public void setModeDAO(ModelDAO modelDAO) {
		this.modelDAO = modelDAO;
	}
	
	public List<ModelLibrary> queryModelLibrary(String modelName){
		logger.info("queryModelLibrary()");
		return modelDAO.queryModelLibrary(modelName);
	}
	
	public ModelCatalog queryModelCatalog(String modelName){
		logger.info("queryModelCatalog()");
		return modelDAO.queryModelCatalog(modelName);
	}
	
	
}
