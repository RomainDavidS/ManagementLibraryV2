package com.library.technical.function;

import com.library.beans.mbooks.book.AuthorBean;
import com.library.beans.mbooks.lending.LendingBean;
import com.library.service.mbooks.lending.ILendingService;
import com.library.service.users.IUsersService;
import com.library.technical.date.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LendingFunction {

    @Autowired
    private IUsersService usersService;

    @Autowired
    private ILendingService lendingService;

    @Autowired
    private SimpleDate simpleDate;

    public String getAuthorFullName(AuthorBean author){
        return author.getFirstName() + " " + author .getLastName() ;

    }

    public String getDate(Date date){return simpleDate.getDateLow( date ); }

    public String getUser(Long id){return usersService.getUser( id ); }

    public boolean isRenewable(LendingBean lending){
        return  lendingService.isRenewable( lending );
    }

    public boolean isReturned(Date date){
        return date == null ;
    }

}

