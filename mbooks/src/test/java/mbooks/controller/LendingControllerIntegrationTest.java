package mbooks.controller;

import mbooks.JsonUtil;
import mbooks.MbooksApplication;
import mbooks.config.ApplicationPropertiesConfig;
import mbooks.model.*;
import mbooks.repository.*;
import mbooks.technical.state.reservation.State;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MbooksApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class LendingControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ILendingRepository lendingRepository;

    @Autowired
    private IBooksRepository booksRepository;
    @Autowired
    private IReservationRepository reservationRepository;
    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private ILanguageRepository languageRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IThemeRepository themeRepository;

    @Autowired
    private IEditionRepository editionRepository;

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
    public void givenLending_whenGetLendingById_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending = createLendingTest(-1L, books);
        lendingRepository.saveAndFlush( lending );

        mvc.perform(get("/lending/"+ lending.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUser", is( -1 ) ) );
    }
    @Test
    public void givenLending_whenGetAllLending_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending1 = createLendingTest(-1L, books);
        lendingRepository.saveAndFlush( lending1 );
        Lending lending2 = createLendingTest(-2L, books);
        lendingRepository.saveAndFlush( lending2 );

        mvc.perform(get("/lending/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].idUser", is( -1 ) ) )
                .andExpect(jsonPath("$[1].idUser", is( -2 ) ) );
    }
    @Test
    public void givenLending_whenGetAllLendingByUser_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending1 = createLendingTest(-1L, books);
        lendingRepository.saveAndFlush( lending1 );
        Lending lending2 = createLendingTest(-1L, books);
        lendingRepository.saveAndFlush( lending2 );

        mvc.perform(get("/lending/user/"+ lending1.getIdUser() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].idUser", is( -1 ) ) )
                .andExpect(jsonPath("$[1].idUser", is( -1 ) ) );
    }

    @Test
    public void givenLending_whenIsRenewable_thenStatus200AndReturnTrue() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending = createLendingTest(-1L,books);

        lendingRepository.saveAndFlush( lending );

        mvc.perform(get("/lending/isRenewable/"+ lending.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( true ) ) );
    }
    @Test
    public void givenLending_whenIsRenewable_thenStatus200AndReturnFalse() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending = createLendingTest(-1L,books);
        lending.setRenewal( 3);
        lendingRepository.saveAndFlush( lending );

        mvc.perform(get("/lending/isRenewable/"+ lending.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( false ) ) );
    }

    @Test
    public void givenLending_whenIsLendingPossible_thenStatus200AndReturnTrue() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );

        Reservation reservation = createReservationTest(-1L,books);
        reservationRepository.saveAndFlush( reservation );
        mvc.perform(get("/lending/isLendingPossible/"+ reservation.getBook().getId() + "/" + reservation.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( true ) ) );
    }

    @Test
    public void givenLending_whenIsLendingPossible_thenStatus200AndReturnFalse() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation );
        Lending lending = createLendingTest(-1L, books);

        lendingRepository.saveAndFlush( lending );
        mvc.perform(get("/lending/isLendingPossible/"+ reservation.getBook().getId() + "/" + reservation.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( false ) ) );
    }

    @Test
    @Transactional
    public void givenLending_whenGetAllLendingByBook_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending1 = createLendingTest(-1L,books);
        lendingRepository.saveAndFlush( lending1 );
        Lending lending2 = createLendingTest(-1L,books);
        lendingRepository.saveAndFlush( lending2 );

        mvc.perform(get("/lending/book/"+ lending1.getBook().getIsbn() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect( content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(greaterThanOrEqualTo(2) ) ) )
                .andExpect( jsonPath("$[0].book.isbn", is( lending1.getBook().getIsbn() ) ) )
                .andExpect( jsonPath("$[1].book.isbn", is( lending2.getBook().getIsbn() ) ) );
    }

    @Test
    @Transactional
    public void whenValidInput_thenCreateLending() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending = createLendingTest(-1L,books);
        lendingRepository.saveAndFlush( lending );

        mvc.perform(post("/lending/save/fromReservation").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(lending)));

        List<Lending> found = lendingRepository.findAll();
        assertThat(found).extracting(Lending::getId).containsOnly(lending.getId());
    }

    @Test
    @Transactional
    public void whenValidInput_thenRenewalLending() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending = createLendingTest(-1L, books);
        lendingRepository.saveAndFlush( lending );


        mvc.perform(put("/lending/renewal").contentType( MediaType.APPLICATION_JSON ).content( JsonUtil.toJson( lending.getId() ) ) );

       Lending fromDb = lendingRepository.findById( lending.getId() ).orElse(null);

        assertThat(fromDb.getRenewal() ).isEqualTo( 1 );
    }

    @Test
    @Transactional
    public void whenValidInput_thenLendingReturned() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Lending lending = createLendingTest(-1L, books);
        lendingRepository.saveAndFlush( lending );
        mvc.perform(put("/lending/return").contentType( MediaType.APPLICATION_JSON ).content( JsonUtil.toJson( lending.getId() ) ) );

        Lending fromDb = lendingRepository.findById( lending.getId() ).orElse(null);

        assertThat(fromDb.getReturnDate() ).isNotNull();
    }


    @Test
    public void whenGetRenewalDay_thenStatus200() throws Exception {
        mvc.perform(get("/lending/getRenewalDay" ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( appPropertiesConfig.getRenewalDay() ) ) );
    }

    private Lending createLendingTest(Long idUser, Books books){
        Calendar c = Calendar.getInstance();
        c.setTime( new Date() );
        c.add(Calendar.DAY_OF_MONTH, 28 );
        Lending lending = new Lending(idUser,books );
        lending.setRenewal( 0 );
        lending.setEndDate( c.getTime() );
        return lending;
    }

    private Reservation createReservationTest(Long idUser, Books books){
        return new Reservation(idUser, State.INPROGRESS,books );
    }

    private Books createTestBooks(String isbn) {

        Language language = languageRepository.getOne(1L);
        Author author = authorRepository.getOne(1L);
        Theme theme = themeRepository.getOne(3L);
        Edition edition = editionRepository.getOne(1L);

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
