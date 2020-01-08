package mbooks.batch;


import mbooks.beans.musers.user.UsersBean;
import mbooks.model.Email;
import mbooks.model.Lending;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.IEmailRepository;
import mbooks.repository.ILendingRepository;
import mbooks.technical.date.SimpleDate;
import mbooks.technical.email.EmailWrapper;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Tache permettant  le traitment par batch la relance des emprunts des livres non rendu
 */
@Component
public class MyTaskOne implements Tasklet  {


    private final  JavaMailSenderImpl sender;;

    private final ILendingRepository lendingRepository;

    private final IUsersProxy usersProxy;

    private final IEmailRepository emailRepository;


    public MyTaskOne(final ILendingRepository lendingRepository,
                     final IUsersProxy usersProxy,
                     final IEmailRepository emailRepository,
                     final JavaMailSenderImpl sender) {
        this.lendingRepository = lendingRepository;
        this.usersProxy = usersProxy;
        this.emailRepository = emailRepository;
        this.sender = sender;

    }


    private SimpleDate simpleDate = new SimpleDate();


    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception
    {
        System.out.println("Début du traitement de relance.");

        Date now = new Date();
        List<Lending> lendingList = lendingRepository.findAllByReturnDateIsNullAndAndEndDateBefore(now);

        ArrayList<EmailWrapper> emails = new ArrayList<>();

        if (lendingList.size() > 0)
            for (Lending l : lendingList) {
                UsersBean usersBean = usersProxy.user(l.getIdUser());
                emails.add(new EmailWrapper(usersBean.getEmail(), l.getBook().getTitle(), simpleDate.getDate(l.getEndDate())));
            }

        List<EmailWrapper> emailList = new ArrayList<>(emails);
        this.sendRevival(emailList);

        System.out.println("Fin du traitement de relance.");
        return RepeatStatus.FINISHED;
    }

    public void sendRevival(List<EmailWrapper> emailList){

        Email email = emailRepository.findByName("relance");

        for (EmailWrapper e: emailList) {
            String text = email.getContent()
                    .replace("[BOOK_TITLE]", e.getTitle())
                    .replace("[END_DATE]", e.getEndDate());
            this.sendSimpleMessage(e.getEmail(),email.getSubject(),text);
        }
    }

    private void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        sender.send(message);
    }
}