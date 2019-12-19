package mbooks.repository;

import mbooks.model.Books;
import mbooks.model.Reservation;
import mbooks.technical.state.reservation.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation,Long> {

   List<Reservation> findAllByBookOrderByReservationDateAsc(Books books);
   List<Reservation> findAllByBookAndStateOrderByReservationDateAsc(Books books, State state);

   List<Reservation> findAllByIdUserReservationOrderByReservationDateAsc( Long idUserReservation );

   Reservation findByBookAndAndIdUserReservationAndState(Books books,Long idUserReservation, State state);



}
