package com.taskmanagement.core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sushikhacapitals.common.security.AESUtil;
import com.sushikhacapitals.common.utils.AutowireUtils;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Properties;

@Configuration
@Slf4j
public class AppplicationConfiguration implements InitializingBean {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    private AutowireUtils autowireUtils;

    @Autowired
    private ApplicationContext context;


    private final Integer emailPort;
    private final String emailId;
    private final String emailKey;
    private final String emailHost;
    private final String secretKey;

    public AppplicationConfiguration(
            @Value("${email.port}") Integer emailPort,
            @Value("${email.id}") String emailId,
            @Value("${email.key}") String emailKey,
            @Value("${email.host}") String emailHost,
            @Value("${hashing.secret-key}") String secretKey
    ) {
        this.emailId = emailId;
        this.emailHost = emailHost;
        this.emailKey = emailKey;
        this.emailPort = emailPort;
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
    public JavaMailSender getJavaMailSender() throws Exception {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailHost);
        mailSender.setPort(emailPort);
        mailSender.setUsername(emailId);
        mailSender.setPassword(emailKey);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        log.info("Java Email Sender bean created");
        return mailSender;
    }

    @Bean
    public HashingUtil hashingUtil() {
        log.info("HashingUtil bean created");
        return getAutowireUtilsBean().autowire(new HashingUtil());
    }
}
