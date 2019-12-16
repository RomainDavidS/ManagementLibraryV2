package mbooks.service;


import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Books;
import mbooks.repository.IBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl implements IBooksService {

    @Autowired
    private IBooksRepository bookRepository;

    /**
     * Permet la recherche d'un livre
     * @param id Identifiant du livre à rechercher
     * @return Entity books
     */
    public Books find(Long id){
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livre non trouvé avec l'id " + id ) );
    }
    /**
     * Permet la recherche d'un livre
     * @param isbn Numéro ISBN du livre à rechercher
     * @return Entity books
     */
    public Books find(String isbn){
        return bookRepository.findByIsbn( isbn );
    }

    /**
     * Permet la recherche de tous les livres
     * @return La liste de tous les livres
     */
    public List<Books> list(){
        return bookRepository.findAll();
    }

    /**
     * Permet la création ou la modification d'un livre
     * @param book Entity books à créer ou à modifier
     * @return Entity books
     */
    public Books  save(Books book){ return bookRepository.save( book ); }

    /**
     * Permet l'effacement d'un livre
     * @param id Idenitifiant du livre à effacer
     * @return true si l'effacement a pu se réaliser sinon false
     */
    public boolean delete(Long id){
        try {
            bookRepository.deleteById( id );
            return true;
        }catch (DataIntegrityViolationException ee){
            return false;
        }
    }

    public boolean isAvailability(Long id){
        Books books = bookRepository.getOne( id );
        return books.getAvailability() <= 0;
    }
}

