package mbooks.repository;

import mbooks.model.Books;
import mbooks.model.Reservation;
import mbooks.model.ReservationState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation,Long> {

   List<Reservation> findAllByBookOrderByReservationDateDesc(Books books);
   List<Reservation> findAllByBookAndReservationStateOrderByReservationDateDesc(Books books,ReservationState reservationState);

   List<Reservation> findAllByIdUserReservationOrderByReservationDateBookDesc( Long idUserReservation );
}
