package mbooks.service.lending;


import mbooks.beans.musers.user.UsersBean;
import mbooks.config.ApplicationPropertiesConfig;
import mbooks.exceptions.ResourceNotFoundException;
import mbooks.model.Books;
import mbooks.model.Lending;
import mbooks.model.Reservation;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.ILendingRepository;
import mbooks.service.IBooksService;
import mbooks.service.email.IEmailService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.date.SimpleDate;
import mbooks.technical.email.EmailWrapper;
import mbooks.technical.state.reservation.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LendingServiceImpl implements ILendingService {

    @Autowired
    private ILendingRepository lendingRepository;

    @Autowired
    private IBooksService booksService;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private IUsersProxy usersProxy;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private SimpleDate simpleDate ;

    @Autowired
    private IReservationService reservationService;

    /**
     * Permet de faire un renouvellement d'emprun
     * @param id Identifiant de l'emprunt à renouveler
     */
    public void renewal(Long id){
    Lending lending = this.find( id );

        if( isRenewable( lending ) ){
            lending.setRenewal( lending.getRenewal() + 1);
            Calendar c = Calendar.getInstance();
            c.setTime( lending.getEndDate() );
            c.add(Calendar.DAY_OF_MONTH, appPropertiesConfig.getRenewalDay() );
            lending.setEndDate( c.getTime() );
            lendingRepository.save( lending );
        }
    }


    public void returnBook(Long id){
        Lending lending = find( id );
        if( lending.getReturnDate() == null ){
            Date now = new Date();
            lending.setReturnDate( now );
            lendingRepository.save( lending );
            reservationService.sendReturnInfo( lending.getBook() , now );
            booksService.updateNextDateReturn( lending.getBook(), getNextReturnDate( lending.getBook() ) );

        }
    }

    private Date getNextReturnDate( Books book ){
        Lending lending = lendingRepository.findAllByBookAndReturnDateIsNullOrderByReturnDateAsc( book ).get( 0 );
        return lending.getBook().getBooksReservation().getNextReturnDate();
    }

    /**
     * Permet la recherche d'un emprunt
     * @param id Identifiant de l'emprunt
     * @return Entity lending si existant
     */
    public Lending find(Long id){
        return lendingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prêt non trouvé avec l'id " + id ) );
    }

    /**
     * Permet la recherche de la liste de tous les emprunts
     * @return La liste de tous les emprunts
     */
    public List<Lending> list(){
        return lendingRepository.findAll();
    }

    /**
     * Permet la recherche de la liste de tous les emprumts lié à un livre
     * @param isbn Numéro ISBN du livre
     * @return Liste des emprunts d'un livre spécifique
     */
    public List<Lending> list(String isbn){
        Books book = booksService.find( isbn );
        return  lendingRepository.findAllByBook( book );
    }

    /**
     * Permet la recherche de la liste de tous les emprunts d'un utilisateur
     * @param idUser Identifiant de l'utilisateur
     * @return Liste de tous les emprunts d'un utilisateur
     */
    public List<Lending> list(Long idUser){
        return lendingRepository.findAllByIdUser( idUser );
    }

    public Lending addFromReservation(Lending lending){

        Books books = booksService.find( lending.getBook().getId() );
        if( isLendingPossible(  lending ) ){
            Lending newLending = addLending( lending );
            updateBooks( books );
            updateReservation( books, lending );
            return newLending;
        }

        return null;
    }

    private Lending addLending( Lending lending ){
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, appPropertiesConfig.getLendingDay() );
        lending.setEndDate( c.getTime() );
        lending.setRenewal( 0 );
        return save( lending );
    }

    private void updateBooks( Books books){
        books.setAvailability( books.getAvailability() - 1);
        books.getBooksReservation().setNumber(books.getBooksReservation().getNumber() - 1 );
        booksService.updateNextDateReturn( books, getNextReturnDate( books ) );
    }

    private void updateReservation(Books books,Lending lending ){
        Reservation reservation = reservationService.find( books,lending.getIdUser() );
        reservation.setState( State.COMPLETED );
        reservationService.save( reservation );
    }

    private boolean isLendingPossible(Books books, Long idUser){
        return books.getAvailability() > 0 && reservationService.positionUser( books.getId(), idUser ) == 1 && !isLendingCurrentUser( books, idUser);
    }

    private boolean isLendingPossible( Lending lending ){
        return isLendingPossible(lending.getBook(), lending.getIdUser() );
    }

    public boolean isLendingPossible(Long idBooks, Long idUser){
        return isLendingPossible( booksService.find( idBooks ),  idUser) ;
    }

    /**
     * Permet la création oou la modification d'un emprunt
     * @param lending Entity de l'enmrpunt à créer ou à modifier
     * @return Entity lending
     */
    public Lending save(Lending lending){ return lendingRepository.save( lending ); }

    /**
     * Permet l'effacement d'un emprunt
     * @param id Identifiant del'emprunt à effacer
     * @return true si l'effacement a pu se réaliser sinon false
     */
    public boolean delete(Long id){
        try {
            lendingRepository.deleteById( id );
            return true;
        }catch (DataIntegrityViolationException ee){
            return false;
        }
    }

    /**
     * Permet de vérifier si l'emprunt est en cours
     * @param lending Emprunt à vérifier
     * @return true si l'emprunt est toujours en vous sinon fdalse
     */
    private boolean isInProgress( Lending lending){
        return isStartDateBeforeEndDate(  lending);
    }



    /**
     * Permet de vérifier si le livre emprunté a été rendu
     * @param lending Emprunt à vérifier
     * @return true si le livre a été rendu sinon false
     */
    private boolean isReturn(Lending lending){
        if ( lending.getReturnDate() == null)
            return false;

        return true;
    }

    /**
     * Permet de vérifier si la date de fin d'un emprunt en cours ou hors délai est dépassée
     * @param lending Emprunt à vérifier
     * @return true si la date de fin est dépassé sinon false
     */
    private boolean isStartDateBeforeEndDate( Lending lending){
        Date now = new Date();
        if( !this.isReturn( lending ) )
            return ( now.compareTo( lending.getEndDate() ) <= 0 );

        return false;
    }

    /**
     * Permet de vérifier le renouvellement est encore possible
     * @param lending Nombre de renouvelement déjà réalisé
     * @return true si le renouvellement est encore possible sinon false
     */
    public boolean isRenewable(Lending lending){
        return ( lending.getRenewal() < appPropertiesConfig.getRenewalNumber() && isInProgress( lending ) );
    }

    /**
     * Permet de réaliser l'envoi d'un mail de relancer des emprunts des livres non rendus
     */
    public void sendLendingRevival(){
        Date now = new Date();
      List<Lending> lendingList = lendingRepository.findAllByReturnDateIsNullAndAndEndDateBefore(now);

        ArrayList<EmailWrapper> emails = new ArrayList<>();

        if (lendingList.size() > 0)
            for (Lending l : lendingList) {
                UsersBean usersBean = usersProxy.user(l.getIdUser());
                emails.add(new EmailWrapper(usersBean.getEmail(), l.getBook().getTitle(), simpleDate.getDate(l.getEndDate())));
            }

        List<EmailWrapper> emailList = new ArrayList<>(emails);
        emailService.sendRevival(emailList);
    }

    public boolean isLendingCurrentUser(Books books,Long idUser){
        if (idUser == 0)
            return false;

        Lending lending= lendingRepository.findByReturnDateIsNullAndBookAndIdUser( books, idUser );

        if( lending != null)
            return true;
        else
            return  false;
    }

    public Integer getRenewalDay(){
        return appPropertiesConfig.getRenewalDay();
    }


}
