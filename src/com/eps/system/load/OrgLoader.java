package com.eps.system.load;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.Org;
import com.eps.dao.service.OrgService;
import com.eps.system.cache.Cache;
import com.eps.system.cache.CacheManager;

public class OrgLoader extends SystemLoader {

	@Override
	public String load() throws IOException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");
		
		Cache cache = new Cache();
		
		List<Org> orgList = orgService.getAllOrg();
		Map<String,Org> orgMap = new HashMap<String, Org>();
		
		for(Iterator iter = orgList.iterator();iter.hasNext();){
			Org org = (Org) iter.next();
			orgMap.put(org.getOrgID(), org);
		}
		
		cache.setKey("orgCache");
		cache.setExpired(false);
		cache.setValue(orgMap);

		CacheManager.putCache("orgs", cache);
		
		return "true";
	}

}
