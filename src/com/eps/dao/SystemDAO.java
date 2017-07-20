package com.eps.dao;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.TableMaxsn;

@MapperScan
public interface SystemDAO {
	
	public int getMaxNum(String tableName);
	
	public TableMaxsn getMaxNumFromTable(String tableName);
	
	public void updateMaxNumFromTable(TableMaxsn tableMaxsn);
	
	public void insertMaxNum(TableMaxsn tableMaxsn);
	
}


