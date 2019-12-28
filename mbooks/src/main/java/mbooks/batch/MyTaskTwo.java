package mbooks.batch;

import mbooks.beans.musers.user.UsersBean;
import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.Books;
import mbooks.model.Email;
import mbooks.model.Reservation;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.IEmailRepository;
import mbooks.repository.IReservationRepository;
import mbooks.technical.date.SimpleDate;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.state.reservation.State;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyTaskTwo implements Tasklet {

    private final ApplicationPropertiesConfig appPropertiesConfig;

    private final IReservationRepository reservationRepository;

    private final JavaMailSender emailSender;

    private final IUsersProxy usersProxy;

    private final IEmailRepository emailRepository;

    private SimpleDate simpleDate = new SimpleDate();

    public MyTaskTwo(
            final ApplicationPropertiesConfig appPropertiesConfig,
            final IReservationRepository reservationRepository,
            final JavaMailSender emailSender,
            final IUsersProxy usersProxy,
            final IEmailRepository emailRepository) {
        this.appPropertiesConfig = appPropertiesConfig;
        this.reservationRepository = reservationRepository;
        this.emailSender = emailSender;
        this.usersProxy = usersProxy;
        this.emailRepository = emailRepository;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("Début du traitement d'annulation de réservation.");

        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, -appPropertiesConfig.getReservationCancellationDay() );
        List<Reservation> reservationList = reservationRepository.findAllByStateAndNotificationDateIsNotNullAndNotificationDateBefore(State.INPROGRESS, c.getTime() );

        for (Reservation r : reservationList ) {
            r.setState( State.CANCELED );
            r.getBook().getBooksReservation().setNumber( r.getBook().getBooksReservation().getNumber() - 1);
            reservationRepository.save( r );
            sendReturnInfo(r.getBook(), new Date() );
        }

        System.out.println("Fin du traitement d'annulation de réservation.");
        return RepeatStatus.FINISHED;
    }

    public void sendReturnInfo(Books books, Date dateReturn ){

        Reservation reservation =  reservationRepository.findAllByBookAndStateAndNotificationDateIsNullOrderByReservationDateAsc(books, State.INPROGRESS).get(0);

        Date now = new Date();

        reservation.setNotificationDate( now );

        Calendar c = Calendar.getInstance();
        c.setTime( now );
        c.add(Calendar.DAY_OF_MONTH, appPropertiesConfig.getReservationCancellationDay() );

        UsersBean usersBean = usersProxy.user(reservation.getIdUserReservation() );

        EmailReturnWrapper email = new EmailReturnWrapper(
                usersBean.getEmail(), reservation.getBook().getTitle(),
                simpleDate.getDate(c.getTime() ),simpleDate.getDate( dateReturn ) ) ;

        sendReturn( email );
        reservationRepository.save( reservation );

    }
    public void sendReturn(EmailReturnWrapper pEmail){

        Email email = emailRepository.findByName("return");
        String text = email.getContent()
                .replace("[BOOK_TITLE]", pEmail.getTitle())
                .replace("[END_DATE]", pEmail.getEndDate())
                .replace("[RETURN_DATE]", pEmail.getReturnDate() );

        sendSimpleMessage(pEmail.getEmail(),email.getSubject(),text);

    }
    private void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
