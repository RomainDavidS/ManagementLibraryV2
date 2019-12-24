package mbooks.config;


import mbooks.technical.date.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public SimpleDate simpleDate(){ return  new SimpleDate(); }


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost( "${spring.mail.host}" );
        mailSender.setPort( Integer.valueOf( "${spring.mail.port}" ));

        mailSender.setUsername( "${spring.mail.username}" );
        mailSender.setPassword( "${spring.mail.password}" );

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp" );
        props.put("mail.smtp.auth", true );
        props.put("mail.smtp.starttls.enable", true );
        props.put("mail.debug", true );

        return mailSender;
    }

    @Bean
    public SimpleMailMessage templateSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(
                "This is the test email template for your email:\n%s\n");
        return message;
    }




}
