package com.library.service.mbooks.reservation;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.beans.mbooks.reservation.ReservationCreateBean;
import com.library.proxies.IReservationProxy;
import com.library.service.users.IUsersService;
import com.library.technical.state.reservation.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements  IReservationService {

    @Autowired
    private IReservationProxy reservationProxy;

    @Autowired
    private IUsersService usersService;

    public boolean isReservationPossible(BookBean book){
        return reservationProxy.isReservationPossible( book.getId() );
    }

    public ReservationBean find(Long id){
        return  reservationProxy.find( id );
    }

    public List<ReservationBean> list(){
        return reservationProxy.list();
    }

    public List<ReservationBean> list(Long idUserReservation){
        return  reservationProxy.listUser( idUserReservation );
    }

    public List<ReservationBean> list(BookBean book){
        return reservationProxy.listBook( book.getId() );
    }

    public Integer positionUser(Long idBook, Long idUserReservation){
        return reservationProxy.positionUser( idBook, idUserReservation);
    }

    public ReservationBean save(ReservationCreateBean reservation){
        return  reservationProxy.save( reservation );
    }

    public ReservationBean save(ReservationBean reservation){

        return  reservationProxy.update( reservation );
    }

    public void delete(Long id){
        ReservationBean reservation = reservationProxy.find( id );
        if ( reservation.getState() == State.INPROGRESS
                && (reservation.getIdUserReservation() == usersService.getCurrentUserId() || usersService.isAdmin() ) ) {
            reservation.setState(State.CANCELED);
            reservation.setIdUserUpdate(usersService.getCurrentUserId());
            reservationProxy.update(reservation);
        }
    }



}
