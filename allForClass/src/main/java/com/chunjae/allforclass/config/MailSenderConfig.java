package com.chunjae.allforclass.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:mail.properties")
@Getter
public class MailSenderConfig {

    @Value("${mail.smtp.port}")
    private int port;
    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;

    @Value("${mail.host}")
    private String host;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setUsername(id);
        mailSender.setPassword(password);
        mailSender.setPort(port);
        mailSender.setJavaMailProperties(getMailProperties());
        mailSender.setDefaultEncoding("UTF-8");
        return mailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.trust",host);
        properties.setProperty("mail.smtp.ssl.enable","true");
        return properties;
    }
}
