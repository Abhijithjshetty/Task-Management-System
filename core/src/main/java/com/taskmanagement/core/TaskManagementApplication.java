package com.taskmanagement.core;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@OpenAPIDefinition
@EnableScheduling
public class TaskManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }
}


