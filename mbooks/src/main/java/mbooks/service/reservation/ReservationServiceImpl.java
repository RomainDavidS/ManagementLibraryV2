package mbooks.service.reservation;


import mbooks.beans.musers.user.UsersBean;
import mbooks.config.ApplicationPropertiesConfig;
import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Books;
import mbooks.model.Reservation;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.IReservationRepository;
import mbooks.service.BooksServiceImpl;
import mbooks.service.email.IEmailService;
import mbooks.technical.date.SimpleDate;
import mbooks.technical.email.EmailReturnWrapper;
import mbooks.technical.state.reservation.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ReservationServiceImpl implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private BooksServiceImpl booksService;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private SimpleDate simpleDate;

    @Autowired
    private IEmailService emailService;

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    private IUsersProxy usersProxy;


    public Reservation find(Long id){
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation find(Books books, Long idUser){
        return reservationRepository.findByBookAndAndIdUserReservationAndState(
                books,idUser,State.INPROGRESS);
    }

    public List<Reservation> list(){
        return reservationRepository.findAll();
    }

    public List<Reservation> list( Long idUserReservation){
        return reservationRepository.findAllByIdUserReservationOrderByReservationDateAsc( idUserReservation );
    }

    public List<Reservation> list(Books books){
        return reservationRepository.findAllByBookOrderByReservationDateAsc( books);
    }

    public List<Reservation> listInProgress(Books books){

        return reservationRepository.findAllByBookAndStateOrderByReservationDateAsc(books, State.INPROGRESS);
    }

    private Reservation firstReservation(Books books){
        List<Reservation> reservationList = reservationRepository.findAllByBookAndStateAndNotificationDateIsNullOrderByReservationDateAsc(books, State.INPROGRESS);
        if (reservationList.isEmpty() )
            return null;

        return reservationList.get( 0 );
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

    public void delete(Long id,Long idUserUpdate){
        Reservation reservation = find( id );
        if ( reservation.getState() == State.INPROGRESS
                && reservation.getIdUserReservation() == idUserUpdate ) {

            reservation.setState(State.CANCELED);
            save(reservation);

            if( reservation.getNotificationDate() != null )
                sendReturnInfo(reservation.getBook(), new Date() );

        }
    }


    public boolean isReservationCurrentUser( Books books, Long idUser ){

        if(idUser == 0 )
            return false;

        if ( find( books, idUser) != null)
            return true;
        else
            return false;
    }

    /**
     * Permet de réaliser l'envoi d'un mail de notification de livre rendu
     */
    public void sendReturnInfo(Books books,Date dateReturn ){

        Reservation reservation = firstReservation( books );
        if( reservation !=null ) {
            reservation.setNotificationDate(new Date());

            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, appPropertiesConfig.getReservationCancellationDay());

            UsersBean usersBean = usersProxy.user(reservation.getIdUserReservation());

            EmailReturnWrapper email = new EmailReturnWrapper(
                    usersBean.getEmail(), reservation.getBook().getTitle(),
                    simpleDate.getDate(c.getTime()), simpleDate.getDate(dateReturn));

            reservationRepository.save(reservation);


            emailService.sendReturn(email);
        }

    }


    public List<Reservation> getReservationToCancel(){
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, -appPropertiesConfig.getReservationCancellationDay() );
        return reservationRepository.findAllByStateAndNotificationDateIsNotNullAndNotificationDateBefore(State.INPROGRESS, c.getTime() );
    }







}
