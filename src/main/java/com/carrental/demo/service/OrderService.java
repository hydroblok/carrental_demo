package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.Order;
import com.carrental.demo.domain.RentRecord;
import com.carrental.demo.repository.RentRecordRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Order service to contain the methods for placing orders
 * @author Jimmy Luo
 * @date 20191221
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    private CarService carService;

    @Autowired
    private RentRecordRepository rentRecordRepository;

    private ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    /**
     * Returns order's details
     *
     * @param rentDate - car's rent date
     * @param returnDate - car's return date
     * @param username - the user who rent the car
     * @return - the future of the order details
     */
    public Order createOrder(Long carId, LocalDate rentDate, LocalDate returnDate, String username){
        addRentRecord(carId,rentDate,returnDate,username);
        Order order = new Order();
        Car car = carService.search(carId);
        car = carService.saveCar(car);
        int totalDays = Period.between(rentDate, returnDate).getDays();
        order.setCarId(car.getCarId());
        order.setCarModel(car.getCarModel());
        order.setCity(car.getCity());
        order.setCarColor(car.getColor());
        order.setRentDate(rentDate);
        order.setReturnDate(returnDate);
        order.setTotalDays(totalDays);
        order.setUnitPrice(car.getPrice());
        order.setTotalPrice(car.getPrice() * totalDays);
        order.setOwnedBy(username);
        return order;
    }

    public RentRecord addRentRecord(Long carId, LocalDate rentDate, LocalDate returnDate, String username) {
        return rentRecordRepository.save(new RentRecord(carId, rentDate, returnDate, username));
    }

}
