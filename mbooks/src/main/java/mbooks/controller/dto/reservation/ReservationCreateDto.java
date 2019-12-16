package mbooks.controller.dto.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mbooks.model.Books;
import mbooks.model.ReservationState;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class ReservationCreateDto {
    @NotNull
    private Long idUserReservation;

    @NotNull
    private Long idUserCreate;

    @NotNull
    private Long idUserUpdate;

    @NotNull
    private ReservationState reservationState;

    @NotNull
    private Books book;
}
