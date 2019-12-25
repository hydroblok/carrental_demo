package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Contains method to asynchronously show the car's details to customers
 * @author Jimmy Luo
 * @date 20191221
 */
@Slf4j
@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    /**
     * Returns car's details to users based on the input rent date and return date
     *
     * @param rentDate - car's rent date
     * @param returnDate - car's return date
     * @return - the future of the available cars
     */
    @Async("searchExecutor")
    public Future<List<Car>> search(LocalDate rentDate, LocalDate returnDate) {

        try{
            log.info("[Thread ID:{}] Search available cars before rent date {}", Thread.currentThread().getId(), rentDate);
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
