package br.com.pratica.spring.praticaspring.repository;

import br.com.pratica.spring.praticaspring.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByUsername(String name);
}
