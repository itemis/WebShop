package de.itemis.webshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import de.itemis.webshop.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.login = :login and u.passwordHash = :passwordHash")
	User login(@Param("login") String login, @Param("passwordHash") String passwordHash);
	
	List<User> findAll();
	User findByName(String username);
	
//	List<User> findByLogin();
//	User findByLogin(String userLogin);
	
	@Query("SELECT u FROM User u WHERE u.login = :login")
	User findByLogin(@Param("login") String login);
}
