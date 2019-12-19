package com.library.proxies;

import com.library.beans.mbooks.book.BooksReservationBean;
import com.library.beans.mbooks.lending.LendingBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@FeignClient(name = "zuul-server",contextId = "bookReservationProxy")
@RibbonClient(name = "microservice-books")
@RequestMapping("/microservice-books/book/reservation")
public interface IBooksReservationProxy {

    @GetMapping("/{id}")
    BooksReservationBean find(@PathVariable("id") Long id);

    @PutMapping("/update")
    BooksReservationBean  update(@RequestBody BooksReservationBean booksReservation);
}
