package mbooks.service.email;




import mbooks.model.Email;
import mbooks.model.Lending;
import mbooks.repository.IEmailRepository;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.email.EmailWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private IEmailRepository emailRepository;

    @Autowired
    public JavaMailSender emailSender;

    /**
     * Permet la recherche de la liste des modèles de mail
     * @return La liste de des modèles de mails
     */
    public List<Email> findAll(){
        return emailRepository.findAll();
    }

    /**
     * Permet la création ou la modification d'un mmodèle de mail
     * @param email Entity email à créer ou à modifier
     */
    public void save(Email email){
        emailRepository.save(email);
    }


    /**
     * Permet l'envoi d'un simple mail
     * @param to Adresse mail du destintaire
     * @param subject Sujet du mail
     * @param text Message du mail
     */
   private void sendSimpleMessage(String to, String subject, String text) {

        //String text = String.format(template.getText(), templateArgs);
        //sendSimpleMessage(to, subject, text);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    /**
     * Permet l'envoi d'un mail avec un fichier rattaché
     * @param to Adresse mail du destinataire
     * @param subject Sujet du mail
     * @param text Message du mail
     * @param pathToAttachment Chemin du fichier à envoyer
     * @throws MessagingException
     */
    private void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment) throws MessagingException {


        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("Invoice", file);

        emailSender.send(message);

    }

    /**
     * Permet d'envoyer le mail de relance des emprunts non rendu
     * @param emailList Liste des emprunteurs qui n'ont pas rendu leur livre
     */
    public void sendRevival(List<EmailWrapper> emailList){

       Email email = emailRepository.findByName("relance");

        for (EmailWrapper e: emailList) {
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", e.getTitle())
                    .replace("[END_DATE]", e.getEndDate());
            sendSimpleMessage(e.getEmail(),email.getSubject(),text);
        }
    }
    public void sendReturn(EmailReturnWrapper pEmail){

        Email email = emailRepository.findByName("return");
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", pEmail.getTitle())
                    .replace("[END_DATE]", pEmail.getEndDate())
                    .replace("[RETURN_DATE]", pEmail.getReturnDate() );

            sendSimpleMessage(pEmail.getEmail(),email.getSubject(),text);

    }
}
