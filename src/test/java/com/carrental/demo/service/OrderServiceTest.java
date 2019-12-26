package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.Order;
import com.carrental.demo.domain.RentRecord;
import com.carrental.demo.domain.User;
import com.carrental.demo.repository.OrderRepository;
import com.carrental.demo.repository.RentRecordRepository;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CarService carService;

    @Mock
    private RentRecordRepository rentRecordRepository;


    @Test
    public void createOrderTest() throws ExecutionException, InterruptedException {


        Long carId = 1000L;
        String carModel = "Toyota Camry";
        Car car = new Car();

        String username = "jimmy";
        User user = new User(username, "password", "user");
        car.setCarId(carId);
        car.setCarModel(carModel);
        car.setPrice(100D);
        LocalDate rentDate = LocalDate.parse("2019-01-01");
        LocalDate returnDate = LocalDate.parse("2019-01-02");
        when(carService.search(carId)).thenReturn(car);
        when(carService.saveCar(car)).thenReturn(car);
        LocalDate newRentDate = returnDate.plusDays(1);
        LocalDate newReturnDate = returnDate.plusDays(2);
        Order order = orderService.createOrder(carId, newRentDate, newReturnDate, username);
        assertEquals(carModel, order.getCarModel());
        assertEquals(username, order.getOwnedBy());
    }
}
