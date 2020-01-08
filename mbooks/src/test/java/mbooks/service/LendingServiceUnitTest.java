package mbooks.service;

import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.*;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.*;
import mbooks.service.email.IEmailService;
import mbooks.service.lending.ILendingService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.date.SimpleDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@Import(BooksTestConfiguration.class)
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class LendingServiceUnitTest {

    @Value("${microservice.config.renewalNumber}")
    private Integer renewalNumber;

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
        appPropertiesConfig.setRenewalNumber(renewalNumber);
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



    }

    @Test
    public void whenIsRenewable_thenReturnTrue(){
        Lending lending = createLendingTest(1L, 1L);
        assertThat(lendingService.isRenewable( lending) ).isEqualTo( true );
    }
    @Test
    public void whenIsNotRenewable_thenReturnFalse(){
        Lending lending = createLendingTest(1L, 1L);
        lending.setRenewal(1);
        assertThat(lendingService.isRenewable( lending) ).isEqualTo( false );
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
