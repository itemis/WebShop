package de.itemis.webshop.services;

import java.util.List;

import de.itemis.webshop.domain.User;

public interface UserService {
	List<User> getUserList();
	User findByName(String username);
	User save(User user);
	void delete(User user);
}
