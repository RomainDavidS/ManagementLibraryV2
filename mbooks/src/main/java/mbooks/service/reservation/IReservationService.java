package mbooks.service.reservation;

import mbooks.model.Books;
import mbooks.model.Reservation;

import java.util.Date;
import java.util.List;

public interface IReservationService {



    Reservation find(Long id);

    List<Reservation> list();

    List<Reservation> list(Long idUserReservation);

    List<Reservation> list(Books books);

    List<Reservation> listInProgress(Books books);

    Integer positionUser(Long idBook , Long idUserReservation);

    Reservation save(Reservation reservation);

    boolean delete(Long id);

    boolean isReservationCurrentUser( Books books, Long idUser );
    void sendReturnInfo(Books books, Date dateReturn );

    List<Reservation> getReservationToCancel();
}
