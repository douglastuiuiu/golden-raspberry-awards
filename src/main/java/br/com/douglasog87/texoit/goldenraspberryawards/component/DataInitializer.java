package br.com.douglasog87.texoit.goldenraspberryawards.component;

import br.com.douglasog87.texoit.goldenraspberryawards.service.AwardService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationRunner {

    private final AwardService awardService;

    public DataInitializer(AwardService awardService) {
        this.awardService = awardService;
    }

    @Override
    public void run(ApplicationArguments args) {
        awardService.loadDataFromCSV();
    }
}
