package mbooks.service.lending;

import mbooks.model.Books;
import mbooks.model.Lending;

import java.util.Date;
import java.util.List;

public interface ILendingService {

    void renewal(Long id);
    void returnBook(Long id);
    Lending find(Long id);
    List<Lending> list();
    List<Lending> list(String isbn);
    List<Lending> list(Long idUser);
    Lending addFromReservation(Lending lending);
    Lending save(Lending lending);

    void sendLendingRevival();
    boolean isRenewable(Lending lending);
    boolean isLendingCurrentUser(Books books,Long idUser);

    boolean isLendingPossible(Long idBooks, Long idUser);

    Integer getRenewalDay();

}
