package de.itemis.webshop.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.itemis.webshop.ServiceApplication;
import de.itemis.webshop.domain.Address;
import de.itemis.webshop.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ServiceApplication.class })
@DataJpaTest
public class AddressTest {
	private User user;
	List<Address> addressList;
	
	@Autowired
	AddressService addressService;

	@Autowired
	UserService userService;
	
	@Before
	public void setUp() {
		user = new User();
		user.setName("Marty McFly");
		addressList = new ArrayList<>();
	}
		
	@Test
	public void createUserAddress() {
		int addressCount = addressService.getAddressList().size();

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
		
		User createdUser = userService.getById(user.getId());
		List<Address> addresses = createdUser.getAddresses();
		assertEquals((addressCount + 2), addresses.size());
		assertEquals(false, addresses.get(0).isPreferredAddress());
		assertEquals(true, addresses.get(1).isPreferredAddress());
	}
	
	@Test
	public void removeUserAddress() {
		Address address = new Address();
		address.setUser(user);
		address.setStreet("Schuhmacherort 2");
		address.setZipCode("25746");
		address.setCity("Heide");
		addressList.add(address);
		
		user.setAddresses(addressList);
		userService.save(user);
		
		User createdUser = userService.getById(user.getId());
		createdUser.setAddresses(new ArrayList<>());
		userService.save(createdUser);
		
		User userWithoutAddress = userService.getById(user.getId());
		List<Address> addresses = userWithoutAddress.getAddresses();
		assertEquals(0, addresses.size());
	}
	
	@Test
	public void removeUserAndAddress() {
		int initialAddressCount = addressService.getAddressList().size();
		
		Address address = new Address();
		address.setUser(user);
		address.setStreet("Schuhmacherort 2");
		address.setZipCode("25746");
		address.setCity("Heide");
		addressList.add(address);
		
		user.setAddresses(addressList);
		
		userService.save(user);
		int addressCountAfterAdd = addressService.getAddressList().size();
		assertEquals((initialAddressCount + 1), addressCountAfterAdd);
		
		userService.delete(user);
		int addressCountAfterDelete = addressService.getAddressList().size();
		assertEquals(initialAddressCount, addressCountAfterDelete);
	}
}
