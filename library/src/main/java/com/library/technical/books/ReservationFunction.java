package com.library.technical.books;

import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.service.mbooks.reservation.IReservationService;
import com.library.service.users.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationFunction {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IUsersService usersService;

    public Integer positionUser(ReservationBean reservation){


        return reservationService.positionUser( reservation.getBook().getId(), reservation.getIdUserReservation() );

    }

   public String getUserReservation(Long id){
        return usersService.getUserReservation( id );
   }
}
