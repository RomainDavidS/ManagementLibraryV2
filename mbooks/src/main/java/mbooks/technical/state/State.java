package mbooks.technical.state;

import lombok.Getter;

@Getter
public enum State {

    INPROGRESS( "En cours" ),
    CANCELED( "Annulé" ),
    COMPLETED("Terminé");

    private String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
