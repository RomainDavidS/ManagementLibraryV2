package com.library.service.mbooks;

import com.library.beans.mbooks.book.BooksReservationBean;

public interface IBooksReservationService {

    BooksReservationBean save (BooksReservationBean booksReservation);
    BooksReservationBean find(Long id);
}
