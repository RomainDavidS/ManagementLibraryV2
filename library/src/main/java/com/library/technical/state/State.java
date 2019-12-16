package com.library.technical.state;

import com.library.service.mbooks.BooksPropertiesImpl;
import lombok.Getter;

@Getter
public enum State {

    INPROGRESS(new BooksPropertiesImpl().getReservationInprogress() ),
    CANCELED(new BooksPropertiesImpl().getReservationCanceled() ),
    COMPLETED(new BooksPropertiesImpl().getReservationCompleted() );

    private String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
