package br.com.pratica.spring.praticaspring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.pratica.spring.praticaspring.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
	
	List<Student> findByName(String name);

	List<Student> findByEmail(String Email);

	List<Student> findByNameIgnoreCaseContaining(String name);


}
