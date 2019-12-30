package com.library.technical.function;

import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.service.mbooks.lending.ILendingService;
import com.library.service.mbooks.reservation.IReservationService;
import com.library.service.users.IUsersService;
import com.library.technical.date.SimpleDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ReservationFunction {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IUsersService usersService;

    @Autowired
    private ILendingService lendingService;


    @Autowired
    private SimpleDate simpleDate;

    public Integer positionUser(ReservationBean reservation){
        return reservationService.positionUser( reservation.getBook().getId(), reservation.getIdUserReservation() );
    }
    public boolean isLendingPossible( Long idBooks,  Long idUser){
        return lendingService.isLendingPossible( idBooks,idUser );
    }
    public String getDate(Date date){return simpleDate.getDateLow( date ); }

    public String getUser(Long id){return usersService.getUser( id ); }


}
