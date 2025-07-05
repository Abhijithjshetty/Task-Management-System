package com.taskmanagement.database.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.taskmanagement.mappers.mysql")
public class MyBatisConfig {
}

