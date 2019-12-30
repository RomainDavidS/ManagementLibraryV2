package com.library.service.mbooks.books;

import com.library.beans.mbooks.book.BookBean;
import com.library.technical.state.books.BooksState;

import java.util.List;

public interface IBooksService {
    BookBean find(Long id );
    String getTitle(String id);
    List<BookBean> list();

    BooksState getBooksState(Long idBooks, Long idUser);
    BookBean save(BookBean book);



}
