package mbooks.service;

import mbooks.model.Books;
import mbooks.model.BooksReservation;
import mbooks.repository.IBooksReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BooksReservationServiceImpl implements IBooksReservationService {

    @Autowired
    private IBooksReservationRepository booksReservationRepository;



    public BooksReservation find(Long id){
        return booksReservationRepository.getOne( id );
    }

    public BooksReservation find(Books books){return  booksReservationRepository.findAllByBooks( books );}

    public List<BooksReservation> list(){
        return booksReservationRepository.findAll();
    }

    public BooksReservation save (BooksReservation booksReservation){
        return booksReservationRepository.save( booksReservation );
    }

    public boolean delete(Long id){
        try {
            booksReservationRepository.deleteById( id );
            return true;
        }catch (DataIntegrityViolationException ee){
            return false;
        }
    }

    public boolean isReservationPossible(Books books){

        return !isMaxReservation( books) && books.getAvailability() <= 0;
    }

    public boolean isMaxReservation(Books books){
        BooksReservation booksReservation = find( books );
        return booksReservation.getNumber() >= booksReservation.getPossible();
    }

    public Date getNextReturnDate(Long idBook){
        BooksReservation booksReservation = find( idBook );
        return booksReservation.getNextReturnDate();
    }

    public Integer getNumber(Long idBook ){
        BooksReservation booksReservation = find( idBook );
        return booksReservation.getNumber();
    }


}
