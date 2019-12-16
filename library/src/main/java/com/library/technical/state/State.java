package com.library.technical.state;

import com.library.service.mbooks.BooksPropertiesImpl;
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
