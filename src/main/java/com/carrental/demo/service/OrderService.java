package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class OrderService {

    @Autowired
    private CarService carService;

    public Order createOrder(Long carId, LocalDate rentDate, LocalDate returnDate, String username){
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
        return order;
    }

}
