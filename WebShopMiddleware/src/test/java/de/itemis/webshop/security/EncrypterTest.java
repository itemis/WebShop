package de.itemis.webshop.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.itemis.webshop.ServiceApplication;
import de.itemis.webshop.domain.User;
import de.itemis.webshop.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServiceApplication.class })
@DataJpaTest
public class EncrypterTest {
	private static final String USER_PASSWORD_TO_ENCODE = "mySecretPassword";

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	UserService userService;
	
	@Test
	public void checkEncryptionService() {
		String passwordHash = encryptionService.encodePassword(USER_PASSWORD_TO_ENCODE);
		boolean passwordMatch = encryptionService.checkPassword(USER_PASSWORD_TO_ENCODE, passwordHash);
		
		assertNotNull(passwordHash);
		assertEquals(true, passwordMatch);
	}

	@Test
	public void checkPasswordHash() {
		User user = new User();
		user.setName("Emmet Brown");
		user.setPassword(USER_PASSWORD_TO_ENCODE);
		userService.save(user);
		
		User createdUser = userService.findByName("Emmet Brown");
		assertNotNull(createdUser.getPasswordHash());
		assertEquals(true, encryptionService.checkPassword(USER_PASSWORD_TO_ENCODE, user.getPasswordHash()));
	}
}
