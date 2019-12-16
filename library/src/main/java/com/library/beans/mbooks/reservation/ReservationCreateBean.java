package com.library.beans.mbooks.reservation;

import com.library.beans.mbooks.book.BookBean;
import com.library.technical.state.State;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public  @Data class ReservationCreateBean {

    private Long idUserReservation;

    private Long idUserCreate;

    private Long idUserUpdate;

    private State state;

    private BookBean book;
}
