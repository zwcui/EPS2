package com.eps.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.CodeCatalog;
import com.eps.bean.CodeLibrary;

@MapperScan
public interface CodeDAO {
	
	public List<CodeCatalog> getAllCodeFromCatalog();
	
	public List<CodeLibrary> getAllCodeFromLibrary();

	public List<CodeLibrary> getCodeLibraryByCodeNo(String codeNo);

}
