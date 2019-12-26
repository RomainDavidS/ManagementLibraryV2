package mbooks.service;

import mbooks.model.Books;
import mbooks.model.BooksReservation;

import java.util.Date;
import java.util.List;

public interface IBooksReservationService {

    BooksReservation find(Long id);
    BooksReservation save (BooksReservation booksReservation);
    List<BooksReservation> list();
    boolean delete(Long id);
}
