/**
 * Copyright (C), 2016-2018, 广州航运电子商务有限公司
 * FileName: SpringContextUtil
 * Author:   Guoqiang
 * Date:     2018/12/6 上午11:06
 * Description: 获取bean的工具类，可用于在线程里面获取bean
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.wujie.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 获取bean的工具类，可用于在线程里面获取bean
 *
 * @author Guoqiang
 * @since 2018/12/6
 * @version 1.0.0
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    /* (non Javadoc)
     * @Title: setApplicationContext
     * @Description: spring获取bean工具类
     * @param applicationContext
     * @throws BeansException
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtil.context = applicationContext;
    }
    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return context;
    }
    // 传入线程中
    public static <T> T getBean(String beanName) {
        return (T) getApplicationContext().getBean(beanName);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    // 国际化使用
    public static String getMessage(String key) {
        return getApplicationContext().getMessage(key, null, Locale.getDefault());
    }

    // 获取当前环境
    public static String getActiveProfile() {
        return getApplicationContext().getEnvironment().getActiveProfiles()[0];
    }
}
