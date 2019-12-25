package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.Order;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

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

    @Value("${cars.total.number}")
    @Setter
    private Long CARS_TOTAL_NUMBER;

    @Value("${cars.available.number}")
    @Setter
    private String CARS_AVAILABLE_NUMBER;

    private ThreadLocal<Long> carsNumberBeforeOrder = new ThreadLocal<>();

    private ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

    /**
     * Returns order's details
     *
     * @param rentDate - car's rent date
     * @param returnDate - car's return date
     * @param username - the user who rent the car
     * @return - the future of the order details
     */
    @Async("placeOrderExecutor")
    public Future<Order> createOrder(Long carId, LocalDate rentDate, LocalDate returnDate, String username){

        //Handle the concurrency issue
        if(map.get(CARS_AVAILABLE_NUMBER) == null) {
            log.info("Initialize a flag in jvm memory to save the available car's number:{}", CARS_TOTAL_NUMBER);
            map.put(CARS_AVAILABLE_NUMBER, CARS_TOTAL_NUMBER);
        }

        log.info("[Thread ID:{}, username:{}] Car number before placing order is :{}", Thread.currentThread().getId(), username, map.get(CARS_AVAILABLE_NUMBER));
        carsNumberBeforeOrder.set(map.get(CARS_AVAILABLE_NUMBER));

        //Reject to place order once there is no available car
        if(carsNumberBeforeOrder.get() == 0) {
            return null;
        }

        try{
            //Update the new available car's number to the flag
            map.put(CARS_AVAILABLE_NUMBER, carsNumberBeforeOrder.get() - 1);
            Order order = new Order();
            Car car = carService.search(carId);
            car.setRentDate(rentDate);
            car.setReturnDate(returnDate);
            car = carService.saveCar(car);
            int totalDays = Period.between(rentDate, returnDate).getDays();
            order.setCarId(car.getCarId());
            order.setCarModel(car.getCarModel());
            order.setCity(car.getCity());
            order.setCarColor(car.getColor());
            order.setRentDate(car.getRentDate());
            order.setReturnDate(car.getReturnDate());
            order.setTotalDays(totalDays);
            order.setUnitPrice(car.getPrice());
            order.setTotalPrice(car.getPrice() * totalDays);
            order.setOwnedBy(username);
            return new AsyncResult<>(order);
        } catch (Exception e) {
            //Rollback the available number if any exception happens
            map.put(CARS_AVAILABLE_NUMBER, carsNumberBeforeOrder.get() + 1);
            return null;
        }

    }

}
