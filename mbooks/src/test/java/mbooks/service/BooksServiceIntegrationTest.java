package mbooks.service;

import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.*;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.*;
import mbooks.service.email.IEmailService;
import mbooks.service.lending.ILendingService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.date.SimpleDate;
import mbooks.technical.state.books.BooksState;
import mbooks.technical.state.reservation.State;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Import(BooksTestConfiguration.class)
@RunWith(SpringRunner.class)
public class BooksServiceIntegrationTest {


    @Autowired
    private IUsersProxy usersProxy;

    @Autowired
    private IBooksService booksService;

    @MockBean
    private IBooksRepository booksRepository;

    @Autowired
    private IReservationService reservationService;

    @MockBean
    private IReservationRepository reservationRepository;

    @Autowired
    private ILendingService lendingService;

    @MockBean
    private ILendingRepository lendingRepository;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private SimpleDate simpleDate;

    @Autowired
    private IEmailService emailService;

    @MockBean
    private IEmailRepository emailRepository;

    @MockBean
    private ILanguageRepository languageRepository;

    @MockBean
    private IAuthorRepository authorRepository;

    @MockBean
    private IThemeRepository themeRepository;

    @MockBean
    private IEditionRepository editionRepository;

    @Before
    public void setUp() {
        Language language = new Language("Français");
        language.setId( 1L );
        Mockito.when(languageRepository.findById( 1L) ).thenReturn(Optional.of( language )  );

        Author author = new Author("Banon","Martiel");
        author.setId( 1L );
        Mockito.when(authorRepository.findById( 1L) ).thenReturn(Optional.of( author )  );

        Theme theme = new Theme("Informatique");
        theme.setId(3L);
        Mockito.when(themeRepository.findById( 3L) ).thenReturn(Optional.of( theme )  );

        Edition edition = new Edition("ENI");
        edition.setId( 1L );
        Mockito.when(editionRepository.findById( 1L) ).thenReturn(Optional.of( edition )  );

        Books books1 = createTestBooks("111");
        books1.setId( 1L );

        Mockito.when(booksRepository.findById( books1.getId() ) ).thenReturn( Optional.of( books1 )  );
        Mockito.when(booksRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(booksRepository.findByIsbn( books1.getIsbn() ) ).thenReturn(  books1   );
        Mockito.when(booksRepository.findByIsbn("Invalid ISBN")).thenReturn(null);
        Mockito.when(booksRepository.save(any(Books.class))).thenReturn(books1);

        Books books2 = createTestBooks("222");

        Books books3 = createTestBooks("333");

        List<Books> booksList = Arrays.asList( books1,books2,books3 );
        Mockito.when(booksRepository.findAll()).thenReturn( booksList );


    }

    @Test
    public void whenValidId_thenBooksShouldBeFound() {
        Books fromDb = booksService.find(1L);
        assertThat(fromDb.getIsbn()).isEqualTo("111");
        verifyBooksFindByIdCalledOnce();
    }

    @Test
    public void whenInvalidId_thenBooksShouldNotBeFound() {
        Books fromDb = booksService.find(-99L);
        verifyBooksFindByIdCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenValidIsbn_thenBooksShouldBeFound() {
        Books fromDb = booksService.find("111");
        assertThat(fromDb.getIsbn()).isEqualTo("111");
        verifyBooksFindByIsbnCalledOnce();
    }

    @Test
    public void whenInvalidIsbn_thenBooksShouldNotBeFound() {
        Books fromDb = booksService.find("Invalid ISBN");
        verifyBooksFindByIsbnCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void given3books_whenGetAll_thenReturn3Records() {

        Books books1 = createTestBooks("111");
        Books books2 = createTestBooks("222");
        Books books3 = createTestBooks("333");

        List<Books> booksList = booksService.list();
        verifyFindAllBooksIsCalledOnce();

        assertThat(booksList).hasSize(3).extracting(Books::getIsbn).containsOnly(
                books1.getIsbn(), books2.getIsbn(), books3.getIsbn());
    }

    @Test
    public void whenSaveBooks_thenReturnBooks() {

        Books books1 = booksService.save(  createTestBooks("111") );
        Books fromDb = booksService.find( books1.getId() );
        verifySaveCalledOnce();
        assertThat(fromDb.getId() ).isEqualTo( books1.getId() );
    }

    @Test
    public void givenBooks_whenGetBooksState_thenReturnBooksState()  {
        BooksState booksState = booksService.getBooksState(1L,1L );
        assertThat( booksState ).isEqualTo( BooksState.AVAILABLE );
        resetBooksDependency();
    }



    private void verifyBooksFindByIdCalledOnce() {
        Mockito.verify(booksRepository, VerificationModeFactory.times(1)).findById( Mockito.anyLong() );
        resetBooksDependency();
    }
    private void verifyBooksFindByIsbnCalledOnce() {
        Mockito.verify(booksRepository, VerificationModeFactory.times(1)).findByIsbn( Mockito.anyString() );
        resetBooksDependency();
    }

    private void verifyFindAllBooksIsCalledOnce() {
        Mockito.verify(booksRepository, VerificationModeFactory.times(1)).findAll();
        resetBooksDependency();
    }

    private void resetBooksDependency() {
        Mockito.reset(booksRepository);
        Mockito.reset( languageRepository);
        Mockito.reset( authorRepository);
        Mockito.reset( themeRepository);
        Mockito.reset( editionRepository);
    }

    private void verifySaveCalledOnce(){

        Mockito.verify(booksRepository, VerificationModeFactory.times(1) ).save( any(Books.class) );
        resetBooksDependency();
    }

    private Books createTestBooks(String isbn) {

        Language language = languageRepository.findById(1L).orElse( null);
        Author author = authorRepository.findById(1L).orElse(null);
        Theme theme = themeRepository.findById(3L).orElse(null);
        Edition edition = editionRepository.findById(1L).orElse(null);

        BooksReservation booksReservation = new BooksReservation(1,1);

        Books books = new Books();
        books.setIsbn( isbn );
        books.setIdCover( "111" );
        books.setTitle( "Title de " + isbn );
        books.setSummary( "Résumé de " + isbn );
        books.setReview( 1L);
        books.setAvailability( 3L );
        books.setLanguage( language );
        books.setAuthor( author);
        books.setTheme( theme );
        books.setEdition( edition );
        books.setBooksReservation( booksReservation );

        return books;
    }


}
