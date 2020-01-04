package mbooks.service.email;

import mbooks.model.Email;
import mbooks.repository.IEmailRepository;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.email.EmailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private IEmailRepository emailRepository;

    @Autowired
    public JavaMailSender emailSender;



    public Email find(String name){
        return emailRepository.findByName( name );
    }



    /**
     * Permet l'envoi d'un simple mail
     * @param to Adresse mail du destintaire
     * @param subject Sujet du mail
     * @param text Message du mail
     */
   private void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }



    /**
     * Permet d'envoyer le mail de relance des emprunts non rendu
     * @param emailList Liste des emprunteurs qui n'ont pas rendu leur livre
     */
    public void sendRevival(List<EmailWrapper> emailList){

       Email email = find("relance");

        for (EmailWrapper e: emailList) {
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", e.getTitle())
                    .replace("[END_DATE]", e.getEndDate());
            sendSimpleMessage(e.getEmail(),email.getSubject(),text);
        }
    }

    public void sendReturn(EmailReturnWrapper pEmail){

        Email email = find("return");
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", pEmail.getTitle())
                    .replace("[END_DATE]", pEmail.getEndDate())
                    .replace("[RETURN_DATE]", pEmail.getReturnDate() );

            sendSimpleMessage(pEmail.getEmail(),email.getSubject(),text);


    }
}
