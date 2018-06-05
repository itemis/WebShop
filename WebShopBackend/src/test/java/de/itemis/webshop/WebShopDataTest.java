package de.itemis.webshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DataApplication.class})
@DataJpaTest
public class WebShopDataTest {
	private static final String USER_PASSWORD = "password";
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void createUser() {
		User user = createTestUser("Marty McFly", "mcfly");
		userRepository.save(user);

		user = createTestUser("Biff Tannen", "tannen");
		userRepository.save(user);

		User createdUser = userRepository.findByName("Marty McFly");
		Iterable<User> users = userRepository.findAll();
		assertNotEquals(null, createdUser);
		assertNotEquals(null, createdUser.getId());
		assertEquals(2, Iterables.size(users));
	}
	
	private User createTestUser(final String name, final String login) {
		User user = new User();
		user.setName(name);
		user.setLogin(login);
		user.setPassword(USER_PASSWORD);

		return user;
	}
}
