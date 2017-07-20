package com.eps.dao.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.DocumentPath;
import com.eps.dao.FileDAO;

@Service
@Transactional
public class FileService extends BaseService {

	private FileDAO fileDAO;

	@Resource(name="fileDAO")
	public void setFileDAO(FileDAO fileDAO) {
		this.fileDAO = fileDAO;
	}
	
	public int insertDocumentInfo(DocumentPath documentPath){
		logger.info("insertDocumentInfo()");
		return fileDAO.insertDocumentInfo(documentPath);
	}
	
	public DocumentPath queryDocumentBySerialNo(String serialNo){
		logger.info("queryDocumentBySerialNo()");
		return fileDAO.queryDocumentBySerialNo(serialNo);
	}
	
	public List<DocumentPath> queryDocumentByOrderSerialNo(String orderSerialNo){
		logger.info("queryDocumentByOrderSerialNo()");
		return fileDAO.queryDocumentByOrderSerialNo(orderSerialNo);
	}

	public int updateDownloadTimes(DocumentPath documentPath){
		logger.info("updateDownloadTimes()");
		return fileDAO.updateDownloadTimes(documentPath);
	}

	public int deleteFileBySerialNo(String serialNo){
		logger.info("deleteFileBySerialNo()");
		return fileDAO.deleteFileBySerialNo(serialNo);
	}

	
}
