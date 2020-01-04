package mbooks.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IReservationRepository reservationRepository;

    @Before
    public void setUp(){
        reservationRepository.deleteAll();
    }

    @After
    public void erase(){
        reservationRepository.deleteAll();
    }

    /**
     *  reservationRepository.save( reservation )
     * reservationRepository.findById(id)
     * reservationRepository.findByBookAndAndIdUserReservationAndState( books,idUser,State.INPROGRESS);
     * reservationRepository.findAll()
     * reservationRepository.findAllByBookOrderByReservationDateAsc( books)
     * reservationRepository.findAllByBookAndStateOrderByReservationDateAsc(books, State.INPROGRESS);
     * reservationRepository.findAllByBookAndStateAndNotificationDateIsNullOrderByReservationDateAsc(books, State.INPROGRESS);
     * reservationRepository.findAllByStateAndNotificationDateIsNotNullAndNotificationDateBefore(State.INPROGRESS, c.getTime() );
     *
     */
}
