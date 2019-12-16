package mbooks.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("microservice.config")
@Getter
@Setter
@RefreshScope
public class ApplicationPropertiesConfig {

    private Integer lendingDay;
    private Integer renewalNumber;
    private Integer renewalDay;
    private Integer reservationCancellationDay;
    private Integer numberReservationPossible;
    private String reservationInprogress;
    private String reservationCanceled;
    private String reservationCompleted;

}
