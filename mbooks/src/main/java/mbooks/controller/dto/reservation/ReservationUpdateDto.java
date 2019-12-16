package mbooks.controller.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbooks.model.Books;
import mbooks.model.ReservationState;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ReservationUpdateDto {
    @Id
    private Long id;

    @NotNull
    private Long idUserReservation;

    @NotNull
    private Long idUserCreate;

    @NotNull
    private Long idUserUpdate;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date notificationDate;

    private ReservationState reservationState;

    private Books book;
}
