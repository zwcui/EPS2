package com.eps.dao.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.TableMaxsn;
import com.eps.dao.SystemDAO;

@Service
@Transactional
public class SystemService extends BaseService {

	private SystemDAO systemDAO;

	@Resource(name="systemDAO")
	public void setSystemDAO(SystemDAO systemDAO) {
		this.systemDAO = systemDAO;
	}
	
	public int getMaxNum(String tableName){
		logger.info("getMaxNum()");
		return systemDAO.getMaxNum(tableName);
	}
	
	public TableMaxsn getMaxNumFromTable(String tableName){
		logger.info("getMaxNumFromTable()");
		return systemDAO.getMaxNumFromTable(tableName);
	}
	
	public void updateMaxNumFromTable(TableMaxsn tableMaxsn){
		logger.info("updateMaxNumFromTable()");
		systemDAO.updateMaxNumFromTable(tableMaxsn);
	}
	
	public void insertMaxNum(TableMaxsn tableMaxsn){
		logger.info("insertMaxNum()");
		systemDAO.insertMaxNum(tableMaxsn);
	}
	
	
}
