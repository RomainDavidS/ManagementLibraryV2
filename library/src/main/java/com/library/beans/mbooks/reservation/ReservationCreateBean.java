package com.library.beans.mbooks.reservation;

import com.library.beans.mbooks.book.BookBean;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public  @Data class ReservationCreateBean {

    private Long idUserReservation;

    private Long idUserCreate;

    private Long idUserUpdate;

    private ReservationStateBean reservationState;

    private BookBean book;
}
