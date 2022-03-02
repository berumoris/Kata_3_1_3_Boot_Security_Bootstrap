package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import java.util.HashSet;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserServiceImp userService, RoleServiceImp roleService) {
		return args -> {
			roleService.createRole(new Role(null, "ROLE_USER"));
			roleService.createRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(new User(null, "Admin", "Adminov", (byte) 33, "admin@mail.ru", "admin", new HashSet<>()));
			userService.saveUser(new User(null, "User", "Userov", (byte) 21, "user@ya.ru", "user", new HashSet<>()));

			userService.setUserRole("admin@mail.ru", "ROLE_ADMIN");
			userService.setUserRole("admin@mail.ru", "ROLE_USER");
			userService.setUserRole("user@ya.ru", "ROLE_USER");

		};
	}

}
