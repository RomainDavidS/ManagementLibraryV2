package mbooks.repository;

import mbooks.model.*;
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
public class LendingRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ILendingRepository lendingRepository;

    @Autowired
    private IBooksRepository booksRepository;

    @Before
    public void setUp(){
        lendingRepository.deleteAll();
    }

    @After
    public void erase(){
        lendingRepository.deleteAll();
    }

    @Test
    public void whenFindById_thenReturnLending() {

        Lending lending = new Lending(1L,booksRepository.getOne(-1L ) );

        entityManager.persistAndFlush(lending);

        Lending fromDb = lendingRepository.findById( lending.getId() ).orElse(null);
        assertThat(fromDb.getId() ).isEqualTo( lending.getId() );
    }

    @Test
    public void whenInvalidIsbn_thenReturnNull() {
        Lending fromDb = lendingRepository.findById(-1L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindByReturnDateIsNullAndBookAndIdUser_thenReturnLending() {
        Books books = booksRepository.getOne(-1L );
        Long idUser = 1L;
        Lending lending = new Lending(idUser, books);

        entityManager.persistAndFlush(lending);

        Lending fromDb = lendingRepository.findByReturnDateIsNullAndBookAndIdUser( books, idUser );
        assertThat(fromDb.getId() ).isEqualTo( lending.getId() );
    }



    @Test
    public void whenFindByReturnDateIsNullAndBookAndIdUserInvalid_thenReturnNull() {
        Lending fromDb = lendingRepository.findByReturnDateIsNullAndBookAndIdUser( null, 5L );
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfLending_whenFindAllByBookAndReturnDateIsNullOrderByReturnDateAsc_thenReturnAllLending() {
        Books books = booksRepository.getOne(-1L );
        Lending lending1 = new Lending(1L, books );
        Lending lending2 = new Lending(1L, books);
        Lending lending3 = new Lending(1L, books );

        entityManager.persist(lending1);
        entityManager.persist(lending2);
        entityManager.persist(lending3);
        entityManager.flush();

        List<Lending> allLending = lendingRepository.findAllByBookAndReturnDateIsNullOrderByReturnDateAsc( books );

        assertThat(allLending).hasSize(3).extracting(Lending::getId).containsOnly(
                lending1.getId(), lending2.getId(), lending3.getId());
    }

    @Test
    public void givenSetOfLending_whenFindAllByBook_thenReturnAllLending() {
        Books books = booksRepository.getOne(-1L );
        Lending lending1 = new Lending(1L, books );
        Lending lending2 = new Lending(1L, books);
        Lending lending3 = new Lending(1L, books );

        entityManager.persist(lending1);
        entityManager.persist(lending2);
        entityManager.persist(lending3);
        entityManager.flush();

        List<Lending> allLending = lendingRepository.findAllByBook( books );

        assertThat(allLending).hasSize(3).extracting(Lending::getId).containsOnly(
                lending1.getId(), lending2.getId(), lending3.getId());
    }

    @Test
    public void givenSetOfLending_whenFindAllByIdUser_thenReturnAllLending() {
        Books books = booksRepository.getOne(-1L );
        Lending lending1 = new Lending(1L, books );
        Lending lending2 = new Lending(1L, books);
        Lending lending3 = new Lending(1L, books );

        entityManager.persist(lending1);
        entityManager.persist(lending2);
        entityManager.persist(lending3);
        entityManager.flush();

        List<Lending> allLending = lendingRepository.findAllByIdUser( 1L );

        assertThat(allLending).hasSize(3).extracting(Lending::getId).containsOnly(
                lending1.getId(), lending2.getId(), lending3.getId());
    }


    @Test
    public void givenSetOfLending_whenFindAll_thenReturnAllLending() {
        Books books = booksRepository.getOne(-1L );
        Lending lending1 = new Lending(1L,books );
        Lending lending2 = new Lending(1L,books );
        Lending lending3 = new Lending(1L,books );

        entityManager.persist(lending1);
        entityManager.persist(lending2);
        entityManager.persist(lending3);
        entityManager.flush();

        List<Lending> allLending = lendingRepository.findAll();

        assertThat(allLending).hasSize(3).extracting(Lending::getId).containsOnly(
                lending1.getId(), lending2.getId(), lending3.getId());
    }

    @Test
    public void givenSetOfLending_whenFindAllByReturnDateIsNullAndAndEndDateBefore_thenReturnAllLending() {
        Books books = booksRepository.getOne(-1L );
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, -1 );


        Lending lending1 = new Lending(1L,books );
        lending1.setEndDate( c.getTime() );
        Lending lending2 = new Lending(1L,books );
        lending2.setEndDate( c.getTime() );
        Lending lending3 = new Lending(1L,books );
        lending3.setEndDate( c.getTime() );

        entityManager.persist(lending1);
        entityManager.persist(lending2);
        entityManager.persist(lending3);
        entityManager.flush();

        List<Lending> allLending = lendingRepository.findAllByReturnDateIsNullAndAndEndDateBefore( new Date() );

        assertThat(allLending).hasSize(3).extracting(Lending::getId).containsOnly(
                lending1.getId(), lending2.getId(), lending3.getId());
    }


    @Test
    public void whenSaveLending_thenReturnLending() {
        Lending lending = new Lending(1L,booksRepository.getOne(-1L ) );
        lendingRepository.save( lending );
        Lending fromDb = lendingRepository.findById( lending.getId() ).orElse(null);
        assertThat(fromDb.getId() ).isEqualTo( lending.getId() );
    }

}
