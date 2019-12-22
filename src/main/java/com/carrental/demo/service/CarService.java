package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Async("searchExecutor")
    public Future<List<Car>> search(LocalDate rentDate, LocalDate returnDate) {

        try{
            List<Car> availableCars = carRepository.findCarsByReturnDateIsBefore(rentDate);
            availableCars.forEach( x -> {x.setRentDate(rentDate);});
            availableCars.forEach( x -> {x.setReturnDate(returnDate);});
            return new AsyncResult<>(availableCars);
        } catch (Exception e){
            return null;
        }
    }

    public Car search(Long carId) {
        return carRepository.findCarByCarId(carId);
    }

    public Long countCars(LocalDate rentDate) {
        return carRepository.countCarsByReturnDateIsBefore(rentDate);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

}
