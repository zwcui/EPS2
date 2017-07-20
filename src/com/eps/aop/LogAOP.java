package com.eps.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.eps.system.utils.DateHelper;

/** 
 * 把这个类声明为一个切面： 
 * 1. 使用注解“@Repository”把该类放入到IOC容器中 
 * 2. 使用注解“@Aspect”把该类声明为一个切面 
 *  
 * 设置切面的优先级: 
 * 3. 使用注解“@Order(number)”指定前面的优先级，值越小，优先级越高 
 */ 

@Order(1)
@Aspect
//@Repository
@Component
public class LogAOP {
	
//	@Pointcut("execution(public com.eps.user.action.UserManager.*(..))")
	@Pointcut("execution(* com.eps.controller.UserController.*(..))")
	public void logPointCut(){}
	
	
	@Before("logPointCut()")
	public void beforeLog(JoinPoint joinPoint){
		//joinPoint.getSignature().getName() 方法名
		System.out.println("--------beforeLog---------"+DateHelper.getCurrentDateAndTime()+"--"+joinPoint.getSignature().getName());
	}
	
	@After("logPointCut()")
	public void afterLog(JoinPoint joinPoint){
		//joinPoint.getStaticPart().getSignature().getDeclaringType().getName()     classpath
		System.out.println("--------afterLog---------"+DateHelper.getCurrentDateAndTime()+"--"+joinPoint.getStaticPart().getSignature().getDeclaringType().getName());
	}
	
//	@AfterReturning("logPointCut()")
//	public void afterReturnLog(JoinPoint joinPoint){
//		System.out.println("--------AfterReturning---------"+DateHelper.getCurrentDateAndTime());
//	}
//	
//	@AfterThrowing("logPointCut()")
//	public void afterExceptionLog(JoinPoint joinPoint){
//		System.out.println("--------AfterThrowing---------"+DateHelper.getCurrentDateAndTime());
//	}
	
	
	
	
	
	
	
	
	
//	@Around("logPointCut()")
//	public List<User> aroundLog(ProceedingJoinPoint joinPoint) throws Throwable{
//		System.out.println("--------aroundLog-----start----"+DateHelper.getCurrentDateAndTime());
//		
//		List<User> userList = (List<User>) joinPoint.proceed();
//		
//		System.out.println("--------aroundLog-----end------"+DateHelper.getCurrentDateAndTime());
//
//		return userList;
//	}
	
}
