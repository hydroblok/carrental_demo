package com.carrental.demo.repository;

import com.carrental.demo.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long>  {

	Car findCarByCarId(Long carId);

}

