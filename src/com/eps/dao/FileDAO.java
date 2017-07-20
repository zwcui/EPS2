package com.eps.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.DocumentPath;

@MapperScan
public interface FileDAO {

	public int insertDocumentInfo(DocumentPath documentPath);
	
	public DocumentPath queryDocumentBySerialNo(String serialNo);
	
	public List<DocumentPath> queryDocumentByOrderSerialNo(String orderSerialNo);

	public int updateDownloadTimes(DocumentPath documentPath);

	public int deleteFileBySerialNo(String serialNo);

}
