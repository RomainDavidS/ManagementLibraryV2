package com.library.proxies;


import com.library.beans.mbooks.lending.LendingBean;
import com.library.beans.mbooks.lending.LendingCreateBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "zuul-server",contextId = "lendingProxy")
@RibbonClient(name = "microservice-books")
@RequestMapping("/microservice-books/lending")
public interface ILendingProxy {

    @GetMapping("/{id}")
    LendingBean find(@PathVariable("id") Long id);

    @GetMapping("/all")
    List<LendingBean> list();

    @GetMapping("/user/{id}")
    List<LendingBean> list(@PathVariable("id") Long id);

    @GetMapping("/book/{id}")
    List<LendingBean> list(@PathVariable("id") String id);

    @PostMapping("/save/fromReservation")
    LendingBean saveFromReservation( @RequestBody LendingCreateBean lending);

    @PutMapping("/renewal")
    void renewal(@RequestBody Long id);

    @PutMapping("/return")
    void returnBook(@RequestBody Long id);

    @GetMapping("/isRenewable/{id}")
    boolean  isRenewable(@PathVariable("id") Long id);

    @GetMapping("/isLendingPossible/{idBooks}/{idUser}")
    boolean isLendingPossible(@PathVariable("idBooks") Long idBooks, @PathVariable("idUser") Long idUser);

    @GetMapping("/getRenewalDay")
    Integer getRenewalDay();

}
