package br.com.douglasog87.texoit.goldenraspberryawards.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "award")
public class Award implements Serializable {

    private static final long serialVersionUID = -404707488723672609L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long year;
    private String title;
    private String studios;
    private String producers;
    private Boolean winner;

}
