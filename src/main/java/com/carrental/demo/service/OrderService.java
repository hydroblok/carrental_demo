package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.Order;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

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

    @Async("placeOrderExecutor")
    public Future<Order> createOrder(Long carId, LocalDate rentDate, LocalDate returnDate, String username){

        //Handle the concurrency issue
        if(map.get(CARS_AVAILABLE_NUMBER) == null) {
            map.put(CARS_AVAILABLE_NUMBER, CARS_TOTAL_NUMBER);
        }

        carsNumberBeforeOrder.set(map.get(CARS_AVAILABLE_NUMBER));

        if(carsNumberBeforeOrder.get() == 0) {
            return null;
        }

        try{
            map.put(CARS_AVAILABLE_NUMBER, carService.countCars(rentDate));
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
            map.put(CARS_AVAILABLE_NUMBER, carsNumberBeforeOrder.get());
            return null;
        }

    }

}
