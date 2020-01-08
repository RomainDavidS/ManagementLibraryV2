package mbooks.controller;

import mbooks.JsonUtil;
import mbooks.MbooksApplication;
import mbooks.model.*;
import mbooks.repository.*;
import mbooks.technical.state.reservation.State;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MbooksApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ReservationControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private IBooksRepository booksRepository;

    @Autowired
    private IReservationRepository reservationRepository;

    @Autowired
    private ILendingRepository lendingRepository;

    @Autowired
    private ILanguageRepository languageRepository;

    @Autowired
    private IAuthorRepository authorRepository;

    @Autowired
    private IThemeRepository themeRepository;

    @Autowired
    private IEditionRepository editionRepository;



    @Test
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void givenReservation_whenGetReservationById_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation );

        mvc.perform(get("/reservation/"+ reservation.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUserReservation", is( -1 ) ) );
    }

    @Test
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void givenReservation_whenGetAllReservation_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation1 = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-2L, books);
        reservationRepository.saveAndFlush( reservation2 );

        mvc.perform(get("/reservation/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].idUserReservation", is( -1 ) ) )
                .andExpect(jsonPath("$[1].idUserReservation", is( -2 ) ) );
    }
    @Test
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void givenReservation_whenGetAllReservationByIdUser_thenStatus200() throws Exception {
        Books books1 = createTestBooks("111");
        booksRepository.saveAndFlush( books1 );
        Reservation reservation1 = createReservationTest(-1L, books1);
        reservationRepository.saveAndFlush( reservation1 );
        Books books2 = createTestBooks("222");
        booksRepository.saveAndFlush( books2 );
        Reservation reservation2 = createReservationTest(-1L, books2);
        reservationRepository.saveAndFlush( reservation2 );

        mvc.perform(get("/reservation/user/"+ reservation1.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].id", is( reservation1.getId().intValue()  ) ) )
                .andExpect(jsonPath("$[1].id", is( reservation2.getId().intValue() ) ) );
    }

    @Test
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void givenReservation_whenGetAllReservationByIdBook_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation1 = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-2L, books);
        reservationRepository.saveAndFlush( reservation2 );

        mvc.perform(get("/reservation/book/"+ reservation1.getBook().getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2) ) ))
                .andExpect(jsonPath("$[0].idUserReservation", is( -1 ) ) )
                .andExpect(jsonPath("$[1].idUserReservation", is( -2 ) ) );
    }

    @Test
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void givenReservation_whenGetPositionUser_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation1 = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-2L, books);
        reservationRepository.saveAndFlush( reservation2 );

        mvc.perform(get("/reservation/position/"+ reservation1.getBook().getId() + "/" + reservation2.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( 2 ) ) );

        mvc.perform(get("/reservation/position/"+ reservation1.getBook().getId() + "/" + reservation1.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is( 1 ) ) );
    }

    @Test
    @Transactional
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void whenValidInput_thenCreateReservation() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation1 = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation1 );

        mvc.perform(post("/reservation/save").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(reservation1)));

        List<Reservation> found = reservationRepository.findAll();
        assertThat(found).extracting(Reservation::getId).containsOnly(reservation1.getId());

    }

    @Test
    @Transactional
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void whenValidInput_thenUpdateReservation() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation1 = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation1 );

        reservation1.setState( State.COMPLETED );

        mvc.perform(put("/reservation/update").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(reservation1)));

        Reservation reservation = reservationRepository.getOne( reservation1.getId() );

        assertThat( reservation.getState() ).isEqualTo( State.COMPLETED );

    }

    @Test
    @Transactional
    @Sql( scripts = "classpath:dataBefore.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD )
    @Sql( scripts = "classpath:dataAfter.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD )
    public void whenValidInput_thenDeleteReservation() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );
        Reservation reservation1 = createReservationTest(-1L, books);
        reservationRepository.saveAndFlush( reservation1 );

        mvc.perform(delete("/reservation/delete/"+ reservation1.getId() + "/" + reservation1.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(reservation1)));

        Reservation reservation = reservationRepository.getOne( reservation1.getId() );

        assertThat( reservation.getState() ).isEqualTo( State.CANCELED );

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
