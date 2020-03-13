package io.ssosso.edu;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
@ToString
public class EduCompletion {

  @Id
  private String eduCompletionCode;

  private String eduCompletionName;

}
