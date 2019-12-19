package com.library.technical.books;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.book.author.AuthorBean;
import com.library.beans.mbooks.lending.LendingBean;
import com.library.service.mbooks.IBooksReservationService;
import com.library.service.mbooks.IBooksService;
import com.library.service.mbooks.author.IAuthorService;
import com.library.service.mbooks.lending.ILendingService;
import com.library.service.mbooks.reservation.IReservationService;
import com.library.technical.date.SimpleDate;
import com.library.technical.state.books.BooksState;
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
    private SimpleDate simpleDate;


    @Autowired
    private IBooksReservationService booksReservationService;

    public String getFullAuthorName(AuthorBean author){ return  authorService.fullAuthorName( author );}

    public boolean isInProgress(LendingBean lending){return lendingService.isInProgress( lending ); }

    public boolean isOutOfTime(LendingBean lending){return lendingService.isOutOfTime( lending );}

    public boolean isReturn(LendingBean lending){return lendingService.isReturn( lending);}

    public String getDate(Date date){return simpleDate.getDate( date ); }

    public boolean isRenewable(LendingBean lending){
        return  lendingService.isRenewable( lending );
    }

    public BooksState getBooksState(Long idBooks, Long idUser){
        return booksService.getBooksState( idBooks,idUser );
    }



}
