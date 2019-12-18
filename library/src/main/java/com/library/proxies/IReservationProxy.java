package com.library.proxies;
import com.library.beans.mbooks.reservation.ReservationBean;
import com.library.beans.mbooks.reservation.ReservationCreateBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "zuul-server",contextId = "reservationProxy")
@RibbonClient(name = "microservice-books")
@RequestMapping("/microservice-books/reservation")
public interface IReservationProxy {

    @GetMapping("/{id}")
    ReservationBean find(@PathVariable("id") Long id);

    @GetMapping("/all")
    List<ReservationBean> list();

    @GetMapping("/user/{id}")
    List<ReservationBean> listUser( @PathVariable("id") Long id);

    @GetMapping("/book/{id}")
    List<ReservationBean> listBook( @PathVariable("id") Long id);

    @GetMapping("/position/{idBook}/{idUser}")
    Integer positionUser( @PathVariable("idBook") Long idBook, @PathVariable("idUser") Long idUser);

    @PostMapping("/save")
    ReservationBean save( @RequestBody ReservationCreateBean reservation );

    @PutMapping("/update")
    ReservationBean update( @RequestBody ReservationBean reservation );

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable("id") Long id);

    @GetMapping("/possible/{id}")
    boolean isReservationPossible( @PathVariable("id") Long id );


}
