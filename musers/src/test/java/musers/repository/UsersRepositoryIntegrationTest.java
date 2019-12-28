package musers.repository;

import musers.model.Role;
import musers.model.Users;
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


    @Test
    public void whenFindByEmailAndActiveTrue_thenReturnUsers() {

        Role role = new Role();
        role.setId(-1L);
        role.setName("ROLE_USER");
        role.setWording("Utilisateur");
        // given
        Users users = new Users(
                "Romain-David",
                "Sergeant",
                "romaind.@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role)  );
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

        Role role = new Role();
        role.setId( -1L );
        role.setName("ROLE_USER");
        role.setWording("Utilisateur");
        // given
        Users users = new Users(
                "Romain-David",
                "Sergeant",
                "romaind.@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role) );

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

        Role role = new Role();
        role.setId(-1L);
        role.setName("ROLE_USER");
        role.setWording("Utilisateur");
        // given
        Users users1 = new Users(
                "Romain-David",
                "Sergeant",
                "romaind1@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role)  );
        Users users2 = new Users(
                "Romain-David",
                "Sergeant",
                "romaind2@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role)  );
        Users users3 = new Users(
                "Romain-David",
                "Sergeant",
                "romaind3@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role));


        entityManager.persist(users1);
        entityManager.persist(users2);
        entityManager.persist(users3);
        entityManager.flush();

        List<Users> allUsers = usersRepository.findAll();

        assertThat(allUsers).hasSize(6).extracting(Users::getEmail).containsOnly(
                users1.getEmail(), users2.getEmail(), users3.getEmail(),
                "romaind.ocrlibrary@gmail.com",
                "romaind.sergeant@gmail.com",
                "romaindavid.sergeant@gmail.com");
    }


}