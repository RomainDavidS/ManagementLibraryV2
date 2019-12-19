package com.library.service.mbooks.reservation;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.beans.mbooks.reservation.ReservationCreateBean;

import java.util.List;

public interface IReservationService {


    boolean isReservationPossible(BookBean book);

    ReservationBean find(Long id);

    List<ReservationBean> list();

    List<ReservationBean> list(Long idUserReservation);

    List<ReservationBean> list(BookBean book);

    Integer positionUser(Long idBook, Long idUserReservation);

    ReservationBean save(ReservationCreateBean reservation);

    ReservationBean save(ReservationBean reservation);

    void delete(Long id);


}
