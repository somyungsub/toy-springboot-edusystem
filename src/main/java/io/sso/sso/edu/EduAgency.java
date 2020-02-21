package myapp.edu;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter @Getter
@Builder @NoArgsConstructor @AllArgsConstructor
@ToString
public class EduAgency {

  @Id
  private String agencyCode;

  private String agencyName;

}
