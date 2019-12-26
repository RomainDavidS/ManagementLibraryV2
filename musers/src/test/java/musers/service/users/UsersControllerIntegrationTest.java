package musers.service.users;

import musers.controller.user.UsersController;
import musers.service.city.IUsersService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IUsersService usersService;

    @Before
    public void setUp() throws Exception {
    }
}
