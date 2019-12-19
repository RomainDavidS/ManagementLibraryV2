package com.library.service.mbooks;

import com.library.beans.mbooks.book.BooksReservationBean;
import com.library.proxies.IBooksReservationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BooksReservationServiceImpl implements IBooksReservationService {

    @Autowired
    private IBooksReservationProxy booksReservationProxy;

    public BooksReservationBean find(Long idBook){return booksReservationProxy.find( idBook );}

    public BooksReservationBean save (BooksReservationBean booksReservation){ return booksReservationProxy.update( booksReservation ); }

}
