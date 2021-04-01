package br.com.pratica.spring.praticaspring;

import br.com.pratica.spring.praticaspring.model.Student;
import br.com.pratica.spring.praticaspring.repository.StudentRepository;
import br.com.pratica.spring.praticaspring.util.PasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().basicAuthentication("rafaelsplima", "aplicacao");
        }
    }

    @Test
    public void whenListStudentUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized () {
        System.out.println("Porta: " + port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students/", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenGetStudentByIdUsingIncorrectUsernameAndPassword_thenReturnStatusCode401Unauthorized () {
        System.out.println("Porta: " + port);
        restTemplate = restTemplate.withBasicAuth("1", "1");
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students/1", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(401);
    }

    @Test
    public void whenListStudentUsingCorrectUsernameAndPassword_thenReturnStatusCode200 () {
        asList (new Student(1L,"Rafael", "rafaelsplima@gmail.com"),
                new Student(2L,"Jessica", "jessica@gmail.com"),
                new Student(3L,"Augusto", "augusto@gmail.com")
        );

        BDDMockito.when(studentRepository.findAll()).thenReturn(asList());
        ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students/", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void whenDeleteStudent_theReturnStatusCode200() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth("rafaelsplima",PasswordEncoder.encrypt());

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        BDDMockito.doNothing().when(studentRepository).deleteById((1L));


        ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE, entity, String.class, 1L);

        assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
    }
}
