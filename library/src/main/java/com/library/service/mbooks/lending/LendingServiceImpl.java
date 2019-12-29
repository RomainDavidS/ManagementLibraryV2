package com.library.service.mbooks.lending;
import com.library.beans.mbooks.lending.LendingBean;
import com.library.beans.mbooks.lending.LendingCreateBean;
import com.library.exception.ResourceNotFoundException;
import com.library.proxies.ILendingProxy;
import com.library.technical.date.SimpleDate;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LendingServiceImpl implements ILendingService {


    @Autowired
    private ILendingProxy lendingProxy;

    @Autowired
    private SimpleDate simpleDate;





    /**
     * Permet de faire le renouvellement de l'emprunt
     * @param id Identifiant de l'emprunt à renouveler
     */
    public void renewal(Long id){

        lendingProxy.renewal( id );
    }

    public void returnBook(Long id){
        try {
            lendingProxy.returnBook( id );
        }catch ( RetryableException e){
            System.out.println( e.getMessage() );
        }
    }

    /**
     * Permet la recherche d'un emprunt
     * @param id Identifiant de l'emprunt à rechercher
     * @return Entity lendingbean si l'emprunt a été trouvé sinon null
     */
    public LendingBean find(Long id){
            try {
                return lendingProxy.find( id ) ;
            }catch (ResourceNotFoundException e){
                return null;
            }
    }

    /**
     * Permet la recherche de la liste de tous les emprunts
     * @return La liste de tous les emprunts si existant sinon null
     */
    public List<LendingBean> list(){
        try {
            return lendingProxy.list();
        }catch (ResourceNotFoundException e){
            return null;
        }

    }

    /**
     * Permet la recherche de la liste de tous les emprunts d'un livre
     * @param isbn Numéro ISBN du livre
     * @return a liste de tous les emprunts si existant sinon null
     */
    public List<LendingBean> list(String isbn){
        try {
            return  lendingProxy.list( isbn );
        }catch (ResourceNotFoundException e){
            return null;
        }


    }
    /**
     * Permet la recherche de la liste de tous les emprunts d'un utilisateur
     * @param idUser Numéro ISBN du livre
     * @return a liste de tous les emprunts si existant sinon null
     */
    public List<LendingBean> list(Long idUser){
        try {
            return lendingProxy.list( idUser );
        }catch (ResourceNotFoundException e){
            return null;
        }

    }

    /**
     * Permet la création d'un emprunt
     * @param lending Entity à créer
     * @return Entity lendingbean
     */
    public LendingBean save(LendingCreateBean lending){
        return lendingProxy.save( lending );
    }

    /**
     * Permet la modification d'un emprunt
     * @param lending Entity à modifier
     * @return Entity lendingbean
     */
    public LendingBean save(LendingBean lending){
        return lendingProxy.update( lending );
    }

    /**
     * Permet m'effacement d'un emprunt
     * @param id Identifiant de l'emprunt à effacer
     * @return true si l'effacement à pu se réaliser sinon false
     */
    public boolean delete(Long id){
        return lendingProxy.delete( id );
    }

    /**
     * Permet la mise en forme d'une date
     * @param date La date à metre en forme
     * @return La date mise en forme "le dd MMM yyyy"
     */
    public String getDate(Date date){ return simpleDate.getDate( date );   }

    /**
     * Permet de vérifier si l'emprunt est en cours
     * @param lending Emprunt à vérifier
     * @return true si l'emprunt est toujours en vous sinon fdalse
     */
    public boolean isInProgress( LendingBean lending){
        return isStartDateBeforeEndDate(  lending);
    }

    /**
     * Permet de vérifier si l'emprunt est hors délais
     * @param lending Emprunt à vérifier
     * @return true si l'emprunt est hors délai sinon false
     */
    public boolean isOutOfTime( LendingBean lending ){
        return !isStartDateBeforeEndDate(  lending);
    }

    /**
     * Permet de vérifier si le livre emprunté a été rendu
     * @param lending Emprunt à vérifier
     * @return true si le livre a été rendu sinon false
     */
    public boolean isReturn(LendingBean lending){
        if ( lending.getReturnDate() == null)
            return false;

        return true;
    }

    /**
     * Permet de vérifier si la date de fin d'un emprunt en cours ou hors délai est dépassée
     * @param lending Emprunt à vérifier
     * @return true si la date de fin est dépassé sinon false
     */
    private boolean isStartDateBeforeEndDate( LendingBean lending){
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
    public boolean isRenewable(LendingBean lending){
        return  lendingProxy.isRenewable( lending.getId() );
    }

    /**
     * Permet de calculer la date renouvelée
     * @param date La date de base
     * @return La date renouvelé
     */
    public String renewalDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime( date );
      //  c.add(Calendar.DAY_OF_MONTH, booksPropertiesProxy.getRenewalDay() );
        return simpleDate.getDateLow( c.getTime() );
    }

    /**
     * permet de mettre en forme la date en format "
     * @param date Date à mettre en forme
     * @return Date en forme "dd MMM yyyy"
     */
    public String getEndDate(Date date){
        return simpleDate.getDateLow( date );
    }


}
