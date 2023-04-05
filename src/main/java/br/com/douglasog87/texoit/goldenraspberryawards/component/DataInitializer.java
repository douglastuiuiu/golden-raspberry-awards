package br.com.douglasog87.texoit.goldenraspberryawards.component;

import br.com.douglasog87.texoit.goldenraspberryawards.service.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final AwardService awardService;

    @Override
    public void run(ApplicationArguments args) {
        awardService.loadDataFromCSV();
    }

}
