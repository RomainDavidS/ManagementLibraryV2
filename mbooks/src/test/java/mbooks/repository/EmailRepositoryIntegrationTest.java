package mbooks.repository;

import mbooks.model.Email;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class EmailRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IEmailRepository emailRepository;

    @Before
    public void setUp(){
        emailRepository.deleteAll();
    }

    @After
    public void erase(){
        emailRepository.deleteAll();
    }

    @Test
    public void whenFindByName_thenReturnEmail() {

        Email email = createTestEmail("RomainDavid");

        entityManager.persistAndFlush(email);

        Email fromDb = emailRepository.findByName( email.getName() );
        assertThat(fromDb.getName()).isEqualTo( email.getName() );
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        Email fromDb = emailRepository.findByName("InvalidName");
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfEmails_whenFindAll_thenReturnAllEmail() {

        Email email1 = createTestEmail("Email1");
        Email email2 = createTestEmail("Email2");
        Email email3 = createTestEmail("Email3");


        entityManager.persist(email1);
        entityManager.persist(email2);
        entityManager.persist(email3);
        entityManager.flush();

        List<Email> allEmail = emailRepository.findAll();

        assertThat(allEmail).hasSize(3).extracting(Email::getName).containsOnly(
                email1.getName(), email2.getName(), email3.getName());
    }

    private Email createTestEmail(String name) {

        return new Email(name,"subject de "+name, "Content de "+ name);
    }
}
