package mbooks.service;

import mbooks.model.Books;
import mbooks.model.BooksReservation;

import java.util.Date;

public interface IBooksReservationService {

    BooksReservation find(Long id);
    BooksReservation save (BooksReservation booksReservation);
}
