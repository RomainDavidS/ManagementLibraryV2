package com.library.beans.mbooks.book;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
public @Data class BooksReservationBean {

    @Id
    private Long id;

    private  BookBean bookBean;

    private Integer number ;

    private Integer possible;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nextReturnDate;
}
