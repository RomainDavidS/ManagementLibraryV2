package mfile.service;


import mfile.model.Cover;
import mfile.repository.ICoverRepository;
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
public class CoverServiceIntegrationTest {
    @TestConfiguration
    static class CoverServiceImplContextConfiguration {
        @Bean
        public CoverServiceImpl coverService() {
            return new CoverServiceImpl();
        }
    }

    @Autowired
    private ICoverService coverService;

    @MockBean
    private ICoverRepository coverRepository;


    @Before
    public void setUp() {
        Cover cover1 = createTestCover("cover1");
        cover1.setId("1234546789");
        Cover cover2 = createTestCover("cover2");
        Cover cover3 = createTestCover("cover3");

        List<Cover> allCovers = Arrays.asList(cover1, cover2, cover3);

        Mockito.when(coverRepository.findById(cover1.getId())).thenReturn(Optional.of(cover1));
        Mockito.when(coverRepository.findAll()).thenReturn(allCovers);
        Mockito.when(coverRepository.findById("InvalidId")).thenReturn(Optional.empty());

    }

    @Test
    public void whenValidId_thenUsersShouldBeFound() {
        Cover fromDb = coverService.find("1234546789");
        assertThat(fromDb.getFileName()).isEqualTo("cover1.png");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void whenInValidId_thenUsersShouldNotBeFound() {
        Cover fromDb = coverService.find("InvalidId");
        verifyFindByIdIsCalledOnce();
        assertThat(fromDb).isNull();
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(coverRepository, VerificationModeFactory.times(1)).findById( Mockito.anyString());
        Mockito.reset(coverRepository);
    }
    private Cover createTestCover(String name) {

        return new Cover(name + ".png","image/png",50000L, "contenu".getBytes(),"site");
    }
}
