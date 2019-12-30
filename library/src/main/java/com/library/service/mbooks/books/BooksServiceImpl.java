package com.library.service.mbooks.books;

import com.library.beans.mbooks.book.BookBean;
import com.library.exception.ResourceNotFoundException;
import com.library.proxies.*;
import com.library.technical.state.books.BooksState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl implements IBooksService {

    @Autowired
    private IBooksProxy booksProxy;



    public BookBean save(BookBean book){
       return booksProxy.update( book );
    }

    /**
     * Permet la recherche d'un livre
     * @param id Identifiant du livre à rechercher
     * @return Entity bookbean si trouvé sinon null
     */
    public BookBean find(Long id ){

        try {
            return  booksProxy.find(id );
        }catch (ResourceNotFoundException e){
            return null;
        }
    }

    /**
     * Permet la recherche d'un titre d'un livre
     * @param id Idenitifiant du livre
     * @return Titre du livre si trouvé sinon null
     */
    public String getTitle(String id){
        try {
            return booksProxy.find( id ).getTitle() ;
        }catch (ResourceNotFoundException e){
            return null;
        }
    }

    /**
     * Permet la recherche de la liste de tous les livres
     * @return Liste de tous les livre si trouvé sinon false
     */
    public List<BookBean> list(){

        try {
            return booksProxy.list();
        }catch (ResourceNotFoundException e){
            return null;
        }
    }



    public BooksState getBooksState(Long idBooks, Long idUser){
        if( idUser == null)
            idUser = 0L;

        return  booksProxy.getBooksState( idBooks, idUser );
    }



}
