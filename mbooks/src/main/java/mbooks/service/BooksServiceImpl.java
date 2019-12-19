package mbooks.service;


import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Books;
import mbooks.model.BooksReservation;
import mbooks.repository.IBooksRepository;
import mbooks.service.lending.ILendingService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.state.books.BooksState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksServiceImpl implements IBooksService {

    @Autowired
    private IBooksRepository bookRepository;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private ILendingService lendingService;

    @Autowired
    private IBooksReservationService booksReservationService;

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
        return bookRepository.getOne( id ).getAvailability() > 0;
    }

    private boolean isAvailability(Books books){
        return books.getAvailability() > 0;
    }

    public BooksState getBooksState(Long idBooks,Long idUser){
        Books books = bookRepository.getOne( idBooks );
        if( isAvailability( books ) )
            return BooksState.AVAILABLE;

        if ( reservationService.isReservationCurrentUser( books,idUser ) )
            return BooksState.ALREADY_BOOKED;

        if ( lendingService.isLendingCurrentUser( books,idUser ) )
            return BooksState.ALREADY_BORROWED;

        if ( isMaxReservation( books ) )
            return BooksState.MAX_RESERVATIONS_REACHED;

        return BooksState.RESERVATION_POSSIBLE;

    }

    private boolean isMaxReservation(Books books){
        return books.getBooksReservation().getNumber() >= books.getBooksReservation().getPossible();
    }
}

