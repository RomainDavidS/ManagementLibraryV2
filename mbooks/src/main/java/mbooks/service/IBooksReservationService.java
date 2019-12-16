package mbooks.service;

import mbooks.model.Books;
import mbooks.model.BooksReservation;

public interface IBooksReservationService {

    BooksReservation find(Books books);
    boolean isReservationPossible(Books books);
}
