package de.itemis.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.itemis.webshop.services", "de.itemis.webshop.security", "de.itemis.webshop.config", "de.itemis.webshop.converter"})
//@ComponentScan(basePackages = {"de.itemis.webshop"})
public class ServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}
}