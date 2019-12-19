package mbooks.service;

import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.BooksReservation;
import mbooks.repository.IBooksReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BooksReservationServiceImpl implements IBooksReservationService {

    @Autowired
    private IBooksReservationRepository booksReservationRepository;




    public BooksReservation find(Long id) {
        return booksReservationRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Paramètre de Réservation non trouvée avec l'id " + id));
    }

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






}
