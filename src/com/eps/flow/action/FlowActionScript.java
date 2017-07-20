package com.eps.flow.action;

import org.springframework.web.context.WebApplicationContext;

public abstract class FlowActionScript {
	
	private WebApplicationContext webApplicationContext;
	
	public WebApplicationContext getWebApplicationContext() {
		return webApplicationContext;
	}

	public void setWebApplicationContext(WebApplicationContext webApplicationContext) {
		this.webApplicationContext = webApplicationContext;
	}

	public abstract Object execute();
	
}
