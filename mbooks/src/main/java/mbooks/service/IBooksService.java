package mbooks.service;


import mbooks.model.Books;
import mbooks.technical.state.books.BooksState;

import java.util.List;

public interface IBooksService {
    Books find(Long id);
    Books find(String isbn);
    List<Books> list();
    BooksState getBooksState(Long idBooks, Long idUser);
    Books save(Books book);
}
