package com.library.technical.function;

import com.library.beans.mbooks.book.AuthorBean;
import com.library.beans.mbooks.lending.LendingBean;
import com.library.service.mbooks.books.IBooksService;
import com.library.service.mbooks.lending.ILendingService;
import com.library.technical.date.SimpleDate;
import com.library.technical.state.books.BooksState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BooksFunction {


    @Autowired
    private IBooksService booksService;

    @Autowired
    private ILendingService lendingService;

    @Autowired
    private SimpleDate simpleDate;


    public String getAuthorFullName(AuthorBean author){
        return author.getFirstName() + " " + author .getLastName() ;

    }

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
