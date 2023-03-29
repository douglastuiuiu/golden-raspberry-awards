package br.com.douglasog87.texoit.goldenraspberryawards.model;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class AwardInterval implements Serializable {
    private static final long serialVersionUID = -8949415525032339182L;

    private final String producer;
    private final Long interval;
    private final Long previousWin;
    private final Long followingWin;
}
