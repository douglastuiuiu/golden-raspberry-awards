package br.com.douglasog87.texoit.goldenraspberryawards.service;

import br.com.douglasog87.texoit.goldenraspberryawards.model.Award;
import br.com.douglasog87.texoit.goldenraspberryawards.repository.AwardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    public void loadDataFromCSV() {
        List<Award> awards = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/movielist.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Ignorar a primeira linha, que contém os nomes das colunas
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                Award award = Award.builder()
                        .year(Long.parseLong(data[0]))
                        .title(data[1])
                        .studios(data[2])
                        .producers(data[3])
                        .winner(Boolean.parseBoolean(data[4]))
                        .build();
                awards.add(award);
            }

            awardRepository.saveAll(awards);
        } catch (Exception e) {
            log.error("Erro ao ler arquivo CSV: {}", e.getMessage());
        }
    }
}