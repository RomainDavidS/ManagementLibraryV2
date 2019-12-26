package musers.service.users;

import musers.model.address.Address;
import musers.model.address.City;
import musers.model.user.Role;
import musers.model.user.Users;
import musers.repository.address.IAddressRepository;
import musers.repository.user.IRoleRepository;
import musers.repository.user.IUsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UsersRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IUsersRepository usersRepository;

    @Autowired
    private IAddressRepository addressRepository;


    @Test
    public void whenFindByName_thenReturnEmployee() {

        Address address = addressRepository.getOne( Long.valueOf( 1) );
        Role role = new Role();
        role.setId(Long.valueOf(-1 ));
        role.setName("ROLE_USER");
        role.setWording("Utilisateur");
        // given
        Users users = new Users(
                "Romain-David",
                "Sergeant",
                "romaind.@gmail.com",
                "xxx",
                "060000000",true, Arrays.asList(role) , address );
        entityManager.persist(users);
        entityManager.flush();

        // when
        Users found = usersRepository.findByEmailAndActiveTrue(users.getEmail() );

        // then
        assertThat(found.getEmail())
                .isEqualTo(users.getEmail());
    }

}