package mbooks.service;

import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.*;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.*;
import mbooks.service.email.IEmailService;
import mbooks.service.lending.ILendingService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.date.SimpleDate;
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
public class LendingServiceIntegrationTest {
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
        Mockito.when( booksRepository.findByIsbn( books1.getIsbn() ) ).thenReturn(  books1   );

        Lending lending1 = createLendingTest(1L, 1L);
        lending1.setId( 1L );
        Mockito.when(lendingRepository.findById( lending1.getId() ) ).thenReturn( Optional.of( lending1 )  );
        Mockito.when(lendingRepository.findById(-99L)).thenReturn(Optional.empty());
        Mockito.when(lendingRepository.save(any(Lending.class))).thenReturn( lending1 );

        Lending lending2 = createLendingTest(1L, 1L);
        Lending lending3 = createLendingTest(1L, 1L);

        List<Lending> lendingList = Arrays.asList( lending1,lending2,lending3 );
        Mockito.when( lendingRepository.findAll() ).thenReturn( lendingList );
        Mockito.when( lendingRepository.findAllByBook( books1 ) ).thenReturn( lendingList );
        Mockito.when( lendingRepository.findAllByIdUser( 1L ) ).thenReturn( lendingList );


    }

    @Test
    public void whenValidId_thenLendingShouldBeFound() {
        Lending fromDb = lendingService.find(1L);
        assertThat(fromDb.getBook().getIsbn() ).isEqualTo("111");
        verifyLendingFindByIdCalledOnce();
    }

    @Test
    public void whenInvalidId_thenLendingShouldNotBeFound() {
        Lending fromDb = lendingService.find(-99L);
        verifyLendingFindByIdCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void given3Lending_whenGetAll_thenReturn3Records() {

        Lending lending1 = createLendingTest(1L, 1L);
        Lending lending2 = createLendingTest(1L, 1L);
        Lending lending3 = createLendingTest(1L, 1L);

        List<Lending> lendingList = lendingService.list();
        verifyFindAllLendingIsCalledOnce();

        assertThat(lendingList).hasSize(3).extracting(Lending::getIdUser).containsOnly(
                lending1.getIdUser(), lending2.getIdUser(), lending3.getIdUser() );
    }

    @Test
    public void given3Lending_whenGetAllByIdUser_thenReturn3Records() {

        Lending lending1 = createLendingTest(1L, 1L);
        Lending lending2 = createLendingTest(1L, 1L);
        Lending lending3 = createLendingTest(1L, 1L);

        List<Lending> lendingList = lendingService.list(1L);
        verifyFindAllLendingByIdUserIsCalledOnce();

        assertThat(lendingList).hasSize(3).extracting(Lending::getIdUser).containsOnly(
                lending1.getIdUser(), lending2.getIdUser(), lending3.getIdUser() );
    }

    @Test
    public void given3Lending_whenGetAllByIsbn_thenReturn3Records() {
        Lending lending1 = createLendingTest(1L, 1L);
        Lending lending2 = createLendingTest(1L, 1L);
        Lending lending3 = createLendingTest(1L, 1L);

        List<Lending> lendingList = lendingService.list("111");
        verifyFindAllLendingByIsbnIsCalledOnce();

        assertThat(lendingList).hasSize(3).extracting(Lending::getIdUser).containsOnly(
                lending1.getIdUser(), lending2.getIdUser(), lending3.getIdUser() );
    }

    @Test
    public void whenSaveBooks_thenReturnBooks() {

        Lending lending1 = lendingService.save(  createLendingTest(1L, 1L) );
        Lending fromDb = lendingService.find( lending1.getId() );
        verifySaveCalledOnce();
        assertThat(fromDb.getId() ).isEqualTo( lending1.getId() );
    }


    private void verifyLendingFindByIdCalledOnce() {
        Mockito.verify(lendingRepository, VerificationModeFactory.times(1)).findById( Mockito.anyLong() );
        resetLendingsDependency();
    }

    private void verifyFindAllLendingIsCalledOnce() {
        Mockito.verify( lendingRepository, VerificationModeFactory.times(1)).findAll();
        resetLendingsDependency();
    }

    private void verifyFindAllLendingByIdUserIsCalledOnce() {
        Mockito.verify( lendingRepository, VerificationModeFactory.times(1)).findAllByIdUser( 1L );

        resetLendingsDependency();
    }
    private void verifyFindAllLendingByIsbnIsCalledOnce() {
        Books books1 = createTestBooks("111");
        books1.setId( 1L );
        Mockito.verify( lendingRepository, VerificationModeFactory.times(1)).findAllByBook( books1 );
        resetLendingsDependency();
    }

    private void verifySaveCalledOnce(){
        Mockito.verify(lendingRepository, VerificationModeFactory.times(1) ).save( any(Lending.class) );
        resetLendingsDependency();
    }
    private void resetLendingsDependency() {
        Mockito.reset( lendingRepository );
        Mockito.reset( booksRepository );
        Mockito.reset( languageRepository );
        Mockito.reset( authorRepository );
        Mockito.reset( themeRepository );
        Mockito.reset( editionRepository );
    }
    private Lending createLendingTest(Long idUser, Long idBook){
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, 28 );
        Lending lending = new Lending(idUser,booksService.find( idBook ) );
        lending.setRenewal( 0 );
        lending.setEndDate( c.getTime() );
        return lending;
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
