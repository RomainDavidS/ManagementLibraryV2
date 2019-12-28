package musers.controller;

import musers.MusersApplication;
import musers.model.Role;
import musers.model.Users;
import musers.repository.IUsersRepository;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;

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
        classes = MusersApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UsersRestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IUsersRepository usersRepository;

    @After
    public void resetDb() {
        usersRepository.deleteAll();
    }

    @Test
    public void givenUser_whenGetUserById_thenStatus200() throws Exception {
        Users bob= createTestUsers("bob");
        mvc.perform(get("/user/byId/"+ bob.getId()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName", is("bob")));

    }
    @Test
    public void givenUser_whenGetUserByEmail_thenStatus200() throws Exception {
        Users john= createTestUsers("john");
        mvc.perform(get("/user/byEmail/"+ john.getEmail()).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lastName", is("john")));
    }

    @Test
    public void givenUserName_whenGetUserById_thenStatus200() throws Exception {
        Users bob= createTestUsers("bob");
        mvc.perform(get("/user/name/"+ bob.getId()).contentType(MediaType.TEXT_PLAIN_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN_VALUE))
                .andExpect( content().string("Sergeant bob") );
    }

    private Users createTestUsers(String name) {
        Role role = new Role();
        role.setId( -1L );
        role.setName("ROLE_USER");
        role.setWording("Utilisateur");
        // given
        Users users = new Users(
                name,
                "Sergeant",
                name + "@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role) );

       return usersRepository.saveAndFlush( users );
    }
}
