package de.itemis.webshop.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.itemis.webshop.ServiceApplication;
import de.itemis.webshop.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServiceApplication.class })
@DataJpaTest
public class UserTest {
	private static final String USER_BIFF_TANNEN = "Biff Tannen";
	private static final String USER_EMMET_BROWN = "Emmet Brown";
	private static final String USER_DR_EMMET_BROWN = "Dr. Emmet Brown";
	private static final String USER_LOGIN = "login";
	private static final String USER_PASSWORD = "password";

	private User user_emmet_brown;
	private User user_biff_tannen;
	
	@Autowired
	UserService userService;
	
	@Before
	public void setUp() {
		user_emmet_brown = new User();
		user_emmet_brown.setName(USER_EMMET_BROWN);
		user_emmet_brown.setLogin(USER_LOGIN);
		user_emmet_brown.setPassword(USER_PASSWORD);

		user_biff_tannen = new User();
		user_biff_tannen.setName(USER_BIFF_TANNEN);
		user_biff_tannen.setLogin(USER_LOGIN);
		user_biff_tannen.setPassword(USER_PASSWORD);
	}
	
	@Test
	public void createUser() {
		int userCount = userService.getUserList().size();
		
		// Create 2 new users
		userService.save(user_emmet_brown);
		userService.save(user_biff_tannen);

		User createdUser = userService.findByName(USER_EMMET_BROWN);
		int userCountAfterAdd = userService.getUserList().size();
		assertNotEquals(null, createdUser);
		assertNotEquals(null, createdUser.getId());
		assertEquals(USER_EMMET_BROWN, createdUser.getName());
		assertEquals(USER_LOGIN, createdUser.getLogin());
		assertEquals(USER_PASSWORD, createdUser.getPassword());
		assertEquals(false, createdUser.getPasswordHash().isEmpty());
		assertEquals((userCount + 2), userCountAfterAdd);
	}

	@Test
	public void updateUser() {
		userService.save(user_emmet_brown);
		
		String previousPasswordHash = user_emmet_brown.getPasswordHash();
		
		User createdUser = userService.findByName(USER_EMMET_BROWN);
		createdUser.setName(USER_DR_EMMET_BROWN);
		createdUser.setLogin("brown");
		createdUser.setPassword("MySecretPassword");
		userService.save(createdUser);
		
		User updatedUser = userService.getById(createdUser.getId());
		assertEquals(true, updatedUser.getId().equals(user_emmet_brown.getId()));
		assertEquals(false, updatedUser.getName().equals(USER_EMMET_BROWN));
		assertEquals(false, updatedUser.getLogin().equals(USER_LOGIN));
		assertEquals(false, updatedUser.getPassword().equals(USER_PASSWORD));
		assertEquals(false, updatedUser.getPasswordHash().equals(previousPasswordHash));
	}

	@Test
	public void removeUser() {
		int initialUserCount = userService.getUserList().size();
		userService.save(user_emmet_brown);

		int userCountAfterAdd = userService.getUserList().size();
		assertEquals((initialUserCount + 1), userCountAfterAdd);
		
		userService.delete(user_emmet_brown);
		int userCountAfterDelete = userService.getUserList().size();
		assertEquals(initialUserCount, userCountAfterDelete);
	}
}
