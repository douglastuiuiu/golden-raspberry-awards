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

        final List<Award> awards = awardRepository.findAllByWinnerIsTrueOrderByYearAsc();

        // Calcula os intervalos mínimo para cada produtor
        final Map<String, AwardInterval> awardIntervalMinMap = new HashMap<>();
        buildAwardIntervalMap(awards, awardIntervalMinMap, true);

        // Calcula os intervalos máximo para cada produtor
        final Map<String, AwardInterval> awardIntervalMaxMap = new HashMap<>();
        buildAwardIntervalMap(awards, awardIntervalMaxMap, false);

        // ordena de forma crescente pelo `interval` e chama o método que limpa a lista mantendo apenas itens com o mesmo `interval`
        final ArrayList<AwardInterval> awardIntervalMinList = new ArrayList<>(awardIntervalMinMap.values());
        Collections.sort(awardIntervalMinList, Comparator.comparingLong(AwardInterval::getInterval));
        List<AwardInterval> awardIntervalMin = cleanList(awardIntervalMinList);

        // ordena de forma decrescente pelo `interval` e chama o método que limpa a lista mantendo apenas itens com o mesmo `interval`
        final ArrayList<AwardInterval> awardIntervalMaxList = new ArrayList<>(awardIntervalMaxMap.values());
        Collections.sort(awardIntervalMaxList, Comparator.comparingLong(AwardInterval::getInterval));
        Collections.reverse(awardIntervalMaxList);
        List<AwardInterval> awardIntervalMax = cleanList(awardIntervalMaxList);

        return Interval.builder().min(awardIntervalMin).max(awardIntervalMax).build();
    }

    private void buildAwardIntervalMap(List<Award> awards, Map<String, AwardInterval> awardIntervals, Boolean isMin) {
        for (Award award : awards) {
            final String producer = award.getProducers();
            final Long year = award.getYear();

            if (awardIntervals.containsKey(producer)) {
                AwardInterval awardInterval = awardIntervals.get(producer);
                if (isMin) {
                    //para o calculo do menor intervalo, só seta o ano/intervalo caso o campo ainda esteja em branco
                    if (Objects.isNull(awardInterval.getFollowingWin())) {
                        awardInterval.setFollowingWin(year);
                    }
                    if (Objects.isNull(awardInterval.getInterval())) {
                        awardInterval.setInterval(calculateInterval(awardInterval));
                    }
                } else {
                    //para o calculo do maior intervalo, já estando o produtor na lista, sempre pega o maior ano/intervalo
                    awardInterval.setFollowingWin(year);
                    awardInterval.setInterval(calculateInterval(awardInterval));
                }
            } else {
                final AwardInterval awardInterval = AwardInterval.builder()
                        .producer(producer)
                        .previousWin(year)
                        .build();
                awardIntervals.put(producer, awardInterval);
            }
        }
    }

    private long calculateInterval(AwardInterval awardInterval) {
        return awardInterval.getFollowingWin() - awardInterval.getPreviousWin();
    }

    private List<AwardInterval> cleanList(List<AwardInterval> awardIntervalList) {
        final List<AwardInterval> cleaned = new ArrayList<>();
        //adiciona o primeiro da lista pois já reebe ordenado
        cleaned.add(awardIntervalList.get(0));

        //processa a partir do segundo registro, adicionando apenas se tem o mesmo interval do anterior
        for (int i = 1; i < awardIntervalList.size(); i++) {
            final AwardInterval awardInterval = awardIntervalList.get(i);
            final Long intervalPrevious = awardIntervalList.get(0).getInterval();
            final Long intervalThis = awardIntervalList.get(i).getInterval();
            if (intervalThis.compareTo(intervalPrevious) == 0) {
                cleaned.add(awardInterval);
            }
        }

        //ordena alfabeticamente pelos produtores
        Collections.sort(cleaned, Comparator.comparing(AwardInterval::getProducer));
        return cleaned;
    }

}