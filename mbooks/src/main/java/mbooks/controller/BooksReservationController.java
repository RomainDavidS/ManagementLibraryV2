package mbooks.controller;

import mbooks.model.BooksReservation;
import mbooks.service.IBooksReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/book/reservation")
public class BooksReservationController {

    @Autowired
    private IBooksReservationService booksReservationService;

    @GetMapping("/next/date/{id}")
    public Date getNextReturnDate(@PathVariable Long id){
        return booksReservationService.getNextReturnDate( id );
    }

    @GetMapping("/number/{id}")
    public Integer getNumber(@PathVariable Long id ){
        return booksReservationService.getNumber( id );
    }
}
