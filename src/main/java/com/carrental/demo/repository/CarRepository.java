package com.carrental.demo.repository;

import java.time.LocalDate;
import java.util.List;

import com.carrental.demo.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long>  {
    
	List<Car> findCarsByReturnDateIsBefore(LocalDate rentDate);
	long countCarsByReturnDateIsBefore(LocalDate rentDate);
	Car findCarByCarId(Long carId);

}

