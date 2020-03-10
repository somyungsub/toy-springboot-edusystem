package io.sso.sso.edu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
//@TestPropertySource(locations = "classpath:/application-test.properties")
public class EduControllerTest {

  /*
    통합테스트
   */

  @Autowired
  MockMvc mockMvc;

  @Test
  public void getUser() throws Exception {
    mockMvc.perform(get("/users/1/1"))
        .andExpect(status().isOk())
        .andDo(print());

  }

}
