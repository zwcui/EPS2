package com.eps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 我们可以把这个@ModelAttribute特性，应用在BaseController当中，所有的Controller继承BaseController，
 * 即可实现在调用Controller时，先执行@ModelAttribute方法。
 * 比如权限的验证（也可以使用Interceptor）等
 * @author zwcui
 *
 */
public class BaseController {

	protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;
      
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        System.out.println("------BaseController------@ModelAttribute----setReqAndRes---");
    }
    
}
