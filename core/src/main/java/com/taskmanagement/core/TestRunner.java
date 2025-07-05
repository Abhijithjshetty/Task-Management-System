package com.taskmanagement.core;

import com.taskmanagement.common.security.AESUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestRunner implements CommandLineRunner {

    @Autowired
    AESUtil aesUtil;

    public TestRunner() {
    }

    @Override
    public void run(String... args) throws Exception {

    }



}