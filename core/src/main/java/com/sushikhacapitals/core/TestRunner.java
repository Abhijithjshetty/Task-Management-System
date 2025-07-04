package com.sushikhacapitals.core;

import com.sushikhacapitals.common.security.AESUtil;
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

    @Autowired
    private JavaMailSender emailSender;


    public TestRunner() {
    }

    @Override
    public void run(String... args) throws Exception {
//        log.info("dec pass {}",aesUtil.decrypt("G/Ok5BA6whqNhSlQf/aJPzCtc8I5xXaUHKJC4rkgy4yIHKbRSz/gH1fJHQ=="));
        //Y3YKTFF8Q3TSA27HATTEE6W6  twilie
        //https://console.twilio.com/
    }



}