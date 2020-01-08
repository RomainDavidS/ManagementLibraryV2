package musers.service;


import musers.model.Role;
import musers.model.Users;
import musers.repository.IUsersRepository;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
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
        // given
        Users john = createTestUsers("john" );
        john.setId(11L);
        Users bob = createTestUsers("bob" );
        Users alex = createTestUsers("alex" );



        List<Users> allEmployees = Arrays.asList(john, bob, alex);
        Mockito.when(usersRepository.findByEmailAndActiveTrue(john.getEmail())).thenReturn(john);
        Mockito.when(usersRepository.findByEmailAndActiveTrue(alex.getEmail())).thenReturn(alex);
        Mockito.when(usersRepository.findByEmailAndActiveTrue("Invalid Email")).thenReturn(null);
        Mockito.when(usersRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(usersRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(usersRepository.findById(-99L)).thenReturn(Optional.empty());

    }

    @Test
    public void whenValidEmailAndActiveTrue_thenUsersShouldBeFound() {
        String name = "john@gmail.com";
        Users found = usersService.findUser( name );

        assertThat( found.getEmail()).isEqualTo(name);
    }

    @Test
    public void whenInValidEmailAndActiveTrue_thenUsersShouldNotBeFound() {
        Users fromDb = usersService.findUser("Invalid Email");
        assertThat(fromDb).isNull();

        verifyFindByEmailAndActiveTrueIsCalledOnce( "Invalid Email");
    }

    @Test
    public void whenValidId_thenUsersShouldBeFound() {
        Users fromDb = usersService.findUser(11L);
        assertThat(fromDb.getEmail()).isEqualTo("john@gmail.com");

        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void whenInvalidId_thenUsersShouldNotBeFound() {
        Users fromDb = usersService.findUser(-99L);
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenUserName_whenGetUserName_thenReturnUserName(){
        Users fromDb = usersService.findUser(11L);
        String userName = usersService.getUserName( 11L );

        assertThat(fromDb.getFirstName() + " " + fromDb.getLastName() ).isEqualTo( userName );
    }


    @Test
    public void given3users_whenGetAll_thenReturn3Records() {

        Users john = createTestUsers("john" );
        Users bob = createTestUsers("bob" );
        Users alex = createTestUsers("alex" );

        List<Users> allUsers = usersService.findAll();
        verifyFindAllUsersIsCalledOnce();

        assertThat(allUsers).hasSize(3).extracting(Users::getEmail).containsOnly(
                john.getEmail(), bob.getEmail(), alex.getEmail());
    }

    private void verifyFindByEmailAndActiveTrueIsCalledOnce(String email) {
        Mockito.verify(usersRepository, VerificationModeFactory.times(1)).findByEmailAndActiveTrue( email );
        Mockito.reset(usersRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(usersRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(usersRepository);
    }

    private void verifyFindAllUsersIsCalledOnce() {
        Mockito.verify(usersRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(usersRepository);
    }

    private Users createTestUsers(String name) {
        Role role = new Role();
        role.setId( -1L );
        role.setName("ROLE_USER");
        role.setWording("Utilisateur");
        // given
        return new Users(
                name,
                "Sergeant",
                name + "@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role) );
    }
}
