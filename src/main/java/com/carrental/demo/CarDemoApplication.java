package com.carrental.demo;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.RentRecord;
import com.carrental.demo.domain.User;
import com.carrental.demo.repository.CarRepository;
import com.carrental.demo.repository.RentRecordRepository;
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
	public CommandLineRunner demo(CarRepository repository, RentRecordRepository rentRecordRepository, UserRepository urepository) {
		return (args) -> {

			LocalDate initRentDate = LocalDate.parse("2019-01-01");
			LocalDate initReturnDate = LocalDate.parse("2019-01-02");
			repository.save(new Car(1L, "Toyota Camry","GuangZhou", "Red",  100D));
			repository.save(new Car(2L, "Toyota Camry", "ShenZhen", "Black", 100D));
			repository.save(new Car(3L, "BMW 650", "GuangZhou", "Red", 200D));
			repository.save(new Car(4L, "BMW 650", "ShenZhen", "Black", 200D));

			rentRecordRepository.save(new RentRecord(1L, initRentDate, initReturnDate, "admin"));
			rentRecordRepository.save(new RentRecord(2L, initRentDate, initReturnDate, "admin"));
			rentRecordRepository.save(new RentRecord(3L, initRentDate, initReturnDate, "admin"));
			rentRecordRepository.save(new RentRecord(4L, initRentDate, initReturnDate, "admin"));
			// Create users with BCrypt encoded password (user/user, admin/admin)
			User user1 = new User("user", "$2a$06$3jYRJrg0ghaaypjZ/.g4SethoeA51ph3UD4kZi9oPkeMTpjKU5uo6", "USER");
			User user2 = new User("admin", "$2a$08$bCCcGjB03eulCWt3CY0AZew2rVzXFyouUolL5dkL/pBgFkUH9O4J2", "ADMIN");
			urepository.save(user1);
			urepository.save(user2); 
		};
	}
}
