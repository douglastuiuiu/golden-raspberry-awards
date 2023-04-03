package br.com.douglasog87.texoit.goldenraspberryawards.service;

import br.com.douglasog87.texoit.goldenraspberryawards.model.Award;
import br.com.douglasog87.texoit.goldenraspberryawards.model.AwardInterval;
import br.com.douglasog87.texoit.goldenraspberryawards.model.Interval;
import br.com.douglasog87.texoit.goldenraspberryawards.repository.AwardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class IntervalService {

    private final AwardRepository awardRepository;

    public Interval getBetterAndWorseProducer() {

        List<Award> awards = awardRepository.findAllByWinnerIsTrueOrderByYearAsc();

        // Calcula os intervalos MIN e MAX para cada produtor
        Map<String, AwardInterval> awardIntervalsMin = new HashMap<>();
        buildAwardIntervalMin(awards, awardIntervalsMin);

        Map<String, AwardInterval> awardIntervalsMax = new HashMap<>();
        buildAwardIntervalMax(awards, awardIntervalsMax);

        // Cria o objeto Interval populado com as listas de menor e maior intervalo
        return Interval.builder()
                .min(cleanMinList(new ArrayList<>(awardIntervalsMin.values())))
                .max(cleanMaxList(new ArrayList<>(awardIntervalsMax.values())))
                .build();
    }

    private void buildAwardIntervalMin(List<Award> awards, Map<String, AwardInterval> awardIntervalsMin) {
        for (Award award : awards) {
            String producer = award.getProducers();
            Long year = award.getYear();

            if (awardIntervalsMin.containsKey(producer)) {
                AwardInterval awardInterval = awardIntervalsMin.get(producer);
                if (Objects.isNull(awardInterval.getFollowingWin())) {
                    awardInterval.setFollowingWin(year);
                }
                if (Objects.isNull(awardInterval.getInterval())) {
                    Long interval = awardInterval.getFollowingWin() - awardInterval.getPreviousWin();
                    awardInterval.setInterval(interval);
                }
            } else {
                AwardInterval awardInterval = AwardInterval.builder()
                        .producer(producer)
                        .previousWin(year)
                        .build();
                awardIntervalsMin.put(producer, awardInterval);
            }
        }
    }

    private void buildAwardIntervalMax(List<Award> awards, Map<String, AwardInterval> awardIntervalsMin) {
        for (Award award : awards) {
            String producer = award.getProducers();
            Long year = award.getYear();

            if (awardIntervalsMin.containsKey(producer)) {
                AwardInterval awardInterval = awardIntervalsMin.get(producer);
                awardInterval.setFollowingWin(year);
                Long interval = awardInterval.getFollowingWin() - awardInterval.getPreviousWin();
                awardInterval.setInterval(interval);
            } else {
                AwardInterval awardInterval = AwardInterval.builder()
                        .producer(producer)
                        .previousWin(year)
                        .build();
                awardIntervalsMin.put(producer, awardInterval);
            }
        }
    }

    private List<AwardInterval> cleanMinList(List<AwardInterval> min) {
        Collections.sort(min, Comparator.comparingLong(AwardInterval::getInterval));

        List<AwardInterval> minCleaned = new ArrayList<>();
        minCleaned.add(min.get(0));
        if (min.size() > 1) {
            for (int i = 1; i < min.size(); i++) {
                AwardInterval awardInterval = min.get(i);
                Long intervalPrevious = min.get(0).getInterval();
                Long intervalThis = min.get(i).getInterval();
                if (intervalThis.compareTo(intervalPrevious) == 0) {
                    minCleaned.add(awardInterval);
                }
            }
        }
        Collections.sort(minCleaned, Comparator.comparing(AwardInterval::getProducer));
        return minCleaned;
    }

    private List<AwardInterval> cleanMaxList(List<AwardInterval> max) {
        Collections.sort(max, Comparator.comparingLong(AwardInterval::getInterval));
        Collections.reverse(max);

        List<AwardInterval> maxCleaned = new ArrayList<>();
        maxCleaned.add(max.get(0));
        if (max.size() > 1) {
            for (int i = 1; i < max.size(); i++) {
                AwardInterval awardInterval = max.get(i);
                Long intervalPrevious = max.get(0).getInterval();
                Long intervalThis = max.get(i).getInterval();
                if (intervalThis.compareTo(intervalPrevious) == 0) {
                    maxCleaned.add(awardInterval);
                }
            }
        }
        Collections.sort(maxCleaned, Comparator.comparing(AwardInterval::getProducer));
        return maxCleaned;
    }

}