package com.eps.system.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

	private String except;
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();
        String userID = (String)session.getAttribute("userID");
        String requestURI = request.getRequestURI();
        boolean flag = true;
        
        if(userID == null || "".equals(userID)){
        	for(String exceptItem : except.split(",")){
        		if(requestURI.endsWith(exceptItem)){
        			flag = false;
        			break;
        		}
        	}
        	if(flag){
	            java.io.PrintWriter out = response.getWriter();  
	            out.println("<html>");  
	            out.println("<script>");  
	            out.println("window.open ('"+request.getContextPath()+"/Logon.jsp"+"','_top')");  
	            out.println("</script>");  
	            out.println("</html>");  
        		return ;
            }
        }
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		except = filterConfig.getInitParameter("except");
	}

}
