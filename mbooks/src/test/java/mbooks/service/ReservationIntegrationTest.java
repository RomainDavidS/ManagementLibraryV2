package mbooks.service;

import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.Reservation;
import mbooks.proxies.IUsersProxy;
import mbooks.repository.*;
import mbooks.service.email.IEmailService;
import mbooks.service.lending.ILendingService;
import mbooks.service.reservation.IReservationService;
import mbooks.technical.date.SimpleDate;
import mbooks.technical.state.reservation.State;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

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



    private Reservation createReservationTest(Long idUser, Long idBook){
        return new Reservation(idUser, State.INPROGRESS,booksRepository.getOne(idBook ) );
    }
}
