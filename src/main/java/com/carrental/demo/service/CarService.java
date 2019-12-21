package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> search(LocalDate rentDate, LocalDate returnDate) {
        List<Car> availableCars = carRepository.findCarsByReturnDateIsBefore(rentDate);
        availableCars.forEach( x -> {x.setRentDate(rentDate);});
        availableCars.forEach( x -> {x.setReturnDate(returnDate);});
        return availableCars;
    }

    public Car search(Long carId) {
        return carRepository.findCarByCarId(carId);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

}
