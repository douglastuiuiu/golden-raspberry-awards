package br.com.douglasog87.texoit.goldenraspberryawards.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Interval implements Serializable {

    private static final long serialVersionUID = -3169808836419739160L;

    private final List<AwardInterval> min;
    private final List<AwardInterval> max;

}
