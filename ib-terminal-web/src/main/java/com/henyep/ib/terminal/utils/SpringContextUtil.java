package com.henyep.ib.terminal.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContextUtil implements ApplicationContextAware {  
	private static ApplicationContext applicationContext;  
	
	public void setApplicationContext(ApplicationContext applicationContext) {  
		SpringContextUtil.applicationContext = applicationContext; // NOSONAR  
	}  
	  
	
	public static ApplicationContext getApplicationContext() {  
		checkApplicationContext();  
		return applicationContext;  
	}  
	 
	@SuppressWarnings("unchecked")  
	public static <T> T getBean(String name) {  
		checkApplicationContext();  
		return (T) applicationContext.getBean(name);  
	}  
	  
	 
	@SuppressWarnings("unchecked")  
	public static <T> T getBean(Class<T> clazz) {  
		checkApplicationContext();  
		return (T) applicationContext.getBeansOfType(clazz);  
	}  
	  
	 
	public static void cleanApplicationContext() {  
		applicationContext = null;  
	}  
	  
	private static void checkApplicationContext() {  
		if (applicationContext == null) {  
			throw new IllegalStateException("applicaitonContext not insert.Please define SpringContextHolder inapplicationContext.xml");  
		}  
	}  
}  