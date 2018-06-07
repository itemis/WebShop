package de.itemis.webshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	// TODO @Qualifier("passwordEncoder")
	PasswordEncoder encryptionService;
	
	public List<User> getUserList() {
		List<User> userList = Lists.newArrayList(userRepository.findAll()); 

		return userList;
	}

	@Override
    public User getById(Long id) {
        return userRepository.findById(id).get();
    }
	
	@Override
	public User findByName(String username) {
		User user = userRepository.findByName(username);
		
		if (user != null) {
			return user;
		}
		
		return null;
	}

	@Override
	public User save(User user) {
        if (user.getPassword() != null){
            user.setPasswordHash(encryptionService.encode(user.getPassword()));
        }
		
		return userRepository.save(user);
	}

	@Override
	public void delete(User user) {
		userRepository.delete(user);
	}
}
