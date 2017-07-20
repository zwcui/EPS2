package com.eps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * ���ǿ��԰����@ModelAttribute���ԣ�Ӧ����BaseController���У����е�Controller�̳�BaseController��
 * ����ʵ���ڵ���Controllerʱ����ִ��@ModelAttribute������
 * ����Ȩ�޵���֤��Ҳ����ʹ��Interceptor����
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
