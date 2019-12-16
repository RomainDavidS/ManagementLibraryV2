package mbooks.controller;

import mbooks.config.ApplicationPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/properties")
public class PropertiesController  {

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @GetMapping("/renewalNumber")
    public Integer getRenewalNumber(){
        return appPropertiesConfig.getRenewalNumber();

    }

    @GetMapping("/renewalDay")
    public Integer getRenewalDay(){
        return appPropertiesConfig.getRenewalDay();

    }
    @GetMapping("/reservationCancellationDay")
    public Integer getReservationCancellationDay(){
        return appPropertiesConfig.getReservationCancellationDay();

    }
    @GetMapping("/numberReservationPossible")
    public Integer getNumberReservationPossible(){
        return appPropertiesConfig.getNumberReservationPossible();

    }

    @GetMapping("/reservationInprogress")
    public String getReservationInprogress(){
        return appPropertiesConfig.getReservationInprogress();

    }
    @GetMapping("/reservationCanceled")
    public String getReservationCanceled(){
        return appPropertiesConfig.getReservationCanceled();

    }
    @GetMapping("/reservationCompleted")
    public String getReservationCompleted(){
        return appPropertiesConfig.getReservationCompleted();

    }



}
