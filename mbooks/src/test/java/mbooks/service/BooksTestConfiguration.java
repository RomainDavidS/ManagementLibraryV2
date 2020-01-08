package mbooks.service;

import mbooks.beans.musers.user.UsersBean;
import mbooks.config.ApplicationPropertiesConfig;
import mbooks.proxies.IUsersProxy;
import mbooks.service.email.EmailServiceImpl;
import mbooks.service.lending.LendingServiceImpl;
import mbooks.service.reservation.ReservationServiceImpl;
import mbooks.technical.date.SimpleDate;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@TestConfiguration
public class BooksTestConfiguration {

    @Bean
    public BooksServiceImpl booksService() {
        return new BooksServiceImpl();
    }

    @Bean
    public ReservationServiceImpl reservationService(){ return new  ReservationServiceImpl();}

    @Bean
    public LendingServiceImpl lendingService(){ return new LendingServiceImpl();}

    @Bean
    public ApplicationPropertiesConfig appPropertiesConfig(){return new ApplicationPropertiesConfig();}

    @Bean
    public JavaMailSenderImpl sender(){return new JavaMailSenderImpl();}

    @Bean
    public SimpleDate simpleDate(){return new  SimpleDate();}

    @Bean
    public EmailServiceImpl emailService(){ return new  EmailServiceImpl();}

    @Bean
    public IUsersProxy usersProxy(){return new IUsersProxy() {
        @Override
        public UsersBean user(Long id) {return null;}

        @Override
        public UsersBean user(String id) {return null;}
    };}


}
