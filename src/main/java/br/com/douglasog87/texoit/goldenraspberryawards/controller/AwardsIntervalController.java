package br.com.douglasog87.texoit.goldenraspberryawards.controller;

import br.com.douglasog87.texoit.goldenraspberryawards.service.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/awards/interval")
public class AwardsIntervalController {

    private final AwardService awardService;

}
