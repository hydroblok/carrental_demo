package com.carrental.demo.service;

import com.carrental.demo.domain.AvailableCar;
import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.RentRecord;
import com.carrental.demo.repository.CarRepository;
import com.carrental.demo.repository.RentRecordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private RentRecordRepository rentRecordRepository;


    /**
     * Returns car's details to users based on the input rent date and return date
     *
     * @param rentDate - car's rent date
     * @param returnDate - car's return date
     * @return - the available cars
     */
    public List<AvailableCar> search(LocalDate rentDate, LocalDate returnDate) {
        List<AvailableCar> availableCars = new ArrayList<>();
        for(Car car: carRepository.findAll()) {
            List<RentRecord> recordList = rentRecordRepository.findRentRecordByCarId(car.getCarId());
            LocalDate latestReturnDate = recordList.stream().map(RentRecord::getReturnDate).max(LocalDate::compareTo).get();

            boolean isAvailable = false;
            if (rentDate.isAfter(latestReturnDate)) {
                isAvailable = true;
            } else {
                recordList.sort((rc1, rc2) -> {
                    return rc1.getRentDate().compareTo(rc2.getRentDate());
                });
                for(int i = 0; i < recordList.size() - 1; i++) {
                    if (rentDate.isAfter(recordList.get(i).getReturnDate()) && returnDate.isBefore(recordList.get(i+1).getRentDate())) {
                        isAvailable = true;
                        break;
                    }
                }
            }

            if (isAvailable) {
                AvailableCar availableCar = new AvailableCar(car.getCarId(), car.getCarModel(), car.getCity(), car.getColor(), car.getPrice(),rentDate, returnDate);
                availableCars.add(availableCar);
            }
        }
        return availableCars;
    }

    public Car search(Long carId) {
        return carRepository.findCarByCarId(carId);
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

}
