package com.eps.system.load;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.CodeCatalog;
import com.eps.bean.CodeLibrary;
import com.eps.dao.service.CodeService;
import com.eps.system.cache.Cache;
import com.eps.system.cache.CacheManager;

public class CodeLoader extends SystemLoader {

	@Override
	public String load() throws IOException {
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		
		CodeService codeService = (CodeService) webApplicationContext.getBean("codeService",CodeService.class);
		
		Cache cache = new Cache();
		
		List<CodeCatalog> codeCatalogList = codeService.getAllCodeFromCatalog();
		Map<String,Map<String,CodeLibrary>> codeMap = new TreeMap<String, Map<String,CodeLibrary>>();
		Map<String,String> paramMap = new TreeMap<String, String>();

		for(Iterator iter = codeCatalogList.iterator();iter.hasNext();){
			CodeCatalog codeCatalog = (CodeCatalog) iter.next();
			String codeNo = codeCatalog.getCodeNo();
			paramMap.put("codeNo", codeNo);
			List<CodeLibrary> codeLibraryList = codeService.getCodeLibraryByCodeNo(codeNo);
			Map<String,CodeLibrary> libraryMap = new TreeMap<String, CodeLibrary>();
			for(Iterator iter2 = codeLibraryList.iterator();iter2.hasNext();){
				CodeLibrary codeLibrary = (CodeLibrary) iter2.next();
				libraryMap.put(codeLibrary.getItemNo(), codeLibrary);
			}
			codeMap.put(codeNo, libraryMap);
		}
		
		cache.setKey("codeCache");
		cache.setExpired(false);
		cache.setValue(codeMap);

		CacheManager.putCache("codes", cache);
		
		return "true";
	}

}
