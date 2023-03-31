package br.com.douglasog87.texoit.goldenraspberryawards.service;

import br.com.douglasog87.texoit.goldenraspberryawards.model.Award;
import br.com.douglasog87.texoit.goldenraspberryawards.repository.AwardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AwardService {

    private final AwardRepository awardRepository;

    public void loadDataFromCSV() {
        log.info("Iniciando carregamento de dados a partir do arquivo CSV");

        List<Award> awards = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/movielist.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            // Ignorar a primeira linha, que contém os nomes das colunas
            bufferedReader.readLine();

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                final String[] data = line.split(";");

                final String data4 = (data.length < 5) ? "no" : data[4];
                final Boolean winner = (data4 == "no") ? Boolean.FALSE : Boolean.TRUE;

                Award award = Award.builder()
                        .year(Long.parseLong(data[0]))
                        .title(data[1])
                        .studios(data[2])
                        .producers(data[3])
                        .winner(winner)
                        .build();
                awards.add(award);
            }

            awardRepository.saveAll(awards);
        } catch (Exception e) {
            log.error("Erro ao ler arquivo CSV: {}", e.getMessage());
        }
    }
}