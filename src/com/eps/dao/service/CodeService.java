package com.eps.dao.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.eps.bean.CodeCatalog;
import com.eps.bean.CodeLibrary;
import com.eps.dao.CodeDAO;

@Service
public class CodeService extends BaseService {

	private CodeDAO codeDAO;

	@Resource(name="codeDAO")
	public void setCodeDAO(CodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}
	
	public List<CodeCatalog> getAllCodeFromCatalog(){
		logger.info("getAllCodeFromCatalog()");
		return codeDAO.getAllCodeFromCatalog();
	}
	
	public List<CodeLibrary> getAllCodeFromLibrary(){
		logger.info("getAllCodeFromLibrary()");
		return codeDAO.getAllCodeFromLibrary();
	}
	
	public List<CodeLibrary> getCodeLibraryByCodeNo(String codeNo){
		logger.info("getCodeLibraryByCodeNo()");
		return codeDAO.getCodeLibraryByCodeNo(codeNo);
	}
}
