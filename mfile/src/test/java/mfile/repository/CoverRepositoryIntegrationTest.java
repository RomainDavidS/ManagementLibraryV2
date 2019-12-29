package mfile.repository;

import mfile.model.Cover;
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
public class CoverRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ICoverRepository coverRepository;

    @Before
    public void setUp(){
        coverRepository.deleteAll();
    }

    @After
    public void erase(){
        coverRepository.deleteAll();
    }

    @Test
    public void whenFindById_thenReturnCover() {

        Cover cover = createTestCover("RomainDavid");

        entityManager.persistAndFlush(cover);

        Cover fromDb = coverRepository.findById(cover.getId()).orElse(null);
        assertThat(fromDb.getFileName()).isEqualTo( cover.getFileName() );
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Cover fromDb = coverRepository.findById("InvalidId").orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenSetOfCovers_whenFindAll_thenReturnAllCover() {

        Cover cover1 = createTestCover("cover1");
        Cover cover2 = createTestCover("cover2");
        Cover cover3 = createTestCover("cover3");

        entityManager.persist(cover1);
        entityManager.persist(cover2);
        entityManager.persist(cover3);
        entityManager.flush();

        List<Cover> allCover = coverRepository.findAll();

        assertThat(allCover).hasSize(3).extracting(Cover::getFileName).containsOnly(
                cover1.getFileName(), cover2.getFileName(), cover3.getFileName());
    }

    private Cover createTestCover(String name) {

        return new Cover(name + ".png","image/png",50000L, "contenu".getBytes(),"site");
    }

}
