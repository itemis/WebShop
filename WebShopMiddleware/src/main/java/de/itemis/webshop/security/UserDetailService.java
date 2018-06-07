package de.itemis.webshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.itemis.webshop.converter.UserToUserDetailsConverter;
import de.itemis.webshop.services.UserService;

//@Service("userDetailsService")
@Service
public class UserDetailService implements UserDetailsService {
	@Autowired
	UserService userService;
	
	@Autowired
	UserToUserDetailsConverter userConverter;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userConverter.convert(userService.findByName(username));
	}
}
