package de.itemis.webshop.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.UserRepository;

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createUsers();
	}

	private void createUsers() {
		User user = new User();
		user.setName("Biff Tannen");
		user.setLogin("tannen");
		user.setPassword("password");
		
		userRepository.save(user);
		
		Iterable<User> users = userRepository.findAll();
		
		System.out.println(users);
	}
}
