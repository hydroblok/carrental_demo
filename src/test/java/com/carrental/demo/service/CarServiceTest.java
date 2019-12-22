package com.carrental.demo.service;

import com.carrental.demo.domain.Car;
import com.carrental.demo.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarService carService;

    private LocalDate rentDate = LocalDate.of(2018, 12, 10);
    private LocalDate returnDate = LocalDate.of(2018, 12, 15);

    @Test
    public void searchByDate() {
        Car car1 = new Car(1001L, "Toyota Camry", "GZ", "Red", rentDate, returnDate, 100D );
        Car car2 = new Car(1002L, "Toyota Camry", "GZ", "Red", rentDate, returnDate, 100D );
        carRepository.save(car1);
        carRepository.save(car2);
        LocalDate rentDate1 = returnDate.plusDays(1);
        LocalDate returnDate1 = returnDate.plusDays(2);
        Future<List<Car>> carsFuture = carService.search(rentDate1, returnDate1);
        if (carsFuture.isDone()){
            List<Car> cars = null;
            try {
                cars = carsFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            assertEquals(2, cars.size());
        }

    }

    @Test
    public void searchByCarId() {
        Car car3 = new Car(1003L, "Toyota Camry", "GZ", "Red", rentDate, returnDate, 100D );
        carRepository.save(car3);
        assertNotNull(carService.search(1003L));
    }
}