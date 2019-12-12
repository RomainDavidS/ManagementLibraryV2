package mbooks.service.lending;

import mbooks.model.Lending;

import java.util.List;

public interface ILendingService {

    void renewal(Long id);
    Lending find(Long id);
    List<Lending> list();
    List<Lending> list(String isbn);
    List<Lending> list(Long idUser);
    Lending save(Lending lending);
    boolean delete(Long id);

    void sendLendingRevival();
    boolean isRenewable(Lending lending);
}
