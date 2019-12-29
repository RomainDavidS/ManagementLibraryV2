package mfile.controller;

import mfile.MfileApplication;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import mfile.model.Cover;
import mfile.repository.ICoverRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK,
        classes = MfileApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class CoverControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ICoverRepository coverRepository;

    @After
    public void resetDb() {
        coverRepository.deleteAll();
    }

    @Test
    public void givenUser_whenGetUserById_thenStatus200() throws Exception {
        Cover cover1 = createTestCover("cover1");
        mvc.perform(get("/cover/"+ cover1.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fileName", is("cover1.png")));

    }

    private Cover createTestCover(String name) {
        Cover cover = new Cover(name + ".png","image/png",50000L, "contenu".getBytes(),"site");
        return coverRepository.saveAndFlush( cover );
    }
}
