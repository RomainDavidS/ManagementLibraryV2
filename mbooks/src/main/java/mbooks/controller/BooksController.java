package mbooks.controller;


import mbooks.controller.dto.books.BooksCreateDto;
import mbooks.controller.dto.books.BooksUpdateDto;
import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Books;
import mbooks.service.IBooksReservationService;
import mbooks.service.IBooksService;
import mbooks.technical.dto.DTO;
import mbooks.technical.state.books.BooksState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BooksController implements HealthIndicator {

    @Autowired
    private IBooksService booksService;



    @GetMapping("/id/{id}")
    public Books find(@PathVariable Long id) {
        return booksService.find( id );
    }

    @GetMapping("/isbn/{isbn}")
    public Books find(@PathVariable String isbn) {
        return booksService.find( isbn );
    }

    @GetMapping("/all")
    public List<Books> list(){

        List<Books> booksList = booksService.list();
        if (booksList.isEmpty()) throw new ResourceNotFoundException( "Aucun livre trouvé.");

        return booksList;
    }

    @GetMapping("/availability/{id}")
    public boolean isAvailability( @PathVariable Long id ){
        return booksService.isAvailability( id );
    }

    @GetMapping("/state/{idBooks}/{idUser}")
    public BooksState getBooksState(@PathVariable Long idBooks, @PathVariable Long idUser){
        return booksService.getBooksState( idBooks, idUser);

    }


    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public Books save(@DTO(BooksCreateDto.class) @RequestBody Books book)  {


        return booksService.save(book);

    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public Books update(@DTO(BooksUpdateDto.class) @RequestBody Books book){
        return booksService.save( book );
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id){
        return booksService.delete( id );
    }

    @Override
    public Health health() {
        List<Books> booksList = booksService.list();

        if(booksList.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
