package de.itemis.webshop.services;

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

import de.itemis.webshop.ServiceApplication;
import de.itemis.webshop.domain.Address;
import de.itemis.webshop.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServiceApplication.class })
@DataJpaTest
public class WebShopServiceTest {
	private static final String USER_PASSWORD = "password";
	
	@Autowired
	UserService userService;

	@Autowired
	AddressService addressService;
	
	@Test
	public void createUser() {
		int userCount = userService.getUserList().size();
		
		User user = createTestUser("Lorraine Baines", "baines");
		userService.save(user);

		user = createTestUser("Biff Tannen", "tannen");
		userService.save(user);

		User createdUser = userService.findByName("Lorraine Baines");
		int userCountAfterAdd = userService.getUserList().size();
		assertNotEquals(null, createdUser);
		assertNotEquals(null, createdUser.getId());
		assertEquals((userCount + 2), userCountAfterAdd);
	}

	@Test
	public void createUserAddress() {
		List<Address> addressList = new ArrayList<>();
		int addressCount = Iterables.size(addressService.getAddressList());
		
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
		userService.save(user);
		
		User createdUser = userService.findByName("Marty McFly");
		List<Address> addresses = createdUser.getAddresses();
		assertEquals((addressCount + 2), addresses.size());
		assertEquals(false, addresses.get(0).isPreferredAddress());
		assertEquals(true, addresses.get(1).isPreferredAddress());
	}
	
	@Test
	public void removeUserAddress() {
		User user = createTestUserWithAddress("George McFly", "george", "Hill Valley");
		userService.save(user);
		
		User createdUser = userService.findByName("George McFly");
		createdUser.setAddresses(new ArrayList<>());
		userService.save(createdUser);
		
		User userWithoutAddress = userService.findByName("George McFly");
		List<Address> addresses = userWithoutAddress.getAddresses();
		assertEquals(0, addresses.size());
	}
	
	@Test
	public void removeUser() {
		int initialUserCount = Iterables.size(userService.getUserList());
		int initialAddressCount = Iterables.size(addressService.getAddressList());

		User user = createTestUserWithAddress("Doc Brown", "brown", "Hill Valley Downtown");
		userService.save(user);
		int userCountAfterAdd = Iterables.size(userService.getUserList());
		int addressCountAfterAdd = Iterables.size(addressService.getAddressList());
		assertEquals((initialUserCount + 1), userCountAfterAdd);
		assertEquals((initialAddressCount + 1), addressCountAfterAdd);
		
		userService.delete(user);
		int userCountAfterDelete = Iterables.size(userService.getUserList());
		int addressCountAfterDelete = Iterables.size(addressService.getAddressList());
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
