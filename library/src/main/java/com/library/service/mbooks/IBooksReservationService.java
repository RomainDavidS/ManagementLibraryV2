package com.library.service.mbooks;

import java.util.Date;

public interface IBooksReservationService {

    Date getNextReturnDate(Long idBook);
    Integer getNumber(Long idBook );
}
