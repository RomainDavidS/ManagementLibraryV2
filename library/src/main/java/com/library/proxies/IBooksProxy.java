package com.library.proxies;



import com.library.beans.mbooks.book.BookBean;
import com.library.technical.state.books.BooksState;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "zuul-server",contextId = "bookProxy")
@RibbonClient(name = "microservice-books")
@RequestMapping("/microservice-books/book")
public interface IBooksProxy {


    @GetMapping("/id/{id}")
    BookBean find(@PathVariable("id") Long id);

    @GetMapping("/isbn/{isbn}")
    BookBean find(@PathVariable("isbn") String isbn);

    @GetMapping("/all")
    List<BookBean> list();

    @PutMapping("/update")
    BookBean update( @RequestBody BookBean book);

    @DeleteMapping("/{id}")
    boolean delete(@PathVariable("id") Long id);

    @GetMapping("/availability/{id}")
    boolean isAvailable(@PathVariable("id") Long id);

    @GetMapping("/state/{idBooks}/{idUser}")
    BooksState getBooksState(@PathVariable("idBooks") Long idBooks, @PathVariable("idUser") Long idUser);





}
