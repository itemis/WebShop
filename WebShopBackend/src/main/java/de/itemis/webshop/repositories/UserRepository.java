package de.itemis.webshop.repositories;

import org.springframework.data.repository.CrudRepository;

import de.itemis.webshop.domain.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByName(String username);
}
