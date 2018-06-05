package de.itemis.webshop.services;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.itemis.webshop.ServiceApplication;
import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServiceApplication.class })
@DataJpaTest
public class WebShopServiceTest {
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;
	
	@Test
	public void findUserByName() {
		User user = new User();
		user.setName("Marty McFly");
		user.setLogin("mcfly");
		user.setPassword("password");
		userRepository.save(user);
		
		User createdUser = userService.findByName("Marty McFly");
		assertNotEquals(null, createdUser);
		assertNotEquals(null, createdUser.getId());
	}
}
