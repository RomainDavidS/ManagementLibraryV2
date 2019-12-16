package mbooks.technical.state;

import lombok.Getter;
import mbooks.config.ApplicationPropertiesConfig;

@Getter
public enum State {

    INPROGRESS(new ApplicationPropertiesConfig().getReservationInprogress() ),
    CANCELED(new ApplicationPropertiesConfig().getReservationCanceled()),
    COMPLETED(new ApplicationPropertiesConfig().getReservationCompleted());

    private String code;

    State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
