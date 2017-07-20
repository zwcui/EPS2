package com.eps.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.ModelCatalog;
import com.eps.bean.ModelLibrary;

@MapperScan
public interface ModelDAO {

	public List<ModelLibrary> queryModelLibrary(String modelName);
	
	public ModelCatalog queryModelCatalog(String modelName);
	
}
