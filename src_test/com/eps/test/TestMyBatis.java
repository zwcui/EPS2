package com.eps.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eps.bean.Role;
import com.eps.dao.service.CodeService;
import com.eps.dao.service.OrgService;
import com.eps.dao.service.ProductService;
import com.eps.dao.service.UserService;

public class TestMyBatis {

	private static Logger logger = Logger.getLogger(TestMyBatis.class);
	
	public static UserService userService;
	public static OrgService orgService;
	public static CodeService codeService;
	public static ProductService productService;


	
	@BeforeClass
	public static void before(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		userService = (UserService) ctx.getBean("userService");
		orgService = (OrgService) ctx.getBean("orgService");
		codeService = (CodeService) ctx.getBean("codeService");
		productService = (ProductService) ctx.getBean("productService");

	}
	
	@Test
	public void testDB(){
//		User user = new User();
//		user.setSerialNo("777556");
//		user.setUserName("frrf");
//		user.setUserID("dddd");
//		userService.updateUser(user);
//		
//		List<User> list = userService.getAllUser();
//		for(User user2 : list){
//			System.out.println(user2.getUserName());
//		}
		
		
		
//		List<Org> list = orgService.getAllOrg();
//		for(Org org : list){
//			System.out.println(org.getOrgName());
//		}
		
//		List<CodeLibrary> list = codeService.getAllCodeFromLibrary();
//		for(CodeLibrary codeLibrary : list){
//			System.out.println(codeLibrary.getItemName());
//		}
		
		List<Role> list = userService.getAllRole();
		for(Role role : list){
			System.out.println(role.getRolename());
		}
		
//		System.out.println(userService.getUserByUserID("quhui").getUserName());
//		System.out.println(userService.getUserByUserID("Admin"));

//		logger.info(userService.getUserByUserID("quhui").getUserName());;
	}
	
}
