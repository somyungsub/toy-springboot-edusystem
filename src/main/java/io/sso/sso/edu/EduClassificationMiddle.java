package io.sso.sso.edu;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
@ToString
public class EduClassificationMiddle {

  @Id
  private String eduClassificationCode;

  private String eduClassificationName;


}
