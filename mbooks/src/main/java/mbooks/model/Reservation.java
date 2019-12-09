package mbooks.model;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Long idUserReservation;

    @NonNull
    private Long idUserCreate;

    @NonNull
    private Long idUserUpdate;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date reservationDate;


    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date notificationDate;

    @ManyToOne
    @JoinColumn(name="id_reservation_state", referencedColumnName="id")
    @NonNull
    private ReservationState reservationState;

    @ManyToOne
    @JoinColumn(name="id_books", referencedColumnName="id")
    @NonNull
    private Books book;


}
