package com.sushikhacapitals.core.utils;

import com.sushikhacapitals.core.config.AppplicationConfiguration;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
@Slf4j
public class NotificationUtil {


    private final JavaMailSender emailSender;
    private final AppplicationConfiguration appplicationConfiguration;

    @Value("${sms.api-key}")
    private String smsKey;

    @Autowired
    public NotificationUtil(JavaMailSender emailSender, AppplicationConfiguration appplicationConfiguration) {
        this.emailSender = emailSender;
        this.appplicationConfiguration = appplicationConfiguration;
    }


    @Async
    public void sendEmail(String to,String body,String subject){
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            log.info("Formatted email sent!");
            emailSender.send(message);
        } catch (Exception e) {
            // Handle exception appropriately
            log.error("Execption: {}",e.getMessage());
        }
    }

    @Async
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // Set the second parameter to true to indicate HTML content
            emailSender.send(mimeMessage);
            log.info("Formatted email sent!");
        } catch (MessagingException e) {
            log.error("Execption: {}",e.getMessage());
        }
    }

//    public String sendSms(String msg,String snd,String num) {
//        try {
//            // Construct data
//            String apiKey = "apikey=" + smsKey;
//            String message = "&message=" + msg;
//            String sender = "&sender=" + snd;
//            String numbers = "&numbers=" + num;
//
//            // Send data
//            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
//            String data = apiKey + numbers + message + sender;
//            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
//            conn.getOutputStream().write(data.getBytes("UTF-8"));
//            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            final StringBuffer stringBuffer = new StringBuffer();
//            String line;
//            while ((line = rd.readLine()) != null) {
//                stringBuffer.append(line);
//            }
//            rd.close();
//
//            return stringBuffer.toString();
//        } catch (Exception e) {
//            log.error("Error SMS "+e);
//            return "Error "+e;
//        }
//    }
}
