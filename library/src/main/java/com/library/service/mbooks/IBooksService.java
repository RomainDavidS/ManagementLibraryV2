package com.library.service.mbooks;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.book.BookCreateBean;
import com.library.beans.mbooks.book.author.AuthorBean;
import com.library.technical.state.books.BooksState;

import java.util.List;

public interface IBooksService {
    BookBean find(Long id );
    String getTitle(String id);
    List<BookBean> list();
    BookBean save(BookCreateBean book);
    BookBean save(BookBean book);
    boolean delete(Long id);

    boolean isAvailability(BookBean book);
    BooksState getBooksState(Long idBooks, Long idUser);



}
