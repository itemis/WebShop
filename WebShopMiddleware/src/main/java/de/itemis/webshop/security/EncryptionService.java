package de.itemis.webshop.security;

public interface EncryptionService {
	String encodePassword(String password);
	boolean checkPassword(String rawPassword, String encodedPassword);
}
