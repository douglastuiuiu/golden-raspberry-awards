package br.com.douglasog87.texoit.goldenraspberryawards.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AwardInterval implements Serializable {
    private static final long serialVersionUID = -8949415525032339182L;

    private String producer;
    private Long interval;
    private Long previousWin;
    private Long followingWin;
}
