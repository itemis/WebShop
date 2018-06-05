package de.itemis.webshop.services;

import de.itemis.webshop.domain.User;

public interface UserService {
	User findByName(String username);
}
