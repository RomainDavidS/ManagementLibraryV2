package musers.service.users;

import musers.repository.user.IUsersRepository;
import musers.service.city.IUsersService;
import musers.service.city.UsersServiceImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class UsersServiceImplIntegrationTest {

    @TestConfiguration
    static class UsersServiceImplContextConfiguration {
        @Bean
        public UsersServiceImpl usersService() {
            return new UsersServiceImpl();
        }
    }

    @Autowired
    private IUsersService usersService;

    @MockBean
    private IUsersRepository usersRepository;

    @Before
    public void setUp() {
        /*
        Employee john = new Employee("john");
        john.setId(11L);

        Employee bob = new Employee("bob");
        Employee alex = new Employee("alex");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
        */

    }
}
