package br.com.douglasog87.texoit.goldenraspberryawards.repository;

import br.com.douglasog87.texoit.goldenraspberryawards.model.Award;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AwardRepository extends CrudRepository<Award, Long> {
    List<Award> findAllByWinnerIsTrueOrderByYearAsc();
}

