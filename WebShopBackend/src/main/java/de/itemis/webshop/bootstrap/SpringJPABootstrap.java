package de.itemis.webshop.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import de.itemis.webshop.domain.Address;
import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.AddressRepository;
import de.itemis.webshop.repositories.UserRepository;

@Component
public class SpringJPABootstrap implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		createUsers();
	}

	private void createUsers() {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		List<Address> addresses = new ArrayList<>();
		
		User user = new User();
		user.setName("myuser");
		user.setLogin("myuser");
		user.setPassword("password");
		user.setPasswordHash(user.getPassword());
		//user.setPasswordHash(encoder.encode(user.getPassword()));
		
		Address address = new Address();
		address.setUser(user);
		address.setStreet("Osterende 1A");
		address.setZipCode("25881");
		address.setCity("Tating");
		address.setPreferredAddress(true);
		addresses.add(address);
		
		address = new Address();
		address.setUser(user);
		address.setStreet("Schuhmacherort 2");
		address.setZipCode("25746");
		address.setCity("Heide");
		address.setPreferredAddress(false);
		addresses.add(address);
		
		user.setAddresses(addresses);
		userRepository.save(user);
		
		user = new User();
		user.setName("Marty McFly");
		user.setLogin("fly");
		user.setPassword("password");
		user.setPasswordHash(user.getPassword());
		//user.setPasswordHash(encoder.encode(user.getPassword()));
		userRepository.save(user);
		System.out.println("test");
	}
}
