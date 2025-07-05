package com.taskmanagement.database.config;

import com.taskmanagement.common.config.DataSourceConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Configuration
public class DatabaseConfiguration implements InitializingBean {
    @Value("${task.database.url}")
    private String url;
    @Value("${task.database.username}")
    private String username;
    @Value("${task.database.password}")
    private String encPassword;


    private DataSource dataSource;

    @Bean
    public DataSource dataSource() throws BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, NoSuchPaddingException, InvalidKeyException {
        log.info("Data source bean created successfully.");
        return dataSource;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("XXXXXXXXXXXXXXX"+url);
        dataSource = new DataSourceConfiguration(
                url,
                username,
                encPassword).getDataSource();

    }

}