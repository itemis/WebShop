package de.itemis.webshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findByName(String username) {
		User user = userRepository.findByName(username);
		
		if (user != null) {
			return user;
		}
		
		return null;
	}
}
