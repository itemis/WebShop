package de.itemis.webshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Iterables;

import de.itemis.webshop.domain.Address;
import de.itemis.webshop.domain.User;
import de.itemis.webshop.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DataApplication.class})
@DataJpaTest
public class WebShopDataTest {
	private static final String USER_PASSWORD = "password";

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRepository addressRepository;
	
	@Test
	public void createUser() {
		int userCount = Iterables.size(userRepository.findAll());
		
		User user = createTestUser("Lorraine Baines", "baines");
		userRepository.save(user);

		user = createTestUser("Biff Tannen", "tannen");
		userRepository.save(user);

		User createdUser = userRepository.findByName("Lorraine Baines");
		int userCountAfterAdd = Iterables.size(userRepository.findAll());
		assertNotEquals(null, createdUser);
		assertNotEquals(null, createdUser.getId());
		assertEquals((userCount + 2), userCountAfterAdd);
	}

	@Test
	public void createUserAddress() {
		List<Address> addressList = new ArrayList<>();
		int addressCount = Iterables.size(addressRepository.findAll());
		
		User user = createTestUser("Marty McFly", "mcfly");

		Address address = new Address();
		address.setUser(user);
		address.setStreet("Schuhmacherort 2");
		address.setZipCode("25746");
		address.setCity("Heide");
		addressList.add(address);

		address = new Address();
		address.setUser(user);
		address.setStreet("Osterende 1A");
		address.setZipCode("25881");
		address.setCity("Tating");
		address.setPreferredAddress(true);
		addressList.add(address);
		
		user.setAddresses(addressList);
		userRepository.save(user);
		
		User createdUser = userRepository.findByName("Marty McFly");
		List<Address> addresses = createdUser.getAddresses();
		assertEquals((addressCount + 2), addresses.size());
		assertEquals(false, addresses.get(0).isPreferredAddress());
		assertEquals(true, addresses.get(1).isPreferredAddress());
	}

	@Test
	public void removeUserAddress() {
		User user = createTestUserWithAddress("George McFly", "george", "Hill Valley");
		userRepository.save(user);
		
		User createdUser = userRepository.findByName("George McFly");
		createdUser.setAddresses(new ArrayList<>());
		userRepository.save(createdUser);
		
		User userWithoutAddress = userRepository.findByName("George McFly");
		List<Address> addresses = userWithoutAddress.getAddresses();
		assertEquals(0, addresses.size());
	}

	@Test
	public void removeUser() {
		int initialUserCount = Iterables.size(userRepository.findAll());
		int initialAddressCount = Iterables.size(addressRepository.findAll());

		User user = createTestUserWithAddress("Doc Brown", "brown", "Hill Valley Downtown");
		userRepository.save(user);
		int userCountAfterAdd = Iterables.size(userRepository.findAll());
		int addressCountAfterAdd = Iterables.size(addressRepository.findAll());
		assertEquals((initialUserCount + 1), userCountAfterAdd);
		assertEquals((initialAddressCount + 1), addressCountAfterAdd);
		
		userRepository.delete(user);
		int userCountAfterDelete = Iterables.size(userRepository.findAll());
		int addressCountAfterDelete = Iterables.size(addressRepository.findAll());
		assertEquals(initialUserCount, userCountAfterDelete);
		assertEquals(initialAddressCount, addressCountAfterDelete);
	}
	
	private User createTestUser(final String name, final String login) {
		User user = new User();
		user.setName(name);
		user.setLogin(login);
		user.setPassword(USER_PASSWORD);

		return user;
	}

	private User createTestUserWithAddress(final String name, final String login, final String city) {
		List<Address> addressList = new ArrayList<>();
		
		User user = new User();
		user.setName(name);
		user.setLogin(login);
		user.setPassword(USER_PASSWORD);
		
		Address address = new Address();
		address.setUser(user);
		address.setStreet("Main Road");
		address.setZipCode("12345");
		address.setCity(city);
		addressList.add(address);
		
		user.setAddresses(addressList);

		return user;
	}
}
