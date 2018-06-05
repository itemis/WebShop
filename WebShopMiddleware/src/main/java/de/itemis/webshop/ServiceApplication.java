package de.itemis.webshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"de.itemis.webshop.services"})
public class ServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DataApplication.class, args);
	}
}