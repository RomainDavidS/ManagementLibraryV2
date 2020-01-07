package mbooks.controller;

import mbooks.JsonUtil;
import mbooks.MbooksApplication;
import mbooks.model.Reservation;
import mbooks.repository.IBooksRepository;
import mbooks.repository.IReservationRepository;
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

    @Before
    public void setUp(){
        reservationRepository.deleteAll();
    }

    @After
    public void erase(){
        reservationRepository.deleteAll();
    }

    @Test
    public void givenReservation_whenGetReservationById_thenStatus200() throws Exception {
        Reservation reservation = createReservationTest(-1L, -2L);
        reservationRepository.saveAndFlush( reservation );

        mvc.perform(get("/reservation/"+ reservation.getId() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUserReservation", is( -1 ) ) );
    }

    @Test
    public void givenReservation_whenGetAllReservation_thenStatus200() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -2L);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-2L, -2L);
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
    public void givenReservation_whenGetAllReservationByIdUser_thenStatus200() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -1L);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-1L, -2L);
        reservationRepository.saveAndFlush( reservation2 );

        mvc.perform(get("/reservation/user/"+ reservation1.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].book.id", is( -1 ) ) )
                .andExpect(jsonPath("$[1].book.id", is(-2  ) ) );
    }

    @Test
    public void givenReservation_whenGetAllReservationByIdBook_thenStatus200() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -1L);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-2L, -1L);
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
    public void givenReservation_whenGetPositionUser_thenStatus200() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -1L);
        reservationRepository.saveAndFlush( reservation1 );
        Reservation reservation2 = createReservationTest(-2L, -1L);
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
    public void whenValidInput_thenCreateReservation() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -1L);
        reservationRepository.saveAndFlush( reservation1 );

        mvc.perform(post("/reservation/save").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(reservation1)));

        List<Reservation> found = reservationRepository.findAll();
        assertThat(found).extracting(Reservation::getId).containsOnly(reservation1.getId());

    }

    @Test
    @Transactional
    public void whenValidInput_thenUpdateReservation() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -1L);
        reservationRepository.saveAndFlush( reservation1 );

        reservation1.setState( State.COMPLETED );

        mvc.perform(put("/reservation/update").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(reservation1)));

        Reservation reservation = reservationRepository.getOne( reservation1.getId() );

        assertThat( reservation.getState() ).isEqualTo( State.COMPLETED );

    }

    @Test
    @Transactional
    public void whenValidInput_thenDeleteReservation() throws Exception {
        Reservation reservation1 = createReservationTest(-1L, -1L);
        reservationRepository.saveAndFlush( reservation1 );

        mvc.perform(delete("/reservation/delete/"+ reservation1.getId() + "/" + reservation1.getIdUserReservation() ).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(reservation1)));

        Reservation reservation = reservationRepository.getOne( reservation1.getId() );

        assertThat( reservation.getState() ).isEqualTo( State.CANCELED );

    }

    private Reservation createReservationTest(Long idUser, Long idBook){
        return new Reservation(idUser, State.INPROGRESS,booksRepository.getOne(idBook ) );
    }
}
