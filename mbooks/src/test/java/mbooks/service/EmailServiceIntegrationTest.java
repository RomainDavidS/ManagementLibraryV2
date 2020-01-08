package mbooks.service;


import mbooks.MbooksApplication;
import mbooks.SmtpServerRule;
import mbooks.model.Email;
import mbooks.repository.IEmailRepository;
import mbooks.service.email.EmailServiceImpl;
import mbooks.service.email.IEmailService;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.email.EmailWrapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmailServiceIntegrationTest {

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.protocol}")
    private String smtp;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;


    @TestConfiguration
    static class EmailServiceImplContextConfiguration {
        @Bean
        public EmailServiceImpl emailService() {
            return new EmailServiceImpl();
        }

        @Bean
        public JavaMailSenderImpl sender(){return new JavaMailSenderImpl();}

    }

    @Autowired
    private IEmailService emailService;

    @Autowired
    private JavaMailSenderImpl sender;

    @MockBean
    private IEmailRepository emailRepository;


    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(3025);

    @Before
    public void setUp() {
        sender.setPort( port );
        sender.setHost( host );
        sender.setProtocol( smtp );
        sender.setDefaultEncoding( defaultEncoding);


        Email email1 = createEmailTest( "email1" );
        Email email2 = createEmailTest( "email2" );
        Email email3 = createEmailTest( "email3" );
        Email relance = createEmailRevivalTest();
        Email retour = createEmailReturnTest();

        List<Email> emailList = Arrays.asList(email1, email2, email3);

        Mockito.when(emailRepository.findByName( email1.getName() ) ).thenReturn( email1  );
        Mockito.when(emailRepository.findByName( relance.getName() ) ).thenReturn( relance  );
        Mockito.when(emailRepository.findByName( retour.getName() ) ).thenReturn( retour  );
        Mockito.when(emailRepository.findAll()).thenReturn(emailList);
        Mockito.when(emailRepository.findByName("InvalidName")).thenReturn( null );

    }


    @Test
    public void shouldSendRevival() throws MessagingException{

        ArrayList<EmailWrapper> emails = new ArrayList<>();
        emails.add(new EmailWrapper("revival@gmail.com", "Titre revival", "10 Janv 2020"));


        /** when: sending a mail */
        emailService.sendRevival( emails );

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals( emails.get(0).getEmail(), current.getAllRecipients()[0].toString() );
    }

    @Test
    public void shouldSendReturn() throws MessagingException {
        EmailReturnWrapper emailReturnWrapper = new EmailReturnWrapper("return@gmail.com", "Titre revival", "08 Janv 2020", "10 Janv 2020");


        /** when: sending a mail */
        emailService.sendReturn( emailReturnWrapper );

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(emailReturnWrapper.getEmail(), current.getAllRecipients()[0].toString());
    }



    @Test
    public void whenValidName_thenEmailShouldBeFound() {
        Email fromDb = emailService.find("email1");
        assertThat(fromDb.getName()).isEqualTo("email1");
        verifyFindByNameIsCalledOnce();
    }

    @Test
    public void whenInValidName_thenEmailShouldNotBeFound() {
        Email fromDb = emailService.find("InvalidName");
        verifyFindByNameIsCalledOnce();
        assertThat(fromDb).isNull();
    }



    private void verifyFindByNameIsCalledOnce() {
        Mockito.verify(emailRepository, VerificationModeFactory.times(1)).findByName( Mockito.anyString());
        Mockito.reset(emailRepository);
    }


    private Email createEmailTest(String name) {
        return new Email( name,"Subject de " + name,"Content de " + name) ;
    }

    private Email createEmailRevivalTest(){
        return new Email("relance","Relance pour livre non rendu","\tBonjour,\n" +
                "\n" +
                "\tVous devriez rendre le livre [BOOK_TITLE] à la blibliothèque au plus tard pour [END_DATE].\n" +
                "Hors nous n avons toujours pas enregistré ce retour.\n" +
                "Nous vous demandons de régulariser la situation dès à présent.\n" +
                "Bien cordialement.");
    }
    private Email createEmailReturnTest(){
        return new Email("return","Information sur votre demande de réservation","\tBonjour,\n" +
                "\n" +
                "\tNous avons le plaisir de vous annoncer que le livre [BOOK_TITLE] que vous aviez réservé est disponible depuis [RETURN_DATE] à la bibliothèque.\n" +
                "Nous vous invitons à venir le retirer au plus tard pour [END_DATE].\n" +
                "Après cette date votre réservation sera automatiquement annulée.\n" +
                "Bien cordialement.");
    }
}
