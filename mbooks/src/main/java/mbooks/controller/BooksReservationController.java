package mbooks.controller;

import mbooks.controller.dto.books.BooksReservationUpdateDto;
import mbooks.model.Books;
import mbooks.model.BooksReservation;
import mbooks.service.IBooksReservationService;
import mbooks.service.IBooksService;
import mbooks.technical.dto.DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/book/reservation")
public class BooksReservationController {

    @Autowired
    private IBooksReservationService booksReservationService;

    @GetMapping("/{id}")
    public BooksReservation find(@PathVariable Long id) { return booksReservationService.find( id); }


    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public BooksReservation updateNumber(@DTO(BooksReservationUpdateDto.class) @RequestBody BooksReservation booksReservation){
        return booksReservationService.save( booksReservation );
    }

}
