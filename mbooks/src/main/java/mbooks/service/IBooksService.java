package mbooks.service;


import mbooks.model.Books;
import mbooks.technical.state.books.BooksState;

import java.util.List;

public interface IBooksService {
    Books find(Long id);
    Books find(String isbn);
    List<Books> list();
    Books save(Books book);
    boolean delete(Long id);
    boolean isAvailability(Long id);
    BooksState getBooksState(Long idBooks, Long idUser);
}
