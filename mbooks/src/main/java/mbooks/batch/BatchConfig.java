package mbooks.batch;

import mbooks.config.ApplicationPropertiesConfig;
import mbooks.proxies.IMicroserviceUsersProxy;
import mbooks.repository.IEmailRepository;
import mbooks.repository.ILendingRepository;
import mbooks.repository.IReservationRepository;
import mbooks.service.lending.ILendingService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;


@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Autowired
    private  ILendingRepository lendingRepository;

    @Autowired
    private  IMicroserviceUsersProxy usersProxy;

    @Autowired
    private IEmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private ApplicationPropertiesConfig appPropertiesConfig;

    @Autowired
    private IReservationRepository reservationRepository;

    @Bean
    public Step stepOne() {
        return steps.get("Revival")
                .tasklet(new MyTaskOne( lendingRepository, usersProxy,emailRepository,emailSender ) )
                .build();
    }
    @Bean
    public Step stepTwo() {
        return steps.get("ReservationCanceled")
                .tasklet(new MyTaskTwo( appPropertiesConfig,reservationRepository ) )
                .build();
    }

    @Bean
    public Job demoJob(){
        return jobs.get("SendRevival")
                .incrementer(new RunIdIncrementer())
                .start(stepOne())
                .next(stepTwo() )
                .build();
    }

}
