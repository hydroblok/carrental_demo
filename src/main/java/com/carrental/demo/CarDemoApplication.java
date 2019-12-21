package com.carrental.demo;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.User;
import com.carrental.demo.repository.CarRepository;
import com.carrental.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class CarDemoApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CarDemoApplication.class, args);
	}
	
	/**
	 * Save demo users, courses and students to H2 DB
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner demo(CarRepository repository, UserRepository urepository) {
		return (args) -> {

			LocalDate initialDate = LocalDate.parse("2019-01-01");
			repository.save(new Car(1L, "Toyota Camry","GuangZhou", "Red",  initialDate, initialDate, 100D));
			repository.save(new Car(2L, "Toyota Camry", "ShenZhen", "Black", initialDate, initialDate, 100D));
			repository.save(new Car(3L, "BMW 650", "GuangZhou", "Red", initialDate, initialDate, 200D));
			repository.save(new Car(4L, "BMW 650", "ShenZhen", "Black", initialDate, initialDate, 200D));

			// Create users with BCrypt encoded password (user/user, admin/admin)
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN");
			urepository.save(user1);
			urepository.save(user2); 
		};
	}
}
