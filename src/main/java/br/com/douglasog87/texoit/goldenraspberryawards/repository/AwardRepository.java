package br.com.douglasog87.texoit.goldenraspberryawards.repository;

import br.com.douglasog87.texoit.goldenraspberryawards.model.Award;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends CrudRepository<Award, Long> {
}

