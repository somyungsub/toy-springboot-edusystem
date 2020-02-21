package myapp.edu;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
@ToString
public class EduClassificationSmall {

  @Id
  private String eduClassificationCode;

  private String eduClassificationName;

}
