package myapp.edu;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EduService {

  @Autowired
  EduRepository eduRepository;

  public List<Edu> getEduRecommendList(String userId) {
    return eduRepository.findAll();
  }
}
