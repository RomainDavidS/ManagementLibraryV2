package com.library.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@FeignClient(name = "zuul-server",contextId = "bookReservationProxy")
@RibbonClient(name = "microservice-books")
@RequestMapping("/microservice-books/book/reservation")
public interface IBooksReservationProxy {

    @GetMapping("/next/date/{id}")
    Date getNextReturnDate(@PathVariable("id") Long idBook);

    @GetMapping("/number/{id}")
    Integer getNumber(@PathVariable("id") Long id );
}
