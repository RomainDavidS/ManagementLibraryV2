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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class BooksRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IBooksRepository booksRepository;

    @Autowired
    private ILanguageRepository languageRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IThemeRepository themeRepository;

    @Autowired
    private IEditionRepository editionRepository;

    @Autowired
    private ILendingRepository lendingRepository;

    @Autowired
    private IReservationRepository reservationRepository;


    @Before
    public void setUp(){
        reservationRepository.deleteAll();
        lendingRepository.deleteAll();
        booksRepository.deleteAll();
    }

    @After
    public void erase(){
        reservationRepository.deleteAll();
        lendingRepository.deleteAll();
        booksRepository.deleteAll();
    }



    @Test
    public void whenFindByIsbn_thenReturnBooks() {

        Books books = createTestBooks("11223344");

        entityManager.persistAndFlush(books);

        Books fromDb = booksRepository.findByIsbn( books.getIsbn() );
        assertThat(fromDb.getIsbn()).isEqualTo( books.getIsbn() );
    }

    @Test
    public void whenInvalidIsbn_thenReturnNull() {
        Books fromDb = booksRepository.findByIsbn("InvalidIsbn");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenSaveBooks_thenReturnBooks() {
        Books books = createTestBooks("11223344");
        booksRepository.save( books );
        Books fromDb = booksRepository.findByIsbn( books.getIsbn() );
        assertThat(fromDb.getIsbn()).isEqualTo( books.getIsbn() );
    }


    @Test
    public void givenSetOfBooks_whenFindAll_thenReturnAllBooks() {

        Books books1 = createTestBooks("1111");
        Books books2 = createTestBooks("2222");
        Books books3 = createTestBooks("3333");

        entityManager.persist(books1);
        entityManager.persist(books2);
        entityManager.persist(books3);
        entityManager.flush();

        List<Books> allBooks = booksRepository.findAll();

        assertThat(allBooks).hasSize(3).extracting(Books::getIsbn).containsOnly(
                books1.getIsbn(), books2.getIsbn(), books3.getIsbn());
    }

    private Books createTestBooks(String isbn) {

        Language language =languageRepository.getOne(1L);
        Author author = authorRepository.getOne(1L);
        Theme theme = themeRepository.getOne(1L);
        Edition edition = editionRepository.getOne(1L);

        BooksReservation booksReservation = new BooksReservation(1,1);

        Books books = new Books();
        books.setIsbn( isbn );
        books.setIdCover( "111" );
        books.setTitle( "Title de " + isbn );
        books.setSummary( "Résumé de " + isbn );
        books.setReview( 1L);
        books.setAvailability( 1L );
        books.setLanguage( language );
        books.setAuthor( author);
        books.setTheme( theme );
        books.setEdition( edition );
        books.setBooksReservation( booksReservation );

        return books;
    }
}
