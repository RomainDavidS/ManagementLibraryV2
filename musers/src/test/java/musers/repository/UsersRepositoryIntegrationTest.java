package musers.repository;

import musers.model.Role;
import musers.model.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUsersRepository usersRepository;

    @Before
    public void setUp(){
        usersRepository.deleteAll();
    }

    @After
    public void erase(){
        usersRepository.deleteAll();
    }

    @Test
    public void whenFindByEmailAndActiveTrue_thenReturnUsers() {
        Users users = createTestUsers("Romain-David");

        entityManager.persist(users);
        entityManager.flush();

        // when
        Users found = usersRepository.findByEmailAndActiveTrue(users.getEmail() );

        // then
        assertThat(found.getEmail())
                .isEqualTo(users.getEmail());
    }

    @Test
    public void whenInvalidEmail_thenReturnNull() {
        Users fromDb = usersRepository.findByEmailAndActiveTrue("doesNotExist");
        assertThat(fromDb).isNull();
    }


    @Test
    public void whenFindById_thenReturnEmployee() {

        Users users = createTestUsers("Romain-David");

        entityManager.persistAndFlush(users);

        Users fromDb = usersRepository.findById(users.getId()).orElse(null);
        assertThat(fromDb.getEmail()).isEqualTo(users.getEmail());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Users fromDb = usersRepository.findById(-11l).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfUsers_whenFindAll_thenReturnAllUsers() {

        Users users1 = createTestUsers("users1");
        Users users2 = createTestUsers("users2");
        Users users3 = createTestUsers("users3");


        entityManager.persist(users1);
        entityManager.persist(users2);
        entityManager.persist(users3);
        entityManager.flush();

        List<Users> allUsers = usersRepository.findAll();

        assertThat(allUsers).hasSize(3).extracting(Users::getEmail).containsOnly(
                users1.getEmail(), users2.getEmail(), users3.getEmail());
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