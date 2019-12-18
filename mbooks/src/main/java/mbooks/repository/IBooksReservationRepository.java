package mbooks.repository;

import mbooks.model.Books;
import mbooks.model.BooksReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBooksReservationRepository extends JpaRepository<BooksReservation, Long> {

    BooksReservation findAllByBooks(Books books);

}
