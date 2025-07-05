package com.taskmanagement.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

@Slf4j
public class AutowireUtils {
    private AutowireCapableBeanFactory beanFactory;
    private ApplicationContext context;

    public AutowireUtils(AutowireCapableBeanFactory beanFactory, ApplicationContext context) {
        this.beanFactory = beanFactory;
        this.context = context;
    }

    public <T> T autowire(T obj) {
        beanFactory.autowireBean(obj);
        if ( obj instanceof InitializingBean) {
            try {
                ((InitializingBean) obj).afterPropertiesSet();
            } catch (Exception e) {
                log.error("Autowiring failed for " + obj.getClass());
            }
        }
        return obj;
    }

    public Object getBean(Class obj) {
        return context.getBean(obj);
    }
}
