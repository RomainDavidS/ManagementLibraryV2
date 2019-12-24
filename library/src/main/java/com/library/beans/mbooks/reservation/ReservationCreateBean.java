package com.library.beans.mbooks.reservation;

import com.library.beans.mbooks.book.BookBean;
import com.library.technical.state.reservation.State;
import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public  class ReservationCreateBean {

    @NotNull
    private Long idUserReservation;

    @NotNull
    private State state;

    @NotNull
    private BookBean book;

    public ReservationCreateBean(@NotNull Long idUserReservation, @NotNull State state, @NotNull BookBean book) {
        this.idUserReservation = idUserReservation;
        this.state = state;
        this.book = book;
    }
}
