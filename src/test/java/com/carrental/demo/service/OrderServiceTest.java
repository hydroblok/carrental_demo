package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.domain.Order;
import com.carrental.demo.domain.User;
import com.carrental.demo.repository.OrderRepository;
import lombok.Setter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private CarService carService;

    @Test
    public void createOrderTest() throws ExecutionException, InterruptedException {

        orderService.setCARS_AVAILABLE_NUMBER("CARS_AVAILABLE_NUMBER");
        orderService.setCARS_TOTAL_NUMBER(4L);
        Long carId = 1000L;
        String carModel = "Toyota Camry";
        Car car = new Car();

        String username = "jimmy";
        User user = new User(username, "password", "user");
        car.setCarId(carId);
        car.setCarModel(carModel);
        car.setPrice(100D);
        when(carService.search(carId)).thenReturn(car);
        when(carService.saveCar(car)).thenReturn(car);
        Order order = orderService.createOrder(carId, LocalDate.parse("2019-01-01"), LocalDate.parse("2019-01-02"), username).get();
        assertEquals(carModel, order.getCarModel());
        assertEquals(username, order.getOwnedBy());
    }
}
