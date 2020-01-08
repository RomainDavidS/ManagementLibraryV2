package mbooks.controller;

import mbooks.JsonUtil;
import mbooks.MbooksApplication;
import mbooks.model.*;
import mbooks.repository.*;
import mbooks.technical.state.books.BooksState;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MbooksApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class BooksControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

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
    public void givenBooks_whenGetBooksById_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );

        mvc.perform(get("/book/id/"+ books.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn", is( books.getIsbn() ) ) );
    }
    @Test
    public void givenBooks_whenGetBooksByIsbn_thenStatus200() throws Exception {
        Books books = createTestBooks("222");
        booksRepository.saveAndFlush( books );

        mvc.perform(get("/book/isbn/"+ books.getIsbn() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isbn", is( books.getIsbn() ) ) );

    }

    @Test
    public void givenBooks_whenGetAllBooks_thenStatus200() throws Exception {
        Books books1 = createTestBooks("111");
        booksRepository.saveAndFlush( books1 );
        Books books2 = createTestBooks("222");
        booksRepository.saveAndFlush( books2 );

        mvc.perform(get("/book/all").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].isbn", is(books1.getIsbn())))
                .andExpect(jsonPath("$[1].isbn", is(books2.getIsbn())));
    }

    @Test
    public void givenBooks_whenGetStateBooks_thenStatus200() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );

        mvc.perform(get("/book/state/"+ books.getId()+"/"+ 1L).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(BooksState.AVAILABLE.name()) ) );
    }

    @Test
    @Transactional
    public void whenValidInput_thenUpdateBooks() throws Exception {
        Books books = createTestBooks("111");
        booksRepository.saveAndFlush( books );

        mvc.perform(put("/book/update").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(books)));

        List<Books> found = booksRepository.findAll();
        assertThat(found).extracting(Books::getIsbn).containsOnly(books.getIsbn());
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
