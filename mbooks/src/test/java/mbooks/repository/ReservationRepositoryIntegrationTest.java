package mbooks.repository;

import mbooks.model.Books;
import mbooks.model.Lending;
import mbooks.model.Reservation;
import mbooks.technical.state.reservation.State;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private IBooksRepository booksRepository;

    @Before
    public void setUp(){
        reservationRepository.deleteAll();
    }

    @After
    public void erase(){
        reservationRepository.deleteAll();
    }

    @Test
    public void whenSaveReservation_thenReturnReservation() {
        Reservation reservation = reservationRepository.save( createReserevationTest() );
        Reservation fromDb = reservationRepository.findById( reservation.getId() ).orElse(null);
        assertThat(fromDb.getId() ).isEqualTo( reservation.getId() );
    }

    @Test
    public void whenFindById_thenReturnReservation() {

        Reservation reservation = createReserevationTest();

        entityManager.persistAndFlush(reservation);

        Reservation fromDb = reservationRepository.findById( reservation.getId() ).orElse(null);
        assertThat(fromDb.getId() ).isEqualTo( reservation.getId() );
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Reservation fromDb = reservationRepository.findById(-1L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindByBookAndAndIdUserReservationAndState_thenReturnReservation() {
        Reservation reservation = createReserevationTest();
        entityManager.persistAndFlush(reservation);
        Reservation fromDb = reservationRepository.findByBookAndAndIdUserReservationAndState( reservation.getBook(),reservation.getIdUserReservation(),reservation.getState() );
        assertThat(fromDb.getId() ).isEqualTo( reservation.getId() );
    }

    @Test
    public void whenInvalidFindByBookAndAndIdUserReservationAndState_thenReturnNull() {
        Reservation fromDb = reservationRepository.findByBookAndAndIdUserReservationAndState( booksRepository.getOne(-2L ),1L,State.INPROGRESS);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfReservation_whenFindAll_thenReturnAllReservation() {

        Reservation reservation1 = createReserevationTest();
        Reservation reservation2 = createReserevationTest();
        Reservation reservation3 = createReserevationTest();

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        List<Reservation> allReservation = reservationRepository.findAll();

        assertThat(allReservation).hasSize(3).extracting(Reservation::getId).containsOnly(
                reservation1.getId(), reservation2.getId(), reservation3.getId());
    }

    @Test
    public void givenSetOfReservation_whenFindAllByIdUserReservationOrderByReservationDateAsc_thenReturnAllReservation() {

        Reservation reservation1 = createReserevationTest();
        Reservation reservation2 = createReserevationTest();
        Reservation reservation3 = createReserevationTest();

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        List<Reservation> allReservation = reservationRepository.findAllByIdUserReservationOrderByReservationDateAsc( reservation1.getIdUserReservation() );

        assertThat(allReservation).hasSize(3).extracting(Reservation::getId).containsOnly(
                reservation1.getId(), reservation2.getId(), reservation3.getId());
    }

    @Test
    public void givenSetOfReservation_whenFindAllByBookAndStateOrderByReservationDateAsc_thenReturnAllReservation() {

        Reservation reservation1 = createReserevationTest();
        Reservation reservation2 = createReserevationTest();
        Reservation reservation3 = createReserevationTest();

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        List<Reservation> allReservation = reservationRepository.findAllByBookAndStateOrderByReservationDateAsc(reservation1.getBook(), reservation1.getState());

        assertThat(allReservation).hasSize(3).extracting(Reservation::getId).containsOnly(
                reservation1.getId(), reservation2.getId(), reservation3.getId());
    }
    @Test
    public void givenSetOfReservation_whenFindAllByBookOrderByReservationDateAsc_thenReturnAllReservation() {

        Reservation reservation1 = createReserevationTest();
        Reservation reservation2 = createReserevationTest();
        Reservation reservation3 = createReserevationTest();

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        List<Reservation> allReservation = reservationRepository.findAllByBookOrderByReservationDateAsc( reservation1.getBook());

        assertThat(allReservation).hasSize(3).extracting(Reservation::getId).containsOnly(
                reservation1.getId(), reservation2.getId(), reservation3.getId());
    }

    @Test
    public void givenSetOfReservation_whenFindAllByBookAndStateAndNotificationDateIsNullOrderByReservationDateAsc_thenReturnAllReservation() {

        Reservation reservation1 = createReserevationTest();
        Reservation reservation2 = createReserevationTest();
        Reservation reservation3 = createReserevationTest();

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        List<Reservation> allReservation = reservationRepository.findAllByBookAndStateAndNotificationDateIsNullOrderByReservationDateAsc(reservation1.getBook(), reservation1.getState() );

        assertThat(allReservation).hasSize(3).extracting(Reservation::getId).containsOnly(
                reservation1.getId(), reservation2.getId(), reservation3.getId());
    }

    @Test
    public void givenSetOfReservation_whenFindAllByStateAndNotificationDateIsNotNullAndNotificationDateBefore_thenReturnAllReservation() {

        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, -3 );

        Reservation reservation1 = createReserevationTest();
        reservation1.setNotificationDate( c.getTime() );
        Reservation reservation2 = createReserevationTest();
        reservation2.setNotificationDate( c.getTime() );
        Reservation reservation3 = createReserevationTest();
        reservation3.setNotificationDate( c.getTime() );

        entityManager.persist(reservation1);
        entityManager.persist(reservation2);
        entityManager.persist(reservation3);
        entityManager.flush();

        List<Reservation> allReservation = reservationRepository.findAllByStateAndNotificationDateIsNotNullAndNotificationDateBefore(State.INPROGRESS,  new Date() );

        assertThat(allReservation).hasSize(3).extracting(Reservation::getId).containsOnly(
                reservation1.getId(), reservation2.getId(), reservation3.getId());
    }


    private Reservation createReserevationTest(){
        return new Reservation(1L, State.INPROGRESS,booksRepository.getOne(-1L ) );
    }

}
