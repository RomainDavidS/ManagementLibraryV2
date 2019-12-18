package mbooks.technical.state.books;

import lombok.Getter;

@Getter
public enum BooksState {

    AVAILABLE( "Disponible" ),
    RESERVATION_POSSIBLE("Réservation possible"),
    ALREADY_BOOKED("Déjà réservé"),
    ALREADY_BORROWED("Déjà emprunté"),
    MAX_RESERVATIONS_REACHED("Nombre maximum de réservation atteint");





    private String code;

    BooksState(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
