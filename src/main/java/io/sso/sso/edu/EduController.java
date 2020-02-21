package myapp.edu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EduController {

  @Autowired
  EduService eduService;

  @Autowired
  ObjectMapper objectMapper;

  @GetMapping("/")
  public String index() {
    return "index.html";
  }

  @GetMapping("/users/{userId}")
  public String getUser(@PathVariable String userId) {
    System.out.println("getUser");
    List<Edu> list = eduService.getEduRecommendList(userId);
    String json = "";
    try {
      json = objectMapper.writeValueAsString(list);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return json;
  }


}
