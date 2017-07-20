package com.eps.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.eps.business.action.BusinessOrderManager;

@Controller
public class OrderController extends BaseController{

//	@RequestMapping(value = "ShowBusinessOrderByStateAndUserID.do", method = RequestMethod.POST)
//	public String showBusinessOrderByStateAndUserID(Model model, HttpServletRequest request, HttpSession session){
//		System.out.println("--------------------------ShowBusinessOrderByStateAndUserID.do------------------------------");
//		
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//		
//		String state = request.getParameter("state");
//		if(state == null || "".equals(state)){
//			System.out.println("==未传入state==");
//			return "false";
//		}
//		
//		String modelName = request.getParameter("modelName");
//		if(modelName == null || "".equals(modelName)){
//			System.out.println("==未传入modelName==");
//			return "false";
//		}
//		
//		String userID = (String) session.getAttribute("userID");
//		
//		Map<String,String> paramMap = new HashMap<String, String>();
//		paramMap.put("state", state);
//		paramMap.put("inputUserID", userID);
//		paramMap.put("modelName", modelName);
//		paramMap.put("CurUserID", userID);
//		
//		BusinessOrderManager manager = new BusinessOrderManager();
//		String res = null;
//		try {
//			res = manager.showBusinessOrderByStateAndUserID(webApplicationContext, paramMap);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//		return res;
//	}
	
}
