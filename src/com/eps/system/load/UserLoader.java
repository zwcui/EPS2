package com.eps.system.load;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.bean.User;
import com.eps.dao.service.UserService;
import com.eps.system.cache.Cache;
import com.eps.system.cache.CacheManager;

public class UserLoader extends SystemLoader {
	
	@Override
	public String load() throws IOException {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		
		Cache cache = new Cache();
		
		List<User> userList = userService.getAllUser();
		Map<String,User> userMap = new HashMap<String, User>();
		
		for(Iterator iter = userList.iterator();iter.hasNext();){
			User user = (User) iter.next();
			userMap.put(user.getUserID(), user);
		}
		
		cache.setKey("userCache");
		cache.setExpired(false);
		cache.setValue(userMap);

		CacheManager.putCache("users", cache);
		
		return "true";
	}

}
