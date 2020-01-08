package mbooks.service.email;


import mbooks.model.Email;
import mbooks.repository.IEmailRepository;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.email.EmailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private IEmailRepository emailRepository;

    @Autowired
    private JavaMailSenderImpl sender;

    public Email find(String name){
        return emailRepository.findByName( name );
    }

    /**
     * Permet l'envoi d'un simple mail
     * @param to Adresse mail du destintaire
     * @param subject Sujet du mail
     * @param text Message du mail
     */
    private void sendSimpleMessage(String to, String subject, String text) throws MessagingException {

       System.out.println(  sender.getHost() );
       System.out.println( sender.getPort() );

       MimeMessage message = sender.createMimeMessage();
       MimeMessageHelper helper = new MimeMessageHelper(message);
       helper.setTo( to );
       helper.setSubject( subject);
       helper.setText( text );

       sender.send(message);
    }
    /**
     * Permet d'envoyer le mail de relance des emprunts non rendu
     * @param emailList Liste des emprunteurs qui n'ont pas rendu leur livre
     */
    public void sendRevival(List<EmailWrapper> emailList) throws MessagingException {

       Email email = find("relance");

        for (EmailWrapper e: emailList) {
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", e.getTitle())
                    .replace("[END_DATE]", e.getEndDate());
            sendSimpleMessage(e.getEmail(),email.getSubject(),text);
        }
    }

    public void sendReturn(EmailReturnWrapper pEmail) throws MessagingException {

        Email email = find("return");
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", pEmail.getTitle())
                    .replace("[END_DATE]", pEmail.getEndDate())
                    .replace("[RETURN_DATE]", pEmail.getReturnDate() );

            sendSimpleMessage(pEmail.getEmail(),email.getSubject(),text);


    }
}
