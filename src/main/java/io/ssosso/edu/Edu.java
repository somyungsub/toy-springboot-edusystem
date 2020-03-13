package io.ssosso.edu;


import lombok.*;

import javax.persistence.*;


@Entity
@Setter @Getter @ToString
@Builder @NoArgsConstructor @AllArgsConstructor
public class Edu {

  @Id
  @GeneratedValue
  private Long eduId;

  private String eduName;

  private Integer curriculumYear;


  @ManyToOne(cascade = CascadeType.ALL)
  private EduAgency eduAgency;

  @ManyToOne(cascade = CascadeType.ALL)
  private EduClassificationLarge eduClassificationLarge;

  @ManyToOne(cascade = CascadeType.ALL)
  private EduClassificationMiddle eduClassificationMiddle;

  @ManyToOne(cascade = CascadeType.ALL)
  private EduClassificationSmall eduClassificationSmall;

  @ManyToOne(cascade = CascadeType.ALL)
  private EduCompletion eduCompletion;



}
