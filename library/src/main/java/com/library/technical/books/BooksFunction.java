package com.library.technical.books;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.book.author.AuthorBean;
import com.library.beans.mbooks.lending.LendingBean;
import com.library.config.ApplicationPropertiesConfig;
import com.library.service.mbooks.BooksServiceImpl;
import com.library.service.mbooks.IBooksService;
import com.library.service.mbooks.author.AuthorServiceImpl;
import com.library.service.mbooks.author.IAuthorService;
import com.library.service.mbooks.lending.ILendingService;
import com.library.service.mbooks.lending.LendingServiceImpl;
import com.library.service.mbooks.reservation.IReservationService;
import com.library.technical.date.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BooksFunction {

    @Autowired
    private IAuthorService authorService;

    @Autowired
    private IBooksService booksService;

    @Autowired
    private ILendingService lendingService;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private SimpleDate simpleDate;


    public String getFullAuthorName(AuthorBean author){ return  authorService.fullAuthorName( author );}

    public boolean isAvailability(BookBean book) {return booksService.isAvailability( book ) ;}

    public boolean isInProgress(LendingBean lending){return lendingService.isInProgress( lending ); }

    public boolean isOutOfTime(LendingBean lending){return lendingService.isOutOfTime( lending );}

    public boolean isReturn(LendingBean lending){return lendingService.isReturn( lending);}

    public String getDate(Date date){return simpleDate.getDate( date ); }

    public boolean isRenewable(LendingBean lending){
        return  lendingService.isRenewable( lending );
    }

    public boolean isReservationPossible(BookBean book){
        return reservationService.isReservationPossible( book );
    }



}
