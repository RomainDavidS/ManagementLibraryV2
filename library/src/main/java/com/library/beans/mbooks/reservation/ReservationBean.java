package com.library.beans.mbooks.reservation;

import com.library.beans.mbooks.book.BookBean;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@NoArgsConstructor
public  @Data
class ReservationBean {

    private Long id;

    private Long idUserReservation;

    private Long idUserCreate;

    private Long idUserUpdate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reservationDate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date notificationDate;

    private ReservationStateBean reservationState;

    private BookBean book;
}
