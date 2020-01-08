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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Import(BooksTestConfiguration.class)
@RunWith(SpringRunner.class)
public class ReservationIntegrationTest {
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
        Mockito.when( booksRepository.findById( books1.getId() ) ).thenReturn( Optional.of( books1 )  );

        Books books2 = createTestBooks("222");
        books2.setId(2L);
        Mockito.when( booksRepository.findById( books2.getId() ) ).thenReturn( Optional.of( books2 )  );

        Reservation reservation1 = createReservationTest(1L, 1L) ;
        reservation1.setId(1L);
        Mockito.when( reservationRepository.findById( reservation1.getId() ) ).thenReturn( Optional.of( reservation1 )  );

        Mockito.when( reservationRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when( reservationRepository.findByBookAndIdUserReservationAndState(
                reservation1.getBook(),reservation1.getIdUserReservation(),State.INPROGRESS) ).thenReturn( reservation1 );

        Mockito.when( reservationRepository.findByBookAndIdUserReservationAndState(
                reservation1.getBook(),2L,State.INPROGRESS) ).thenReturn( null );
        Mockito.when( reservationRepository.findByBookAndIdUserReservationAndState(
                books2,1L,State.INPROGRESS) ).thenReturn( null );
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenReturn( reservation1 );



        Reservation reservation2 = createReservationTest(1L, 1L) ;
        Reservation reservation3 = createReservationTest(1L, 1L) ;

        List<Reservation> reservationList = Arrays.asList( reservation1,reservation2,reservation3 );
        Mockito.when( reservationRepository.findAll() ).thenReturn( reservationList );
        Mockito.when( reservationRepository.findAllByIdUserReservationOrderByReservationDateAsc( reservation1.getIdUserReservation() ) ).thenReturn( reservationList );
        Mockito.when( reservationRepository.findAllByBookOrderByReservationDateAsc( reservation1.getBook() ) ).thenReturn( reservationList );
    }


    @Test
    public void whenValidId_thenReservationShouldBeFound() {
        Reservation fromDb = reservationService.find(1L);
        assertThat(fromDb.getBook().getIsbn() ).isEqualTo("111");
        verifyReservationFindByIdCalledOnce();
    }

    @Test
    public void whenInvalidId_thenReservationShouldNotBeFound() {
        Reservation fromDb = reservationService.find(-99L);
        verifyReservationFindByIdCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenValidIdUserAndValidBooks_thenReservationShouldBeFound() {
        Reservation fromDb = reservationService.find(booksRepository.findById(1L ).orElse(null),1L);
        assertThat(fromDb.getBook().getIsbn() ).isEqualTo("111");
        verifyReservationFindByIdUserAndByBooksCalledOnce();
    }

    @Test
    public void whenInvalidIdUserAndValidBook_thenReservationShouldNotBeFound() {
        Reservation fromDb = reservationService.find(booksRepository.findById(1L ).orElse(null),2L);
        verifyReservationFindByIdUserAndByBooksCalledOnce();
        assertThat(fromDb).isNull();

    }

    @Test
    public void whenValidIdUserAndInvalidBook_thenReservationShouldNotBeFound() {
        Reservation fromDb = reservationService.find(booksRepository.findById(2L ).orElse(null),1L);
        verifyReservationFindByIdUserAndByBooksCalledOnce();
        assertThat(fromDb).isNull();

    }

    @Test
    public void given3Reservation_whenGetAll_thenReturn3Records() {

        Reservation reservation1 = createReservationTest(1L, 1L) ;
        Reservation reservation2 = createReservationTest(1L, 1L) ;
        Reservation reservation3 = createReservationTest(1L, 1L) ;

        List<Reservation> reservationList = reservationService.list();
        verifyFindAllReservationIsCalledOnce();

        assertThat(reservationList).hasSize(3).extracting(Reservation::getIdUserReservation).containsOnly(
                reservation1.getIdUserReservation(), reservation2.getIdUserReservation(), reservation3.getIdUserReservation() );
    }

    @Test
    public void given3Reservation_whenGetAllByIdUserReservation_thenReturn3Records() {

        Reservation reservation1 = createReservationTest(1L, 1L) ;
        Reservation reservation2 = createReservationTest(1L, 1L) ;
        Reservation reservation3 = createReservationTest(1L, 1L) ;

        List<Reservation> reservationList = reservationService.list( reservation1.getIdUserReservation());
        verifyFindAllReservationByIdUserReservationIsCalledOnce();

        assertThat(reservationList).hasSize(3).extracting(Reservation::getIdUserReservation).containsOnly(
                reservation1.getIdUserReservation(), reservation2.getIdUserReservation(), reservation3.getIdUserReservation() );
    }

    @Test
    public void given3Reservation_whenGetAllByBook_thenReturn3Records() {
        Reservation reservation1 = createReservationTest(1L, 1L) ;
        Reservation reservation2 = createReservationTest(1L, 1L) ;
        Reservation reservation3 = createReservationTest(1L, 1L) ;

        List<Reservation> reservationList = reservationService.list(reservation1.getBook() );
        verifyFindAllReservationByBooksIsCalledOnce();

        assertThat(reservationList).hasSize(3).extracting(Reservation::getIdUserReservation).containsOnly(
                reservation1.getIdUserReservation(), reservation2.getIdUserReservation(), reservation3.getIdUserReservation() );
    }

    @Test
    public void whenSaveBooks_thenReturnBooks() {

        Reservation reservation1 = reservationService.save(  createReservationTest(1L, 1L) );
        Reservation fromDb = reservationService.find( reservation1.getId() );
        verifySaveCalledOnce();
        assertThat(fromDb.getId() ).isEqualTo( reservation1.getId() );
    }

    private void verifyReservationFindByIdCalledOnce() {
        Mockito.verify(reservationRepository, VerificationModeFactory.times(1)).findById( Mockito.anyLong() );
        resetReservationDependency();
    }

    private void verifyReservationFindByIdUserAndByBooksCalledOnce() {
        Mockito.verify(reservationRepository, VerificationModeFactory.times(1)).findByBookAndIdUserReservationAndState(
                Mockito.any(Books.class),
                Mockito.anyLong(),
                Mockito.any() );
        resetReservationDependency();
    }

    private void verifyFindAllReservationIsCalledOnce() {
        Mockito.verify( reservationRepository, VerificationModeFactory.times(1)).findAll();
        resetReservationDependency();
    }
    private void verifyFindAllReservationByIdUserReservationIsCalledOnce() {
        Mockito.verify( reservationRepository, VerificationModeFactory.times(1)).findAllByIdUserReservationOrderByReservationDateAsc( Mockito.anyLong() );
        resetReservationDependency();
    }
    private void verifyFindAllReservationByBooksIsCalledOnce() {
        Mockito.verify( reservationRepository, VerificationModeFactory.times(1)).findAllByBookOrderByReservationDateAsc(Mockito.any(Books.class));
        resetReservationDependency();
    }

    private void verifySaveCalledOnce(){
        Mockito.verify(reservationRepository, VerificationModeFactory.times(1) ).save( any(Reservation.class) );
        resetReservationDependency();
    }
    private void resetReservationDependency() {
        Mockito.reset( reservationRepository );
        Mockito.reset( booksRepository );
        Mockito.reset( languageRepository );
        Mockito.reset( authorRepository );
        Mockito.reset( themeRepository );
        Mockito.reset( editionRepository );
    }
    private Reservation createReservationTest(Long idUser, Long idBook){
        return new Reservation(idUser, State.INPROGRESS,booksRepository.findById( idBook ).orElse( null ) );
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
