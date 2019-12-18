package mbooks.service;

import mbooks.model.Books;
import mbooks.model.BooksReservation;

import java.util.Date;

public interface IBooksReservationService {

    BooksReservation find(Books books);
    boolean isReservationPossible(Books books);
    boolean isMaxReservation(Books books);
    Date getNextReturnDate(Long idBook );
    Integer getNumber(Long idBook );
}
