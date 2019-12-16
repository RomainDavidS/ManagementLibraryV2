package mbooks.service.reservation;


import mbooks.model.Books;
import mbooks.model.Reservation;
import mbooks.repository.IReservationRepository;
import mbooks.service.BooksServiceImpl;
import mbooks.technical.state.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private BooksServiceImpl booksService;


    public Reservation find(Long id){
        return  reservationRepository.getOne( id );
    }

    public List<Reservation> list(){
        return reservationRepository.findAll();
    }

    public List<Reservation> list( Long idUserReservation){
        return reservationRepository.findAllByIdUserReservationOrderByReservationDateBookDesc( idUserReservation );
    }

    public List<Reservation> list(Books books){
        return reservationRepository.findAllByBookOrderByReservationDateDesc( books);
    }

    public List<Reservation> listInProgress(Books books){

        return reservationRepository.findAllByBookAndReservationStateOrderByReservationDateDesc(books, State.INPROGRESS);
    }

    public Integer positionUser(Long idBook , Long idUserReservation){

        List<Reservation> reservationList = listInProgress( booksService.find( idBook ) );

        Integer pos = 1;
        boolean trouve = false;

        for ( Reservation reservation : reservationList ) {
            if ( idUserReservation.equals( reservation.getIdUserReservation() ) ){
                trouve = true;
                break;
            }
            pos++;
        }

        if( trouve == true )
            return pos;
        else
            return 0;
    }

    public Reservation save(Reservation reservation){

        return  reservationRepository.save( reservation );
    }

    public boolean delete(Long id){
        try {
            reservationRepository.deleteById( id );
            return true;
        }catch (DataIntegrityViolationException ee){
            return false;
        }
    }






}
