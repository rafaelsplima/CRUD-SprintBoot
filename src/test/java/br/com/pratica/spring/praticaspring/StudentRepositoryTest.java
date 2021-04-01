package br.com.pratica.spring.praticaspring;

import br.com.pratica.spring.praticaspring.model.Student;
import br.com.pratica.spring.praticaspring.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) <-- Testes na própria base de dados
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenCreate_thenPersistData() {
        Student student = new Student("Rafael", "rafaelsplima@gmail.com");
        this.studentRepository.save(student);
        assertThat(student.getId()).isNotNull();
        assertThat(student.getName()).isEqualTo("Rafael");
        assertThat(student.getEmail()).isEqualTo("rafaelsplima@gmail.com");
    }

    @Test
    public void whenDelete_thenRemoveData() {
        Student student = new Student("Rafael", "rafaelsplima@gmail.com");
        this.studentRepository.save(student);
        studentRepository.delete(student);
        assertThat(studentRepository.findById(student.getId())).isEmpty();
    }

    @Test
    public void whenUpdate_ChangeAndPersistData() {
        Student student = new Student("Rafael-1", "rafaelsplima@gmail.com");
        this.studentRepository.save(student);
        student.setName("Rafael-2");
        student.setEmail("rafaelsplima2@gmail.com");
        this.studentRepository.save(student);
        student = this.studentRepository.findById(student.getId()).get();
        assertThat(student.getName()).isEqualTo("Rafael-2");
        assertThat(student.getEmail()).isEqualTo("rafaelsplima2@gmail.com");
    }

    @Test
    public void wheFindByNameIgnoreCaseContaining_thenIgnoreCase() {
        Student student = new Student("rafael", "rafaelsplima@gmail.com");
        Student studentUm = new Student("rafael", "rafaelsplima1@gmail.com");
        this.studentRepository.save(student);
        this.studentRepository.save(studentUm);
        List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("rafael");
        assertThat(studentList.size()).isEqualTo(2);
    }

    @Test
    public void whenNotEmptyName_thenNoConstraintViolations() {
        Student studentAux = new Student("", "rafaelsplima@gmail.com");
        this.studentRepository.save(studentAux);

        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> studentRepository.findByName("rafael"));

        assertTrue(exception.getMessage().contains("Nome não deve estar vazio"));
    }

    @Test
    public void whenNotEmptyEmail_thenNoConstraintViolations() {
        Student studentAux = new Student("Rafael", "");
        this.studentRepository.save(studentAux);

        Exception exception = assertThrows(
                ConstraintViolationException.class,
                () -> studentRepository.findByEmail("rafaelsplima@gmail.com"));

        assertTrue(exception.getMessage().contains("E-mail não deve estar vazio"));
    }
}