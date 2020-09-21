package com.wujie.ac.app.framework.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 
 *************************************************
 * 	功能描述:  获取到Spring IOC容器中的Bean,用于处理Shiro与Spring Actuator冲突                  
 * @author  Mr.Chen                   
 * @version 1.0                
 * @date    2019年3月6日 创建文件                                             
 * @see                        
 *************************************************
 */
public class SpringContextUtil2 {

	private static ApplicationContext applicationContext;
	
	public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil2.applicationContext = applicationContext;
	}

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanId) throws BeansException {
        return applicationContext.getBean(beanId);
    }

}
