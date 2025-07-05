package com.taskmanagement.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagement.common.security.AESUtil;
import com.taskmanagement.common.utils.AutowireUtils;
import com.taskmanagement.core.security.handler.JwtLogoutHandler;
import com.taskmanagement.core.utils.HashingUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@Slf4j
public class AppplicationConfiguration implements InitializingBean {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private AutowireUtils autowireUtils;

    @Autowired
    private ApplicationContext context;

    private final String secretKey;

    public AppplicationConfiguration(
            @Value("${hashing.secret-key}") String secretKey
    ) {

        this.secretKey = secretKey;
    }

    @Bean
    public AutowireUtils getAutowireUtilsBean() {
        autowireUtils.autowire(autowireUtils);
        log.info("Generating bean for autowire utils.");
        return autowireUtils;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.autowireUtils = new AutowireUtils(beanFactory, context);
    }

    @Bean
    public AESUtil getAESUtil() throws Exception {
        return autowireUtils.autowire(new AESUtil());
    }

    @Bean
    public LogoutHandler getLogoutHandler() {
        return autowireUtils.autowire(new JwtLogoutHandler());
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        log.info("Object mapper bean created");
        return new ObjectMapper();
    }


    @Bean
    public HashingUtil hashingUtil() {
        log.info("HashingUtil bean created");
        return getAutowireUtilsBean().autowire(new HashingUtil());
    }
}
