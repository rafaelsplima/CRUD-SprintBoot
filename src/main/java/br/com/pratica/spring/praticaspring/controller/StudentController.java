package br.com.pratica.spring.praticaspring.controller;

import br.com.pratica.spring.praticaspring.error.ResourceNotFoundException;
import br.com.pratica.spring.praticaspring.model.Student;
import br.com.pratica.spring.praticaspring.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("v1")
public class StudentController {

    private final StudentRepository studentDAO;

    @Autowired
    public StudentController(StudentRepository studentDAO) {
        this.studentDAO = studentDAO;
    }

    @GetMapping(path = "protected/students")
    public ResponseEntity<?> listAll(Pageable pageable) {
        System.out.println("----------------------------------------------------");
        System.out.println("List All");
        System.out.println(studentDAO.findAll(pageable));
        System.out.println("-----------------------------------------------------");

        return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> student = studentDAO.findById(id);

        if (!student.isPresent())
            throw new ResourceNotFoundException("Student not found for id: " + id);

        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @GetMapping(path = "protected/students/findByName/{name}")
    public ResponseEntity<?> findStudentsByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.POST)
    @PostMapping(path = "admin/students")
    public ResponseEntity<?> save(@Valid @RequestBody Student student) {
        return new ResponseEntity<>(studentDAO.save(student), HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.DELETE)
    @DeleteMapping(path = "admin/students/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        studentDAO.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@RequestMapping(method = RequestMethod.PUT)
    @PutMapping(path = "admin/students")
    public ResponseEntity<?> update(@RequestBody Student student) {
        // student com id - atualizado
        // student sem id - criado
        studentDAO.save(student);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
