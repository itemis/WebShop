package de.itemis.webshop.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import de.itemis.webshop.domain.User;
import de.itemis.webshop.security.UserDetailsImpl;

@Component
public class UserToUserDetailsConverter implements Converter<User, UserDetails> {
	@Override
	public UserDetails convert(User user) {
		UserDetailsImpl userDetails = new UserDetailsImpl();
		
		if (user != null) {
			userDetails.setUsername(user.getLogin());
			userDetails.setPassword(user.getPassword());
			userDetails.setEnabled(user.isEnabled());
		}
		
		return userDetails;
	}
}
