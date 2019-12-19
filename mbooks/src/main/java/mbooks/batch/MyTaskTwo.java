package mbooks.batch;

import mbooks.beans.musers.user.UsersBean;
import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.Lending;
import mbooks.model.Reservation;
import mbooks.repository.IReservationRepository;
import mbooks.technical.email.EmailWrapper;
import mbooks.technical.state.reservation.State;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyTaskTwo implements Tasklet {

    private final ApplicationPropertiesConfig appPropertiesConfig;

    private final IReservationRepository reservationRepository;

    public MyTaskTwo(
            final ApplicationPropertiesConfig applPropertiesConfig,
            final IReservationRepository reservationRepository) {
        this.appPropertiesConfig = applPropertiesConfig;
        this.reservationRepository = reservationRepository;
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
        }
        System.out.println("Fin du traitement d'annulation de réservation.");
        return RepeatStatus.FINISHED;
    }

}
