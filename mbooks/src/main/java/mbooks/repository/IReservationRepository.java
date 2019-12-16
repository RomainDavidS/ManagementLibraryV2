package mbooks.repository;

import mbooks.model.Books;
import mbooks.model.Reservation;
import mbooks.technical.state.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation,Long> {

   List<Reservation> findAllByBookOrderByReservationDateDesc(Books books);
   List<Reservation> findAllByBookAndStateOrderByReservationDateDesc(Books books, State state);

   List<Reservation> findAllByIdUserReservationOrderByReservationDateDesc( Long idUserReservation );
}
