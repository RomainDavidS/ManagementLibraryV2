package com.library.service.mbooks;

import com.library.proxies.IBooksReservationProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BooksReservationServiceImpl implements IBooksReservationService {

    @Autowired
    private IBooksReservationProxy booksReservationProxy;

    public Date getNextReturnDate(Long idBook){
        return booksReservationProxy.getNextReturnDate( idBook );
    }

    public Integer getNumber(Long idBook ){

        return booksReservationProxy.getNumber( idBook );
    }

}
