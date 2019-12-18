package com.library.service.mbooks;

import com.library.beans.mbooks.book.BookBean;
import com.library.beans.mbooks.book.BookCreateBean;
import com.library.beans.mbooks.book.author.AuthorBean;
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

    @Autowired
    private ICoverProxy coverProxy;

    @Autowired
    private IAuthorProxy authorProxy;

    @Autowired
    private IEditionProxy editionProxy;

    @Autowired
    private IThemeProxy themeProxy;

    @Autowired
    private ILanguageProxy languageProxy;

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

    /**
     * Permet la création d'un livre
     * @param book Entity à créer
     * @return Entity bookbean
     */
    public BookBean save(BookCreateBean book){

        book.setAuthor( authorProxy.find( book.getAuthor().getId() ) );
        book.setEdition( editionProxy.find( book.getEdition().getId() ) );
        book.setTheme( themeProxy.find( book.getTheme().getId() ) );
        book.setLanguage( languageProxy.find( book.getLanguage().getId() ) );

        return booksProxy.save( book );
    }
    /**
     * Permet la modification d'un livre
     * @param book Entity à modifier
     * @return Entity bookbean
     */
    public BookBean save(BookBean book){
        return booksProxy.update( book );
    }

    /**
     * Permet l'effacement d'un livre
     * @param id Identifiant du livre à effacer
     * @return true si l'effacement à pu se réaliser sinon false
     */
    public boolean delete(Long id){
        return booksProxy.delete( id );
    }

    /**
     * Vérification de l disponibilité d'un livre
     * @param book Livre à vérifier
     * @return true si le livre est encore disponible sinon false
     */
    public boolean isAvailability(BookBean book){
        return booksProxy.isAvailable( book.getId() );
    }

    public BooksState getBooksState(Long idBooks, Long idUser){
        if( idUser == null)
            idUser = Long.valueOf( 0);

        return  booksProxy.getBooksState( idBooks, idUser );
    }



}
