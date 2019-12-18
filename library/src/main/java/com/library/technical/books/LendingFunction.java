package com.library.technical.books;

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
    private SimpleDate simpleDate;

    public String getDate(Date date){return simpleDate.getDateLow( date ); }

    public String getUser(Long id){return usersService.getUser( id ); }


}

