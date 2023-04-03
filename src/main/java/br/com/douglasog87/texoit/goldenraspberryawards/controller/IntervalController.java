package br.com.douglasog87.texoit.goldenraspberryawards.controller;

import br.com.douglasog87.texoit.goldenraspberryawards.model.Interval;
import br.com.douglasog87.texoit.goldenraspberryawards.service.IntervalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@AllArgsConstructor
@RequestMapping("/awards/interval")
public class IntervalController {

    private final IntervalService intervalService;

    @GetMapping
    public ResponseEntity<Interval> getBetterAndWorseProducer() {
        return ResponseEntity.ok(intervalService.getBetterAndWorseProducer());
    }

}
