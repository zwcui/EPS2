package com.eps.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.eps.bean.Org;
import com.eps.bean.User;
import com.eps.bean.UserRole;
import com.eps.dao.service.OrgService;
import com.eps.dao.service.UserService;

/**
 * 当有@Controller注解的控制器收到一个请求时，它会寻找一个合适的handler方法去处理这个请求。这就需要控制器通过一个或多个handler映射去把每个请求映射到handler方法
 * @author zwcui
 *
 */
@Controller
public class UserController extends BaseController{

	//一个控制器类的方法需要被@RequestMapping注解装饰，使它们成为handler方法
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("userid")String userID, @RequestParam("password")String password,Model model, HttpServletRequest request, HttpSession session){
		System.out.println("--------------------------UserController.login--------------"+userID+"---------------"+password+"----------------------");
		
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		UserService userService = (UserService) webApplicationContext.getBean("userService");
		OrgService orgService = (OrgService) webApplicationContext.getBean("orgService");
		
		User user = userService.getUserByUserID(userID);
		if(user == null || !password.equals(user.getPassword())){
			System.out.println("UserController.login.falied");
			ModelAndView mad = new ModelAndView("Logon");
			return mad;
		}
		Org org = orgService.getOrgByOrgID(user.getOrgID());
		ModelAndView mad = new ModelAndView("welcome");
		//将数据存入modelMap
//		mad.addObject("user", user);
		mad.addObject("userID", userID);
		
		List<UserRole> userRoleList = userService.getUserRoleByUserID(userID);
		Map<String,String> roleMap = new HashMap<String,String>();
		for(UserRole role : userRoleList){
			roleMap.put(role.getRoleID(), userService.getRoleByRoleID(role.getRoleID()).getRoleName());
		}
		
		session.setAttribute("userID", userID);
		session.setAttribute("orgID", user.getOrgID());
		session.setAttribute("userName", user.getUserName());
		session.setAttribute("orgName", org.getOrgName());
		session.setAttribute("orgLevel", org.getOrgLevel());
		session.setAttribute("phone", user.getPhone());
		session.setAttribute("telephone", user.getTelephone());
		session.setAttribute("mail", user.getMail());
		session.setAttribute("superior", user.getSuperior());
		session.setAttribute("status", user.getStatus());
		session.setAttribute("roles", roleMap);
		

		System.out.println("UserController.login.success");
		return new ModelAndView("welcome");
	}
	
//	@RequestMapping(value = "login.do", method = RequestMethod.POST)
//	public String login(@RequestParam("userid")String userID, @RequestParam("password")String password){
//		System.out.println("--------------------------UserController.login--------------"+userID+"---------------"+password+"----------------------");
//		
//		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//		UserService userService = (UserService) webApplicationContext.getBean("userService");
//		
//		User user = userService.getUserByUserID(userID);
//		if(user == null || !password.equals(user.getPassword())){
//			System.out.println("UserController.login.falied");
//			return "Logon";
//		}
//		
//		System.out.println("UserController.login.success");
//		return "welcome";
//	}
	
	
	@RequestMapping(value="logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpSession session){
		session.removeAttribute("userID");
		session.removeAttribute("userName");
		session.removeAttribute("orgID");
		session.removeAttribute("phone");
		session.removeAttribute("telephone");
		session.removeAttribute("mail");
		session.removeAttribute("superior");
		session.removeAttribute("status");
		return new ModelAndView("Logon");
	}
	
}
